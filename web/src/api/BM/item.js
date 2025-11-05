import request from '@/utils/request'

// 查询预算配置列表
export function listItem(query) {
  return request({
    url: '/budget/item/list',
    method: 'get',
    params: query
  })
}
// 查询预算配置列表
export function listByUser(query) {
  return request({
    url: '/budget/item/listByUser',
    method: 'get',
    params: query
  })
}

// 查询预算配置详细
export function getItem(id) {
  return request({
    url: '/budget/item/' + id,
    method: 'get'
  })
}

// 新增预算配置
export function addItem(data) {
  return request({
    url: '/budget/item',
    method: 'post',
    data: data
  })
}

// 修改预算配置
export function updateItem(data) {
  return request({
    url: '/budget/item',
    method: 'put',
    data: data
  })
}

// 删除预算配置
export function delItem(id) {
  return request({
    url: '/budget/item/' + id,
    method: 'delete'
  })
}
// 修改预算配置状态
export function updateStatus(data) {
  return request({
    url: '/budget/item/updateStatus',
    method: 'post',
    data: data
  })
}
// 获取字段信息

export function GetZdList(query) {
  return request({
    url: '/budget/model/list',
    method: 'get',
    params: query
  })
}
// 保存字段信息
export function addZdList(data) {
  return request({
    url: '/budget/model',
    method: 'post',
    data: data
  })
}
// 删除字段
export function delZDItem(id) {
  return request({
    url: '/budget/model/' + id,
    method: 'delete'
  })
}
// 修改字段
export function updateZDItem(data) {
  return request({
    url: '/budget/model',
    method: 'put',
    data: data
  })
}
// 查询预算配置列表
export function listItemAll(query) {
  return request({
    url: '/budget/item/listAll',
    method: 'get',
    params: query
  })
}
// 驳回表

export function rejectByItem(data) {
  return request({
    url: '/budget/reporting/rejectByItem',
    method: 'post',
    data: data
  })
}