<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
    
      <el-form-item label="选择年份" prop="year">
        <el-date-picker
          v-model="queryParams.year"
          type="year"
          placeholder="选择年份"
          value-format="YYYY"
          :editable="false"
        />
      </el-form-item>
      <el-form-item label="任务名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入任务名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="完成情况" prop="startTime">
              <el-select
                  v-model="queryParams.completionStatus"
                  placeholder="请选择"
                  clearable
                  style="width: 192px"
              >
                  <el-option
                    v-for="dict in completion_status"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
              </el-select>
      </el-form-item>
     
      <el-form-item label="结果" prop="auditExplain">
        <el-select
                  v-model="queryParams.auditStatus"
                  placeholder="请选择"
                  clearable
                  style="width: 192px"
              >
                  <el-option
                    v-for="dict in result_status"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
              </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['budget:task:add']"
        >新增</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['budget:task:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['budget:task:remove']"
        >删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['budget:task:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="taskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="填报任务" align="center" prop="id" /> -->
      <el-table-column label="年份" align="center" prop="year" />
      <el-table-column label="预算年度" align="center" prop="budgetYear" />

      <el-table-column label="任务名称" align="center" prop="name"  show-overflow-tooltip />
      <el-table-column label="起始时间" align="center" prop="startTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="填报说明" align="center" prop="reportingExplain"  
      :show-overflow-tooltip="{ effect: 'dark', placement: 'bottom', showArrow: true,  popperClass :'popperClass'}">
        
        </el-table-column>
      <el-table-column label="完成情况 " align="center" prop="completionStatus" >
        <template #default="scope">
         
         <dict-tag :options="completion_status" :value="scope.row.completionStatus" />
       </template>
      </el-table-column>
      <el-table-column label="审核状态 " align="center" prop="auditStatus" >
        <template #default="scope">
         
          <dict-tag :options="result_status" :value="scope.row.auditStatus" />
        </template>
      </el-table-column>
      <!-- <el-table-column label="审核说明" align="center" prop="auditExplain" /> -->
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="状态" align="center" width="100" >
        <template #default="scope">
          <el-switch
              v-model="scope.row.completionStatus"
              width="60px"
              active-value="1"
              inactive-value="2"
              size="large"
              @change='changeTackStatus(scope.row.id,scope.row.completionStatus)'
            />
        </template>
      </el-table-column>

      <el-table-column label="操作" width="300" align="center" class-name="small-padding fixed-width">
        <template #default="scope">

          <el-button link type="primary"  icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['budget:task:edit']"> {{scope.row.completionStatus==2?'结果登记':'修改'}} </el-button>
          <el-button link type="primary"  icon="Edit" @click="handleAdd(scope.row,2)"  v-if='scope.row.auditStatus==3'> 重新发起任务 </el-button>

          <!-- <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['budget:task:remove']">删除</el-button> -->
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

    <!-- 添加或修改预算任务对话框 -->
    <el-dialog :title="title" v-model="open" width="800" append-to-body draggable>
      <el-form ref="taskRef" :model="form"  label-width="80px" >
        <el-row  :gutter="24">
          <el-col :span="11">
            
              <el-form-item label="预算年度"   prop="budgetYear" :rules="[
      {
          required: true,
          message: '年份不能为空',
          trigger: 'blur'
      }
    ]">
                <el-date-picker
                :disabled='isShow'
                v-model="form.budgetYear"
                type="year"
                placeholder="选择年份"
                value-format="YYYY"
                :editable="false"
              />
              </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="任务名称" prop="name" :rules='{
          required: true,
          message: "任务名称不能为空",
          trigger: "blur"
      }'>
          <el-input :disabled='isShow' v-model="form.name" placeholder="请输入任务名称"  />
          <!-- maxlength="20"  show-word-limit -->
        </el-form-item>
          </el-col>
      </el-row>
      <el-row  :gutter="24">
          <el-col :span="11">
            <el-form-item label="开始时间" prop="startTime" :rules='[
      {
        required: true,
          message: "开始时间不能为空",
          trigger: "blur"
      }
    ]'>
                  <el-date-picker
                  :disabled='isShow'
                v-model="form.startTime"
                value-format="YYYY-MM-DD"
                type="date"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        
      </el-row>
    
      <el-row  :gutter="24">
          <el-col :span="22">
            <el-form-item label="填报说明" prop="reportingExplain" :rules='[{
          required: true,
          message: "填报说明不能为空",
          trigger: "blur"
      }]'>
          
              <el-input :disabled='isShow' v-model="form.reportingExplain" type="textarea" placeholder="请输入填报说明" />
            </el-form-item>
          </el-col>

      </el-row>
      <div v-if='isShow'>
        <el-row  :gutter="24">
          <el-col :span="22">
            <el-form-item label="审核结果" prop="auditStatus"   :rules="[{ required: true, message: '请选择审核结果' ,trigger:'change'},
            {
            validator: (rule, value, callback) => {
              if (value == 2 || value == 3) {
                callback(); // 验证通过
              } else {
                callback(new Error('选择审核结果 通过 或者 不通过')); // 验证失败
              }
            },
            trigger: 'change',
          },
          ]">
              <el-radio-group v-model="form.auditStatus" class="ml-4" >
                <!-- :disabled='form.auditStatus!=1' -->
                <!-- <el-radio label="1" size="large">待审核</el-radio> -->
                <el-radio label="2" size="large">通过</el-radio>
                <el-radio label="3" size="large">不通过</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      
        <el-row  :gutter="24">
            <el-col :span="22">
              <el-form-item label="原因" prop="auditExplain">
                <!-- :disabled='form.auditStatus!=1' -->
                <el-input  v-model="form.auditExplain" type="textarea" placeholder="请输入说明" />
              </el-form-item>
            </el-col>

        </el-row>

      </div>
      
        
     
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm" :disabled="submitFormDisabled" >确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>

      


    </el-dialog>
  </div>
</template>

<script setup name="Task">
  import {getTask, listTask,updateTask,addTask,relaunchTask} from "@/api/BM/task";
import { ref } from "vue";

  const { proxy } = getCurrentInstance();
  const { result_status ,completion_status} = proxy.useDict("result_status","completion_status");

const taskList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const isShow = ref(false);
const isdisable=ref(false)
const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    budgetYear: null,
    name: null,
    startTime: null,
    endTime: null,
    reportingExplain: null,
    completionStatus: null,
    auditStatus: '',
    auditExplain: null,
  },
  rules: {
    budgetYear:[
      {
          required: true,
          message: "年份不能为空",
          trigger: "blur"
      }
    ],
    name:[{
          required: true,
          message: "任务名称不能为空",
          trigger: "blur"
      }],
    dateRange:[
      {
        required: true,
          message: "起止时间不能为空",
          trigger: "blur"
      }
    ],
    reportingExplain:[{
          required: true,
          message: "填报说明不能为空",
          trigger: "blur"
      }]
  }
});

const { queryParams, form, rules } = toRefs(data);
const isrelaunch=ref(false)
const submitFormDisabled=ref(false)
// 修改状态
async function changeTackStatus(id,completionStatus){
  updateTask({id,completionStatus}).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          getList()
        });

}
/** 查询预算任务列表 */
function getList() {
  loading.value = true;
  listTask(queryParams.value).then(response => {
    taskList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
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
    budgetYear: null,
    name: null,
    startTime: null,
    endTime: null,
    reportingExplain: null,
    completionStatus: "1",
    auditStatus: null,
    auditExplain: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    delFlag: null,
    lastTask:null
  };
  proxy.resetForm("taskRef");
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
function handleAdd(row,type) {
  isrelaunch.value=false
  reset();
  open.value = true;
  isShow.value = false;
  title.value = "添加预算任务";
  if(row&&row.id)  {
    form.value['lastTask']= row.id
    title.value = "重新发起预算任务";
    isrelaunch.value=true
  } 

}

/** 修改按钮操作 */
function handleUpdate(row) {

  reset();
  const _id = row.id || ids.value
  getTask(_id).then(response => {
    console.log(response.data.completionStatus)

    form.value = response.data;
    form.value.budgetYear=""+form.value.budgetYear
    // form.value.dateRange=[response.data.startTime,response.data.endTime]
    open.value = true;
    title.value = "修改预算任务";
    console.log( form.value)
    if (response.data.completionStatus==2) {
    
      isShow.value = true;
    }
    else {
      isShow.value = false;
      
    }
    
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["taskRef"].validate(valid => {
    console.log(valid)
    if (valid) {
      if(isrelaunch.value){
        submitFormDisabled.value=true
        relaunchTask({...form.value}).then(response => {
          proxy.$modal.msgSuccess("重新发起成功");
          submitFormDisabled.value=false
          open.value = false;
          isrelaunch.value=false
          getList();
          return
        }).catch((error) => {
          proxy.$modal.msgErrer(error);
          submitFormDisabled.value=false
          isrelaunch.value=false
        return
        })
      }else{
        if (form.value.id != null) {
        // startTime: form.value.dateRange[0],endTime: form.value.dateRange[1]
        updateTask({...form.value}).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
          if(form.value.auditStatus==3){
            // ,startTime: form.value.dateRange[0],endTime: form.value.dateRange[1]
            handleAdd({...form.value}) 
          }

        });
      } else {
        // ,startTime: form.value.dateRange[0],endTime: form.value.dateRange[1]
        addTask({...form.value}).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }

      }
    
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除预算任务编号为"' + _ids + '"的数据项？').then(function() {
    return delTask(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('budget/task/export', {
    ...queryParams.value
  }, `task_${new Date().getTime()}.xlsx`)
}

getList();
</script>
<style lang="scss" >
    .popperClass{width:50% !important}
</style>
