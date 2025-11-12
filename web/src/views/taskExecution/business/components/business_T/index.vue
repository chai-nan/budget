<template>
  <div style="width: 100%">
    <div
      style="
        margin-bottom: 10px;
        display: flex;
        justify-content: flex-end;
        align-items: center;
      "
    >
      <el-button
        type="primary"
        @click="changeStatus"
        v-if="visibleData.type == 2"
        v-hasPermi="['budget:business:submit']"
        >提交审核</el-button
      >
      <el-button
        type="warning"
        plain
        icon="Download"
        @click="handleExport"
        v-hasPermi="['budget:business:export']"
        >导出</el-button
      >
    </div>
    <el-table
      row-key="id"
      element-loading-text="正在加载..."
      :data="visibleData.rightDataList"
      ref="tableRef"
      v-loading="loading"
      height="65vh"
      @selection-change="handleSelectionChange"
      
    >
      <el-table-column
        :selectable="selectable"
        type="selection"
        width="55"
        align="center"
      />
      <el-table-column width="200" align="center" label="部门" prop="dept_id">
        <template #default="scope">
          <el-select
            v-model="scope.row.dept_id"
            placeholder="请选择"
            :disabled="
              scope.row.status == 2 ||
              scope.row.status == 3 ||
              scope.row.status == 5 ||
              scope.row.status == 6
            "
            style="width: 150px"
            @change="changeDept(scope.row.dept_id, scope.$index)"
          >
            <el-option
              v-for="dict in deptList"
              :key="dict.deptId"
              :label="dict.parentName + '-' + dict.deptName"
              :value="dict.deptId"
            />
          </el-select>
        </template>
      </el-table-column>
      <!-- <el-table-column  min-width="200" align="center" label='填报人'  prop="informant"  >
                      <template  #default="scope">
                            <el-input v-model="scope.row.informant"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                        </el-table-column> -->

      <template v-for="(item, index) in visibleData.rightTableList" :key="index">
        <el-table-column
          width="120"
          align="center"
          label="状态"
          prop="status"
          v-if="item.fieldName == 'status'"
        >
          <template #default="scope">
            <dict-tag :options="budget_status" :value="scope.row.status" />
          </template>
        </el-table-column>

        <el-table-column
          width="150"
          align="center"
          :label="year + 1 + '预算费用（元）'"
          prop="budget"
          v-else-if="item.fieldName == 'budget'"
        >
          <template #default="scope">
            <span
              v-if="
                scope.row.status == 2 ||
                scope.row.status == 3 ||
                scope.row.status == 5 ||
                scope.row.status == 6
              "
              >{{ scope.row[item.fieldName] }}</span
            >

            <el-input
              v-else
              v-model="scope.row.budget"
              type="number"
              :disabled="
                scope.row.status == 2 ||
                scope.row.status == 3 ||
                scope.row.status == 5 ||
                scope.row.status == 6
              "
            />
          </template>
        </el-table-column>
        <el-table-column
          width="150"
          align="center"
          :label="year + '年预算（元）'"
          prop="budget_year"
          v-else-if="item.fieldName == 'budget_year'"
        >
          <template #default="scope">
            <span
              v-if="
                scope.row.status == 2 ||
                scope.row.status == 3 ||
                scope.row.status == 5 ||
                scope.row.status == 6
              "
              >{{ scope.row[item.fieldName] }}</span
            >

            <el-input
              v-else
              v-model="scope.row.budget_year"
              type="number"
              :disabled="
                scope.row.status == 2 ||
                scope.row.status == 3 ||
                scope.row.status == 5 ||
                scope.row.status == 6
              "
            />
          </template>
        </el-table-column>
        <el-table-column
          width="150"
          align="center"
          :label="year + '年实际发生额（元）'"
          prop="actual_incurred"
          v-else-if="item.fieldName == 'actual_incurred'"
        >
          <template #default="scope">
            <span
              v-if="
                scope.row.status == 2 ||
                scope.row.status == 3 ||
                scope.row.status == 5 ||
                scope.row.status == 6
              "
              >{{ scope.row[item.fieldName] }}</span
            >

            <el-input
              v-else
              v-model="scope.row[item.fieldName]"
              :disabled="
                scope.row.status == 2 ||
                scope.row.status == 3 ||
                scope.row.status == 5 ||
                scope.row.status == 6
              "
            />
          </template>
        </el-table-column>
        <el-table-column
          width="150"
          align="center"
          :label="year + '年预计发生费用（元）'"
          prop="estimated_incurred"
          v-else-if="item.fieldName == 'estimated_incurred'"
        >
          <template #default="scope">
            <span
              v-if="
                scope.row.status == 2 ||
                scope.row.status == 3 ||
                scope.row.status == 5 ||
                scope.row.status == 6
              "
              >{{ scope.row[item.fieldName] }}</span
            >
            <el-input
              v-else
              v-model="scope.row[item.fieldName]"
              :disabled="
                scope.row.status == 2 ||
                scope.row.status == 3 ||
                scope.row.status == 5 ||
                scope.row.status == 6
              "
            />
          </template>
        </el-table-column>
        <el-table-column
          v-else
          :label="item.fieldDisplayName"
          prop=""
          align="center"
          width="160"
        >
          <template #default="scope">
            <div v-if="item.fieldType != 4">
              <span
                v-if="
                  scope.row.status == 2 ||
                  scope.row.status == 3 ||
                  scope.row.status == 5 ||
                  scope.row.status == 6
                "
                >{{ scope.row[item.fieldName] }}</span
              >

              <el-input
                v-model="scope.row[item.fieldName]"
                v-else
                :disabled="
                  scope.row.status == 2 ||
                  scope.row.status == 3 ||
                  scope.row.status == 5 ||
                  scope.row.status == 6
                "
              />
            </div>

            <el-select
              v-else
              v-model="scope.row[item.fieldName]"
              placeholder="请选择"
              :disabled="
                scope.row.status == 2 ||
                scope.row.status == 3 ||
                scope.row.status == 5 ||
                scope.row.status == 6
              "
              style="width: 130px"
            >
              <el-option
                v-for="dict in item.dictDatas"
                :key="dict.value"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
            <!-- <dict-tag v-else :options="scope.row[item.fieldName]" :value="scope.row[item.fieldName]" /> -->
          </template>
        </el-table-column>
      </template>

      <el-table-column label="操作" align="center" width="200" fixed="right">
        <template #default="scope">
          <template v-if="!visibleQueryParams.show">
            <el-button
              link
              type="success"
              @click="save(scope.row, scope.$index)"
              v-if="
                (scope.row.status == 1 || scope.row.status == 4) && visibleData.type == 2
              "
              v-hasPermi="['budget:business:add']"
              >保存</el-button
            >
          </template>
          <template v-if="!visibleQueryParams.show">
            <el-button
              link
              type="success"
              @click="recalls(scope.row.id, scope.$index)"
              v-if="scope.row.status == 2 && visibleData.type == 2"
              v-hasPermi="['budget:business:add']"
              >撤回</el-button
            >
          </template>

          <el-button
            link
            type="primary"
            @click="clickSpInfo(scope.row, scope.$index)"
            v-if="scope.row.id"
            >查看审批</el-button
          >
          <el-popconfirm
            v-hasPermi="['budget:business:delete']"
            title="确定要删除吗？"
            @confirm="clickDelete(scope.row, scope.$index)"
            v-if="
              visibleData.type == 2 && (scope.row.status == 1 || scope.row.status == 4)
            "
          >
            <template #reference>
              <el-button link type="danger">删除 </el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 20px" v-if="visibleData.type == 2">
      <el-button type="primary" icon="Plus" @click="addOne">新增一条</el-button>
    </div>
    <div class="hjbox">
      <h4>合计</h4>
      <!-- 修改：添加 formatNumber 函数格式化数字显示 -->
      <div>预计费用（元）：¥ {{ formatNumber(visibleData.budget) }}</div>
      <!-- 修改：添加 formatNumber 函数格式化数字显示 -->
      <div>本年预算（元）：¥ {{ formatNumber(visibleData.budget_year) }}</div>
      <!-- 修改：添加 formatNumber 函数格式化数字显示 -->
      <div>本年实际发生额（元）：¥ {{ formatNumber(visibleData.actual_incurred) }}</div>
      <!-- 修改：添加 formatNumber 函数格式化数字显示 -->
      <div>全年预计发生费用（元）：¥ {{ formatNumber(visibleData.estimated_incurred) }}</div>
    </div>
    <div style="width: 98%" v-if="visibleQueryParams.total > 0">
      <pagination
        :total="visibleQueryParams.total"
        v-model:page="visibleQueryParams.pageNum"
        v-model:limit="visibleQueryParams.pageSize"
        @pagination="getRightDataList"
      />
    </div>
  </div>
</template>

<script setup>
import { nextTick, onMounted, watch, toRefs, toRaw, defineEmits } from "vue";
import usePermissionStore from "@/store/modules/permission";
import useTagsViewStore from "@/store/modules/tagsView";
import { deptTreeSelect } from "@/api/system/user";
import {
  deleteItem,
  reportingLog,
  getXZ,
  updateXZ,
  getLeftList,
  getRightTable,
  getRightData,
  getInfoForm,
  budgetSave,
  delTableItem,
  updateStatus,
  addRightData,
  getDeptList,
  getDeptActualInfo,
} from "@/api/taskExecution/index";
const { proxy } = getCurrentInstance();
const { field_type, item_type, budget_status } = proxy.useDict(
  "field_type",
  "item_type",
  "budget_status"
);

const emit = defineEmits(["clickSpInfo"]);
let  timeSum
const route = useRoute();
const isopen = ref(true);
const loading = ref(true);
const deptOptions = ref(undefined);
const ids = ref([]);
const selectType = 1;
const visibleData = reactive({
  leftIndex: 0,
  id: route.query.id,
  tableName: "",
  leftTitle: "",
  rightTitle: "",
  type: 1, // type 1查看 2编辑（新增）
  leftDataList: [],
  rightDataList: [],
  rightTableList: [],
  budget: 0,
  budget_year: 0,
  actual_incurred: 0,
  estimated_incurred: 0,
  cacheAddList: [],
});
const visibleQueryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  show: false,
});
const tableRef = ref(null);

const deptList = ref([]);
const year = ref(Number(route.query.year));

//  watch(()=>visibleData.query,(newValue, oldValue)=>{
//    if(!newValue) {
//      visibleData.leftDataList.forEach(item=>item['show']=true)
//    }else{
//      visibleData.leftDataList.forEach(item=>{

//        if(!item.tableDisplayName.toLowerCase().includes(newValue.toLowerCase())){
//          item['show']=false
//        }
//      })
//    }

//  })
watch(
  () => visibleData.rightDataList,
  (newValue, oldValue) => {
    clearTimeout(timeSum)
   timeSum= setTimeout(() => {
      let budget = 0,
      budget_year = 0,
      actual_incurred = 0,
      estimated_incurred = 0;
    try {
      newValue.forEach((item) => {
        if (item.budget) budget = parseFloat(budget) + parseFloat(item.budget);
        if (item.budget_year)
          budget_year = parseFloat(budget_year) + parseFloat(item.budget_year);
        if (item.actual_incurred)
          actual_incurred = parseFloat(actual_incurred) + parseFloat(item.actual_incurred);
        if (item.estimated_incurred)
          estimated_incurred =
            parseFloat(estimated_incurred) + parseFloat(item.estimated_incurred);
      });
      // 保留两位小数以避免浮点数精度问题
      budget = parseFloat(budget.toFixed(2));
      budget_year = parseFloat(budget_year.toFixed(2));
      actual_incurred = parseFloat(actual_incurred.toFixed(2));
      estimated_incurred = parseFloat(estimated_incurred.toFixed(2));
    } catch (error) {
      proxy.$message.error("请注意数字和汉字");
    }
    visibleData.budget = budget;
    visibleData.budget_year = budget_year;
    visibleData.actual_incurred = actual_incurred;
    visibleData.estimated_incurred = estimated_incurred;
      
    }, 500);
    
  },
  { deep: true }
);

// 添加千分位格式化函数
function formatNumber(num) {
  if (num === null || num === undefined || num === '') return '0.00';
  // 保留两位小数并添加千分位分隔符
  return new Intl.NumberFormat('zh-CN', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  }).format(num);
}
 
 
function clickSpInfo(row) {

  emit("clickSpInfo", row);
}
function changeIsopen() {
  isopen.value = !isopen.value;
}
function clickDelete(row, index) {
  if (row.id) {
    deleteItem({
      itemId: visibleData.leftDataList[visibleData.leftIndex].id,
      id: row.id,
    }).then((res) => {
      if (res.code == 200) {
        proxy.$message.error("删除成功！");
        //   getLeftList({taskId:route.query.id,selectType,reportingType:1}).then(r=>{
        // visibleData.leftDataList=r.data||[]

        // if( visibleData.leftDataList.length>0) {
        getRightTableList();

        // }

        // })
      }
    });
  } else {
    visibleData.rightDataList.splice(index, 1);
    visibleData.cacheAddList = visibleData.cacheAddList.filter(
      (item) => item.cid != row.cid
    );
  }
}

function handleExport() {
  proxy.download(
    "budget/reporting/export",
    {
      taskId: visibleData.id,
      itemId: visibleData.leftDataList[visibleData.leftIndex].id,
    },
    `填报明细${new Date().getTime()}.xlsx`
  );
}
// 禁用选择
function selectable(row, index) {
  return (row.id != "" && row.status == 1) || row.status == 4;
}
function changeDept(deptid, index) {
  if (!deptid) return;

  getDeptActualInfo({
    itemId: visibleData.leftDataList[visibleData.leftIndex].id,
    taskId: visibleData.id,
    deptId: deptid,
  }).then((res) => {
    visibleData.rightDataList[index].actual_incurred = res.data.actual_incurred || 0;
    visibleData.rightDataList[index].estimated_incurred =
      res.data.estimated_incurred || 0;
  });
}
function save(row, index) {
  let obj = {
    itemId: visibleData.leftDataList[visibleData.leftIndex].id,
    taskId: visibleData.id,
    ...row,
  };
  //  if(!row.budget||!row.budget_year) return proxy.$message.error('预算费用、本年预算必填！')

  let isNext = visibleData.rightTableList.some((item) => {
    if (item.fieldRequired == 0 && !row[item.fieldName]) {
      proxy.$message.error(`${item.fieldDisplayName}必填！`);
      return true;
    }
  });
  if (isNext) return;
  // console.log(visibleData.rightTableList)
  // return

  // if( obj.estimated_incurred<obj.actual_incurred) return  proxy.$message.error('全年预计发生费用必须>=本年实际发生额')

  budgetSave(obj).then((res) => {
    if (res.code != 200) return proxy.$message.error(res.msg);
    proxy.$message.success("操作成功");
    visibleData.cacheAddList = visibleData.cacheAddList.filter(
      (item) => item.cid != row.cid
    );

    getRightTableList();
  });
}
// 改变提交状态
function changeStatus() {
  console.log(ids.value);
  if (ids.length == 0) return proxy.$message.error("请选择数据");
  updateStatus({
    itemId: visibleData.leftDataList[visibleData.leftIndex].id,
    id: ids.value.join(","),
    status: 2,
  }).then((res) => {
    if (res.code != 200) return proxy.$message.error(res.msg);
    proxy.$message.success("提交成功");
    // getLeftList({taskId:visibleData.id,selectType,reportingType:1}).then(r=>{
    //   visibleData.leftDataList=r.data||[]
    //   if( visibleData.leftDataList.length>0)
    getRightTableList();
    // })
  });
}
function recalls(id) {
  updateStatus({
    itemId: visibleData.leftDataList[visibleData.leftIndex].id,
    id: id,
    status: 1,
  }).then((res) => {
    if (res.code != 200) return proxy.$message.error(res.msg);
    proxy.$message.success("撤回成功");
    // getLeftList({taskId:visibleData.id,selectType,reportingType:1}).then(r=>{
    //   visibleData.leftDataList=r.data||[]
    //   if( visibleData.leftDataList.length>0)
    getRightTableList();
    // })
  });
}

// 新增一条
function addOne() {
  let cid = proxy.getUUid();
  let obj = {
    id: "",
    cid,
    dept_id: "",
    budget: "",
    budget_year: "",
    actual_incurred: "",
    estimated_incurred: "",
    status: 1,
  };
  visibleData.rightTableList.forEach((item) => {
    obj[item.fieldName] = "";
  });
  obj["status"] = 1;

  visibleData.rightDataList.push(obj);
  visibleData.cacheAddList.push(obj);
  nextTick(() => {
    tableRef.value.setScrollTop(visibleData.rightDataList.length * 100);
  });
}
// 获取右边 table title 数据
function getRightTableList() {
  loading.value = true;
  // console.log(visibleData.leftIndex)

  getRightTable({ itemId: visibleData.leftDataList[visibleData.leftIndex].id ,taskId:visibleData.id}).then(
    (r) => {
      visibleData.rightTableList = r.data || [];

      getRightDataList();
    }
  );
}
// 获取 table 内容数据
async function getRightDataList() {
  getDeptList({ itemId: visibleData.leftDataList[visibleData.leftIndex].id }).then(
    (res) => {
      deptList.value = res.data;
    }
  );

  if (route.query.type == 1) {
    getRightData({
      taskId: visibleData.id,
      itemId: visibleData.leftDataList[visibleData.leftIndex].id,
      selectType,
      ...visibleQueryParams,
    }).then((r) => {
      visibleData.rightDataList = [...r.rows, ...visibleData.cacheAddList] || [
        ...visibleData.cacheAddList,
      ];

      visibleQueryParams.total = r.total;
      visibleQueryParams.show = true;
      loading.value = false;
    });
  } else {
    addRightData({
      taskId: visibleData.id,
      itemId: visibleData.leftDataList[visibleData.leftIndex].id,
      selectType,
    }).then(async (r) => {
      let arr = r.data || [];

      let arr2 = new Set(arr.map((item) => item.dept_id));

      //  r.data||[]
      for (const item of deptList.value) {
        if (!arr2.has(item.deptId)) {
          let obj = {
            id: "",
            dept_id: item.deptId,
            budget: "",
            budget_year: "",
            actual_incurred: 0,
            estimated_incurred: 0,
            status: 1,
          };
          //  visibleData.rightTableList.forEach(item=>{

          //    obj[item.fieldName]=''
          //  })
          //  if(!obj['status'])obj['status']=1

          arr.push(obj);
        }

        //  if(!arr2.has(item.deptId)){
        //   console.log(item)

        //    let res= await  getDeptActualInfo({itemId:visibleData.leftDataList[visibleData.leftIndex].id,
        //      taskId:visibleData.id,deptId:item.deptId})
        //    let obj={
        //      id:"",
        //      dept_id:item.deptId,
        //      budget:"",
        //      budget_year:"",
        //      actual_incurred:res.data.actual_incurred,
        //      estimated_incurred:0,
        //      status:1

        //    }
        //   //  visibleData.rightTableList.forEach(item=>{

        //   //    obj[item.fieldName]=''
        //   //  })
        //   //  if(!obj['status'])obj['status']=1

        //      arr.push(obj)
        //  }
      }
      let allArr = [...arr, ...visibleData.cacheAddList];
      for (let item of allArr) {
        if ((item["status"] == 1 || item["status"] == 4) && item.dept_id) {
          let res = await getDeptActualInfo({
            itemId: visibleData.leftDataList[visibleData.leftIndex].id,
            taskId: visibleData.id,
            deptId: item.dept_id,
          });
          if (!item.actual_incurred) item.actual_incurred = res.data.actual_incurred || 0;
          if (!item.estimated_incurred)
            item.estimated_incurred = res.data.estimated_incurred || 0;
        }
      }
      visibleData.rightDataList = allArr;
      // visibleQueryParams.total=r.total
      visibleQueryParams.show = false;
      visibleQueryParams.total = 0;
      loading.value = false;
    });
  }
}

/** 查询部门下拉树结构 */
function getDeptTree() {
  deptTreeSelect().then((response) => {
    deptOptions.value = response.data;
  });
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map((item) => item.id);
  // single.value = selection.length != 1;
  // multiple.value = !selection.length;
}

// getDeptTree()
function changeVisibleData(leftIndex, tableName, type, leftDataList) {
  visibleData.leftIndex = leftIndex;
  visibleData.tableName = tableName;
  visibleData.type = type;
  visibleData.leftDataList = leftDataList;

  visibleData.cacheAddList = [];
  visibleData.rightDataList = [];

  getRightTableList();
}
defineExpose({
  changeVisibleData,
});
</script>
<style lang="scss" scoped>
// .v-enter-active,
// .v-leave-active {
//   transition: width 0.5s ease;
//   transition: all 0.5s ease;
// }

// .v-enter-from,
// .v-leave-to {
//   // opacity: 0;
//   width:0%
// }

.hjbox {
  display: flex;
  justify-content: space-between;
  width: 100%;
  align-items: center;
  padding-right: 20px;
  box-sizing: border-box;

  & > div {
    font-size: 14px;
    flex: 0 0 auto;
  }
}
.pagination-container {
  margin-top: 0;
}
</style>
