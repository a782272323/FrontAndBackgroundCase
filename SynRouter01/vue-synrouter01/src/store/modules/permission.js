// 权限管理模块
import { asyncRoutes, constantRoutes } from '@/router'
// 新增的导入
import { getRouterMenu } from '@/api/user'
import Layout from '@/layout'

/**
 * Use meta.role to determine if the current user has permission
 * 使用 meta.role ，以确定当前用户是否具有权限,判断是否与当前用户权限匹配
 * @param roles 用户拥有角色
 * @param route 待判定的路由
 */
function hasPermission(roles, route) {
  // 如果当前路由有roles 字段则需要判断用户访问权限
  if (route.meta && route.meta.roles) {
    // 若用户拥有的角色中有被包含在待定路由角色表中的则拥有访问权限
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    // 没有设置roles 则无需判定即可访问
    return true
  }
}

/**
 * 自定义的递归遍历，
 * 拼装获取到的后端数据成前端路由格式的数据
 * @param routers
 * @returns {*}
 */
export const filterAsyncRouter = (routers) => {
  // 遍历后端传来的路由数据，转换为组件对象
  const accessRouters = routers.filter(router => {
    // component 组件的特殊处理
    if (router.component) {
      // 如果 component 为 Layout
      if (router.component === 'Layout') {
        router.component = Layout
      } else {
        // 如果 component 不为 Layout
        const component = router.component
        console.log('子路由component = ' + component)
        router.component = import(`@views${component}`)
      }
    }
    // 判断是否存在子路由（子菜单）
    if (router.children && router.children.length) {
      router.children = filterAsyncRouter(router.children)
    }
    return true
  })
  return accessRouters
}

/**
 * 通过递归过滤异步路由表
 * @param routes 待过滤路由表,首次传入的就是 AsyncRoutes
 * @param roles 用户拥有角色
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    // 复制一份
    const tmp = { ...route }
    // 如果用户有访问权限则加入结果路由表
    if (hasPermission(roles, tmp)) {
      // 如果存在子路由则递归过滤之
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  // 完整路由表
  routes: [],
  // 用户可访问路由表
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    // routes 用户可以访问的权限
    state.addRoutes = routes
    // 完整的路由表
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, roles) {
    // 异步操作
    return new Promise(((resolve, reject) => {
      console.log('获取后端路由数据')
      getRouterMenu().then(response => {
        // es6 语法的 解构赋值
        const { getRouterTree } = response.data
        console.log(' getRouterTree ' + JSON.stringify(getRouterTree))
        // 渲染前先清一遍路由
        commit('SET_ROUTES', '')
        // 调用上面自定义的方法，把后端数据渲染成vue需要的路由格式
        const asyncRouter = filterAsyncRouter(getRouterTree)
        commit('SET_ROUTES', asyncRouter)
        resolve(asyncRouter)
      }).catch(error => {
        reject(error)
      })
    }))
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}


































// import { asyncRoutes, constantRoutes } from '@/router'
//
// /**
//  * Use meta.role to determine if the current user has permission
//  * 使用 meta.role 确定当前用户是否具有权限
//  * @param roles
//  * @param route
//  */
// function hasPermission(roles, route) {
//   if (route.meta && route.meta.roles) {
//     return roles.some(role => route.meta.roles.includes(role))
//   } else {
//     return true
//   }
// }
//
// /**
//  * Filter asynchronous routing tables by recursion
//  * 通过递归过滤异步路由表
//  * @param routes asyncRoutes
//  * @param roles
//  */
// export function filterAsyncRoutes(routes, roles) {
//   const res = []
//
//   routes.forEach(route => {
//     const tmp = { ...route }
//     if (hasPermission(roles, tmp)) {
//       if (tmp.children) {
//         tmp.children = filterAsyncRoutes(tmp.children, roles)
//       }
//       res.push(tmp)
//     }
//   })
//
//   return res
// }
//
// const state = {
//   routes: [],
//   addRoutes: []
// }
//
// const mutations = {
//   SET_ROUTES: (state, routes) => {
//     state.addRoutes = routes
//     state.routes = constantRoutes.concat(routes)
//   }
// }
//
// const actions = {
//   generateRoutes({ commit }, roles) {
//     // 异步操作
//     return new Promise(resolve => {
//       let accessedRoutes
//       if (roles.includes('admin')) {
//         accessedRoutes = asyncRoutes || []
//       } else {
//         accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
//       }
//       commit('SET_ROUTES', accessedRoutes)
//       resolve(accessedRoutes)
//     })
//   }
// }
//
// export default {
//   namespaced: true,
//   state,
//   mutations,
//   actions
// }
