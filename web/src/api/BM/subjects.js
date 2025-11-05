import request from '@/utils/request'

// 查询上报科目管理列表
export function listSubjects(query) {
  return request({
    url: '/budget/subjects/list',
    method: 'get',
    params: query
  })
}

// 查询上报科目管理详细
export function getSubjects(id) {
  return request({
    url: '/budget/subjects/' + id,
    method: 'get'
  })
}

// 新增上报科目管理
export function addSubjects(data) {
  return request({
    url: '/budget/subjects',
    method: 'post',
    data: data
  })
}

// 修改上报科目管理
export function updateSubjects(data) {
  return request({
    url: '/budget/subjects',
    method: 'put',
    data: data
  })
}

// 删除上报科目管理
export function delSubjects(id) {
  return request({
    url: '/budget/subjects/' + id,
    method: 'delete'
  })
}
// 获取部门树

export function deptTreeSelect(level='') {
  return request({
    url: '/system/user/deptTreeByUser?level='+level ,
    method: 'get'
  })
}
