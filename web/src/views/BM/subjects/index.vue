<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="上报科目" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入上报科目"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="填报科目" prop="subjects">
        <el-input
          v-model="queryParams.subjects"
          placeholder="请输入填报科目"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="排序" prop="orderNum">
        <el-input
          v-model="queryParams.orderNum"
          placeholder="请输入排序"
          clearable
          @keyup.enter="handleQuery"
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
          :type="queryParams.type==1?'primary':''"
          plain
        @click='changeList(1)'
        >销售</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
           :type="queryParams.type==2?'primary':''"
          plain
        @click='changeList(2)'
        >管理</el-button>
      </el-col>
      <el-col :span="1.5">
        <!-- <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['budget:subjects:remove']"
        >删除</el-button> -->
         <el-button
         :type="queryParams.type==3?'primary':''"
          plain
         @click='changeList(3)'

        >研发</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
        :type="queryParams.type==4?'primary':''"
          plain
         @click='changeList(4)'
    
        >制造</el-button>
      </el-col>
      <!-- <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar> -->
      <el-col :span="18" :push="1">
      </el-col>
    
    </el-row>
    <div style="width: 100%;display:flex;justify-content: flex-start;margin-bottom: 10px;">
          <el-button
        type="success"
          plain
          @click="handleAdd"
        >新增一条</el-button>
        </div>

    <el-table v-loading="loading" :data="subjectsList" @selection-change="handleSelectionChange">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="排序" align="center" prop="orderNum" />
      <el-table-column label="上报科目" align="center" prop="name" />
      <el-table-column label="汇总科目" align="center" prop="subjectNames" />
      <el-table-column label="操作人" align="center" prop="createBy" />
      <el-table-column label="时间" align="center" prop="createTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['budget:subjects:edit']">编辑</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['budget:subjects:remove']">删除</el-button>
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

    <!-- 添加或修改上报科目管理对话框 -->
    <!-- <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="subjectsRef" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="上报科目" prop="name">
          <el-input v-model="form.name" placeholder="请输入上报科目" />
        </el-form-item>
        <el-form-item label="填报科目" prop="subjects">
          <el-input v-model="form.subjects" placeholder="请输入填报科目" />
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input v-model="form.orderNum" placeholder="请输入排序" />
        </el-form-item>
      
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog> -->
       <!-- 添加或修改预算科目对话框 -->
       <el-dialog :title="title" v-model="open" width="800px" append-to-body >
      <el-form ref="subjectsRef" :model="form" :rules="rules" label-width="auto">
        <el-row  :gutter="24">
          <el-col :span="11">
            <!-- <el-form-item label="汇总科目" prop="name">
              <el-input v-model="form.name" placeholder="请输入预算科目" />
            </el-form-item> -->
            <el-form-item label="上报科目" prop="name">
              <el-input v-model="form.name" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="11">
           
          <el-form-item label="汇总科目" prop="subjects">
              <el-select
                     v-model="form.subjects"
                     placeholder="请选择"
                     clearable
                     style="width: 240px"
                     multiple
                     filterable
                  >
                     <el-option
                        v-for="dict in subjectList"
                        :key="dict.value"
                      :label="dict.name"
                      :value="dict.id"
                      :disabled="dict.children!=null&&dict.children.length!=0" 
                     />
                   
                    
                  </el-select>
            </el-form-item>
            
          
          </el-col>
        </el-row>
        <el-row  :gutter="24">
          <el-col :span="11">
            <el-form-item label="排序" prop="orderNum">
            <el-input v-model="form.orderNum" placeholder="请输入"  ></el-input>
          </el-form-item>
           
          </el-col>
          <el-col :span="11">
            <!-- <el-form-item label="科目属性" prop="budgetType">
         
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
            <!-- <el-form-item label="预算模板科目" prop="specialType">
              <el-select
                     v-model="form.specialType"
                     placeholder="请选择"
                     clearable
                     style="width: 240px"
                  >
                     <el-option
                        v-for="dict in special_type"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                     />
                  </el-select>
            </el-form-item> -->
          
           
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="11">
           
            <el-form-item label="第一季度占比" prop="oneQuarterlyRatio">
            <el-input v-model="form.oneQuarterlyRatio" placeholder="请输入"  ><template #append>%</template></el-input>
          </el-form-item>
           
          </el-col>

          <el-col :span="11">
            <el-form-item label="第二季度占比" prop="twoQuarterlyRatio">
            <el-input v-model="form.twoQuarterlyRatio" placeholder="请输入"  ><template #append>%</template></el-input>
          </el-form-item>
           
          </el-col>
          
       
       
        </el-row>
        <el-row :gutter="24">
         
            <el-col :span="11">
            <el-form-item label="第三季度占比" prop="threeQuarterlyRatio">
            <el-input v-model="form.threeQuarterlyRatio" placeholder="请输入">  <template #append>%</template></el-input>
          </el-form-item>
           
          </el-col>
          <el-col :span="11">
            <el-form-item label="第四季度占比" prop="fourQuarterlyRatio">
            <el-input v-model="form.fourQuarterlyRatio" placeholder="请输入"  >  <template #append>%</template></el-input>
          </el-form-item>
           
          </el-col>
          
       
       
        </el-row>
     

       
        
       
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

<script setup name="Subjects">
  import {getSubjects, listSubjects,updateSubjects, delSubjects, addSubjects} from "@/api/BM/subjects";
  import {listSubjectAll} from "@/api/BM/subject"

  const { proxy } = getCurrentInstance();
  const { item_type} = proxy.useDict("dept_type","financial_subjects", "special_type",'item_type');


const subjectsList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const subjectList=ref([])


const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: null,
    type: 1,
    budgetType: null,
    subjects: null,
    orderNum: null,
    
  },
  rules: {
    
  }
});

const { queryParams, form, rules } = toRefs(data);
function changeList(type){
  queryParams.value.type=type
  queryParams.value.pageNum=1
  getList()
}

/** 查询上报科目管理列表 */
function getList() {
  loading.value = true;
  listSubjects(queryParams.value).then(response => {
    subjectsList.value = response.rows;
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
    type: null,
    budgetType: null,
    subjects: [],
    orderNum: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    delFlag: null,
    oneQuarterlyRatio:"",
  twoQuarterlyRatio:"",
  threeQuarterlyRatio:"",
  fourQuarterlyRatio:"",
  };
  proxy.resetForm("subjectsRef");
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
  title.value = "添加上报科目管理";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getSubjects(_id).then(response => {
    form.value = response.data;

    form.value.subjects=response.data.subjects.split(',').map(item=>{
     return item*1  })
   
    open.value = true;
    title.value = "修改上报科目管理";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["subjectsRef"].validate(valid => {
    if (valid) {
      let obj=JSON.parse(JSON.stringify(form.value))
      
      obj.subjects=obj.subjects.join(',')
      obj.type=queryParams.value.type
      if (form.value.id != null) {
        updateSubjects(obj).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addSubjects(obj).then(response => {
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
  proxy.$modal.confirm('是否确认删除排序为"' + row.orderNum + '"的数据项？').then(function() {
    return delSubjects(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('budget/subjects/export', {
    ...queryParams.value
  }, `subjects_${new Date().getTime()}.xlsx`)
}

getList();
listSubjectAll().then(response => {
  subjectList.value = response
 
})
</script>
