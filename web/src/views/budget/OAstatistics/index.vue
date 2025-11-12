<template>
     <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryRef"
      v-show="showSearch"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="选择版本" prop="name">
        <el-select
            v-model="queryParams.versionId"
            placeholder="请选择"
            clearable
            style="width: 400px"
        >
            <el-option
            v-for="dict in versionOptions"
            :key="dict.deptId"
            :label="dict.name"
            :value="dict.id"
            />
        </el-select>
      </el-form-item>
     
     
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button type="warning" plain icon="Download" @click="handleExport()"
        >导出</el-button
      >
        <!-- <el-button icon="Refresh" @click="resetQuery">重置</el-button> -->
      </el-form-item>
    </el-form>
  <el-tabs type="border-card" v-model="hzData.name" :before-leave="changeTabs">
   
    <el-tab-pane label="股份、供气汇总" name="1">
      
      <tableList maxHeight="75vh" :tableData="hzData.tabdataList" :tableHeadData="hzData.tabheadData" />
    </el-tab-pane>
    <el-tab-pane label="区域汇总" name="2">
    
      <tableList maxHeight="75vh" :tableData="hzData.tabdataList" :tableHeadData="hzData.tabheadData" />
    </el-tab-pane>


  </el-tabs>
</div>
</template>
<script setup name="OaStatistics">
import { saveAs } from "file-saver";
import * as XLSX from "xlsx";
  import {listVersionAll} from "@/api/BM/version";
  import {budgetSummaryOA} from "@/api/budget/adjustment";
  import tableList from "../../taskExecution/finance/component/TabList";
  const { proxy } = getCurrentInstance();

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: undefined,
    //  roleKey: undefined,
    year: undefined,
  },
  rules: {},
});
const { queryParams, form, rules } = toRefs(data);
const versionOptions=ref([])
const showSearch = ref(true);
// 汇总 数据
const hzData = reactive({
  id: "",
  show: false,
  name: "1",
  tabheadData: [
    // { name: "id", key: "key1" },
    // {
    //   name: "2024",
    //   key: "name",
    //   children: [
    //     { name: "2", key: "name-1" },
    //     { name: "3", key: "name-2" },
    //   ],
    // },
    // { id: 2, name: "测试", key: "name-3" },
  ],
  tabdataList: [
    // {
    //   key1: "我是第一个",
    //   name: "diyige",
    //   "name-1": "第二个",
    //   "name-2": "第三个",
    //   "name-3": "第四个",
    // },
  ],
});
function handleExport() {

  let type=hzData.name
  let name = "";
  switch (type) {
    case '1':
      name = "股份供气汇总";
      break;
    case '2':
      name = "区域汇总";
      break;

    case 3:
      name = "销售费用预算";
      break;
    case 4:
      name = "管理费用预算";
      break;
    case 5:
      name = "研发费用预算";
      break;
    case 6:
      name = "制造费用预算";
      break;
  }

  if (type == 10) {
    proxy.download(
      "budget/statistics/exportTotal",
      {
        taskId: hzData.id,
      },
      `资本性支出各单位明细${new Date().getTime()}.xlsx`
    );
  }
  // else if(type==1||type==2){

  //   let data = mergeArrays( hzData.tabdataList,  hzData.tabheadData)
  //   data=moveKeysToStart(data,['费用名称', '费用类型'])
  //   if(type==2) data=moveKeysToEnd(data,['郑州区域预算汇总'])

  //   // 将数据转换为工作表
  //   const worksheet = XLSX.utils.json_to_sheet(data);

  //   // 创建工作簿并添加工作表
  //   const workbook = XLSX.utils.book_new();
  //   XLSX.utils.book_append_sheet(workbook, worksheet, 'Sheet1');

  //   // 生成Excel文件并导出
  //   const excelBuffer = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
  //   const dataBlob = new Blob([excelBuffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8' });
  //   saveAs(dataBlob, name+'.xlsx');
  // }
  else {
    let wb = XLSX.utils.table_to_book(document.querySelector("#Table"), {
      sheet: "Sheet1",
      raw: true,
    });
    let wbout = XLSX.write(wb, { bookType: "xlsx", bookSST: true, type: "array" });
    const dataBlob = new Blob([wbout], {
      type:
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8",
    });
    saveAs(dataBlob, name + ".xlsx");
  }
}
async function changeTabs(activeName, oldActiveName) {
  hzData.tabdataList = [];
  hzData.tabheadData = [];

//   await budgetSummary({ type: activeName, taskId: hzData.id }).then((res) => {
//     hzData.tabdataList = res.data.titleDate;
//     hzData.tabheadData = res.data.titleName;
//     hzData.show = true;
//   });
hzData.name=activeName
  getList()

}
/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;

  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  dateRange.value = [];
  proxy.resetForm("queryRef");
  handleQuery();
}
async function getListVersionAll(){
    let res=await listVersionAll({status:2})
    versionOptions.value=res.data
    queryParams.value.versionId=res.data[0].id
    getList()
}
async function getList(){
    if(!queryParams.value.versionId) return proxy.$message.error('请选择版本')
  
    let res=await budgetSummaryOA({versionId:queryParams.value.versionId,type:hzData.name})
    hzData.tabdataList = res.data.titleDate;
    hzData.tabheadData = res.data.titleName;

}
getListVersionAll()

</script>
