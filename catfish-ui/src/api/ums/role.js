import request from '@/utils/request'

// 分页查询角色列表
export function fetchPage(PageQuery) {
  return request({
    url: '/UMS/role/search',
    method: 'get',
    params: PageQuery
  })
}

// 新增角色信息
export function addRole(roleDTO) {
  return request({
    url: '/UMS/role/create',
    method: 'post',
    data: roleDTO

  })
}

// 编辑角色信息
export function editRole(roleDTO) {
  return request({
    url: '/UMS/role/update',
    method: 'put',
    data: roleDTO
  })
}

// 删除
export function deleteRole(id) {
  return request({
    url: '/UMS/role/del/' + id,
    method: 'delete'
  })
}
