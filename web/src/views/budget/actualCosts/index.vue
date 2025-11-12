<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="日期" prop="createTime">
        <el-date-picker
            v-model="queryParams.createTime"
            type="date"
            placeholder="选择日期"
            value-format="YYYY-MM-DD"
            :editable="false"
          />
      </el-form-item>
     
      <!-- <el-form-item label="会计科目说明" prop="kjkmsm" label-width="100">
        <el-input
          v-model="queryParams.kjkmsm"
          placeholder="请输入"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门" prop="deptId" style="width: 308px">
        <el-tree-select
            v-model="queryParams.deptId"
            :data="deptOptions"
            :props="{ value: 'id', label: 'label', children: 'children' }"
            value-key="id"
            placeholder="请选择归属部门"
            check-strictly
          />
      </el-form-item>
      <el-form-item label="凭证编号" prop="pzbh">
        <el-input
          v-model="queryParams.pzbh"
          placeholder="请输入"
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
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['budget:actualCosts:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['budget:actualCosts:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['budget:actualCosts:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['budget:actualCosts:export']"
        >导出</el-button>
      </el-col> -->
      <el-col :span="1.5">
                  <el-button
                     type="info"
                     plain
                     icon="Upload"
                     @click="handleImport"
                     v-hasPermi="['budget:actualCosts:import']"
                  >导入</el-button>
               </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="actualCostsList" >
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column type="index" width="55" label="序号" align="center"  show-overflow-tooltip="true"/>
      <el-table-column label="源文件" align="center" prop="sourceName" />
      <el-table-column label="操作人" align="center" prop="createBy" />
      <el-table-column label="操作时间" align="center" prop="createTime" />
      <el-table-column label="明细数量" align="center" prop="infoNum" >
        <template #default="scope">
          <div style="color: #409EFF; cursor: pointer;" @click="showUInfo(scope.row)">{{ scope.row.infoNum }}</div>
        </template>
      </el-table-column>
      

   
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <!-- <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['budget:actualCosts:edit']">修改</el-button> -->
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" >删除</el-button>
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
    <el-dialog  title="详情" v-model="listInfoDialog" width="1400px" append-to-body>
      <el-table v-loading="info.loading" :data="infoList" >
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column type="index" width="55" label="序号" align="center"  show-overflow-tooltip="true"/>
      <el-table-column label="年份" align="center"  width="100" prop="year"  show-overflow-tooltip="true"/>
      <!-- <el-table-column label="实际财务科目"  width="200" align="center" prop="kjkmsm"  show-overflow-tooltip="true"/> -->
      <el-table-column label="所属部门" width="200" align="center" prop="deptId" >
        <template  #default="scope">
          {{ scope.row.deptParentName +'-'+  scope.row.deptName}}
        </template>
      </el-table-column>
      <el-table-column label="总账日期"  width="200" align="center" prop="zzrq"  show-overflow-tooltip="true"/>
      <el-table-column label="会计科目"  width="200" align="center" prop="kjkm"  show-overflow-tooltip="true"/>
      <el-table-column label="会计科目说明"  width="200" align="center" prop="kjkmsm"  show-overflow-tooltip="true"/>
      <el-table-column label="凭证编号"  width="200" align="center" prop="pzbh"  show-overflow-tooltip="true"/>
      <el-table-column label="来源"  width="200" align="center" prop="ly"  show-overflow-tooltip="true"/>
      <el-table-column label="日记账头说明"  width="300" align="center" prop="rjztsm"  show-overflow-tooltip="true"/>
      <el-table-column label="摘要"  width="200" align="center" prop="zy"  show-overflow-tooltip="true"/>
      <el-table-column label="币种"  width="200" align="center" prop="bz"  show-overflow-tooltip="true"/>
      <el-table-column label="本位币借方"  width="200" align="center" prop="actualIncurred"  show-overflow-tooltip="true"/>
      <el-table-column label="本位币贷方"  width="200" align="center" prop="bwbdf"  show-overflow-tooltip="true"/>
      <el-table-column label="数量增加"  width="200" align="center" prop="slzj" show-overflow-tooltip="true" />
      <el-table-column label="数量减少"  width="200" align="center" prop="sljs"  show-overflow-tooltip="true"/>
      <el-table-column label="方向"  width="200" align="center" prop="fx"  show-overflow-tooltip="true"/>
      <el-table-column label="余额"  width="200" align="center" prop="estimatedIncurred"  show-overflow-tooltip="true"/>
      <el-table-column label="数量余额"  width="200" align="center" prop="slye"  show-overflow-tooltip="true"/>
      

   
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['budget:actualCosts:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['budget:actualCosts:remove']">删除</el-button>
        </template>
      </el-table-column> -->
    </el-table>
    
    <pagination
      v-show="info.total>0"
      :total="info.total"
      v-model:page="info.pageNum"
      v-model:limit="info.pageSize"
      @pagination="getInfoList"
    />
    </el-dialog>


    <!-- 导入对话框 -->
    <el-dialog :title="upload.title" v-model="upload.open" width="400px" append-to-body>
         <el-upload
            ref="uploadRef"
            :limit="1"
            accept=".xlsx, .xls"
            :headers="upload.headers"
            :action="upload.url + '?updateSupport=' + upload.updateSupport"
            :disabled="upload.isUploading"
            :on-progress="handleFileUploadProgress"
            :on-success="handleFileSuccess"
            :auto-upload="false"
            drag
         >
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <template #tip>
               <div class="el-upload__tip text-center">
                  <!-- <div class="el-upload__tip">
                     <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的数据
                  </div> -->
                  <span>仅允许导入xls、xlsx格式文件。</span>
                  <!-- <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link> -->
               </div>
            </template>
         </el-upload>
         <template #footer>
            <div class="dialog-footer">
               <el-button type="primary" @click="submitFileForm">确 定</el-button>
               <el-button @click="upload.open = false">取 消</el-button>
            </div>
         </template>
      </el-dialog>
  </div>
</template>

<script setup name="ActualCosts">
  import {getActualCosts, listActualCosts,listActualCostsFile,delActualCosts} from "@/api/budget/actualCosts";
  import { getToken } from "@/utils/auth";
  import {  deptTreeSelect } from "@/api/system/user";
  import {listSubjectAll} from "@/api/BM/subject";

  const { proxy } = getCurrentInstance();

const actualCostsList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const deptOptions = ref(undefined);
const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    createTime: null,
    itemId: null,
    deptId: null,
    actualIncurred: null,
    estimatedIncurred: null,
    kjkmsm:null,
    pzbh: null,
  },
  rules: {
    year: [
      { required: true, message: "年份不能为空", trigger: "blur" }
    ],
    itemId: [
      { required: true, message: "填报目录不能为空", trigger: "blur" }
    ],
    deptId: [
      { required: true, message: "部门不能为空", trigger: "blur" }
    ],
    delFlag: [
      { required: true, message: "删除标志不能为空", trigger: "blur" }
    ]
  }
});
const subjectList=ref([])
const { queryParams, form, rules } = toRefs(data);
/*** 用户导入参数 */
const upload = reactive({
  // 是否显示弹出层（用户导入）
  open: false,
  // 弹出层标题（用户导入）
  title: "",
  // 是否禁用上传
  isUploading: false,
  // 是否更新已经存在的用户数据
  updateSupport: 0,
  // 设置上传的请求头部
  headers: { Authorization: "Bearer " + getToken() },
  // 上传的地址
  url: import.meta.env.VITE_APP_BASE_API + "/budget/actualCosts/importData"
});

const listInfoDialog=ref(false)
const infoList=ref([])
const info=reactive({
    pageNum: 1,
    pageSize: 10,
    total:0,
    loading:true,
    id:""
})
function getInfoList(){
  info.loading = true;
  listActualCosts({fileId:info.id,...info}).then(response => {
    infoList.value = response.rows;
    info.total = response.total;
    info.loading = false;
  });

}
function showUInfo(row){
  listInfoDialog.value=true
  info.id=row.id
 
  getInfoList()
}
function handleImport() {
  upload.title = "费用导入";
  upload.open = true;
};

/** 下载模板操作 */
function importTemplate() {
  proxy.download("budget/actualCosts/importTemplate", {
  }, `费用导入模板${new Date().getTime()}.xlsx`);
};

/**文件上传中处理 */
const handleFileUploadProgress = (event, file, fileList) => {
  upload.isUploading = true;
};

/** 文件上传成功处理 */
const handleFileSuccess = (response, file, fileList) => {
  upload.open = false;
  upload.isUploading = false;
  proxy.$refs["uploadRef"].handleRemove(file);
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
  getList();
};

/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs["uploadRef"].submit();
};
/** 查询实际费用列表 */
function getList() {
  loading.value = false;
  listActualCostsFile(queryParams.value).then(response => {
    actualCostsList.value = response.rows;
    total.value = response.total;
    info.loading = false;
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
deptTreeSelect().then(response => {
  deptOptions.value = response.data;
});
}
// 表单重置
function reset() {
  form.value = {
    id: null,
    year: null,
    itemId: null,
    deptId: null,
    actualIncurred: null,
    estimatedIncurred: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    delFlag: null
  };
  proxy.resetForm("actualCostsRef");
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
  title.value = "添加实际费用";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getActualCosts(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改实际费用";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["actualCostsRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateActualCosts(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addActualCosts(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除实际费用编号为"' + _ids + '"的数据项？').then(function() {
    return delActualCosts(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('budget/actualCosts/export', {
    ...queryParams.value
  }, `actualCosts_${new Date().getTime()}.xlsx`)
}

getList();
getDeptTree()
getListSubjectAll()
</script>
