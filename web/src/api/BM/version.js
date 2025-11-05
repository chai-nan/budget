import request from '@/utils/request'

// 查询版本控制列表
export function listVersion(query) {
  return request({
    url: '/budget/version/list',
    method: 'get',
    params: query
  })
}
// 查询版本控制列表
export function listVersionAll(query) {
  return request({
    url: '/budget/version/listAll',
    method: 'get',
    params: query
  })
}

// 查询版本控制详细
export function getVersion(id) {
  return request({
    url: '/budget/version/' + id,
    method: 'get'
  })
}

// 新增版本控制
export function addVersion(data) {
  return request({
    url: '/budget/version',
    method: 'post',
    data: data
  })
}

// 修改版本控制
export function updateVersion(data) {
  return request({
    url: '/budget/version',
    method: 'put',
    data: data
  })
}

// 删除版本控制
export function delVersion(id) {
  return request({
    url: '/budget/version/' + id,
    method: 'delete'
  })
}

export function getDeptList() {
  return request({
    url: '/system/dept/selectChildrenDeptById',
    method: 'get'
  })
}
export function getTjList(query, config = {}) {
  return request({
    url: '/budget/statistics/statisticsAnalysis',
    method: 'get',
    params: query,
    ...config
  })
}

