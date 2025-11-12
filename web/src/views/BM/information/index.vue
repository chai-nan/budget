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

        <!-- <el-table-column label="任务名称" align="center" prop="taskName" /> -->
        <el-table-column label="版本名称" align="center" prop="name" />
        <!-- <el-table-column label="审核状态" align="center" prop="status" /> -->
        <!-- <el-table-column label="年份" align="center" prop="year" /> -->
        <el-table-column label="生成状态" align="center" prop="taskName" >
          <template #default="scope">
            <dict-tag :options="version_status" :value="scope.row.status" />
          </template>
        </el-table-column>
         
        <el-table-column label="生成时间" align="center" prop="createTime" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-button link type="primary" icon="Edit" v-if="scope.row.status==2" @click="handExport(scope.row,3)" >导出</el-button>
            <el-button link type="primary" icon="Edit" v-if="scope.row.status==2" @click='clickHz(scope.row)' >查看</el-button>
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
      <el-dialog :title="title" v-model="open" width="500px" append-to-body>
        <el-form ref="versionRef" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="版本名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入版本名称" />
          </el-form-item>
          <el-form-item label="年份" prop="year">
            <el-input v-model="form.year" placeholder="请输入年份" />
          </el-form-item>
          <el-form-item label="任务ID" prop="taskId">
            <el-input v-model="form.taskId" placeholder="请输入任务ID" />
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </template>
      </el-dialog>
        <!-- 汇总弹窗信息 -->
        <el-dialog fullscreen v-model="hzData.show" width='80%' title="" draggable top="40vh"   :close-on-press-escape='false'>
        <el-tabs type="border-card" v-model='hzData.name'  :before-leave='changeTabs'>
          <el-tab-pane label="股份、供气汇总" name='1'>
              <tableList :tableData='hzData.tabdataList' :tableHeadData='hzData.tabheadData'/>
          </el-tab-pane>
          
          <el-tab-pane  label="区域汇总" name='2'>           
             <tableList :tableData='hzData.tabdataList' :tableHeadData='hzData.tabheadData'/>
          </el-tab-pane>
          
          <el-tab-pane label="销售费用预算" name='3'>      
              
              <tableList  :tableData='hzData.tabdataList' :tableHeadData='hzData.tabheadData'/>
          </el-tab-pane>
          <el-tab-pane label="管理费用预算" name='4'>
           
            <tableList  :tableData='hzData.tabdataList' :tableHeadData='hzData.tabheadData'/>
             
          </el-tab-pane>
          <el-tab-pane label="研发费用预算" name='5'>
        
            <tableList :tableData='hzData.tabdataList' :tableHeadData='hzData.tabheadData'/>
          </el-tab-pane>
          <el-tab-pane label="制造费用预算" name='6'>
            
            <tableList :tableData='hzData.tabdataList' :tableHeadData='hzData.tabheadData'/>
          </el-tab-pane>
          <!-- <el-tab-pane  label="资本性支出汇总" name='6'>           
             <tableList :tableData='hzData.tabdataList' :tableHeadData='hzData.tabheadData'/>
          </el-tab-pane> -->
        </el-tabs>
      
      </el-dialog>
    </div>
  </template>
  
  <script setup name="Version">
    import {getVersion, listVersion} from "@/api/BM/version";
    import {listTaskAll} from '@/api/BM/task'
    import {budgetSummary} from '@/api/BM/information'
    import tableList from '../../taskExecution/finance/component/TabList.vue'; 
  
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
  const data = reactive({
    form: {},
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
  // 汇总 数据
  const hzData=reactive({
      id:'',
      show:false,
      name:'1',
      tabheadData:[{name:'id',key:'key1'},{name:'2024',key:'name',children:[{name:'2',key:'name-1'},{name:'3',key:'name-2'}]},{id:2,name:'测试',key:'name-3'}] ,
      tabdataList:[{key1:'我是第一个',name:'diyige','name-1':'第二个','name-2':'第三个','name-3':'第四个'}],
      versionId:""
  })
  const { queryParams, form, rules } = toRefs(data);
  function clickHz(row){
 
    hzData.id=row.taskId
    hzData.versionId=row.id
    budgetSummary({type:1,taskId:hzData.id,versionId:row.id}).then(res=>{
      hzData.tabdataList=res.data.titleDate
      hzData.tabheadData=res.data.titleName
      hzData.name='1'
      hzData.show=true
    })
   
  
  
  }
  function handExport(row,type){
  proxy.download('budget/statistics/budgetExport', {
    taskId:row.taskId,versionId:row.id,type
  }, `${row.taskName}-${row.name}.zip`)
}
  async function changeTabs(activeName,oldActiveName){
    
    await budgetSummary({type:activeName,taskId:hzData.id,versionId:hzData.versionId}).then(res=>{
      hzData.tabdataList=res.data.titleDate
      hzData.tabheadData=res.data.titleName
      hzData.show=true
      
    })
  
  
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
      status: null,
      year: null,
      taskId: null,
      createBy: null,
      createTime: null
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
  