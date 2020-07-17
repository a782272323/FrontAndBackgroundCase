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

export function getInfo(token) {
    return request({
        url: '/v1/user/info',
        method: 'get',
        params: { token }
    })
}

export function logout() {
    return request({
        url: '/v1/user/logout',
        method: 'post'
    })
}
