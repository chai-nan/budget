// 预算任务填写 公共方法
import request from '@/utils/request'

// 获取须知
export function getXZ(id) {
  return request({
    url: '/budget/task/selectTips/'+ id,
    method: 'get',
    // params: query
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

// 修改须知
export function updateXZ(id) {
  return request({
    url: '/budget/task/updateTips/'+id,
    method: 'post',
    // data: data
  })
}

// 删除预算配置
export function delItem(id) {
  return request({
    url: '/budget/item/' + id,
    method: 'delete'
  })
}
// 获取 左侧列表
export function getLeftList(query) {
    return request({
      url: '/budget/reporting/itemList',
      method: 'get',
      params: query
    })
  }
//   获取右侧列表表头数据
  export function getRightTable(query) {
    return request({
      url: '/budget/reporting/itemFieldList',
      method: 'get',
      params: query
    })
  }
  //   获取右侧列表内容数据
  export function getRightData(query) {
    return request({
      url: '/budget/reporting/budgetList',
      method: 'get',
      params: query
    })
  }
//   获取动态表单数据
export function getInfoForm(query) {
    return request({
      url: '/budget/reporting/itemEditField',
      method: 'get',
      params: query
    })
  }
//   保存表单数据

export function budgetSave(data) {
    return request({
      url: '/budget/reporting/budgetSave',
      method: 'post',
      data: data
    })
  }
//   删除弹窗预选项
  export function delTableItem(data) {
    return request({
      url: '/budget/reporting/budgetDelete',
      method: 'post',
      data: data
    })
  }
  //   提交

export function updateStatus(data) {
    return request({
      url: '/budget/reporting/updateStatus',
      method: 'post',
      data: data
    })
  }
//   获取 流程
export function reportingLog(query) {
    return request({
      url: '/budget/reporting/reportingLog',
      method: 'get',
      params: query
    })
  }
//   获取审核信息
export function itemAuditField(query) {
    return request({
      url: '/budget/reporting/itemAuditField',
      method: 'get',
      params: query
    })
  }

// 职能部门统计
export function itemInfoTj(query){
    return request({
        url: '/budget/reporting/itemInfo',
        method: 'get',
        params: query
      })
}
// 添加任务获取数据
export function addRightData(query) {
  return request({
    url: '/budget/reporting/budgetAddList',
    method: 'get',
    params: query
  })
}
// 获取部门信息
export function getDeptList(query) {
  return request({
    url: '/budget/reporting/itemFieldDetp',
    method: 'get',
    params: query
  })
}
// 获取部门预算信息
export function getDeptActualInfo(query) {
  return request({
    url: '/budget/reporting/itemCostByDept',
    method: 'get',
    params: query
  })
}
//删除任务
export function deleteItem(data) {
  return request({
    url: '/budget/reporting/budgetDelete',
    method: 'post',
    data: data
  })
}
// 重新恢复记录

export function recovery(data) {
  return request({
    url: '/budget/reporting/backfill',
    method: 'post',
    data: data
  })
}
// 获取完成 未完成列表

export function getShInfoList(query) {
  return request({
    url: '/budget/reporting/itemSubmissionDept',
    method: 'get',
    params: query
  })
}