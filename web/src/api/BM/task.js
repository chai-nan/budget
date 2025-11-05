import request from '@/utils/request'

// 查询预算任务列表
export function listTask(query) {
  return request({
    url: '/budget/task/list',
    method: 'get',
    params: query
  })
}

// 查询预算任务详细
export function getTask(id) {
  return request({
    url: '/budget/task/' + id,
    method: 'get'
  })
}

// 新增预算任务
export function addTask(data) {
  return request({
    url: '/budget/task',
    method: 'post',
    data: data
  })
}

// 修改预算任务
export function updateTask(data) {
  return request({
    url: '/budget/task',
    method: 'put',
    data: data
  })
}

// 删除预算任务
export function delTask(id) {
  return request({
    url: '/budget/task/' + id,
    method: 'delete'
  })
}
// 查询预算任务列表
export function listTaskAll(query) {
  return request({
    url: '/budget/task/listAll',
    method: 'get',
    params: query
  })
}

// 重新发起预算任务
export function relaunchTask(data) {
  return request({
    url: '/budget/task/relaunch',
    method: 'post',
    data: data
  })
}