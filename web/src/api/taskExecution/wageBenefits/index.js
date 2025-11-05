import request from '@/utils/request'

// 获取须知
export function getLeftList(query) {
  return request({
    url: '/system/dept/listAll',
    method: 'get',
    params: query
  })
}
export function getRightData(query) {
    return request({
      url: '/fixed/wages/listAll',
      method: 'get',
      params: query
    })
  }
  export function saveItem(data) {
    return request({
      url: '/fixed/wages',
      method: 'post',
      data
    })
  }
  export function updateStatus(data) {
    return request({
      url: '/fixed/wages/updateStatus',
      method: 'post',
      data
    })
  }
  
  export function recovery(data) {
    return request({
      url: '/fixed/wages/backfill',
      method: 'post',
      data
    })
  }

  export function getRightList(query) {
    return request({
      url: '/fixed/wages/parentList',
      method: 'get',
      params: query
    })
  }
  