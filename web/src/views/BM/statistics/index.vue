<template>
  <div class="app-container">
   
   <div style="display: flex;justify-content: space-between">
    <div>
          选择公司/部门：
          <!-- <el-select
                        v-model="form.dept_id"
                        placeholder="请选择"
                        style="width: 150px"
                        @change="getTj()"
                    >
                        <el-option
                            v-for="dict in deptList"
                            :key="dict.deptId"
                            :label="dict.parentName+'-'+dict.deptName"
                            :value="dict.deptId"
                        />
                        </el-select> -->
                        <el-tree-select
                        v-model="form.dept_id"
                        :data="deptOptions"
                        :props="{ value: 'id', label: 'label', children: 'children' }"
                        value-key="id"
                        placeholder="请选择"
                        check-strictly
                         @change="getTj()"
                        style="width: 600px"
                     />
        </div>
      
        <el-button type="primary" @click="exportTable">导出表</el-button>

   </div>
   
     
  
   
        <!-- v-loading="loading" -->
        <el-table  :data="form.list"  height="83vh" stripe  v-loading="loading">
            <el-table-column label="科目/月份" min-width="240px" align="left" prop="name"  fixed="left"/>
            <el-table-column label="预算" min-width="150px" align="center"  prop="budget" fixed="left" />
            <el-table-column label="实际累计" min-width="150px" align="center" prop="total"  fixed="left"/>
            <el-table-column label="完成预算比例" min-width="200px" align="center">
              <template #default="scope">
                <span :style="{ color: getColor(scope.row.ratio) }">
                  {{ scope.row.ratio }}
                </span>
              </template>
            </el-table-column>
            <!-- <el-table-column label="年份" align="center" prop="year" /> -->
            <el-table-column label="1月" min-width="150" align="center" prop="month1" />
            <el-table-column label="2月" min-width="150" align="center" prop="month2" />
            <el-table-column label="3月" min-width="150" align="center" prop="month3" />
            <el-table-column label="4月" min-width="150" align="center" prop="month4" />
            <el-table-column label="5月" min-width="150" align="center" prop="month5" />
            <el-table-column label="6月" min-width="150" align="center" prop="month6" />
            <el-table-column label="7月" min-width="150" align="center" prop="month7" />
            <el-table-column label="8月" min-width="150" align="center" prop="month8" />
            <el-table-column label="9月" min-width="150" align="center" prop="month9" />
            <el-table-column label="10月" min-width="150" align="center" prop="month10" />
            <el-table-column label="11月" min-width="150" align="center" prop="month11" />
            <el-table-column label="12月" min-width="150" align="center" prop="month12" />
            <el-table-column label="累计" min-width="150" align="center" prop="total" />
        </el-table>
    
  </div>
</template>

<script  setup>
  import {getDeptList,getTjList} from "@/api/BM/version";
import { deptTreeSelect } from "@/api/BM/subjects";

const { proxy } = getCurrentInstance();
import { ref } from "vue";
  const deptList=ref([])
  const loading = ref(true);
  const deptOptions = ref(undefined);
const data = reactive({
  form: {
    id: null,
    dept_id:"",
    list:[],
  },

  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: null,
    status: null,
    year: null,
    taskId: null,
  },
  rules: {
  }
});

// 根据比例返回对应的颜色
  const getColor = (ratio) => {
    const num = parseFloat(ratio.replace('%', ''));
    if (num > 80) {
      return 'red';
    } else if (num > 50) {
      return 'orange'; 
    }
    return 'inherit';
  };

const { queryParams, form, rules } = toRefs(data);
/** 查询部门下拉树结构 */
async function getDeptTree() {
  let response = await deptTreeSelect()
  console.log(response.data)
  
  // 保留原始的children数据
  const childrenData = response.data[0].children || [];
  
  // 创建根节点对象（郑州区域），作为第一个选项
  const rootNode = {
    id: response.data[0].id,
    label: response.data[0].label
  };
  
  // 将根节点放在最前面， followed by its children
  deptOptions.value = [rootNode, ...childrenData];
  
  // 默认选中根节点
  if (!form.value.dept_id) {
    form.value.dept_id = response.data[0].id || "";
  }
};
const handleTj =async () => {

  await getDeptTree()
  // let res= await getDeptList()
  // console.log(res.data+'@@@')
  // deptList.value=res.data
  // form.value.dept_id=deptList.children[0].id
 
  getTj()
 
}
// const getTj=async ()=>{
//   loading.value=true
//   form.value.list=[]
//   let res= await getTjList({deptId:form.value.dept_id})
//     form.value.list=res.data
  
//     loading.value=false
  
// }

const abortController = ref(null);
const getTj=async ()=>{
  loading.value=true
  form.value.list=[]

  // 关键：发起新请求前，先取消上一个未完成的请求（如果有）
  if (abortController.value) {
    abortController.value.abort(); // 终止旧请求
    abortController.value = null; // 重置控制器
  }

  // 创建新的控制器，用于控制当前请求
  abortController.value = new AbortController();
  const signal = abortController.value.signal; // 获取请求信号

  console.log('请求信号：', signal); 

  try {
    let res= await getTjList(
      {deptId:form.value.dept_id},
      {signal}
    )
    form.value.list=res.data
  
    loading.value=false
  } catch (error) {
    // 忽略“请求被取消”的错误（用户主动切换组织导致的取消，无需提示）
    if (error.name !== 'CanceledError') {
      console.error('获取数据失败：', error);
      proxy.$message.error('数据加载失败')
    }
  } finally {
    // 无论请求成功/失败/取消，都关闭loading
    loading.value = false;
  }
}

const exportTable= async()=>{
  proxy.download('budget/statistics/exportStatisticsAnalysis', {
    deptId: form.value.dept_id,
  }, `${new Date().getTime()}.xlsx`)
}
handleTj()

</script>

<style lang="" scoped>

</style>