import request from '@/utils/request'

// 查询预算调整（OA填报）列表
export function listAdjustment(query) {
  return request({
    url: '/budget/adjustment/list',
    method: 'get',
    params: query
  })
}

// 查询预算调整（OA填报）详细
export function getAdjustment(id) {
  return request({
    url: '/budget/adjustment/' + id,
    method: 'get'
  })
}

// 新增预算调整（OA填报）
export function addAdjustment(data) {
  return request({
    url: '/budget/adjustment',
    method: 'post',
    data: data
  })
}

// 修改预算调整（OA填报）
export function updateAdjustment(data) {
  return request({
    url: '/budget/adjustment',
    method: 'put',
    data: data
  })
}

// 删除预算调整（OA填报）
export function delAdjustment(id) {
  return request({
    url: '/budget/adjustment/' + id,
    method: 'delete'
  })
}
// 删除预算调整（OA填报） 子项
export function delAdjustmentItem(id) {
  return request({
    url: '/budget/adjustment/removeInfo/' + id,
    method: 'delete'
  })
}

export function budgetSummaryOA(query) {
  return request({
    url: '/budget/statistics/budgetSummaryOA',
    method: 'get',
    params: query
  })
}
