import { createPinia } from "pinia";
// pinia数据持久化
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
const store = createPinia();
store.use(piniaPluginPersistedstate); // 使用持久化插件

export default store;
