import request from '@/utils/request'
import qs from 'qs'

export function onlineUsers(query) {
  return request({
    url: '/UMS/user/online/list',
    method: 'get',
    params: query
  })
}

export function forceLogout(logoutDTO) {
  return request({
    url: '/UMS/user/online/logout',
    method: 'post',
    headers: { 'content-type': 'application/x-www-form-urlencoded' },
    data: qs.stringify(logoutDTO)
  })
}

// 下面是json格式数据
// export function forceLogout(logoutDTO) {
//   return request({
//     url: '/UMS/user/online/logout',
//     method: 'post',
//     data: logoutDTO
//   })
// }

export function logout(token) {
  return request({
    url: '/UMS/user/online/logout/' + token,
    method: 'post'
  })
}
