import request from '@/utils/request'

// 查询预算科目列表
export function listSubject(query) {
  return request({
    url: '/budget/subject/list',
    method: 'get',
    params: query
  })
}
// 查询预算科目列表
export function listSubjectAll(query) {
  return request({
    url: '/budget/subject/listAll',
    method: 'get',
    params: query
  })
}

// 查询预算科目详细
export function getSubject(id) {
  return request({
    url: '/budget/subject/' + id,
    method: 'get'
  })
}

// 新增预算科目
export function addSubject(data) {
  return request({
    url: '/budget/subject',
    method: 'post',
    data: data
  })
}

// 修改预算科目
export function updateSubject(data) {
  return request({
    url: '/budget/subject',
    method: 'put',
    data: data
  })
}

// 删除预算科目
export function delSubject(id) {
  return request({
    url: '/budget/subject/' + id,
    method: 'delete'
  })
}
