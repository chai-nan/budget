import request from '@/utils/request'



export function getRightData(query) {
    return request({
      url: '/fixed/tables/listAll',
      method: 'get',
      params: query
    })
  }
  export function saveItem(data) {
    return request({
      url: '/fixed/tables',
      method: 'post',
      data
    })
  }
  export function updateStatus(data) {
    return request({
      url: '/fixed/tables/updateStatus',
      method: 'post',
      data
    })
  }
  export function  deleteItem(data){
    return request({
        url: '/fixed/tables/remove',
        method: 'post',
        data
      })
  }
  
  