import request from '@/utils/request'
// 保存版本
export function saveVersion(data) {
    return request({
      url: '/budget/reporting/saveVersion',
      method: 'post',
      data: data
    })
  }
  export function budgetSummary(query) {
    return request({
      url: '/budget/statistics/budgetSummary',
      method: 'get',
      params: query
    })
  }
  export function budgetSummary2(query) {
    return request({
      url: '/budget/statistics/reportSummary',
      method: 'get',
      params: query
    })
  }
  export function budgetSummary1(query) {
    return request({
      url: '/budget/statistics/itemSummary',
      method: 'get',
      params: query
    })
  }
  export function chageAllStatus(data) {
    return request({
      url: '/budget/reporting/clickAudit',
      method: 'post',
      data: data
    })
  }
  