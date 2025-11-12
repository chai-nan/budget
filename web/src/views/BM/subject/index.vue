<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="汇总科目" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入汇总科目"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="总账明细账科目" label-width="120px" prop="financelSubject">
            <el-select
              v-model="queryParams.financelSubject"
              placeholder="请选择"
              clearable
              filterable
              style="width: 192px"
              @keyup.enter="handleQuery"
            
          >
              <el-option
                v-for="dict in financial_subjects"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
          </el-select>
      </el-form-item>
      <!-- <el-form-item label="特殊类型" prop="specialType">
            <el-select
              v-model="queryParams.specialType"
              placeholder="请选择"
              clearable
              style="width: 192px"
          >
              <el-option
                v-for="dict in special_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
          </el-select>
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
          v-hasPermi="['budget:subject:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['budget:subject:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['budget:subject:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['budget:subject:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
<!-- @selection-change="handleSelectionChange"  -->
    <el-table v-loading="loading" :data="subjectList"  row-key="id" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="序号"  prop="orderNum"  width="80" align="center"/>
      <el-table-column label="汇总科目" align="center" prop="name" />
      <el-table-column label="总账明细账科目" align="center" prop="financelSubjectName" >
      </el-table-column>
     
      <el-table-column label="科目类型" align="center" prop="type" >
        <template #default="scope">
          <dict-tag :options="dept_type" :value="scope.row.type" />
        </template>

      </el-table-column>
      <el-table-column label="预算完成比例" align="center" prop="budgetRatio" >
        <template #default="scope">
          <div :style="{'color':scope.row.budgetRatio>0?'red':'green'}">  {{ scope.row.budgetRatio||0}} %</div>
        </template>
      </el-table-column>
      <el-table-column label="增长率" align="center" prop="growthRate" >
        <template #default="scope">
          <div :style="{'color':scope.row.growthRate>0?'red':'green'}">  {{ scope.row.growthRate}} %</div>
        </template>
      </el-table-column>
      
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Plus" @click="handleMove(scope.row)"  v-if="!scope.row.children">移动</el-button>

          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['budget:subject:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['budget:subject:remove']">删除</el-button>
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

    <!-- 添加或修改预算科目对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body >
      <el-form ref="subjectRef" :model="form" :rules="rules" label-width="auto">
        <el-row  :gutter="24">
          <el-col :span="11">
            <el-form-item label="汇总科目" prop="name">
              <el-input v-model="form.name" placeholder="请输入预算科目" />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="实际财务科目" prop="financelSubject">
             
              <el-select
                     v-model="form.financelSubject"
                     placeholder="请选择"
                     clearable
                     style="width: 240px"
                     multiple
                     filterable
                  >
                     <el-option
                        v-for="dict in financial_subjects"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                     />
                  </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row  :gutter="24">
          <el-col :span="11">
            <el-form-item label="科目年增长率" prop="growthRate">
              <el-input v-model="form.growthRate" placeholder="请输入增长率"  ><template #append>%</template></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="预算完成比例" prop="budgetRatio">
              <el-input v-model="form.budgetRatio" placeholder="预算完成比例"  ><template #append>%</template></el-input>
            </el-form-item>
            
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="11">
            
          <!-- <el-form-item label="费用类型" prop="budgetType">
              <el-select
                     v-model="form.budgetType"
                     placeholder="请选择"
                     clearable
                     style="width: 240px"
                  >
                     <el-option
                        v-for="dict in item_type"
                        :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                     />
                  </el-select>
            </el-form-item> -->
          </el-col>
          <el-col :span="11">
         
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="11">
          <el-form-item label="科目类型" prop="type">
              <el-select
                     v-model="form.type"
                     placeholder="请选择"
                     clearable
                     style="width: 240px"
                  >
                     <el-option
                        v-for="dict in dept_type"
                        :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                     />
                  </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="11">
           <!--  -->
         
            <el-form-item label="排序" prop="orderNum">
            <el-input v-model="form.orderNum" placeholder="请输入"  ></el-input>
          </el-form-item>
          </el-col>
         
        </el-row>
        <!-- <el-row :gutter="24">
          <el-col :span="11">
            <el-form-item label="销售导出" prop="xsExport">
              <div style="display: flex;">
                <el-switch
                v-model="form.xsExport" 
            inline-prompt
            active-text="是"
            inactive-text="否"
             :active-value="1"
    :inactive-value="2"
          />
          <el-input style="margin-left: 10px;" v-show="form.xsExport==1" v-model="form.xsNumber" placeholder="请输入排序" />
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="管理导出" prop="glExport">
              <div style="display: flex;">
          
                      <el-switch
                      v-model="form.glExport" 
            inline-prompt
            active-text="是"
            inactive-text="否"
             :active-value="1"
    :inactive-value="2"
          />
          <el-input style="margin-left: 10px;" v-show="form.glExport==1"  v-model="form.glNumber" placeholder="请输入排序" />
        </div>
            </el-form-item>
          </el-col>
         
        </el-row>
        <el-row :gutter="24">
          <el-col :span="11">
            <el-form-item label="研发导出" prop="yfExport">
              <div style="display: flex;">
              <el-switch
            v-model="form.yfExport"
              inline-prompt
              active-text="是"
              inactive-text="否"
              :active-value="1"
    :inactive-value="2"
            />
            <el-input style="margin-left: 10px;" v-show="form.yfExport==1"  v-model="form.yfNumber" placeholder="请输入排序" />
            </div>
            
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="制造导出" prop="zzExport">
              <div style="display: flex;">
                <el-switch
                  v-model="form.zzExport"
                  inline-prompt
                  active-text="是"
                  inactive-text="否"
                  :active-value="1"
                  :inactive-value="2"
                />
                <el-input style="margin-left: 10px;" v-show="form.zzExport==1" v-model="form.zzNumber" placeholder="请输入排序" />
                
              </div>
             
    

            </el-form-item>
          </el-col>
         
        </el-row> -->
       
        
       
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
    <el-dialog :title="'移动:'+moveForm.title" v-model="moveShow" width="800px" append-to-body >
      <el-form ref="moveFormRef" :model="moveForm" :rules="moveForm.rules" label-width="auto">
        <el-row  :gutter="24">
          
          <el-col :span="11">
            <el-form-item label="移动到" prop="parentId">
             
              <el-select
                     v-model="moveForm.parentId"
                     placeholder="请选择"
                     clearable
                     style="width: 240px"
                     
                     filterable
                     
                  >
                     <el-option
                        v-for="dict in moveList"
                        :key="dict.id"
                        :label="dict.name"
                        :value="dict.id"
                        :disabled="dict.level==2||dict.id==moveForm.id"
                        
                     />
                  </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitMoveForm">确 定</el-button>
          <el-button @click="cancelMoveForm">取 消</el-button>
        </div>
      </template>
    </el-dialog>

   
  </div>
</template>

<script setup name="Subject">
  import {getSubject, listSubject,addSubject,updateSubject,delSubject} from "@/api/BM/subject";
import { reactive, ref } from "vue";

  const { proxy } = getCurrentInstance();
  const { financial_subjects, special_type ,item_type,dept_type} = proxy.useDict("dept_type","financial_subjects", "special_type",'item_type');


const subjectList = ref([]);
const moveList=ref([])
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: null,
    financelSubject: null,
    specialType: null,
    growthRate: null,
    specialType:null
  },
  rules: {
    name:[{
      required: true,
      trigger: 'blur',
      message: '请填写预算科目',
    },

    ],
    financelSubject:[{
      required: true,
      trigger: 'blur',
      message: '请选择财务科目',
    },
    ],
    growthRate:[{
      required: true,
      trigger: 'blur',
      message: '请填写增长率',
    },
    ],
    budgetType :[{
        required: true,
         message: "请选择类型",
         trigger: "blur",
      }],
      budgetRatio:[{
      required: true,
      trigger: 'blur',
      message: '请填写',
    },
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);
const moveForm=reactive({
  leve:1,
  List:[],
  id:"",
  title:"",
  rules:{},
  parentId:0

})
const rowInfo=ref()

const moveFormRef=ref()
const moveShow=ref(false)
function handleMove(row){
  rowInfo.value=row
  moveShow.value=!moveShow.value
  moveForm.id=row.id
  moveForm.title=row.name
  moveForm.parentId=0
}
function submitMoveForm(){
  updateSubject({...rowInfo.value,id:moveForm.id,parentId:moveForm.parentId}).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          moveShow.value=!moveShow.value
          getList();
        });
}
function cancelMoveForm(){
  moveShow.value=!moveShow.value
  moveForm.id=''
  moveForm.title=''
}
/** 查询预算科目列表 */
function getList() {
  loading.value = true;
  // financelSubject:queryParams.value.financelSubject.join(",")
  listSubject({...queryParams.value}).then(response => {
    moveList.value=response.data
    subjectList.value  = proxy.handleTree(response.data, "id");
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
    name: null,
    financelSubject: [],
    specialType: null,
    growthRate: null,
    specialType: null,
    budgetType:null,
    orderNum: null,
    type:null,
    hzExport:1,
    reportName:"",
    xsExport:"2",
    xsNumber:"",
    glExport:"2",
    glNumber:"",
    yfExport:"2",
    yfNumber:"",
    zzExport:"2",
    zzNumber:"",
    budgetRatio:"",
    parentId:0,
    level:2
    
    
  };
  proxy.resetForm("subjectRef");
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
function handleAdd(row) {
 
  reset();
  form.value.level=2
  if (row != undefined) {
    form.value.parentId = row.id;
    form.value.level=1
  }
  open.value = true;
  title.value = "添加预算科目";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getSubject(_id).then(response => {
    response.data.financelSubject=response.data.financelSubject.split(',')
    form.value = response.data;
    open.value = true;
    title.value = "修改预算科目";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["subjectRef"].validate(valid => {
    if (valid) {
      let obj=JSON.parse(JSON.stringify(form.value))
      obj.financelSubject=obj.financelSubject.join(',')
      if (form.value.id != null) {
     
        updateSubject(obj).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addSubject(obj).then(response => {
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
  proxy.$modal.confirm('是否确认删除汇总科目为"' + row.name+ '"的数据项？').then(function() {
    return delSubject(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('budget/subject/export', {
    ...queryParams.value
  }, `subject_${new Date().getTime()}.xlsx`)
}

getList();
</script>
