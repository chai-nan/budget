import request from '@/utils/request'
// 获取公司列表
export function getDeptDataList(query) {
    return request({
      url: '/budget/reporting/budgetSummaryList',
      method: 'get',
      params: query
    })
  }
  // 获取公司列表 资本性
export function getDeptDataListZ(query) {
  return request({
    url: '/fixed/tables/budgetSummaryList',
    method: 'get',
    params: query
  })
}

// 获取去年已填报但是今年在填报时间内未填报的数据
export function getNotReportedList(query) {
  return request({
    url: '/budget/reporting/getNotReportedList',
    method: 'get',
    params: query
  })
}