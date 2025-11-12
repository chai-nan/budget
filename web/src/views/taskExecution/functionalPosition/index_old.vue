<!-- 职能岗位 -->
<template>
    <div class="app-container">
      <el-form
        :model="queryParams"
        ref="queryRef"
        v-show="showSearch"
        :inline="true"
        label-width="68px"
      >
        <el-form-item label="选择年份" prop="name">
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
            placeholder="请输入"
            clearable
            style="width: 240px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="任务时间" style="width: 308px">
          <el-date-picker
            v-model="dateRange"
            value-format="YYYY-MM-DD"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery"
            >搜索</el-button
          >
          <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-row :gutter="10" class="mb8">
        <right-toolbar
          v-model:showSearch="showSearch"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>
  
      <!-- 表格数据 -->
      <el-table
        v-loading="loading"
        :data="roleList"
       
      >
      <!--  @selection-change="handleSelectionChange" -->
        <!-- <el-table-column type="selection" width="55" align="center" /> -->
        <el-table-column type="index" label="序号" width="55" />
        
        <el-table-column label="年份" align="center" prop="year" />
      <el-table-column label="任务名称" align="center" prop="name" />
      <el-table-column label="起始时间" align="center" prop="startTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="填报说明" align="center" prop="reportingExplain" />
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
        <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template #default="scope">
          <el-tooltip
            content="查看"
            placement="top"
          
          >
            <el-button
              link
              type="primary"
              @click="changeDialogVisible(scope.row,1)"
           
              >查看</el-button
            >
          </el-tooltip>
          <!--  v-hasPermi -->
          <el-tooltip
            content="处理"
            placement="top"
          
          >
            <el-button
              link
              type="primary"
              @click="changeDialogVisible(scope.row,2)"
              >处理</el-button
            >
          </el-tooltip>
        </template>
      </el-table-column>
      </el-table>
  
      <pagination
        v-show="total > 0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
      />
      <el-dialog v-model="dialogVisible" width='90%' draggable :close-on-press-escape='false'>
        <div class="dialogBox">
            <div class="left">
                <div class='title'>{{visibleData.leftTitle}}</div>
                <div class="content">
                    <div @click="changeIndex(index)" :class="visibleData.leftIndex==index? 'item action':'item'" v-for="(item,index) in visibleData.leftDataList" :key="index">
                        <div><el-tooltip
                        effect="dark"
                        :content="item.tableDisplayName"
                         >{{item.tableDisplayName}} </el-tooltip></div>
                         
                         <el-tag :type="item.isAudit==0?'success':'error'">{{item.isAudit==0?'已审核':'未审核'}}</el-tag>

                    </div>
                </div>

            </div>
            <div class="right">
                <!-- <div class='title'>{{visibleData.rightTitle}}</div> -->
                <div class='countBox'>
                    <div>目前总数量：{{ visibleData.tj.count}}</div>
                    <div> 目前已审核数量：{{visibleData.tj.audit}}</div>
                    <div> 已提交数量：{{ visibleData.tj.submit}}</div>
                </div>
                <div class='btnBox' >
                
                    <div style="width:200px;margin-right:20px">
                      <el-tree-select
                        v-model="visibleQueryParams.deptId"
                        :data="deptOptions"
                        :props="{ value: 'id', label: 'label', children: 'children' }"
                        value-key="id"
                        placeholder="请选择归属部门"
                        check-strictly
                        clear
                      
                     />
                    </div>
                    <el-button
                    type="primary"
                    plain
                    @click="getRightTableList"
                    >查询</el-button>
                    <el-button
                    type="info"
                    plain
                   v-if='visibleData.type==2'
                   @click="changePLsh"
                    >批量审核</el-button>

        
                </div>
                <el-table  :data="visibleData.rightDataList" max-height='75%' @selection-change="handleSelectionChange">
                  <el-table-column :selectable="selectable" type="selection" width="55" align="center"  >
                  </el-table-column>
                  <template   v-for="(item,index) in  visibleData.rightTableList" :key='index'>
                      <el-table-column
                          :label="item.fieldDisplayName"
                          prop=""
                          :show-overflow-tooltip="true"
                          v-if='item.fieldDisplay==0'
                      >
                      <template  #default="scope">
                          <span > {{scope.row[item.fieldName] }}</span>
                          
                      </template>
                    
                    </el-table-column>
                    </template>
                    <el-table-column
                          label="状态"
                          prop="status"
                          :show-overflow-tooltip="true"
                      >
                      <template  #default="scope">
                        <dict-tag :options="budget_status" :value="scope.row.status " />
                      </template>
                    
                    </el-table-column>
                    <el-table-column  label="提交人"
                          prop="create_by"> 

                    </el-table-column>
                    <el-table-column  label="提交时间"
                          prop="create_time"> 

                    </el-table-column>
                    
                    <el-table-column  
                    label="操作"
                    align="center"
                    >
                    <template #default="scope">
                        <el-button
                            link
                            type="primary"
                            @click='changeInfoFormType(scope.row,1)'
                            >详情</el-button>
                            <el-button
                            v-if='scope.row.status==6||scope.row.status==2&&visibleData.type==2'
                            link
                            type="primary"
                            @click='changeInfoFormType(scope.row,2)'
                            >审核</el-button>
                            
                    </template>
                    </el-table-column>
                </el-table>
                <div style="width:98%;position: relative;">
                    <pagination
                    v-show="visibleQueryParams.total > 0"
                    :total="visibleQueryParams.total"
                    v-model:page="visibleQueryParams.pageNum"
                    v-model:limit="visibleQueryParams.pageSize"
                    />
                </div>
                

            </div>
        </div>
        <!-- 新增编辑 查看等操作 -->
        <el-dialog v-model="dialogFormVisible" width='60%' :title="infoForm.type==1?'查看':'审核'" draggable append-to-body  :close-on-press-escape='false'>
            <div  style="max-height:80vh;overflow-y: auto; overflow-x: hidden;;width:100%;box-sizing:border-box" >
                <el-form   label-position="right" label-width="150" ref="refInfoForm" :model="infoForm">
                    <div class="formBox">
                      <div v-for="(item,index) in infoForm.List" :key="index">
                            <el-form-item :label="item.fieldDisplayName" :prop="'List.'+index+'.value'" :rules='item.rules'>
                              {{item[item.fieldName]}}
                                <el-input v-model="item.value" disabled  v-if='item.fieldName!="cost_description"'/>
                                 
                                <el-input  v-model="item.value" v-else  maxlength="300"
                                  placeholder=""
                                  show-word-limit
                                  type="textarea"
                                  disabled
                                  />
                            </el-form-item>
                           
                        </div>
                    </div>
                    <div  style='width:90%;margin:0 auto 10px' v-if='infoForm.type==2'>
                    <h3 style="color:#000;font-weight:bold">审核意见</h3>
                    <el-form-item label="" prop="status" :rules='[{required:true,message:"请选择",trigger:"change"}]'>
                      <el-radio-group v-model="infoForm.status" :disabled='infoForm.type==1' >
                      <el-radio label="3" size="large">通过</el-radio>
                      <el-radio label="4" size="large">不通过</el-radio>
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item label="审核意见" prop="remark"  :rules='[{required:true,message:"请填写原因",trigger:"blur"}]'>
                    <el-input :disabled='infoForm.type==1' v-model="infoForm.remark"   maxlength="300"
                            placeholder=""
                            show-word-limit
                            type="textarea"
                            :rows='5'
                            resize='none'
                           
                            />
                  </el-form-item>
                </div>
                </el-form>
                <div v-if='infoForm.logList.length>0&&infoForm.type==1' style='width:80%;margin:0 auto 10px'>
                    <h3 style="color:#000;font-weight:bold">审批流程</h3>
                    <el-timeline>
                      <template v-for="(item,index) in infoForm.logList" :key="index">
                        <el-timeline-item :timestamp="item.deptName" placement="top">
                      <el-card>
                        <div style='display:flex;width:100%'>
                            <div style="display:flex;justify-content:space-between;width:100%;height:40px;align-items:center;">
                              <div style="display:flex;align-items:center;">
                                <el-image style="width: 40px; height: 40px;border-radius: 50%" src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" fit="cover" />
                                <div style="margin-left:10px">{{item.userName}}</div>
                              </div>
                              <div style="color:#02a7f0;font-weight:bold">
                              </div>
                              <div>{{item.time}}</div>
                            </div>
                        </div>
                        <div style="margin-left:50px" >
                          <div style="margin-bottom:10px" v-if='item.status==1||item.status==4'>
                            {{item.remark}}

                          </div>
                          <div style="margin-bottom:10px" v-if='item.status==2||item.status==3'>
                            审批结果：<span :style='{"color": item.status==2?"02a7f0":item.status==3?"red":"" }'>{{item.status==2?'审批通过':item.status==3?'审批驳回':''}}</span>
                          </div>
                          <div style="margin-bottom:10px"  v-if='item.status==2||item.status==3'>
                            审批意见：<span style='color: #02a7f0;'>{{item.remark}}</span>
                          </div>
                        </div>
                        
                          
                      </el-card>
                      </el-timeline-item>
                      </template>
                      
                  </el-timeline>
                </div>
               
            </div>
            <div style="display:flex;justify-content:flex-end;width:100%">
                <div v-if='infoForm.type==2'>
                    <el-button type="primary" @click='save'>确定</el-button>
                    <el-button type="info"  @click="()=>dialogFormVisible=false">取消</el-button>
                </div>
                <el-button v-else type="info" @click="()=>dialogFormVisible=false">关闭</el-button>
            </div>
        </el-dialog>

        <!-- 预选填报须知 -->
        <el-dialog v-model="dialogXzVisible.show" width='60%' :title="dialogXzVisible.title" draggable append-to-body @close="changeTips" :close-on-press-escape='false'>
            <el-input
                v-model="dialogXzVisible.value"
                type="textarea"
                :readonly='true'
                resize='none'
                :rows="20"
            />
                <div style="display:flex;justify-content:flex-end;width:100%">
                    <el-checkbox v-model="dialogXzVisible.checked" label="下次不在提示" size="large" />
                </div>
        </el-dialog>
        <!-- 批量审核弹窗 -->
        <el-dialog v-model="dialogPlshVisible" width='60%' title="批量审核" draggable append-to-body  :close-on-press-escape='false'>
          <el-form  :rules="infoRules" label-position="right" label-width="150" ref="plInfoForm" :model="infoForm">
                    <div  style='width:90%;margin:0 auto 10px'>
                      <h3 style="color:#000;font-weight:bold">审核意见</h3>
                      <el-form-item label="" prop="status">
                        <el-radio-group v-model="infoForm.status" class="ml-4">
                        <el-radio label="3" size="large">通过</el-radio>
                        <el-radio label="4" size="large">不通过</el-radio>
                      </el-radio-group>
                    </el-form-item>
                    <el-form-item label="审核意见" prop="remark">
                      <el-input v-model="infoForm.remark"   maxlength="300"
                              placeholder=""
                              show-word-limit
                              type="textarea"
                              :rows='5'
                              resize='none'
                              />
                    </el-form-item>
                  </div>
            </el-form>
            <div style="display:flex;justify-content:flex-end;width:100%;margin-top:40px">
               
                    <el-button type="primary" @click='plSave'>确定</el-button>
                    <el-button type="info"  @click="()=>dialogPlshVisible=false">取消</el-button>
                
              
            </div>
        </el-dialog>
        
     
    </el-dialog>
    </div>
  </template>
   
   <script setup name="functionalPosition">
  import {
    addRole,
    changeRoleStatus,
    dataScope,
    delRole,
    getRole,
    listRole,
    updateRole,

  } from "@/api/system/role";
import { changeUserStatus, listUser, resetUserPwd, delUser, getUser, updateUser, addUser, deptTreeSelect } from "@/api/system/user";
import {getTask, listTask,updateTask,addTask} from "@/api/BM/task";
import {itemInfoTj,itemAuditField,reportingLog,getXZ,updateXZ,getLeftList,getRightTable,getRightData,getInfoForm,budgetSave,delTableItem,updateStatus} from "@/api/taskExecution/index"


const router = useRouter();
const { proxy } = getCurrentInstance();
const { budget_status,result_status ,completion_status } = proxy.useDict("budget_status","result_status","completion_status");

  const roleList = ref([]);
  const loading = ref(true);
  const showSearch = ref(true);
  const ids = ref([]);
  const single = ref(true);
  const multiple = ref(true);
  const total = ref(0);
  const dateRange = ref([]);
  const selectType=2
  const refInfoForm=ref()
  const data = reactive({
    form: {},
    queryParams: {
      pageNum: 1,
      pageSize: 10,
      name: undefined,
      //  roleKey: undefined,
      year: '',
      
    },
    rules: {},
  });
  
  const { queryParams, form, rules } = toRefs(data);

  // 弹窗分页
 const dialogVisible=ref(false)
const visibleQueryParams=reactive({
  pageNum: 1,
  pageSize: 10,
  total: 10,
  deptId: '',
  status:""
})
const options = [
  {
    value: 'Option1',
    label: 'Option1',
  },
  {
    value: 'Option2',
    label: 'Option2',
  },
  {
    value: 'Option3',
    label: 'Option3',
  },
  {
    value: 'Option4',
    label: 'Option4',
  },
  {
    value: 'Option5',
    label: 'Option5',
  },
]
const deptOptions = ref(undefined);
const visibleData=reactive({
    leftIndex:0,
    id:"",
    leftTitle:"",
    rightTitle:'xxxx部门-票据印刷费-预算申请',
    type:1 , // type 1查看 2编辑（新增）
    leftDataList:[],
    rightDataList:[],
    rightTableList:[],
    tj:{
      audit:0,
      count:0,
      submit:0
    }
})
const dialogFormVisible=ref(false)
const infoForm=reactive({
    type:1,  // type 1查看 2编辑（新增）
    name:"",
    id:'',
    List:[],
    logList:[],
    remark:'',
    status:3,
    
})
const  infoRules=reactive({
    status:[{
      required: true,
      trigger: 'change',
      message: '审核状态不能为空',
    },],
    remark:[{
      required: true,
      trigger: 'blur',
      message: '审核意见不能为空',
    },],
})
const dialogXzVisible=reactive({
    title:"",
    show:false,
    value:"",
    checked:false
})
// 批量审核
const dialogPlshVisible=ref(false)
const plInfoForm=ref()
function changePLsh(){
  if(ids.value.length==0){
    proxy.$modal.msgError("请选择要审核的数据")
    return
  }
  infoForm.status='3'

 dialogPlshVisible.value=true

}
// 批量审核
function plSave(){

  let obj={
    itemId:visibleData.leftDataList[visibleData.leftIndex].id,
    taskId:visibleData.id,
    id: ids.value.join(','),
    status:infoForm.status,
    remark:infoForm.remark
  }
  plInfoForm.value.validate((valid) => {
    if (valid) {
      updateStatus(obj).then(res=>{
        if(res.code!=200) return proxy.$message.error(res.msg)
        proxy.$message.success("提交成功")
        getLeftList({taskId:visibleData.id,selectType}).then(r=>{
          visibleData.leftDataList=r.data||[]
          dialogPlshVisible.value=false
          if( visibleData.leftDataList.length>0)  getRightTableList()
        })
      })
     
    } else {
      console.log('error submit!')
      return false
    }
  })
}
// 禁用选择
function selectable(row, index){
 
  // console.log(visibleData.rightDataList[index].status)
  return row.status==3?false:true
}
// 提交表单保存数据
function save(){
  let obj={
    itemId:visibleData.leftDataList[visibleData.leftIndex].id,
    taskId:visibleData.id,
    id: infoForm.id,
    status:infoForm.status,
    remark:infoForm.remark
  }
 

  refInfoForm.value.validate((valid) => {
    if (valid) {
      updateStatus(obj).then(res=>{
        if(res.code!=200) return proxy.$message.error(res.msg)
        proxy.$message.success("提交成功")
        getLeftList({taskId:visibleData.id,selectType}).then(r=>{
          visibleData.leftDataList=r.data||[]
          dialogFormVisible.value=false
          if( visibleData.leftDataList.length>0)  getRightTableList()
        })
      })
     
    } else {
      console.log('error submit!')
      return false
    }
  })


 

  
}
function changeDialogVisible(row,type){
    // type 1查看 2编辑（新增）
    visibleData.id=row.id
    visibleData.leftTitle=row.name
    visibleData.type=type
    dialogVisible.value=true
    visibleQueryParams.deptId=""
    getLeftList({taskId:row.id,selectType}).then(r=>{
      visibleData.leftDataList=r.data||[]
      if( visibleData.leftDataList.length>0)  getRightTableList()
    })
    switch(type){
        case 1 :
            // console.log(type)
        break;
        case 2 :
        getXZ(row.id).then(res=>{
              dialogXzVisible.title=row.name
              dialogXzVisible.value=row.reportingExplain
              if(res.data.isTips==0)dialogXzVisible.show=true 
              else dialogXzVisible.show=false
            })
            // console.log(type)
        break;
        default:
            console.log('默认')
    }
}
// 点击左侧更换下标
function changeIndex(index){
  visibleData.leftIndex=index
  getRightTableList()
}
// 获取左边 table title 数据
function getRightTableList() {

  getRightTable({itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType,...visibleQueryParams}).then(r=>{
      visibleData.rightTableList=r.data||[]
      getRightDataList()
     
    })
}
// 获取 table 内容数据
function getRightDataList(){
  getRightData({taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType,...visibleQueryParams}).then(r=>{
       visibleData.rightDataList=r.rows||[]
       visibleQueryParams.total=r.total
       itemInfoTj({taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id}).then(res=>{
        visibleData.tj.audit=res.data.audit||0
        visibleData.tj.count=res.data.count||0
        visibleData.tj.submit=res.data.submit||0


       })
    })
}
function changeInfoFormType(row,type){
   // type 1查看 2编辑（新增）
   infoForm.type=type
    infoForm.id=row&&row.id||""
let obj={itemId:visibleData.leftDataList[visibleData.leftIndex].id,id:row.id||"",selectType,taskId: visibleData.id}
  itemAuditField(obj).then(res=>{
    infoForm.status=res.data[0].value==2?'3':res.data[0].value
    infoForm.remark=res.data[1].value
  })
  getInfoForm(obj).then((res)=>{
    res.data.forEach((item) => {
      switch(item.fieldName){
        case  'year':
         item.disabled=true
          break;
        case 'budget_year' :
            item.disabled=true
          break;
        case 'actual_incurred' :
        item.disabled=true
          break;
          default:
          item.disabled=false
      }
      item.rules=[
        {
          required: item.fieldRequired==0,
          trigger: 'blur',
          message: '不能为空',
        },
      ]
      if(item.fieldType!=1) item.rules.push({ 
          validator: (rule, value, callback) => {
            if (!value&&item.fieldRequired==0) {
              return callback(new Error('不能为空'));
            }
            const num = Number(value);
            if (isNaN(num)) {
              return callback(new Error('请输入有效的数字'));
            }
            
            callback();
          },
          trigger: ['blur', 'change']
        })

    });
    let sindex= res.data.findIndex(item=>item.fieldName=='cost_description')
    let info=res.data.splice(sindex,1)
     infoForm.List=res.data.concat(info)
    dialogFormVisible.value=true



  })
  reportingLog({itemId:visibleData.leftDataList[visibleData.leftIndex].id,id:row.id||0,taskId: visibleData.id}).then(res=>{
    if(res.code!=200) return proxy.$message.error(res.msg)
    infoForm.logList=res.data
  })
}
  /** 查询角色列表 */
  function getList() {
    loading.value = true;
    listTask(proxy.addDateRange(queryParams.value, dateRange.value)).then(
      (response) => {
        roleList.value = response.rows;
        total.value = response.total;
        loading.value = false;
      }
    );
  }
  /** 查询部门下拉树结构 */
function getDeptTree() {
  deptTreeSelect().then(response => {
    deptOptions.value = response.data;
  });
};
  
  /** 搜索按钮操作 */
  function handleQuery() {
    queryParams.value.pageNum = 1;
    getList();
  }
  
  /** 重置按钮操作 */
  function resetQuery() {
    dateRange.value = [];
    proxy.resetForm("queryRef");
    handleQuery();
  }
  /** 多选框选中数据 */
  function handleSelectionChange(selection) {
    ids.value = selection.map((item) => item.id);
    single.value = selection.length != 1;
    multiple.value = !selection.length;
  }
  
  /** 角色状态修改 */
  function handleStatusChange(row) {
    let text = row.status === "0" ? "启用" : "停用";
    proxy.$modal
      .confirm('确认要"' + text + '""' + row.roleName + '"角色吗?')
      .then(function () {
        return changeRoleStatus(row.roleId, row.status);
      })
      .then(() => {
        proxy.$modal.msgSuccess(text + "成功");
      })
      .catch(function () {
        row.status = row.status === "0" ? "1" : "0";
      });
  }
  
  /** 修改角色 */
  function handleUpdate(row) {}
  getDeptTree();
  getList();
  </script>
  <style lang="scss" scoped>
  .dialogBox {
  display: flex;
  height: 85vh;
  justify-content: space-between;
  &>.left {
    width:30%;
    height: 100%;
    box-sizing:border-box ;
    // border: 1px solid #ddd;
    .title{
        border-bottom: 1px solid #ccc;
    }
    .content{
        width: 80%;
        box-sizing: border-box;
        height: 90%;
        // border: 1px solid #ccc;
        margin: 10px auto 0;
        overflow-y:auto ;
        .item{
            width: 100%;
            display: flex;
            height: 60px;
            align-items: center;
            padding:0 20px;
            font-size: 16px;
            cursor: pointer;
            &>div:first-child{
                width: 80%;
                white-space: nowrap;
                overflow: hidden;
                word-break: break-all;
                text-overflow: ellipsis;
                margin-right: 10px;
                
            }
        }
        .action{
            background: #eef3ff;
        }
    }

  }
  &>.right {
    width:65%;
    height: 100%;
    box-sizing:border-box ;
    // border: 1px solid #ddd;
    .countBox{
      display: flex;
      margin: 10px 0;
      &>div{
        margin-left: 20px;
      }

    }
    .btnBox{
        display: flex;
        justify-content: flex-end;
        padding:10px;
        box-sizing: border-box;
    }
  }
  .title{
    width: 100%;
    box-sizing: border-box;
    padding:10px 30px;
    font-weight: bold;
    font-size: 18px;
  }
 


}
.formBox{
    display:flex;justify-content: space-between;flex-wrap:wrap;
    &>div{
        width:49%;
    }
    &>div:last-child{
        width: 100%;
    }
  }

  .el-form-item__error {
  display: block!important;
}
  </style>
   