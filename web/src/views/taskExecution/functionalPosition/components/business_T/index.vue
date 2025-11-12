<!-- 职能岗位 -->
<template>
    <div style='width: 100%;height: 100%;overflow: auto;'>
                <div class='btnBox' >
                  <el-form
                      :model="visibleQueryParams"
                      ref="visibleQueryParamsRef"
                      v-show="showSearch"
                      :inline="true"
                      label-width="68px"
                    >
                     
                      <el-form-item label="企业名称" prop="deptName">
                        <el-input
                          v-model="visibleQueryParams.deptName"
                          placeholder="请输入"
                          clearable
                          style="width: 240px"
                        
                        />
                      </el-form-item>
                    
                      <el-form-item>
                        <el-button type="primary" icon="Search" @click="getRightDataList"
                          >搜索</el-button
                        >
                        <el-button icon="Refresh" @click="resetVisibleQuery">重置</el-button>
                      </el-form-item>
                    </el-form>
                    <div style="display: flex;justify-content: space-between;">
                        <div>
                          预算费用:{{ visibleData.tj.budget}} /{{visibleData.tj.allBudget}}&nbsp;&nbsp;&nbsp;&nbsp;
                          本年预算费用:{{visibleData.tj.budgetYear}}/{{visibleData.tj.allBudgetYear}}&nbsp;&nbsp;&nbsp;&nbsp;
                          本年实际发生费用:{{ visibleData.tj.actualIncurred}}/{{visibleData.tj.allActualIncurred}}&nbsp;&nbsp;&nbsp;&nbsp;
                          全年预计发生费用:{{ visibleData.tj.estimatedIncurred}}/{{visibleData.tj.allEstimatedIncurred}}&nbsp;&nbsp;&nbsp;&nbsp;
                          总填报部门: <span  @click="()=>{openType=1,openInfoShow=true}" style="text-decoration: underline;cursor: pointer;">{{ visibleData.tj.allCount}}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                          已填报部门:<span  @click="()=>{openType=2,openInfoShow=true}" style="text-decoration: underline;cursor: pointer;color: royalblue;">{{ visibleData.tj.submitCount}}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                          未填报部门:<span @click="()=>{openType=3,openInfoShow=true}"  style="text-decoration: underline;cursor: pointer; color: red;">{{ visibleData.tj.notCount}}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                          待审核部门 <span  @click="()=>{openType=4,openInfoShow=true}" style="text-decoration: underline;cursor: pointer;color: royalblue;">{{ visibleData.tj.notCheckCount}}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                          <el-dialog v-model="openInfoShow" width='60%' :title="openType==1?'总填报部门':openType==2?'已填报部门':openType==3?'未填报部门':'待审核部门'" draggable  :close-on-press-escape='false'>
                            <el-table  height='600' :data="openType==1?visibleData.allDepts:openType==2?visibleData.submitDepts:openType==3?visibleData.notDepts:visibleData.notCheckDepts" >
                              <el-table-column   align="center" label='公司'  prop="parentName" />
                              <el-table-column  align="center" label='部门'  prop="deptName" />
                            </el-table>
                          </el-dialog>

   
    
                        </div>
                        <div style="padding-right: 50px;margin-bottom:10px;display:flex;justify-content: flex-end;align-items: center">
                          <el-button type="primary" size="small"  @click="plBtn">批量审批</el-button>
                          <el-button
                            type="warning"
                            plain
                            icon="Download"
                            @click='handleExport'
                          size="small"
                              v-hasPermi="['budget:business:export']"
                            >导出</el-button>
                        </div>
                    </div>
                  
                    

        
                </div>
                <el-table  :data="visibleData.rightDataList" height='79%' row-key="deptId"
  @expand-change="handleNodeExpand"   :expand-row-keys="expandedRowKeys">

                 
                  <el-table-column type="expand">
                    <template #default="props">
                    
                      <!-- v-loading="props.row.loadDetails"  -->
                      <el-table :data="props.row.dataTable"  @selection-change="(selection)=>handleSelectionChange(props.$index,selection)"   style="width:98%;margin:0 auto" height='400px'  size='small'  >
                        <!-- @selection-change="handleSelectionChange" -->
                            <!-- <el-table-column  type="selection" width="55" align="center"   :selectable="selectable" /> -->
                            <!--   -->
                            <el-table-column type="selection" width="55" :selectable="selectable"/>

                            <el-table-column  width="120" align="center" label='部门' fixed="left"  prop="dept_name"  ></el-table-column>
                            <el-table-column  width="120" align="center" label='填报人'  prop="user_name"  ></el-table-column>
                   
                          
                            <template   v-for="(item,index) in  props.row.headTable" :key='index'>
                              <el-table-column  width="120" align="center"  :label='(visibleData.year+1)+"预算费用（元）"'  prop="budget" v-if="item.fieldName=='budget'"  > 
                                <template #default="scope">
                                    ¥ {{ formatNumber(scope.row.budget) }}  
                                </template>

                              </el-table-column>
                            <el-table-column  width="120" align="center" :label='visibleData.year+"年预算（元）"'  prop="budget_year"  v-else-if="item.fieldName=='budget_year'" >
                                <template #default="scope">
                                    ¥ {{ formatNumber(scope.row.budget_year) }}  
                                </template>
                            </el-table-column>
                            <el-table-column  width="120" align="center" :label='visibleData.year+"年实际发生额（元）"'  prop="actual_incurred" v-else-if="item.fieldName=='actual_incurred'" >
                                <template #default="scope">
                                    ¥ {{ formatNumber(scope.row.actual_incurred) }}  
                                </template>
                            </el-table-column>
                            <el-table-column  width="120" align="center"  :label='visibleData.year+"年预计发生费用（元）"'   prop="estimated_incurred" v-else-if="item.fieldName=='estimated_incurred'"  >
                                <template #default="scope">
                                    ¥ {{ formatNumber(scope.row.estimated_incurred) }}  
                                </template>

                            </el-table-column>
                                <el-table-column  width="140" align="center" label='状态'  prop="status" v-else-if="item.fieldName=='status'" >
                                  <template #default="scope">
                                    <dict-tag :options="budget_status" :value="scope.row.status" />
                                  </template>
                                </el-table-column>
                                <template v-else>
                                  <el-table-column
                                      :label="item.fieldDisplayName"
                                      prop=""
                                      align="center"
                                      width="160"
                                    v-if='item.fieldType!=4'
                                  >
                                  <template  #default="scope">
                                    
                                      <span>{{scope.row[item.fieldName]}}</span>
                                      
                                  </template>
                                
                                </el-table-column>
                                <el-table-column
                                      :label="item.fieldDisplayName"
                                      prop=""
                                      align="center"
                                      width="200"
                                      v-else
                                  >
                                  <template  #default="scope">
                                      <el-select
                                      v-model="scope.row[item.fieldName]"
                                      placeholder="请选择"
                                      clearable
                                      style="width: 160px"
                                      disabled
                                    >
                                      <el-option
                                          v-for="dict in item.dictDatas"
                                          :key="dict.value"
                                        :label="dict.dictLabel"
                                        :value="dict.dictValue"
                                      />
                                    </el-select>
                                  </template>
                                </el-table-column>
                              </template>

                            </template>
                      
                            <el-table-column  width="140" align="center" label='提交时间'  prop="create_time"  >
                              <template #default="scope">
                                {{ scope.row.create_time }}
                              </template>
                              </el-table-column>

                          
                            <el-table-column  
                            label="操作"
                            align="center"
                            width="100"
                            fixed="right"
                            >
                            <template #default="scope">
                            
                                <el-button
                                    link
                                    type="primary"
                                  @click='spBtn(scope.row,props.$index,1)'
                                   v-if='scope.row.status==2||scope.row.status==6'
                                  v-hasPermi="['budget:functional:audit']"
                                    >审批</el-button>
                                    <el-button
                                      link
                                      type="primary"
                                        @click='recalls(scope.row,props.$index)'
                                      v-if='scope.row.status==3'
                                      >撤回</el-button>
                                      <el-button
                                        link
                                        type="primary"
                                        @click='spBtn(scope.row,scope.$index,3)'
                                        v-if="scope.row.id"
                                        >查看审批</el-button>
                                  
                            </template>
                            </el-table-column>
                        </el-table>
                    </template>
                  </el-table-column>
                  <!-- <template   v-for="(item,index) in  visibleData.rightTableList" :key='index'>
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
                    </template> -->
                    <el-table-column  width="140" align="center" label='企业名称'  prop="deptName"  />
                    <el-table-column  width="140" align="center" label='未审核数量'  prop="reportNumber"  />

                    

                    <el-table-column  width="140" align="center" :label='(visibleData.year+1)+"预算费用（元）"' prop="budget"  >
                      <template #default="scope">
                          ¥ {{ formatNumber(scope.row.budget) }}  
                      </template>
                    </el-table-column>
                    <el-table-column  width="140" align="center" :label='visibleData.year+"年预算（元）"'  prop="budgetYear"  >
                      <template #default="scope">
                          ¥ {{ formatNumber(scope.row.budgetYear) }}  
                      </template>
                    </el-table-column>
                    <el-table-column   align="center" :label='visibleData.year+"年实际发生额（元）"'  prop="actualIncurred"  >
                      <template #default="scope">
                          ¥ {{ formatNumber(scope.row.actualIncurred) }}  
                      </template>
                    </el-table-column>
                    <el-table-column   align="center" :label='visibleData.year+"年预计发生费用（元）"'  prop="estimatedIncurred"  >
                      <template #default="scope">
                          ¥ {{ formatNumber(scope.row.estimatedIncurred) }}  
                      </template>
                    </el-table-column>
                 
                    
                  
                </el-table>
                <div style="width:98%;position: relative;">
                    <pagination
                    v-show="visibleQueryParams.total > 0"
                    :total="visibleQueryParams.total"
                    v-model:page="visibleQueryParams.pageNum"
                    v-model:limit="visibleQueryParams.pageSize"
                    @pagination="getRightDataList"
                    />
                </div>
                <el-dialog v-model="dialogPlshVisible" width='60%' :title="dialogPlshVisibleType==3?'审批流程':'审核'" draggable append-to-body  :close-on-press-escape='false'>
          <el-form  v-if="dialogPlshVisibleType==1"  :rules="infoRules" label-position="right" label-width="150" ref="plInfoForm" :model="infoForm">
                    <div  style='width:90%;margin:0 auto 10px'>
                      <!-- <h3 style="color:#000;font-weight:bold">审核意见</h3> -->
                      <el-form-item label="审核意见" prop="status">
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
            <!-- <div>

            </div> -->
            <div style="display:flex;justify-content:flex-end;width:100%;margin-top:40px" v-if="dialogPlshVisibleType==1" >
               
                    <el-button type="primary" @click='plSave'>确定</el-button>
                    <el-button type="info"  @click="()=>dialogPlshVisible=false">取消</el-button>
                
              
            </div>
            <div style="max-height:500px;overflow:auto" v-if="dialogPlshVisibleType==3">
              <el-timeline>
                        <template v-for="(item,index) in logList" :key="index">
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
                            <div style="margin-bottom:10px" v-if='item.status==1||item.status==4||item.status==5'>
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
            
        </el-dialog>
    </div>
  </template>
   
   <script setup >
import {itemInfoTj,itemAuditField,reportingLog,getXZ,updateXZ,getLeftList,getRightTable,getRightData,getInfoForm,budgetSave,delTableItem,updateStatus,getShInfoList} from "@/api/taskExecution/index"
import {getDeptDataList} from "@/api/taskExecution/functionalPosition/index"
import { toReactive } from "@vueuse/core";
import { nextTick, toRaw,getCurrentInstance, ref } from "vue";
import {useLog} from "../../useLog/index"
const {logList,getLogList}=useLog()
const emit=defineEmits([''])

const router = useRouter();
const { proxy ,ctx} = getCurrentInstance();
const { budget_status,result_status ,completion_status } = proxy.useDict("budget_status","result_status","completion_status");

  const showSearch = ref(true);
  const ids = ref([]);

  const selectType=2

 
  const openInfoShow=ref(false)
  const openType=ref(1)
  // 弹窗分页
 const dialogVisible=ref(false)
const visibleQueryParams=reactive({
  pageNum: 1,
  pageSize: 10,
  total: 10,
  deptId: '',
  status:"",
  deptName:""
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
const visibleData=reactive({
    leftIndex:0,
    id:"",
    leftTitle:"",
    tableName:"",
    rightTitle:'xxxx部门-票据印刷费-预算申请',
    type:1 , // type 1查看 2编辑（新增）
    leftDataList:[],
    rightDataList:[],
    rightTableList:[],
    query:"",
    year:"",
    tj:{
      audit:0,
      count:0,
      submit:0,
      budget:0,
      budgetYear:0,
      actualIncurred:0,
      estimatedIncurred:0,
      allActualIncurred:0,
      allBudget:0,
      allBudgetYear:0,
      allCount:0,
      allEstimatedIncurred:0,
  
      notCount:0,
      submitCount:0,
      notCheckCount:0,
    },
    allDepts:[],
    notDepts:[],
    submitDepts:[],
    notCheckDepts:[]
 
})

const infoForm=reactive({
    type:1,  // type 1查看 2编辑（新增）
    name:"",
    id:'',
    List:[],
    logList:[],
    remark:'',
    status:3,
    Pindex:false
    
})

const dialogXzVisible=reactive({
    title:"",
    show:false,
    value:"",
    checked:false
})

// 添加千分位格式化函数
function formatNumber(num) {
  // 如果值为空或不是数字，返回'0.00'
  if (num === null || num === undefined || num === '' || isNaN(num)) {
    return '0.00';
  }
  // 使用Intl.NumberFormat进行千分位格式化，保留两位小数
  return new Intl.NumberFormat('zh-CN', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  }).format(parseFloat(num));
}

watch(()=>infoForm.status,(newVal,oldVal)=>{
  if(newVal==3) infoForm.remark="通过"
   else infoForm.remark=""
})
const expandedRowKeys=ref([])

async function handleNodeExpand(node, expandedRows){
   node= toReactive(node)
   ids.value=[]
  if (expandedRows.length) {
    // 如果有行被展开，则只保留当前行的expandedRowKeys
  
    expandedRowKeys.value = [node.deptId];
  } else {
    // 如果没有行被展开，清空expandedRowKeys
    expandedRowKeys.value = [];
  }
  if( expandedRowKeys.value.length<=0) return
  // node.loadDetails=true
  // if(node.hasOpen==false){
   let r= await getRightTable({itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType,taskId:visibleData.id})
      node.headTable=r.data||[]
    let res=await getRightData({taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,parentId:node.deptId,selectType,pageSize:999999})
      node.dataTable=res.rows||[]
      // node.loadDetails=false
        // node.hasOpen=true
        // console.log(node)

  // }else{
    // node.hasOpen=false
    // node.loadDetails=false  
    // console.log(node)
  // }


  
}


function handleExport() {
     proxy.download('budget/reporting/export', {
       taskId:visibleData.id,tableName:visibleData.leftDataList[visibleData.leftIndex].tableName,itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType:selectType,exportType:2
     }, `填报明细${new Date().getTime()}.xlsx`)
   }



// 批量审核弹窗
const dialogPlshVisible=ref(false)
const dialogPlshVisibleType=ref()
const plInfoForm=ref()

function plBtn(){
  if(ids.value.length==0){
    proxy.$modal.msgError("请选择要审核的数据")
    return
  }

  infoForm.id=''
  dialogPlshVisible.value=true
   infoForm.remark=''
   infoForm.status=""
   dialogPlshVisibleType.value=1
}


function plSave(){

let obj={
  itemId:visibleData.leftDataList[visibleData.leftIndex].id,
  taskId:visibleData.id,
  id: infoForm.id|| ids.value.join(','),
  status:infoForm.status,
  remark:infoForm.remark
}
plInfoForm.value.validate((valid) => {
  if (valid) {
    updateStatus(obj).then(async res=>{
      if(res.code!=200) return proxy.$message.error(res.msg)
      proxy.$message.success("提交成功")
      dialogPlshVisible.value=false
      await getRightDataList()
      // visibleData.rightDataList[infoForm.Pindex].loadDetails=true
    
      let r= await getRightTable({itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType,taskId:visibleData.id})
      let re=await getRightData({taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,parentId:visibleData.rightDataList[infoForm.Pindex].deptId,selectType,pageSize:999999})
      visibleData.rightDataList[infoForm.Pindex]['dataTable']=re.rows||[]
      visibleData.rightDataList[infoForm.Pindex]['headTable']=r.data||[]

      // visibleData.rightDataList[infoForm.Pindex].loadDetails=false
     
    })
   
  } else {
    console.log('error submit!')
    return false
  }
})
}
function recalls(row,pindex){


  let obj={
  itemId:visibleData.leftDataList[visibleData.leftIndex].id,
  taskId:visibleData.id,
  id: row.id,
  status:2,
  remark:'职能审核撤回'
}
updateStatus(obj).then(async res=>{
      if(res.code!=200) return proxy.$message.error(res.msg)
      proxy.$message.success("撤回成功")
      dialogPlshVisible.value=false
      await getRightDataList()
      // visibleData.rightDataList[infoForm.Pindex].loadDetails=true
    
      let r= await getRightTable({itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType,taskId:visibleData.id})
      let re=await getRightData({taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,parentId:visibleData.rightDataList[pindex].deptId,selectType,pageSize:999999})
      visibleData.rightDataList[pindex]['dataTable']=re.rows||[]
      visibleData.rightDataList[pindex]['headTable']=r.data||[]

      // visibleData.rightDataList[infoForm.Pindex].loadDetails=false
     
    })
}


  
  // infoForm.id=row.id
  // dialogPlshVisible.value=true
  // infoForm.remark=''
  // infoForm.status=""
  // if(!row.id && ids.value.length==0) return proxy.$message.error('请选择审核项')
  // ||ids.value.join(',')
async function spBtn(row,pindex,type){

  if(!row.id && ids.value.length==0) return proxy.$message.error('请选择审核项')
  await getLogList(visibleData.leftDataList[visibleData.leftIndex].id,row.id, visibleData.id)
  infoForm.id=row.id||ids.value.join(',')
   infoForm.Pindex=pindex
   
   dialogPlshVisible.value=true
   infoForm.remark=''
   infoForm.status=""
   dialogPlshVisibleType.value=type
   


}
// 禁用选择
function selectable(row, index){
 

  return  row.status==3||row.status==5?false:true

}

function changeDialogVisible(row,type){
    // type 1查看 2编辑（新增）
    visibleData.id=row.id
    visibleData.leftTitle=row.name
    visibleData.leftIndex=0
    visibleData.type=type
    dialogVisible.value=true
    visibleQueryParams.deptId=""
    expandedRowKeys.value=[]
    getLeftList({taskId:row.id,selectType,reportingType:1}).then(r=>{
      r.data.forEach(item=> item['show']=true)
      visibleData.leftDataList=r.data||[]
      if( visibleData.leftDataList.length>0)  getRightDataList()
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

// 获取左边展开 table title 数据
function getRightTableList(row) {

  getRightTable({taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType,...visibleQueryParams}).then(r=>{
 
      visibleData.rightTableList=r.data||[]
      getRightDataList()
      
     
    })
}
// 获取 table 内容数据
function getRightDataList(){
  let obj={taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType}


  getDeptDataList({...obj,...visibleQueryParams}).then(r=>{
    
     
      r.rows.forEach(item=>{
        item['headTable']=[]
        item['dataTable']=[]
        // item['loadDetails']=true
        // item['hasOpen']=false
      })
       visibleData.rightDataList=r.rows||[]
       visibleQueryParams.total=r.total||0
       itemInfoTj(obj).then(res=>{
        visibleData.tj.audit=res.data.audit||0
        visibleData.tj.count=res.data.count||0
        visibleData.tj.submit=res.data.submit||0
        visibleData.tj.budget=res.data.budget||0
        visibleData.tj.budgetYear=res.data.budgetYear||0
        visibleData.tj.actualIncurred=res.data.actualIncurred||0
        visibleData.tj.estimatedIncurred=res.data.estimatedIncurred||0
        visibleData.tj.allActualIncurred=res.data.allActualIncurred||0
        visibleData.tj.allBudget=res.data.allBudget||0
        visibleData.tj.allBudgetYear=res.data.allBudgetYear||0
        visibleData.tj.allCount=res.data.allCount||0
        visibleData.tj.allEstimatedIncurred=res.data.allEstimatedIncurred||0
       
       })
       getShInfoList(obj).then(res=>{
        visibleData.tj.allCount=res.data.allCount||0
        visibleData.tj.notCount=res.data.notCount||0
        visibleData.tj.submitCount=res.data.submitCount||0    
        visibleData.tj.notCheckCount=res.data.notCheckCount||0    

        
        visibleData.allDepts=res.data.allDepts||[]
        visibleData.notDepts=res.data.notDepts||[]
        visibleData.submitDepts=res.data.submitDepts||[]
        visibleData.notCheckDepts=res.data.notCheckDepts||[]



       })

       
    })
}



  

  
  function resetVisibleQuery(){
    
    proxy.resetForm("visibleQueryParamsRef");
    getRightDataList()
  }

  /** 多选框选中数据 */
  function handleSelectionChange(Pindex,selection) {
  
    infoForm.Pindex=Pindex
    ids.value = selection.map((item) => item.id);
  
  }
  
  function changeVisibleData(leftIndex,tableName,type,leftDataList,id,year){
        visibleData.leftIndex=leftIndex
        visibleData.tableName=tableName
        visibleData.type=type
        visibleData.leftDataList=leftDataList
        visibleData.id=id
        visibleData.year=year
        expandedRowKeys.value=[]
        ids.value=[]
        visibleData.rightDataList=[]
        visibleQueryParams.pageSize=10
        visibleQueryParams.pageNum=1
        getRightTableList()
    }
    
   defineExpose({
    changeVisibleData,
   })
  



  </script>
  <style lang="scss" scoped>
  :deep(.el-table__inner-wrapper) {
    height:100% !important
  }

.btnBox{
        padding:10px;
    }
  .el-form-item__error {
  display: block!important;
}
  </style>
   