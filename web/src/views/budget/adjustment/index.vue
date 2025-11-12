<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="预算任务" prop="budgetYear">
        <!-- <el-input
          v-model="queryParams.budgetYear"
          placeholder="请输入预算年份"
          clearable
          @keyup.enter="handleQuery"
        /> -->
        <!-- <el-date-picker
            v-model="queryParams.budgetYear"
            type="year"
            placeholder="选择预算年份"
             value-format="YYYY"
              :editable="false"
          
          /> -->
          <div style="width:400px">
            <el-select v-model="queryParams.taskId">
              <el-option :label="item.name" :value="item.id" v-for="item in tasklist" :key="item.id"></el-option>
            </el-select>
          </div>
          
      </el-form-item>
      <!-- <el-form-item label="调整科目" prop="subjectId">
  
        <el-select
                     v-model="queryParams.subjectId"
                     placeholder="请选择"
                     clearable
                     style="width: 240px"
                  >
                     <el-option
                        v-for="dict in subjectList"
                        :key="dict.value"
                        :label="dict.name"
                        :value="dict.id"
                     />
                  </el-select>
      </el-form-item>
      <el-form-item label="调出部门" prop="outDept">
      <div style="width: 240px;">
        <el-select
                     v-model="queryParams.outDept"
                     placeholder="请选择"
                     clearable
                     style="width: 240px"
                  >
                     <el-option
                        v-for="dict in deptOptions"
                        :key="dict.deptId"
                        :label="dict.parentName+'-'+dict.deptName"
                        :value="dict.deptId"
                     />
                  </el-select>
                  </div>
      </el-form-item>
      <el-form-item label="调入部门" prop="inDept">
        <div style="width: 240px;">
          <el-select
                     v-model="queryParams.inDept"
                     placeholder="请选择"
                     clearable
                     style="width: 240px"
                  >
                     <el-option
                        v-for="dict in deptOptions"
                        :key="dict.deptId"
                        :label="dict.parentName+'-'+dict.deptName"
                        :value="dict.deptId"
                     />
                  </el-select>
                  </div>
      </el-form-item> -->

      
      <!-- <el-form-item label="OA编号" prop="number">
        <el-input
          v-model="queryParams.number"
          placeholder="请输入OA编号"
          clearable
          @keyup.enter="handleQuery"
          style="width: 240px;"
        />
      </el-form-item> -->
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
          v-hasPermi="['budget:adjustment:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['budget:adjustment:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['budget:adjustment:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['budget:adjustment:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="adjustmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="预算任务" align="center" prop="taskName" />
    
      <!-- <el-table-column label="调整科目" align="center" prop="subjectName" />
      <el-table-column label="调出部门" align="center" prop="outDeptName" />
      <el-table-column label="调入部门" align="center" prop="inDeptName" /> -->
      <el-table-column label="调整金额" align="center" prop="amount" />
      <!-- <el-table-column label="OA编号" align="center" prop="number" /> -->
      <el-table-column label="调整说明" align="center" prop="remark" show-overflow-tooltip />
      
      <el-table-column label="创建人" align="center" prop="createBy" />

      <el-table-column label="创建时间" align="center" prop="createTime" />

      
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['budget:adjustment:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['budget:adjustment:remove']">删除</el-button>
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

    <!-- 添加或修改预算调整（OA填报）对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body draggable>
      <el-form  ref="adjustmentRef" :model="form" :rules="rules" label-width="80px" style="max-height: 70vh; overflow-y: auto;">
       
          <el-form-item label="预算任务" prop="taskId">
          
              <!-- <el-date-picker
            v-model="form.budgetYear"
            type="year"
            placeholder="选择预算年份"
             value-format="YYYY"
              :editable="false"
          
          /> -->
          <el-select v-model="form.taskId">
          <el-option :label="item.name" :value="item.id" v-for="item in tasklist" :key="item.id"></el-option>
        </el-select>

          </el-form-item>
          <!-- <el-form-item label="OA编号" prop="number">
          <el-input v-model="form.number" placeholder="请输入OA编号" />
        </el-form-item> -->
        <el-form-item label="调整说明" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入调整说明" />
        </el-form-item>
        
            <el-row>
              <el-col :span="11">
             
                  <el-form-item label="调出科目" 
                  :rules='[{ required: true, message: "预算科目不能为空", trigger: "blur" }]'>
                    
                    <el-select
                              v-model="form.outSubject"
                              placeholder="请选择"
                              clearable
                              style="width: 240px"
                          >
                              <el-option
                                v-for="dict in subjectList"
                                :key="dict.value"
                                :label="dict.name"
                                :value="dict.id"
                              />
                          </el-select>
                  </el-form-item>
               </el-col>
               <el-col :span="11">
                <el-form-item label="调入科目" 
                  :rules='[{ required: true, message: "预算科目不能为空", trigger: "blur" }]'>
                    
                    <el-select
                              v-model="form.inSubject"
                              placeholder="请选择"
                              clearable
                              style="width: 240px"
                          >
                              <el-option
                                v-for="dict in subjectList"
                                :key="dict.value"
                                :label="dict.name"
                                :value="dict.id"
                              />
                          </el-select>
                  </el-form-item>
                
               </el-col>
            </el-row>
            <el-row>
              <el-col :span="11">
                <el-form-item label="调出部门"    :rules='[{ required: true, message: "调出部门不能为空", trigger: "blur" }]'>
              <div style="width:240px">
                <el-select
                          v-model="form.outDept"
                          placeholder="请选择"
                          clearable
                          style="width: 240px"
                      >
                          <el-option
                            v-for="dict in deptOptions"
                            :key="dict.deptId"
                            :label="dict.parentName+'-'+dict.deptName"
                            :value="dict.deptId"
                          />
                      </el-select>
               
              </div>
            
              
                 </el-form-item>
               </el-col>
               <el-col :span="11">
                <el-form-item label="调入部门"    :rules='[{ required: true, message: "调入部门不能为空", trigger: "blur" }]'>
            <div style="width: 240px;">
          
                        <el-select
                          v-model="form.inDept"
                          placeholder="请选择"
                          clearable
                          style="width: 240px"
                      >
                          <el-option
                            v-for="dict in deptOptions"
                            :key="dict.deptId"
                            :label="dict.parentName+'-'+dict.deptName"
                            :value="dict.deptId"
                          />
                      </el-select>
            </div>
            
                 </el-form-item>
                
               </el-col>
            </el-row>
           
            <el-row>
              <el-col :span="17">
                    <el-form-item label="调整金额"   :rules='[{ required: true, message: "调整金额不能为空", trigger: "blur" }]'>
                  <el-input v-model="form.amount" placeholder="请输入调整金额" />
                </el-form-item>
              </el-col>
              <el-col :span="1" ></el-col>
             
            </el-row>
            
         
       
  
 
    
   
          <!-- <template  v-for="item,index in form.items" :key="index">
            <el-row>
              <el-col :span="11">
             
                  <el-form-item label="调整科目" 
                  :prop="'items.' + index + '.subjectId'"  :rules='[{ required: true, message: "预算科目不能为空", trigger: "blur" }]'>
                    
                    <el-select
                              v-model="item.subjectId"
                              placeholder="请选择"
                              clearable
                              style="width: 240px"
                          >
                              <el-option
                                v-for="dict in subjectList"
                                :key="dict.value"
                                :label="dict.name"
                                :value="dict.id"
                              />
                          </el-select>
                  </el-form-item>
               </el-col>
               <el-col :span="11">
                <el-form-item label="类型"   :prop="'items.' + index + '.type'" :rules='[{ required: true, message: "类型不能为空", trigger: "blur" }]'>
                
                <el-select
                          v-model="item.type"
                          placeholder="请选择"
                          clearable
                          style="width: 240px"
                      >
                          <el-option
                            v-for="dict in adjustment_type"
                            :key="dict.value"
                            :label="dict.label"
                            :value="dict.value"
                          />
                      </el-select>
              </el-form-item>
                
               </el-col>
            </el-row>
            <el-row>
              <el-col :span="11">
                <el-form-item label="调出部门"   :prop="'items.' + index + '.outDept'" :rules='[{ required: true, message: "调出部门不能为空", trigger: "blur" }]'>
              <div style="width:240px">
                <el-select
                          v-model="item.outDept"
                          placeholder="请选择"
                          clearable
                          style="width: 240px"
                      >
                          <el-option
                            v-for="dict in deptOptions"
                            :key="dict.deptId"
                            :label="dict.parentName+'-'+dict.deptName"
                            :value="dict.deptId"
                          />
                      </el-select>
                <el-tree-select
                          v-model="form.outDept"
                          :data="deptOptions"
                          :props="{ value: 'deptId', label: 'name', children: 'children' }"
                          value-key="deptId"
                          placeholder="请选择调出部门"
                          check-strictly
                          
                        />
              </div>
            
              
                 </el-form-item>
               </el-col>
               <el-col :span="11">
                <el-form-item label="调入部门"   :prop="'items.' + index + '.inDept'"  :rules='[{ required: true, message: "调入部门不能为空", trigger: "blur" }]'>
            <div style="width: 240px;">
          
                        <el-select
                          v-model="item.inDept"
                          placeholder="请选择"
                          clearable
                          style="width: 240px"
                      >
                          <el-option
                            v-for="dict in deptOptions"
                            :key="dict.deptId"
                            :label="dict.parentName+'-'+dict.deptName"
                            :value="dict.deptId"
                          />
                      </el-select>
            </div>
            
                 </el-form-item>
                
               </el-col>
            </el-row>
           
            <el-row>
              <el-col :span="17">
                    <el-form-item label="调整金额"   :prop="'items.' + index + '.amount'"  :rules='[{ required: true, message: "调整金额不能为空", trigger: "blur" }]'>
                  <el-input v-model="item.amount" placeholder="请输入调整金额" />
                </el-form-item>
              </el-col>
              <el-col :span="1" ></el-col>
              <el-col :span="4" >
                <el-button type="danger" :icon="Delete" circle  @click="deleteItem(item,index)"/>
              </el-col>
            </el-row>
            
          </template> -->
          
          <!-- <el-button type="success" @click="addItems">添加一条</el-button> -->
      
      </el-form>
      
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Adjustment">
  import {getAdjustment, listAdjustment,addAdjustment ,updateAdjustment,delAdjustment,delAdjustmentItem} from "@/api/budget/adjustment";
  import { deptTreeSelect } from "@/api/system/user";
  import {listSubjectAll} from "@/api/BM/subject";
  import {getLeftList} from "@/api/taskExecution/wageBenefits/index" 
  import {
  Delete,
} from '@element-plus/icons-vue'
import {taskList} from "@/api/index"

  const { proxy } = getCurrentInstance();
  const { adjustment_type } = proxy.useDict("adjustment_type" );
const tasklist=ref([])
const adjustmentList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const deptOptions = ref(undefined);
const subjectList=ref([])
const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    budgetYear: null,
    subjectId: null,
    outDept: null,
    inDept: null,
    amount: null,
    number: null,
    taskId:""
  },
  rules: {
    taskId: [
      { required: true, message: "预算任务不能为空", trigger: "blur" }
    ],
    
   
    remark: [
      { required: true, message: "调整说明不能为空", trigger: "blur" }
    ],
   
  },
 
  
});

const { queryParams, form, rules } = toRefs(data);

/** 查询预算调整（OA填报）列表 */
function getList() {
  loading.value = true;
  listAdjustment(queryParams.value).then(response => {
    adjustmentList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}
// 获取预算科目列表
async function getListSubjectAll(){
  let res=await listSubjectAll()
  subjectList.value=res
}
// 取消按钮
function cancel() {
  open.value = false;
  reset();
}
/** 查询部门下拉树结构 */
function getDeptTree() {
  getLeftList({level:2}).then(response => {
    deptOptions.value = response.data;
    // console.log(deptOptions.value)
  });
};
function addItems(){
  form.value.items.push({
    subjectId: null,
    outDept: null,
    inDept: null,
    amount: null,
    type:null,
    taskId:null,
  })
}
function deleteItem(item,index){
  if(!item.id){
    form.value.items.splice(index,1)
    return
  }
  delAdjustmentItem(item.id).then(res=>{
    if(res.code!=200) return proxy.$message.error(res.msg)
    form.value.items.splice(index,1)    
  })
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    taskId:"",
    budgetYear: null,
    number: null,
    remark: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    delFlag: null,
    subjectId:"",
    outDept:"",
    inDept:"",
    amount:"",
    inSubject:"",
    outSubject:"",
    items:[
      {
        subjectId: null,
        outDept: null,
        inDept: null,
        amount: null,
        type:null,
      }
    ]
  };
  proxy.resetForm("adjustmentRef");
}
function getTaskList(){
  taskList().then(res=> {
    tasklist.value=res.data
    // if(tasklist.value.length>0){
    //   taskId.value=tasklist.value[0].id
    // }
    })
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {

  queryParams.value.taskId=null
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
  title.value = "添加预算调整（OA填报）";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getAdjustment(_id).then(response => {
   
    form.value = response.data;
    form.value.budgetYear=""+form.value.budgetYear
    open.value = true;
    title.value = "修改预算调整（OA填报）";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["adjustmentRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateAdjustment(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addAdjustment(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除' ).then(function() {
    return delAdjustment(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('budget/adjustment/export', {
    ...queryParams.value
  }, `adjustment_${new Date().getTime()}.xlsx`)
}

getList();
getDeptTree()
getListSubjectAll()
getTaskList()
</script>
<style>

.el-date-editor.el-input{
  width:240px !important
}

</style>
