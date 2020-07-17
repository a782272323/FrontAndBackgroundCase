import request from '@/utils/request'

/**
 * 登录接口
  */
export function login(data) {
    return request({
        url: '/v1/user/login',
        method: 'post',
        params: { username: data.username, password: data.password }
    })
}

// 跳转首页
export function getInfo(token) {
    return request({
        url: '/v1/user/info',
        method: 'get',
        params: { token }
    })
}

// 注销
export function logout() {
    return request({
        url: '/v1/user/logout',
        method: 'post'
    })
}

// 请求后端路由数据
export function getRouterMenu(token) {
  return request({
    url: '/v1/user/router',
    method: 'get'
  })
}
