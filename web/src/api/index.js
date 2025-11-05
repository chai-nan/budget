import request from '@/utils/request'



// 
export function taskList() {
  return request({
    url: '/index/statistics/taskList',
    method: 'get'
  })
}

// 
export function queryBudget(data) {
  return request({
    url: '/index/statistics/queryBudget',
    method: 'get',
    params:data
  })
}
// 获取代办事项
export function queryDaiban() {
  return request({
    url: '/index/statistics/queryDaiban',
    method: 'get'
  })
}
// 获取预警列表
// export function warningList() {
//   return request({
//     url: '/budget/warning/getList',
//     method: 'get'
//   })
// }
// 获取完成信息
export function getTask() {
  return request({
    url: '/budget/reporting/checkTaskStatus',
    method: 'get'
  })
}
// 获取完成信息
export function changeTask(data) {
  return request({
    url: '/budget/reporting/updateTaskStatus',
    method: 'post',
    data: data
  })
}
// 获取预警列表
export function warningList() {
  return request({
    url: '/index/statistics/queryMessages',
    method: 'get'
  })
}


