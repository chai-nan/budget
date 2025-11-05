import request from '@/utils/request'

// 查询实际费用列表
export function listActualCosts(query) {
  return request({
    url: '/budget/actualCosts/list',
    method: 'get',
    params: query
  })
}

// 查询实际费用详细
export function getActualCosts(id) {
  return request({
    url: '/budget/actualCosts/' + id,
    method: 'get'
  })
}

// 新增实际费用
export function addActualCosts(data) {
  return request({
    url: '/budget/actualCosts',
    method: 'post',
    data: data
  })
}

// 修改实际费用
export function updateActualCosts(data) {
  return request({
    url: '/budget/actualCosts',
    method: 'put',
    data: data
  })
}

// 删除实际费用
export function delActualCosts(id) {
  return request({
    url: '/budget/actualCostsFile/' + id,
    method: 'delete'
  })
}


// 查询实际费用文件列表
export function listActualCostsFile(query) {
  return request({
    url: '/budget/actualCostsFile/list',
    method: 'get',
    params: query
  })
}

// 查询实际费用文件详细
export function getActualCostsFile(id) {
  return request({
    url: '/budget/actualCostsFile/' + id,
    method: 'get'
  })
}

// 新增实际费用文件
export function addActualCostsFile(data) {
  return request({
    url: '/budget/actualCostsFile',
    method: 'post',
    data: data
  })
}

// 修改实际费用文件
export function updateActualCostsFile(data) {
  return request({
    url: '/budget/actualCostsFile',
    method: 'put',
    data: data
  })
}

// 删除实际费用文件
export function delActualCostsFile(id) {
  return request({
    url: '/budget/actualCostsFile/' + id,
    method: 'delete'
  })
}