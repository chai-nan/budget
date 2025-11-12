<!-- 业财部门 -->
<template>
  <div style="width:100%;height: 100%;overflow: auto;">
        <div class='btnBox' >
          <div style="width:100%;display: flex;justify-content: space-between;">
            <div>
                  预算费用:{{ visibleData.tj.budget}} /{{visibleData.tj.allBudget}}&nbsp;&nbsp;&nbsp;&nbsp;
                  本年预算费用:{{visibleData.tj.budgetYear}}/{{visibleData.tj.allBudgetYear}}&nbsp;&nbsp;&nbsp;&nbsp;
                  本年实际发生费用:{{ visibleData.tj.actualIncurred}}/{{visibleData.tj.allActualIncurred}}&nbsp;&nbsp;&nbsp;&nbsp;
                  全年预计发生费用:{{ visibleData.tj.estimatedIncurred}}/{{visibleData.tj.allEstimatedIncurred}}&nbsp;&nbsp;&nbsp;&nbsp;
                </div>
              <div style="padding-right: 50px;margin-bottom:10px;display:flex;justify-content: flex-end;align-items: center">
                <el-button type="primary" size="small" @click="plBtn">批量审批</el-button>
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
        <!-- @selection-change="handleSelectionChange" -->
        <el-table  :data="visibleData.rightDataList"  height='80%'  row-key="deptId" @expand-change="handleNodeExpand"   :expand-row-keys="expandedRowKeys">
          <!-- <el-table-column :selectable="selectable" type="selection" width="55" align="center"  >
            </el-table-column> -->
            <el-table-column type="expand">
              <template #default="props">
                <el-table :data="props.row.dataTable" @selection-change="(selection)=>handleSelectionChange(props.$index,selection)"    style="width:98%;margin:0 auto" height='400px'  size='small' >
                    <el-table-column  type="selection" width="55" align="center"  :selectable="selectable"  />
                 
                    <el-table-column  width="120" align="center" fixed="left" label='部门'  prop="dept_name"  />
                    <!-- <el-table-column  width="200" align="center" label='预算表名称'  prop="budget"  /> -->
                    <el-table-column  width="120" align="center" fixed="left" label='填报人'  prop="user_name"  />
                    <template    v-for="(item,index) in  props.row.headTable" :key='index'>
                      <el-table-column  width="140" align="center" label='状态'  prop="status" v-if="item.fieldName=='status'" >
                              <template #default="scope">
                                <dict-tag :options="budget_status" :value="scope.row.status" />
                              </template>
                            </el-table-column>
                           <el-table-column  width="120" align="center"  :label='(visibleData.year+1)+"预算费用（元）"'  prop="budget" v-else-if="item.fieldName=='budget'"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.budget) }}
                            </template>
                           </el-table-column>
                            <el-table-column  width="120" align="center" :label='visibleData.year+"年预算（元）"'  prop="budget_year"  v-else-if="item.fieldName=='budget_year'" >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.budget_year) }}
                            </template>
                            </el-table-column>
                            <el-table-column  width="120" align="center" :label='visibleData.year+"年实际发生额（元）"'  prop="actual_incurred"  v-else-if="item.fieldName=='actual_incurred'" >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.actual_incurred) }}
                            </template>
                            </el-table-column>
                            <el-table-column  width="120" align="center"  :label='visibleData.year+"年预计发生费用（元）"'   prop="estimated_incurred" v-else-if="item.fieldName=='estimated_incurred'"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.estimated_incurred) }}
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
                              @click='spBtn(scope.row,props.$index)'
                            v-if='scope.row.status==3'
                              v-hasPermi="['budget:financial:audit']"
                            >审批</el-button>
                            
                    </template>
                    </el-table-column>
                </el-table>
              </template>
            </el-table-column>
            <el-table-column  width="140" align="center" label='企业名称'  prop="deptName"  />


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
            <!-- <template   v-for="(item,index) in  visibleData.rightTableList" :key='index'>
                <el-table-column
                    :label="item.fieldDisplayName"
                    prop=""
                    :show-overflow-tooltip="true"
                    v-if='item.fieldDisplay==0'
                    min-width='100'
                >
                <template  #default="scope">
                    <span > {{scope.row[item.fieldName] }}</span>
                    
                </template>
              
              </el-table-column>
              </template> -->

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

      <!-- 批量审核弹窗 -->
      <el-dialog v-model="dialogPlshVisible" width='60%' title="审核" draggable append-to-body  :close-on-press-escape='false'>
        <el-form  :rules="infoRules" label-position="right" label-width="150" ref="plInfoForm" :model="infoForm">
                  <div  style='width:90%;margin:0 auto 10px'>
                    <!-- <h3 style="color:#000;font-weight:bold">确认意见</h3> -->
                    <el-form-item label="审核意见" prop="status">
                      <el-radio-group v-model="infoForm.status" class="ml-4">
                      <el-radio label="5" size="large">通过</el-radio>
                      <el-radio label="6" size="large">不通过</el-radio>
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item label="审批意见" prop="remark">
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
          <div style="max-height:500px;overflow:auto">
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

import {itemInfoTj,itemAuditField,reportingLog,getXZ,updateXZ,getLeftList,getRightTable,getRightData,getInfoForm,budgetSave,delTableItem,updateStatus} from "@/api/taskExecution/index"
import {saveVersion,budgetSummary} from '@/api/taskExecution/finance/index'

import {getTask, listTask,updateTask,addTask} from "@/api/BM/task";
import { defineComponent, reactive, watch } from "vue";
import { toReactive } from "@vueuse/core";

import {getDeptDataList} from "@/api/taskExecution/functionalPosition/index"
import {useLog} from "../../../functionalPosition/useLog/index"
const {logList,getLogList}=useLog()
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
const selectType=3
const refInfoForm =ref()
const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: undefined,
    //  roleKey: undefined,
    year: undefined,
  },
  rules: {},
});

const { queryParams, form, rules } = toRefs(data);
const dialogBbVisibleData=reactive({
  show:false,
  value:"",
  id:"",
})
// 弹窗分页
const dialogVisible=ref(false)
const visibleQueryParams=reactive({
pageNum: 1,
pageSize: 10,
total: 10,
deptId: undefined,
status:""
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
    yaer:"",
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
  },
  query:"",
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
    Pindex:"",
    
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
watch(()=>infoForm.status,(newVal,oldVal)=>{

  if(newVal==5) infoForm.remark="通过"
  else  infoForm.remark=""
})
// 汇总 数据
const hzData=reactive({
    id:'',
    show:false,
    name:'1',
    tabheadData:[{name:'id',key:'key1'},{name:'2024',key:'name',children:[{name:'2',key:'name-1'},{name:'3',key:'name-2'}]},{id:2,name:'测试',key:'name-3'}] ,
    tabdataList:[{key1:'我是第一个',name:'diyige','name-1':'第二个','name-2':'第三个','name-3':'第四个'}],
   
})

function plBtn(){
if(ids.value.length==0){
  proxy.$modal.msgError("请选择要审核的数据")
  return
}

infoForm.id=''
dialogPlshVisible.value=true
 infoForm.remark=''
 infoForm.status=""
}

function clickHz(row){
  hzData.id=row.id
  budgetSummary({type:1,taskId:hzData.id}).then(res=>{
    hzData.tabdataList=res.data.titleDate
    hzData.tabheadData=res.data.titleName
    hzData.name='1'
    hzData.show=true
  })


}


function handleExport() {
     proxy.download('budget/reporting/export', {
       taskId:visibleData.id,tableName:visibleData.leftDataList[visibleData.leftIndex].tableName,itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType:selectType,exportType:2
     }, `填报明细${new Date().getTime()}.xlsx`)
   }

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
node.loadDetails=true
// if(node.hasOpen==false){
 let r= await getRightTable({itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType,taskId:visibleData.id})
    node.headTable=r.data||[]
  let res=await getRightData({taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,parentId:node.deptId,selectType,pageSize:999999})
    node.dataTable=res.rows||[]
    node.loadDetails=false
      // node.hasOpen=true
      // console.log(node)

// }else{
  // node.hasOpen=false
  // node.loadDetails=false  
  // console.log(node)
// }



}
async function spBtn(row,pindex){
  await getLogList(visibleData.leftDataList[visibleData.leftIndex].id,row.id, visibleData.id)
 
if(!row.id && ids.value.length==0) return proxy.$message.error('请选择审核项')
 infoForm.id=row.id||ids.value.join(',')
 dialogPlshVisible.value=true
 infoForm.Pindex=pindex
 infoForm.remark=''
 infoForm.status=""


}


// 批量审核
const dialogPlshVisible=ref(false)
const plInfoForm=ref()
function changePLsh(){

if(ids.value.length==0){
  proxy.$modal.msgError("请选择要审核的数据")
  return
}
infoForm.status='5'
dialogPlshVisible.value=true

}
// 批量审核
// function plSave(){

// let obj={
//   itemId:visibleData.leftDataList[visibleData.leftIndex].id,
//   taskId:visibleData.id,
//   id: ids.value.join(','),
//   status:infoForm.status,
//   remark:infoForm.remark
// }
// plInfoForm.value.validate((valid) => {
//   if (valid) {
//     updateStatus(obj).then(res=>{
//       if(res.code!=200) return proxy.$message.error(res.msg)
//       proxy.$message.success("提交成功")
//       getLeftList({taskId:visibleData.id,selectType}).then(r=>{
            // r.data.forEach(item=> item['show']=true)
//         visibleData.leftDataList=r.data||[]
//         dialogPlshVisible.value=false
//         if( visibleData.leftDataList.length>0)  getRightDataList()
//       })
//     })
   
//   } else {
//     console.log('error submit!')
//     return false
//   }
// })
// }
function plSave(){

let obj={
itemId:visibleData.leftDataList[visibleData.leftIndex].id,
taskId:visibleData.id,
id: infoForm.id|| ids.value.join(','),
status:infoForm.status,
remark:infoForm.remark
}
plInfoForm.value.validate(async (valid) => {
if (valid) {
  updateStatus(obj).then(async res=>{
    if(res.code!=200) return proxy.$message.error(res.msg)
    proxy.$message.success("提交成功")
    dialogPlshVisible.value=false
    console.log(1)
    await  getRightDataList()
  
    visibleData.rightDataList[infoForm.Pindex].loadDetails=true
    let r= await getRightTable({itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType,taskId:visibleData.id})
    let re=await getRightData({taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,parentId:visibleData.rightDataList[infoForm.Pindex].deptId,selectType,pageSize:999999})
  
    visibleData.rightDataList[infoForm.Pindex].headTable=r.data||[]
    visibleData.rightDataList[infoForm.Pindex].dataTable=re.rows||[]

    visibleData.rightDataList[infoForm.Pindex].loadDetails=false
  
  
   
 
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
 return row.status==5?false:true
}
watch(()=>visibleData.query,(newValue, oldValue)=>{
if(!newValue) {
  visibleData.leftDataList.forEach(item=>item['show']=true)
}else{
  visibleData.leftDataList.forEach(item=>{
   
    if(!item.tableDisplayName.toLowerCase().includes(newValue.toLowerCase())){
      item['show']=false
    }
  })
}

})




// 获取左边 table title 数据
function getRightTableList() {

  getRightTable({itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType,...visibleQueryParams,taskId:visibleData.id}).then(r=>{
      visibleData.rightTableList=r.data||[]
   
    
      getRightDataList()
    })
}
// 获取 table 内容数据
function getRightDataList(){
  getDeptDataList({taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType,...visibleQueryParams}).then(r=>{
    r.rows.forEach(item=>{
      item['headTable']=[]
      item['dataTable']=[]
      item['loadDetails']=true
      // item['hasOpen']=false
    })
       visibleData.rightDataList=r.rows||[]
       visibleQueryParams.total=r.total
       
      
    })
    itemInfoTj({taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType}).then(res=>{
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
.dialogBox {
display: flex;
height: 85vh;
justify-content: space-between;
&>.left {
  width:15%;
  height: 100%;
  box-sizing:border-box ;
  // border: 1px solid #ddd;
     color: #000;
  .title{
      border-bottom: 1px solid #ccc;
      display: flex;
      justify-content: space-between;
      align-items: center;
  }
  .content{
      width: 80%;
      box-sizing: border-box;
      height: 90%;
      // border: 1px solid #ccc;
      margin: 0px auto 0;
      overflow-y:auto ;
      .item{
          width: 100%;
          display: flex;
          height: 30px;
          line-height: 30px;
          align-items: center;
          padding:0 20px;
          font-size: 14px;
          cursor: pointer;
          &>div:first-child{
              width: 100%;
              white-space: nowrap;
              overflow: hidden;
              word-break: break-all;
              text-overflow: ellipsis;
              // margin-right: 10px;
          }
      }
      .action{
          background: #eef3ff;
      }
  }

}
&>.right {
  width:85%;
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
      // justify-content: flex-end;
      padding:10px;
      box-sizing: border-box;
  }
}
.title{
  width: 90%;
  box-sizing: border-box;
  margin:0 auto;
  padding:10px 10px;
  font-weight: bold;
  font-size: 15px;
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
 