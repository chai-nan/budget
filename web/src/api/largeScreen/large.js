import request from "@/utils/request";

// 查询公司及部门list
export function getSelectList(query) {
  return request({
    url: "/getParams",
    method: "get",
    params: query,
  });
}

// 查询实际费用详细
export function getLargeScreenData(query) {
  return request({
    url: "/getDatas",
    method: "get",
    params: query,
  });
}

// 查询大屏子页面数据
export function getSubPageData(query) {
  return request({
    url: "/getSubDatas",
    method: "get",
    params: query,
  });
}

// 查询table实际费用详情
export function getTableDetailInfo(query) {
  return request({
    url: "/getTableDetailInfo",
    method: "get",
    params: query,
  });
}
