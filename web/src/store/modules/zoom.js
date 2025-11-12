import { defineStore } from "pinia";

export const useZoomState = defineStore({
  id: "zoom",
  state: () => ({
    zoomState: false,
  }),
  actions: {
    updateZoomState(newState) {
      this.zoomState = newState;
    },
  },
  persist: {
    enabled: true, // 启用持久化
    storage: localStorage, // 使用 localStorage 存储
    paths: ["zoomState"], // 指定持久化的字段
  },
});
