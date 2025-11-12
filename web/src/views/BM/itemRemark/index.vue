<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
        <!-- <el-form-item label="数据表名" prop="tableName">
          <el-input
            v-model="queryParams.tableName"
            placeholder="请输入数据表名"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item> -->
        <el-form-item label="预算表" prop="tableDisplayName">
          <el-input
            v-model="queryParams.tableDisplayName"
            placeholder="请输入预算表"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <!-- <el-form-item label="职能部门" prop="deptId">
         
          <div style="width:192px">
            <el-tree-select
                v-model="queryParams.deptId"
                :data="deptOptions"
                :props="{ value: 'id', label: 'label', children: 'children' }"
                value-key="id"
                placeholder="请选择归属部门"
                check-strictly
              />
          </div>
         
        </el-form-item> -->
        <!-- <el-form-item label="审核人" prop="userId">
          <el-select
                       v-model="queryParams.userId"
                       placeholder="请选择"
                       clearable
                       style="width: 240px"
                    >
                       <el-option
                          v-for="dict in userList"
                          :key="dict.userId"
                          :label="dict.nickName"
                          :value="dict.userId"
                       />
                    </el-select>
        </el-form-item> -->
        <el-form-item label="预算科目" prop="subjectId">
         
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
        <el-form-item label="费用类型" prop="subjectId">
            <el-select
                  v-model="queryParams.type"
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
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
          <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
  
      <el-row :gutter="10" class="mb8">
        
       
        <el-col :span="1.5">
          <div @click="changeTableData(1)" :class="reportingType==1?'items active':'items'">动态表</div>
        </el-col>
        <el-col :span="1.5">
          <div @click="changeTableData(2)" :class="reportingType==2?'items active':'items'">定制表</div>
        </el-col>
        <el-col :span="1.5">
          <div @click="changeTableData(3)" :class="reportingType==3?'items active':'items'">工资</div>
        </el-col>
  
        <!-- <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['budget:item:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['budget:item:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['budget:item:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['budget:item:export']"
          >导出</el-button>
        </el-col> -->
        <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
  
      <el-table v-loading="loading" :data="itemList" @selection-change="handleSelectionChange">
        <!-- <el-table-column type="selection" width="55" align="center" /> -->
        <el-table-column label="排序" align="center" prop="orderNum" width="55"/>
        
        <el-table-column label="预算类型" align="center" prop="type" >
          <template #default="scope">
            <dict-tag :options="budget_type" :value="scope.row.type" />
          </template>
        </el-table-column>
  
        <el-table-column label="预算表名称" align="center" prop="tableDisplayName" />
        <!-- <el-table-column label="管控部门" align="center" prop="deptName" /> -->
        <!-- <el-table-column label="审核人" align="center" prop="userName" /> -->
        <el-table-column label="费用科目" align="center" prop="subjectName" />
        <!-- <el-table-column label="费用类型" align="center"  >
          <template #default="scope">
            <dict-tag :options="item_type" :value="scope.row.type" />
          </template>
        </el-table-column> -->
        
        <el-table-column label="职能部门" align="center" prop="deptName" />
  
        <el-table-column label="审核人" align="center" prop="userName" />
        <el-table-column label="说明" width="200" align="center" prop="remark"  show-overflow-tooltip />
  
        
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <!-- <el-button link type="primary" icon="Edit" @click="handleOpenZd(scope.row)"  v-if="reportingType==1">字段管理</el-button> -->
            
            <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"  v-hasPermi="['budget:item:edit']">修改</el-button>
            <!-- <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['budget:item:remove']">删除</el-button> -->
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
  
      <!-- 修改预算配置表说明 -->
      <el-dialog :title="title" draggable v-model="open" width="800px" append-to-body>
        <el-form ref="itemRef" :model="form" :rules="rules" label-width="80px" style="min-height: 200px;">
          <el-row  :gutter="24">
            <el-col :span="24">
              <el-form-item label="填报说明" prop="remark">
                <el-input v-model="form.remark" placeholder="请输入预算表填报说明"   type="textarea"  rows="10"/>
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
      <!-- 字段管理 -->
         <el-dialog title="辅助字段管理" v-model="openZD" width="1000px" append-to-body draggable>
          <div style="display:flex;justify-content: flex-end;margin-bottom: 10px;">
            <el-button type="primary" @click="AddZdList">新增</el-button>
          </div>
          <el-table  :data="zdList"  max-height='500px'>
            <el-table-column label="排序" align="center" prop="orderNum" min-width="50px" >
                <template #default="scope">
           
                  {{scope.row.orderNum}}
                </template>
              </el-table-column>
              <el-table-column label="字段名称" align="center" prop="tableDisplayName" min-width="200px" >
                <template #default="scope">
             
                  {{scope.row.fieldDisplayName}}
                </template>
              </el-table-column>
              <el-table-column label="字段属性" align="center" prop="fieldType" min-width="200px">
                <template #default="scope">
                  <dict-tag :options="field_type" :value="scope.row.fieldType" />
                 
                </template>
              </el-table-column>
             
              <el-table-column label="是否必填" align="center" prop="fieldRequired" >
                <template #default="scope">
                          
                            {{scope.row.fieldRequired==0?'是':'否'}}
                  </template>
              </el-table-column>
              <el-table-column label="是否查询" align="center" prop="fieldQuery" >
                <template #default="scope">
                           
                            {{scope.row.fieldQuery==0?'是':'否'}}
                          </template>
              </el-table-column>
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template #default="scope">
                  <el-button type="primary" link @click="changeItem(scope.row)">编辑</el-button>
                  <el-button link type="primary" @click="handleDeleteZD(scope)" v-if='scope.row.type!=1' >删除</el-button>
                  <!-- <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['budget:item:remove']">删除</el-button> -->
                </template>
              </el-table-column>
            </el-table>
       
          <template #footer>
            <div class="dialog-footer">
              <!-- <el-button type="primary" @click="saveZd">保 存</el-button> -->
              <el-button @click="()=>openZD=false">取 消</el-button>
            </div>
          </template>
          <el-dialog title="添加/修改" v-model="show" width="800px" append-to-body draggable>
            <el-form ref="subjectRef" :model="item"  label-width="auto">
              <el-row  :gutter="24">
                <el-col :span="11">
                  <el-form-item label="字段名称" prop="fieldDisplayName">
                    <el-input v-model="item.fieldDisplayName" placeholder="请输入字段名称" />
                  </el-form-item>
                </el-col>
                <el-col :span="11">
                  <el-form-item label="字段属性" prop="fieldType">
                     <el-select
                      v-model="item.fieldType"
                      placeholder="请选择"
                      clearable
                      style="width: 192px"
                  >
                      <el-option
                        v-for="dict in field_type"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      />
                  </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
             
              
              <el-row  :gutter="24">
                <el-col :span="11" v-if='item.fieldType==4'>
                  <!-- <el-form-item label="字段长度" prop="fieldDisplayName">
                    <el-input v-model="item.fieldLength" placeholder="请输入字段名称" />
                  </el-form-item> -->
                  <el-form-item label="类型" prop="dictId" >
                      <el-select
                        v-model="item.dictId"
                        placeholder="请选择"
                        clearable
                        style="width: 192px"
                    >
                        <el-option
                          v-for="dict in dictList"
                          :key="dict.dictId"
                          :label="dict.dictName"
                          :value="dict.dictId"
                        />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="11"> 
                  <el-form-item label="排序" prop="orderNum">
                    <el-input v-model="item.orderNum" placeholder="请输入"  />
                  </el-form-item>
                </el-col>
                
               
              </el-row>
              <el-row  :gutter="24">
                <el-col :span="11">
                  <el-form-item label="是否查询" prop="fieldQuery">
                     <el-switch
                                v-model="item.fieldQuery"
                                active-value="0"
                                inactive-value="1"
                            ></el-switch>
                  </el-form-item>
                </el-col>
                <el-col :span="11">
                  <el-form-item label="是否必填" prop="fieldDisplayName">
                     <el-switch
                                v-model="item.fieldRequired"
                                active-value="0"
                                inactive-value="1"
                            ></el-switch>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
            <template #footer>
            <div class="dialog-footer">
              <el-button type="primary" @click="saveZd">保 存</el-button>
              <el-button @click="()=>show=false">取 消</el-button>
            </div>
          </template>
          </el-dialog>
         </el-dialog>
    </div>
  </template>
  
  <script setup name="Item">
    import {getItem, listByUser,addItem,updateItem,updateStatus,GetZdList,addZdList,delZDItem,updateZDItem} from "@/api/BM/item";
    
    import {listSubjectAll} from "@/api/BM/subject";
    import {getListData} from "@/api/system/dict/data"
    import { deptTreeSelect } from "@/api/system/user";
    import {  listUserAll } from "@/api/system/user";
    import { watch,ref,reactive,getCurrentInstance } from "vue";
  
  const { proxy } = getCurrentInstance();
  const { field_type,item_type,budget_type } = proxy.useDict("budget_type","field_type","item_type" );
  const reportingType=ref(1)
  const itemList = ref([]);
  const open = ref(false);
  const loading = ref(true);
  const showSearch = ref(true);
  const ids = ref([]);
  const single = ref(true);
  const multiple = ref(true);
  const total = ref(0);
  const title = ref("");
  const deptOptions = ref(undefined);
  const deptLeveOptions=ref([])
  const userList=ref([])
  const subjectList=ref([])
  const data = reactive({
    form: {},
    queryParams: {
      pageNum: 1,
      pageSize: 10,
      tableName: null,
      tableDisplayName: null,
      deptId: null,
      userId: null,
      subjectId: null,
      type:null
    },
    rules: {
     
    }
      
    
  });
  const { queryParams, form, rules } = toRefs(data);
  const openZD=ref(false)
  const zdData=reactive({
    info:{},
    zdList:[],
    show:false,
    item:{
    
    }
  })
  const { zdList,info,show,item} = toRefs(zdData);
  const dictList=ref([])
  // 获取编辑字段
  async function changeItem(row){
    item.value=JSON.parse(JSON.stringify(row)) 
    show.value=true
  }
  function checkChange(data,{checkedKeys}){
    form.value.deptIds=checkedKeys
  }
  function changeTableData(type){
    reportingType.value=type
    handleQuery()
  
  }
  // 保存字段
  async function saveZd(){
    let res
    if(item.value.id){
      res =await updateZDItem({...item.value,tableName:info.value.tableName,itemId:info.value.id})
    }else{
      res =await addZdList({...item.value,tableName:info.value.tableName,itemId:info.value.id})
  
    }
    if(res.code!=200) return proxy.$message.error(res.msg)
    show.value=false
    getZDList()
  }
  // 删除字段
  async function handleDeleteZD(data){
    // zdList.value.splice(data.$index,1)
    let res=await delZDItem(data.row.id)
    if(res.code!=200) return proxy.$message.error(res.msg)
    getZDList()
  }
  // 添加字段
  async function AddZdList(){
    show.value=true
    item.value={
      fieldDisplayName:"",
      fieldType:"",
      fieldLength:"",
      fieldRequired:"1",
      fieldDisplay:"1",
      dictId:"",
      fieldQuery:"1"
    
    }
    // zdList.value.push({
    //   fieldDisplayName:"",
    //   fieldType:"",
    //   fieldLength:"",
    //   fieldRequired:"0",
    //   fieldDisplay:"0",
    // })
  }
  // 打开字段获取信息
  async function handleOpenZd(row) {
    info.value=row
    getZDList()
    openZD.value=true
  }
  async function getZDList(){
    let res=await GetZdList({tableName:info.value.tableName,itemId:info.value.id,pageNum:1,pageSize:9999})
    if(res.code!=200) return proxy.$message.error(res.msg)
  
    zdList.value=res.rows
  }
  // 获取预算科目列表
  async function getListSubjectAll(){
    let res=await listSubjectAll()
    subjectList.value=res
  }
  // 修改状态
  async function changeItemDelFlag(id,delFlag){
    await updateStatus({id,delFlag})
    getList()
  }
  // 监听添加修改表单数据部门ID
  watch(()=> form.value.deptId,(newValue, oldValue) => {
    if(newValue==oldValue) return;
    if(form.value.deptId)  getUserList(form.value.deptId);
   
  });
  watch(()=> queryParams.value.deptId,(newValue, oldValue) => {
    if(newValue==oldValue) return;
    if(queryParams.value.deptId)  getUserList(queryParams.value.deptId);
   
  });
  // 查询部门下面人员
  function getUserList(deptId){
  
    listUserAll({deptId}).then(res => {
      userList.value = res;
  
    });
  }
  
  
  /** 查询部门下拉树结构 */
  function getDeptTree() {
    deptTreeSelect().then(response => {
      deptOptions.value = response.data;
    });
  };
  /** 查询部门下拉树结构 */
  function getDeptLeve() {
    deptTreeSelect(2).then(response => {
      deptLeveOptions.value = response.data;
    });
  };
  /** 查询预算配置列表 */
  function getList() {
    loading.value = true;
    listByUser({reportingType:reportingType.value,...queryParams.value}).then(response => {
      itemList.value = response.rows;
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
      tableName: null,
      tableDisplayName: null,
      deptId: [],
      userId: null,
      subjectId: null,
      createBy: null,
      createTime: null,
      updateBy: null,
      updateTime: null,
      remark: null,
      deptIds:[],
      orderNum:null,
      delFlag: null
    };
    proxy.resetForm("itemRef");
  }
  
  /** 搜索按钮操作 */
  function handleQuery() {
    queryParams.value.pageNum = 1;
    getList();
    
  }
  
  /** 重置按钮操作 */
  function resetQuery() {
    proxy.resetForm("queryRef");
    queryParams.value.type=null;
  
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
    title.value = "添加预算配置";
  }
  
  /** 修改按钮操作 */
  function handleUpdate(row) {
    reset();
    const _id = row.id || ids.value
    getDeptTree()
    getDeptLeve()
    getItem(_id).then(response => {
      form.value = response.data;
      open.value = true;
      console.log(response)
      title.value ="修改说明-"+ response.data.tableDisplayName;
      
    });
  }
  
  /** 提交按钮 */
  function submitForm() {
  
    proxy.$refs["itemRef"].validate(valid => {
      if (valid) {
        if (form.value.id != null) {
          updateItem(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
          });
        } else {
          addItem(form.value).then(response => {
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
    proxy.$modal.confirm('是否确认删除预算配置编号为"' + _ids + '"的数据项？').then(function() {
      return delItem(_ids);
    }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    }).catch(() => {});
  }
  
  /** 导出按钮操作 */
  function handleExport() {
    proxy.download('budget/item/export', {
      ...queryParams.value
    }, `item_${new Date().getTime()}.xlsx`)
  }
  // 获取所有字典字段
  function getListDataAll(){
    getListData().then(res=>{
      dictList.value=res.data
    })
  }
  getListDataAll()
  getListSubjectAll()
  getDeptTree()
  getDeptLeve()
  getList();
  
  </script>
  <style lang="scss" scoped>
  .items{
    padding-bottom: 10px;
    margin-right: 10px;
    font-size: 14px;
    letter-spacing: 1px;
    cursor: pointer;
    transition: all 0.2s ease;
  
  }
  .active{
    color: #409EFF;
    border-bottom: 1px solid #409EFF;
    // font-weight: bold;
  }
  </style>
  