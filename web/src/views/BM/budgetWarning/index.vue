<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="任务名称" prop="taskId">
        <!-- <el-input
          v-model="queryParams.taskId"
          placeholder="请输入任务ID"
          clearable
          @keyup.enter="handleQuery"
        /> -->
        <el-select v-model="queryParams.taskId" style="width: 210px;">
          <el-option :label="item.name" :value="item.id" v-for="item in tasklist" :key="item.id"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="预警类型" prop="type">
        
        <el-select v-model="queryParams.type" style="width: 210px;">
          <el-option :label="item.label" :value="item.value" v-for="item in typeList" :key="item.id"></el-option>
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
          v-hasPermi="['budget:budgetWarning:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['budget:budgetWarning:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['budget:budgetWarning:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <!-- <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['budget:budgetWarning:export']"
        >导出</el-button> -->
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="budgetWarningList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="任务名称" align="center" prop="taskName" />
      <el-table-column label="预警类型" align="center" prop="type" >
        <template #default="scope">
          {{ 
            scope.row.type === 1 ? '填报预警' :
            scope.row.type === 2 ? '超出预警' :
            scope.row.type === 3 ? '费用报销预警' :
            scope.row.type === 4 ? '经济事项' :
            scope.row.type === 5 ? '未及时审核预警' :
            ''
          }}
        </template>
      </el-table-column>

      <!-- <el-table-column label="预警条件" align="center" >
        <template #default="scope">
          <span v-if="scope.row.day">{{ scope.row.day}}日</span>
          <span v-else>{{scope.row.proportion +'%'}}</span>

        </template>
      </el-table-column> -->
      <el-table-column label="预警时间" align="center">
        <template #default="scope">
          {{ scope.row.startTime ? scope.row.startTime : '无' }}
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="状态" align="center" prop="status" >
        <template #default="scope">
          {{ scope.row.status == 1 ? '启用' : '禁用' }}
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['budget:budgetWarning:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['budget:budgetWarning:remove']">删除</el-button>
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

    <!-- 添加或修改预警配置对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body >
      <el-form ref="budgetWarningRef" :model="form" :rules="rules" label-width="80px" :inline=true>
        <el-form-item label="任务" prop="taskId">
          <el-select v-model="form.taskId" style="width: 210px;" @change="getV">
            <el-option :label="item.name" :value="item.id" v-for="item in tasklist" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="预警类型" prop="type">
          <el-select v-model="form.type" style="width: 210px;" >
            <el-option :label="item.label" :value="item.value" v-for="item in typeList" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
       

        <el-form-item label="选择表" prop="itemIds" v-if="form.type == 1">
          <!-- <el-input v-model="form.day" placeholder="请输入日期" >
            <template #suffix>
             日
             </template>
          </el-input> -->
          <el-select v-model="form.itemIds" style="width: 210px;" multiple  collapse-tags
          collapse-tags-tooltip filterable>
            <el-option :label="item.tableDisplayName" :value="item.id" v-for="item in Blist" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
        <template v-else>
          <el-form-item label="选择版本" prop="versionId"  v-if="form.type == 2">
          <!-- <el-input v-model="form.day" placeholder="请输入日期" >
            <template #suffix>
             日
             </template>
          </el-input> -->
          <el-select v-model="form.versionId" style="width: 210px;">
            <el-option :label="item.name" :value="item.id" v-for="item in Vlist" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="选择科目" prop="subjects" v-if="form.type != 1 && form.type != 4  && form.type != 5">
          <!-- <el-input v-model="form.day" placeholder="请输入日期" >
            <template #suffix>
             日
             </template>
          </el-input> -->
          <el-select
                v-model="form.subjects"
                placeholder="请选择"
                clearable
                style="width: 210px"
                multiple  collapse-tags
              collapse-tags-tooltip filterable
            >
            <template #header>
              <el-checkbox
                v-model="checkAll"
                :indeterminate="indeterminate"
                @change="handleCheckAll"
              >
                全选
              </el-checkbox>
            </template>
                <el-option
                  v-for="dict in subjectList"
                  :key="dict.value"
                  :label="dict.name"
                  :value="dict.id"
                />
            </el-select>
        </el-form-item>
        <el-form-item label="百分比" prop="proportion" :rules='[{ validator: validateProportion, trigger: "blur" }]' v-if="form.type == 2">
          <el-input v-model="form.proportion" placeholder="请输入比例" style="width: 210px;" >
            <template #suffix>%</template>
          </el-input>
        </el-form-item>
        <el-form-item label="事项名称" prop="itemName" v-if="form.type == 4">
          <el-input v-model="form.itemName" placeholder="请输入事项名称" style="width: 210px;" >
          </el-input>
        </el-form-item>
        <!-- <el-form-item label="选择公司" prop="companyId" v-if="form.type == 4">
          <el-select v-model="form.companyId" style="width: 210px;">
            <el-option :label="item.label" :value="item.id" v-for="item in companyList" :key="item.id"></el-option>
          </el-select>
        </el-form-item> -->
        <!-- <el-form-item label="选择部门" prop="deptId" v-if="form.type == 4 && (form.companyId == 100 || form.companyId == 101)">
          <el-select v-model="form.deptId" style="width: 210px;" v-if="form.companyId == 100">
            <el-option :label="item.label" :value="item.id" v-for="item in deptList100" :key="item.id"></el-option>
          </el-select>
          <el-select v-model="form.deptId" style="width: 210px;" v-if="form.companyId == 101">
            <el-option :label="item.label" :value="item.id" v-for="item in deptList101" :key="item.id"></el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="选择部门" prop="deptId" v-if="form.type == 4">
          <el-tree-select
            v-model="form.deptId"
            :data="deptTree"
            :props="{ value: 'id', label: 'label', children: 'children' }"
            value-key="id"
            placeholder="请选择"
            multiple
            show-checkbox
            check-on-click-node
            style="width: 210px"
            collapse-tags 
            collapse-tags-tooltip 
          />
        </el-form-item>
        <el-form-item label="天数" prop="pendingReviewDays" v-if="form.type === 5">
          <el-input v-model="form.pendingReviewDays" placeholder="请输入预审核天数" style="width: 210px;" >
          </el-input>
        </el-form-item> 
        </template>
        <el-form-item label="预警时间" prop="startTime" v-if="form.type !== 5 && form.type !== 2">
              <div style="width:200px">
                <el-date-picker v-model="form.startTime" type="date" placeholder="请选择" format="YYYY-MM-DD" />
              </div>
          </el-form-item>
        <el-form-item label="提醒" prop="remind" v-if="form.type !== 5">
          <el-select v-model="form.remind" style="width: 210px;">
            <el-option label="一次" :value="3" ></el-option>
            <el-option label="一日一次" :value="1" ></el-option>
            <el-option label="一周一次" :value="2" ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status" >
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" active-color="#13ce66" inactive-color="#ff4949" />
        </el-form-item>
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

<script setup name="BudgetWarning">
  import {getBudgetWarning, listBudgetWarning,addBudgetWarning ,updateBudgetWarning,delBudgetWarning} from "@/api/budget/budgetWarning";
  import {taskList} from "@/api/index"
import { ref } from "vue";
import {listAll,versionList} from "@/api/budget/budgetWarning"
import { listSubjectAll } from "@/api/BM/subject";
import { deptTreeSelect } from "@/api/system/user";


  const { proxy } = getCurrentInstance();
  const checkAll = ref(false)
  const indeterminate = ref(false)
const budgetWarningList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const tasklist=ref([])
const subjectList=ref([])
const typeList=[
  {label:"填报预警",value:1},
  {label:"超出预警",value:2},
  {label:"费用报销预警",value:3},
  { label: "经济事项", value: 4 },
  { label: "未及时审核预警", value: 5 }
]

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    taskId: null,
    type: null,
    day: null,
    proportion: null,
    status: null,
    remind: null
  },
  rules: {
    taskId: [
      { required: true, message: "任务不能为空", trigger: "blur" }
    ],
    // type: [
    //   { required: true, message: "预警类型不能为空", trigger: "blur" }
    // ],
    // day: [
    //   { required: true, message: "时间不能为空", trigger: "blur" }
    // ],
    // proportion: [
    //   { required: true, message: "比例不能为空", trigger: "blur" }
    // ],
    itemName: [ // 添加新的验证规则
      { required: true, message: "事项名称不能为空", trigger: "blur" }
    ],
    // companyId: [
    //   { required: true, message: "公司不能为空", trigger: "blur" }
    // ],
    deptId: [
      { required: true, message: "部门不能为空", trigger: "blur" }
    ],
    subjects: [
      { required: true, message: "科目不能为空", trigger: "blur" }
    ],
        // ... 已有代码 ...
    pendingReviewDays: [ // 新增验证规则
      { required: true, message: "预审核天数不能为空", trigger: "blur" },
      { 
        validator: (rule, value, callback) => {
          const reg = /^[1-9]\d*$/;
          if (!reg.test(value)) {
            callback(new Error('天数必须为正整数'));
          } else {
            callback();
          }
        }, 
        trigger: "blur" 
      }
    ]
  
  }
});
const validateProportion=(rule, value, callback) => {
 if(value<=100 && value>=0){
  callback()
 } else{
  callback(new Error('不能小于0,不能大于100'));
 }
}


const { queryParams, form, rules } = toRefs(data);
const Blist=ref([])
const Vlist = ref([])
const companyList = ref([])
const deptList100 = ref([])
const deptList101 = ref([])
const deptTree = ref([])
getCompanyList()
getDeptList()
async function getV(){
 let res=await versionList({taskId:form.value.taskId,status:2})
 Vlist.value=res.data
 form.value.versionId=""
}

async function getCompanyList(){
  deptTreeSelect(1).then(response => {
    companyList.value = response.data
  });
}

async function getDeptList(){
  deptTreeSelect().then(response => {
    deptTree.value = response.data
    console.log("deptTree",deptTree.value)
    const id100Children = response.data.find(item => item.id === 1)?.children?.find(item => item.id === 100)?.children || [];
    deptList100.value = id100Children;
    const id101Children = response.data.find(item => item.id === 1)?.children?.find(item => item.id === 101)?.children || [];
    deptList101.value = id101Children;
  });
}

// 判断节点是否为叶子节点

watch(()=>form.value.subjects, (val) => {
  if (val.length === 0) {
    checkAll.value = false
    indeterminate.value = false
  } else if (val.length === subjectList.value.length) {
    checkAll.value = true
    indeterminate.value = false
  } else {
    indeterminate.value = true
  }
})
const handleCheckAll = (val) => {
  indeterminate.value = false
  if (val) {
    form.value.subjects = subjectList.value.map((_) => _.id)
    console.log( form.value.subjects)
  } else {
    form.value.subjects= []
  }
}
async function getB(){
  listAll({queryType:3,delFlag:0}).then(r=>{
      Blist.value=r.data
    })
}
// 获取预算科目列表
async function getListSubjectAll(){
  let res=await listSubjectAll()
  subjectList.value=res
}
/** 查询预警配置列表 */
function getList() {
  loading.value = true;
  listBudgetWarning(queryParams.value).then(response => {
    budgetWarningList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}
function getTaskList(){
  taskList().then(res=> {
    tasklist.value=res.data
   
    })
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
    taskId: null,
    type: 1,
    day: null,
    proportion: null,
    status: 1,
    remind: 3,
    itemIds:[],
    startTime:"",
    subjects: [],
    companyId: null,
    deptId: null,
    itemName: null,
  };
  proxy.resetForm("budgetWarningRef");
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
  title.value = "添加预警配置";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getBudgetWarning(_id).then(async response => {
    form.value = response.data;
 
    form.value.itemIds= response.data.itemIds && (response.data.itemIds).split(',').map((item)=>Number(item))||[]

    form.value.subjects = response.data.subjects && (response.data.subjects)?.split(',')?.map((item) => Number(item)) || []
    form.value.companyId = response.data.companyId && Number(response.data.companyId)
    // form.value.deptId = response.data.deptId && Number(response.data.deptId)

    // 如果 deptId 是字符串，则将其转换为数组
    if (typeof response.data.deptId === 'string') {
      form.value.deptId = response.data.deptId.split(',').map((item) => Number(item));
    } else {
      form.value.deptId = response.data.deptId; // 确保兼容性
    }

    open.value = true;
    title.value = "修改预警配置";
    
    let res=await versionList({taskId:form.value.taskId,status:2})
    Vlist.value=res.data
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["budgetWarningRef"].validate(valid => {
   
    if (valid) {
      let itemIds=form.value.itemIds.toString()
      let subjects = form.value.subjects.toString()

      // 如果 type === 4，将 deptId 从数组转换为字符串
      if (form.value.type === 4 && Array.isArray(form.value.deptId)) {
        form.value.deptId = form.value.deptId.join(',');
      }
      
      if (form.value.id != null) {
        updateBudgetWarning({...form.value,itemIds,subjects}).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        console.log("未及时审核预警",{...form.value, itemIds,subjects})
        addBudgetWarning({...form.value,itemIds,subjects}).then(response => {
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
  proxy.$modal.confirm('是否确认删除数据项？').then(function() {
    return delBudgetWarning(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('budget/budgetWarning/export', {
    ...queryParams.value
  }, `budgetWarning_${new Date().getTime()}.xlsx`)
}

getList();
getTaskList()
getB()
getListSubjectAll()
</script>

