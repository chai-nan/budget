<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="任务名称" prop="taskId">
        
        <el-select v-model="queryParams.taskId" c filterable placeholder="请选择" clearable style="width:192px">
          <el-option
            v-for="item in taskList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="版本名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入版本名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="年份" prop="year">
        <el-input
          v-model="queryParams.year"
          placeholder="请输入年份"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
     
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['budget:version:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['budget:version:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['budget:version:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['budget:version:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row> -->

    <el-table v-loading="loading" :data="versionList" @selection-change="handleSelectionChange">
      <el-table-column label="任务名称" align="center" prop="taskName" />
      <el-table-column label="版本名称" align="center" prop="name" />
      <el-table-column label="状态" align="center" prop="status" >
        <template #default="scope">
          <dict-tag :options="version_status" :value="scope.row.status " />
        </template>
      </el-table-column>
      <!-- <el-table-column label="年份" align="center" prop="year" /> -->
      <el-table-column label="生成时间" align="center" prop="createTime" />
      <el-table-column label="操作" align="center" width="400px" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="SortDown" @click="handExport(scope.row,4)"  v-if='scope.row.status==2'>研发费用导出</el-button>

          <el-button link type="primary" icon="SortDown" @click="handExport(scope.row,1)"  v-if='scope.row.status==2'>导出</el-button>
          <el-button link type="primary" icon="SortUp" @click="handExport(scope.row,2)" v-if='scope.row.status==2' >上报导出</el-button>
          <el-button link type="primary" icon="Histogram" @click="handleTj(scope.row)" >统计分析</el-button>
          <!-- <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['budget:version:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['budget:version:remove']">删除</el-button> -->
        </template>
      </el-table-column>
    </el-table>
     
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />


    <!-- 添加或修改版本控制对话框 -->
    <el-dialog :title="title" v-model="open" width="90%" append-to-body>
        <div>
          部门：
          <el-select
                        v-model="form.dept_id"
                        placeholder="请选择"
                      
                        style="width: 150px"
                        @change="changeDept()"
                        
                    >
                        <el-option
                            v-for="dict in deptList"
                            :key="dict.deptId"
                            :label="dict.parentName+'-'+dict.deptName"
                            :value="dict.deptId"
                        />
                        </el-select>
        </div>
        <!-- v-loading="loading" -->
        <el-table  :data="form.list"  height="800px">
            <el-table-column label="科目/月份" min-width="280px" align="left" prop="name"  fixed="left"/>
            <el-table-column label="预算" min-width="200px" align="center" prop="budget" />
            <el-table-column label="实际累计" min-width="200px" align="center" prop="total" />
            <el-table-column label="完成预算比例" min-width="200px" align="center" prop="ratio" />
            <!-- <el-table-column label="年份" align="center" prop="year" /> -->
            <el-table-column label="1月" min-width="200px" align="center" prop="month1" />
            <el-table-column label="2月" min-width="200px" align="center" prop="month2" />
            <el-table-column label="3月" min-width="200px" align="center" prop="month3" />
            <el-table-column label="4月" min-width="200px" align="center" prop="month4" />
            <el-table-column label="5月" min-width="200px" align="center" prop="month5" />
            <el-table-column label="6月" min-width="200px" align="center" prop="month6" />
            <el-table-column label="7月" min-width="200px" align="center" prop="month7" />
            <el-table-column label="8月" min-width="200px" align="center" prop="month8" />
            <el-table-column label="9月" min-width="200px" align="center" prop="month9" />
            <el-table-column label="10月" min-width="200px" align="center" prop="month10" />
            <el-table-column label="11月" min-width="200px" align="center" prop="month11" />
            <el-table-column label="12月" min-width="200px" align="center" prop="month12" />
            <el-table-column label="累计" min-width="200px" align="center" prop="total" />
        </el-table>
     
    </el-dialog>
  </div>
</template>

<script setup name="Version">
  import {getVersion, listVersion,getDeptList,getTjList} from "@/api/BM/version";
  import {listTaskAll} from '@/api/BM/task'


  const { proxy } = getCurrentInstance();
  const { version_status} = proxy.useDict("version_status");

const versionList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const taskList = ref([])
const deptList=ref([])
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

const { queryParams, form, rules } = toRefs(data);
function handExport(row,type){
  proxy.download('budget/statistics/budgetExport', {
    taskId:row.taskId,versionId:row.id,type
  }, `${type==1?'导出':type==2?'上报导出':'研发费用导出'}${row.name}.zip`)
}
// 获取任务列表
function getTaskAll(){
  listTaskAll().then(response => {
    taskList.value = response
  })
}
/** 查询版本控制列表 */
function getList() {
  loading.value = true;
  listVersion(queryParams.value).then(response => {
    versionList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}
const changeDept=()=>{
  getTj()
}
const handleTj =async (row) => {
  form.value.id=row.id
  title.value =row.taskName+' —— '+row.name+ "版本统计";
  let res= await getDeptList()
  deptList.value=res.data
  form.value.dept_id=deptList.value[0].deptId
  getTj()
  open.value = true;
}
const getTj=async ()=>{
  form.value.list=[]
  let res= await getTjList({versionId:form.value.id,deptId:form.value.dept_id})
    form.value.list=res.data
  
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    dept_id:"",
    list:[],
  };
  proxy.resetForm("versionRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加版本控制";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getVersion(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改版本控制";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["versionRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateVersion(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addVersion(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除版本控制编号为"' + _ids + '"的数据项？').then(function() {
    return delVersion(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('budget/version/export', {
    ...queryParams.value
  }, `version_${new Date().getTime()}.xlsx`)
}
getTaskAll()
getList();
</script>
