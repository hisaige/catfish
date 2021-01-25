import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/UMS/user/login',
    method: 'post',
    data
  })
}

// 获取用户信息
export function getInfo() {
  return request({
    url: '/UMS/user/info',
    method: 'get'
    // params: { token }
  })
}

// 登出
export function logout(token) {
  return request({
    url: '/UMS/user/online/logout/' + token,
    method: 'post'
  })
}

// 分页查询用户列表
export function fetchPage(PageQuery) {
  return request({
    url: '/UMS/user/search',
    method: 'get',
    params: PageQuery
  })
}

// 新增用户信息
export function addUser(userDTO) {
  return request({
    url: '/UMS/user/register',
    method: 'post',
    data: userDTO

  })
}

// 编辑用户信息
export function editUser(userDTO) {
  return request({
    url: '/UMS/user/update',
    method: 'put',
    data: userDTO
  })
}

// 删除
export function deleteUser(id) {
  return request({
    url: '/UMS/user/del/' + id,
    method: 'delete'
  })
}
