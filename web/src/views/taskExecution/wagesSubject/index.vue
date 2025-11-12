<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="字段名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入字段名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="字段" prop="field">
        <el-input
          v-model="queryParams.field"
          placeholder="请输入字段"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
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
          v-hasPermi="['fixed:wagesSubject:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['fixed:wagesSubject:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['fixed:wagesSubject:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['fixed:wagesSubject:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row> -->

    <el-table v-loading="loading" :data="wagesSubjectList" @selection-change="handleSelectionChange">
      <el-table-column label="字段名" align="center" prop="name" />
      <el-table-column label="字段" align="center" prop="field" />
      <el-table-column label="费用科目" align="center" prop="typeName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['fixed:wagesSubject:edit']">修改</el-button>
          <!-- <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['fixed:wagesSubject:remove']">删除</el-button> -->
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

    <!-- 添加或修改工资字段类型对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="wagesSubjectRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="费用科目" prop="type">
              <!-- <el-input v-model="form.financelSubject" placeholder="请输入财务科目" /> -->
              <el-select
                     v-model="form.type"
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

<script setup name="WagesSubject">
  import {getWagesSubject, listWagesSubject,updateWagesSubject} from "@/api/taskExecution/fixed/wagesSubject"
  import {listSubjectAll} from "@/api/BM/subject";

  const { proxy } = getCurrentInstance();

const wagesSubjectList = ref([]);
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
    field: null,
    type: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);
const subjectList=ref([])
// 获取预算科目列表
async function getListSubjectAll(){
  let res=await listSubjectAll()
  subjectList.value=res
}
/** 查询工资字段类型列表 */
function getList() {
  loading.value = true;
  listWagesSubject(queryParams.value).then(response => {
    wagesSubjectList.value = response.rows;
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
    field: null,
    type: null
  };
  proxy.resetForm("wagesSubjectRef");
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
  title.value = "添加工资字段类型";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getWagesSubject(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改工资字段类型";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["wagesSubjectRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateWagesSubject(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addWagesSubject(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除工资字段类型编号为"' + _ids + '"的数据项？').then(function() {
    return delWagesSubject(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('fixed/wagesSubject/export', {
    ...queryParams.value
  }, `wagesSubject_${new Date().getTime()}.xlsx`)
}

getList();
getListSubjectAll()
</script>
