import request from '@/utils/request'

// 查询预警配置列表
export function listBudgetWarning(query) {
  return request({
    url: '/budget/budgetWarning/list',
    method: 'get',
    params: query
  })
}

// 查询预警配置详细
export function getBudgetWarning(id) {
  return request({
    url: '/budget/budgetWarning/' + id,
    method: 'get'
  })
}

// 新增预警配置
export function addBudgetWarning(data) {
  return request({
    url: '/budget/budgetWarning',
    method: 'post',
    data: data
  })
}

// 修改预警配置
export function updateBudgetWarning(data) {
  return request({
    url: '/budget/budgetWarning',
    method: 'put',
    data: data
  })
}

// 删除预警配置
export function delBudgetWarning(id) {
  return request({
    url: '/budget/budgetWarning/' + id,
    method: 'delete'
  })
}
// 查询预警配置列表
export function listAll(query) {
  return request({
    url: '/budget/item/listAll',
    method: 'get',
    params: query
  })
}
// 查询预警配置列表
export function versionList(query) {
  return request({
    url: '/budget/version/listAll',
    method: 'get',
    params: query
  })
}
