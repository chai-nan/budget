import request from '@/utils/request'

// 查询工资字段类型列表
export function listWagesSubject(query) {
  return request({
    url: '/fixed/wagesSubject/list',
    method: 'get',
    params: query
  })
}

// 查询工资字段类型详细
export function getWagesSubject(id) {
  return request({
    url: '/fixed/wagesSubject/' + id,
    method: 'get'
  })
}

// 新增工资字段类型
export function addWagesSubject(data) {
  return request({
    url: '/fixed/wagesSubject',
    method: 'post',
    data: data
  })
}

// 修改工资字段类型
export function updateWagesSubject(data) {
  return request({
    url: '/fixed/wagesSubject',
    method: 'put',
    data: data
  })
}

// 删除工资字段类型
export function delWagesSubject(id) {
  return request({
    url: '/fixed/wagesSubject/' + id,
    method: 'delete'
  })
}
