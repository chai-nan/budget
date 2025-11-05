import request from '@/utils/request'


  export function budgetSummary(query) {
    return request({
      url: '/budget/statistics/budgetVersionSummary',
      method: 'get',
      params: query
    })
  }
 
  