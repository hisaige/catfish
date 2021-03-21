import request from '@/utils/request'

// 获取跟组织
export function getRoot(recursion) {
  return request({
    url: '/UMS/organization/getUserRootTree',
    method: 'get',
    params: recursion
  })
}

// 新增组织
export function addNode(node) {
  return request({
    url: '/UMS/organization/create',
    method: 'post',
    data: node
  })
}

// 编辑组织
export function editNode(node) {
  return request({
    url: '/UMS/organization/update',
    method: 'put',
    data: node
  })
}

// 删除组织
export function delNode(id) {
  return request({
    url: '/UMS/organization/del/' + id,
    method: 'delete'
  })
}
