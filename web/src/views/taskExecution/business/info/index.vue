<template>
 <div class="app-container box">
    <!-- -->
    <div class="hb" @mousedown="startDrag" :style="{left:isopen?'calc(15% + '+ (leftFlex+'px')+')':'1%' }"><el-icon @click="changeIsopen"><Fold v-if="isopen" /> <Expand v-else/></el-icon></div>
    <!-- <div class="splitter"  :style="{left:isopen?'calc(15% + '+ (leftFlex+'px')+')':'1%'}"></div> -->
   
        <div class="left" :style="{ transition: 'all 0.2s ease',width:isopen? 'calc(15% + '+ (leftFlex+'px')+')':'0%',overflow:'hidden' ,opacity:isopen? '1':'0',minWidth:'200px'}">
        
            <!-- 展开目录 -->
            
             <div class='title' >
                  <div style="width:45%;">填报目录</div>
               
                 <div style="display: flex;align-items: center;width:55%;justify-content: flex-end;">
                  <el-icon style="cursor: pointer;margin-right: 10px;" @click="dialogXzVisible.show=true"><WarningFilled /></el-icon>
                  <el-button type="primary" size="small"  @click="getShData">审核人</el-button>
                 </div>
              </div>
             <el-input placeholder="搜索" v-model="visibleData.query" size="small" style="margin: 10px 5%;width:90%;;"></el-input>

              <div class="content">
                <template v-for="(item,index) in  visibleData.leftDataList" :key="item.id">
                  <div  v-if="item.show" @click="changeIndex(index,item.tableName,item.reportingType)"  :class="visibleData.leftIndex==index? 'item action':'item'" >
                    <div style="display: flex;align-items: center;width:100%">
                      <el-tooltip
                      effect="dark"
                     
                        > 
                       
                        <template #content> {{item.tableDisplayName}} — {{ item.userName }}<br /><div v-text="item.remark"></div></template>
                        <el-icon style="margin-right: 5px;"><QuestionFilled/></el-icon>
                       </el-tooltip>
                       <div>
                        {{item.tableDisplayName}} 
                       </div> 
                    
                    </div>
                      <!-- <el-tag :type="item.isReporting==0?'error':'success'">{{item.isReporting==0?'未提交':'已提交'}}</el-tag> -->
                       <div style="color: red;font-weight: bold;margin-right: 10px;" v-if="item.isReject>0">×</div>
                       <div style="color: green;font-weight: bold;" v-if="item.isReporting>0">√</div>
                  </div>
                </template>
              </div>
        </div>
        
        
        <div class="right" :style="{width:isopen?'calc(85% - '+ (leftFlex+'px')+')':'99%' }">   

           <bussinessT ref="bussinessTRef" v-show='visibleData.reportingType==1' @clickSpInfo="clickSpInfo" />
           <capitalT ref="capitalTRef" v-show='visibleData.reportingType==2' @clickSpInfo="clickSpInfo" />

            

            
        </div> 
         <!-- 预选填报须知 -->
         <el-dialog destroy-on-close v-model="dialogXzVisible.show" width='60%' :title="dialogXzVisible.title" draggable append-to-body @close="changeTips" :close-on-press-escape='false'>
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
        <!-- 审批流程 -->
        <el-dialog destroy-on-close v-model="logListShow" width='60%' title="审批流程" draggable append-to-body :close-on-press-escape='false'>
                <div style='width:80%;margin:0 auto 10px;height: 60vh;overflow-y:auto;'>
                  
                    <el-timeline v-if="logList.length>0">
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
                  <el-empty description="暂无流程" v-else />
                </div>
        </el-dialog>
        <!-- 审核说明 -->
        <el-dialog destroy-on-close v-model="shData.show" width='60%' title="审核人员信息" draggable append-to-body :close-on-press-escape='false'>
          <el-table :data="visibleData.leftDataList" height="80vh">
        <!-- <el-table-column type="selection" width="55" align="center" /> -->
  
        <el-table-column label="预算表名称" align="center" prop="tableDisplayName" />
      
     
        

  
        <el-table-column label="审核人" align="center" prop="userName" />
        <el-table-column label="说明" width="200" align="center" prop="remark"  show-overflow-tooltip />
  
        
      </el-table>
        </el-dialog>
        
  </div>
</template>

<script setup>
import { defineAsyncComponent, nextTick, onMounted, watch } from "vue";
import usePermissionStore from '@/store/modules/permission'
import useTagsViewStore from '@/store/modules/tagsView'
import {} from "@/api/BM/item";

import {  deptTreeSelect } from "@/api/system/user";
import {deleteItem,reportingLog,getXZ,updateXZ,getLeftList,getRightTable,getRightData,getInfoForm,budgetSave,delTableItem,updateStatus,addRightData,getDeptList,getDeptActualInfo} from "@/api/taskExecution/index"
// import bussinessT from "../components/business_T/index.vue"
const bussinessT= defineAsyncComponent(() => import("../components/business_T/index.vue"))
const capitalT= defineAsyncComponent(() => import("../components/capital_T/index.vue"))

const bussinessTRef=ref()
const capitalTRef=ref()
const { proxy } = getCurrentInstance();
const { field_type,item_type ,budget_status} = proxy.useDict("field_type","item_type" ,'budget_status');
const router = useRouter();
const route= useRoute();
const PermissionStore=usePermissionStore()
const isopen=ref(true)
const routes = computed(() => PermissionStore.routes);
const visitedViews = computed(() => useTagsViewStore().visitedViews);
const loading = ref(true);
const showSearch = ref(true);
const deptOptions = ref(undefined);
const logList=ref([])
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

const bTypeId=ref()
const leftIndex=ref(0)
const ids=ref([])
const dialogXzVisible=reactive({
    title:"",
    show:false,
    value:"",
    checked:false
})

const selectType=1
const visibleData=reactive({
    leftIndex:0,
    id:route.query.id,
    reportingType:1,
    tableName:"",
    leftTitle:"",
    rightTitle:'',
    type:route.query.type,  // type 1查看 2编辑（新增）
    leftDataList:[],
    rightDataList:[],
    rightTableList:[],
    budget:0,
    budget_year:0,
    actual_incurred:0,
    estimated_incurred:0,
    query:"",

})
const visibleQueryParams=reactive({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  show:false
})
const shData=reactive({
  show:false,
  data:[]
})

const deptList=ref([])
const logListShow=ref(false)
const isDragging=ref(false)
const initialX=ref(0)
const leftFlex=ref(0)
const rightFlex=ref(0)
const initialLeftFlex=ref(0)
const getShData=()=>{
  shData.show=true
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
function startDrag(event) {
      console.log(event.clientX)
      isDragging.value = true;
      initialX.value = event.clientX;
      initialLeftFlex.value = leftFlex.value;
      document.addEventListener('mousemove', drag);
      document.addEventListener('mouseup', endDrag);
    }
function  drag(event) {
      if (!isDragging.value) return;
      
      const delta = event.clientX - initialX.value;
      console.log(leftFlex.value<0&&leftFlex.value >-160)
    
     
      
      leftFlex.value =  initialLeftFlex.value + delta ;
      if(leftFlex.value <=-160 )  leftFlex.value=-160
     
     
    }
function endDrag() {
      isDragging.value = false;
      document.removeEventListener('mousemove', this.drag);
      document.removeEventListener('mouseup', this.endDrag);
    }

// watch(()=>visibleData.rightDataList,(newValue, oldValue)=>{
//     let budget=0,budget_year=0,actual_incurred=0,estimated_incurred=0
//     try{
//         newValue.forEach((item)=>{
//       if(item.budget)  budget=Number(budget)+ Number(item.budget)
//       if(item.budget_year)   budget_year=Number(budget_year)+ Number(item.budget_year)
//       if(item.actual_incurred)  actual_incurred=Number(actual_incurred)+ Number(item.actual_incurred)
//       if(item.estimated_incurred)  estimated_incurred=Number(estimated_incurred)+ Number(item.estimated_incurred)
//     })
//     } catch (error) {
//         proxy.$message.error("请注意数字和字符串")
//     }
//     visibleData.budget=budget
//     visibleData.budget_year=budget_year
//     visibleData.actual_incurred=actual_incurred
//     visibleData.estimated_incurred=estimated_incurred
   
   

// },{deep:true})
watch([()=>visibleData.leftIndex,()=>visibleData.tableName,()=>visibleData.type,()=>visibleData.leftDataList],(newValue, oldValue)=>{
  
  if(bussinessTRef.value&&bussinessTRef.value.changeVisibleData &&visibleData.reportingType==1) bussinessTRef.value.changeVisibleData(visibleData.leftIndex,visibleData.tableName,visibleData.type,visibleData.leftDataList)
  if(capitalTRef.value&&capitalTRef.value.changeVisibleData &&visibleData.reportingType==2) capitalTRef.value.changeVisibleData(visibleData.leftIndex,visibleData.tableName,visibleData.type,visibleData.leftDataList)
 
},{deep:true})

onMounted(() => {
    // console.log(route)
    chageTitle()
   
    getXZ(route.query.id).then(res=>{
              dialogXzVisible.title=route.query.name
              dialogXzVisible.value=res.data.reportingExplain
              if(res.data.isTips==0)dialogXzVisible.show=true 
              else dialogXzVisible.show=false
            })
           
    getLeftList({taskId:route.query.id,selectType}).then(r=>{
      
      r.data.forEach(item=> item['show']=true)
      visibleData.leftDataList=r.data||[]
      visibleData.leftIndex=0
      visibleData.reportingType=visibleData.leftDataList[visibleData.leftIndex].reportingType
      visibleData.tableName=visibleData.leftDataList[visibleData.leftIndex].tableName
      // if( r.data.length>0) {
        // getRightTableList()
        
      // } 
     
    })
});

function changeIsopen(){
  isopen.value=!isopen.value
  if (isopen.value){
    leftFlex.value=0
  }
}
function clickDelete(row,index){
  if(row.id) {
    deleteItem({itemId:visibleData.leftDataList[visibleData.leftIndex].id,id:row.id}).then(res=>{
      if(res.code==200) {
      
      
        proxy.$message.error('删除成功！')
  
        getRightTableList()
        
     
      }
    })
  }else{
    visibleData.rightDataList.splice(index,1)
  }


}
function clickSpInfo(row){
  console.log(row)
  reportingLog({itemId:visibleData.leftDataList[visibleData.leftIndex].id,id:row.id||0,taskId: route.query.id}).then(res=>{
    if(res.code!=200) return proxy.$message.error(res.msg)
    logList.value=res.data
    logListShow.value=true
  })
}
function handleExport() {
  proxy.download('budget/reporting/export', {
    taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id
  }, `填报明细${new Date().getTime()}.xlsx`)
}
// 禁用选择
function selectable(row, index){

 return row.id!=''&&row.status==1|| row.status==4
}
function changeDept(deptid,index){
    if(!deptid)return
  
    getDeptActualInfo({itemId:visibleData.leftDataList[visibleData.leftIndex].id,
    taskId:visibleData.id,dept_id:deptid}).then(res=>{
    visibleData.rightDataList[index].actual_incurred=res.data.actual_incurred||0
    visibleData.rightDataList[index].estimated_incurred=res.data.estimated_incurred||0

        
    })

}
function save(row,index){
  let obj={
    itemId:visibleData.leftDataList[visibleData.leftIndex].id,
    taskId:visibleData.id,
    ...row
  }
  if(!row.budget||!row.budget_year) return proxy.$message.error('预算费用、本年预算必填！')
  
  let isNext= visibleData.rightTableList.some(item=>{
  if(item.fieldRequired==0 &&!row[item.fieldName]){
    proxy.$message.error(`${item.fieldDisplayName}必填！`)
    return true
  }
})
if(isNext) return

  // console.log(visibleData.rightTableList)
  // return
 


    // if( obj.estimated_incurred<obj.actual_incurred) return  proxy.$message.error('全年预计发生费用必须>=本年实际发生额')
    budgetSave(obj).then(res=>{
    if(res.code!=200) return proxy.$message.error(res.msg)
        proxy.$message.success("操作成功")
        
        getRightTableList()
    })
    



 

  
}
// 改变提交状态
function changeStatus(){
    console.log(ids.value)
  updateStatus({itemId:visibleData.leftDataList[visibleData.leftIndex].id,id: ids.value.join(','),status:2}).then(res=>{
    if(res.code!=200) return proxy.$message.error(res.msg)
    proxy.$message.success("提交成功")
   
       getRightTableList()
 
  })
}
// 新增一条
function addOne(){
    let obj={
        id:"",
        dept_id:"",          
        budget:"",
        budget_year:"",
        actual_incurred:"",
        estimated_incurred:"",
        status:1
    }
  visibleData.rightTableList.forEach(item=>{
    obj[item.fieldName]=''

  })
  
  visibleData.rightDataList.push(obj)
 
}
// 获取右边 table title 数据
function getRightTableList() {
  loading.value = true;
  getRightTable({itemId:visibleData.leftDataList[visibleData.leftIndex].id,taskId:visibleData.id}).then(r=>{
  
      visibleData.rightTableList=r.data||[]
      
      // getRightDataList()
    })
}
// 获取 table 内容数据
async function getRightDataList(){
    getDeptList({itemId:visibleData.leftDataList[visibleData.leftIndex].id}).then(res=>{
            deptList.value=res.data
            })
        
    if(route.query.type==1){
        getRightData({taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType,...visibleQueryParams}).then(r=>{
         
        
       visibleData.rightDataList=r.rows||[]
    
       visibleQueryParams.total=r.total
      //  visibleQueryParams.show=true
      
    })
   
    }else{
        addRightData({taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType}).then(async r=>{
       
          let arr=r.data||[]
          let arr2=new Set(arr.map(item=>item.dept_id))
        //  r.data||[]
        for(const item of deptList.value){
          if(!arr2.has(item.deptId)){
            let res= await  getDeptActualInfo({itemId:visibleData.leftDataList[visibleData.leftIndex].id,
              taskId:visibleData.id,dept_id:item.deptId})
            let obj={
              id:"",
              dept_id:item.deptId,          
              budget:"",
              budget_year:"",
              actual_incurred:res.data.actual_incurred,
              estimated_incurred:res.data.estimated_incurred,
              status:1
             
            }
            visibleData.rightTableList.forEach(item=>{
              obj[item.fieldName]=''
            })
              arr.push(obj)
          }
        }
        visibleData.rightDataList=arr
        //    visibleQueryParams.total=r.total
         visibleQueryParams.show=false
         visibleQueryParams.total=0
    })
    }
    loading.value = false
 
}
function changeTips(){
  if(dialogXzVisible.checked){
    updateXZ(route.query.id)
  }
}
/** 查询部门下拉树结构 */
function getDeptTree() {
deptTreeSelect().then(response => {
  deptOptions.value = response.data;
});
};
// 点击左侧更换下标
function changeIndex(index,tableName,reportingType){
  loading.value = true;
  visibleData.leftIndex=index
  visibleData.tableName=tableName
  visibleData.reportingType=reportingType
  visibleData.rightDataList=[]
  visibleData.rightTableList=[]
 if(visibleData.reportingType==1)  getRightTableList()
}
function chageTitle(){
    let name=route.name
    let index=routes.value.findIndex(item=>item.path=='/taskExecution')
    routes.value[index].children.forEach(item => {
        if(item.name==name) item.meta.title=route.query.name   
    })
    PermissionStore.setRoutesAll(routes) 
    for (const r of visitedViews.value) {
      if (r.path === route.path) {
        r.title=route.query.name
        useTagsViewStore().updateVisitedView(r)
        // when query is different then update
        
      } 
    }
    // 关闭页签
    // const obj = { path: "/monitor/job" };
    // proxy.$tab.closeOpenPage(obj);
}
/** 重置按钮操作 */
function resetQuery() {
  dateRange.value = [];
  proxy.resetForm("queryRef");
  handleQuery();
}
/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}
function getList() {
  loading.value = true;
//   let obj=proxy.addDateRange(queryParams.value, dateRange.value)

//   listTask({...obj,selectType}).then(
//     (response) => {
//       roleList.value = response.rows;
//       total.value = response.total;
//       loading.value = false;
      
//     }
//   );
}
  /** 多选框选中数据 */
  function handleSelectionChange(selection) {
    ids.value = selection.map((item) => item.id);
    // single.value = selection.length != 1;
    // multiple.value = !selection.length;
  }
// getDeptTree()
</script>
<style lang="scss" scoped>
// .v-enter-active,
// .v-leave-active {
//   transition: width 0.5s ease;
//   transition: all 0.5s ease;
// }

// .v-enter-from,
// .v-leave-to {
//   // opacity: 0;
//   width:0%
// }
.box{
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
    display: flex;
    justify-content: space-between;
    height: calc(100vh - 84px);
    position: relative;

    .hb{
      position: absolute;
      font-size: 20px;
      top: 5px;
    
      cursor: pointer;
      transition: all 0.2s ease;
     
    }
    .splitter {
        width: 4px;
        background-color: #ccc;
        height: 100%;
        cursor: ew-resize;
        position: absolute;
        z-index: 99;
  }
    .left{
        // min-width: ;
      .title{
        // border-bottom: 1px solid #ccc;
        letter-spacing: 2px;
        cursor: pointer;
        font-weight: bold;
        padding:0 10px;
        font-size: 14px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        width: 100%;
        box-sizing: border-box;
       }
      
        width:15%;
        // border-right: 1px solid #eee;
        display: flex;
        // justify-content: center;
        flex-direction: column;
        align-items: center;
        .content{
        width: 95%;
        box-sizing: border-box;
        height: 91%;
        // border: 1px solid #ccc;
        margin: 0px auto 0;
        overflow-y:auto ;
        .item{
            width: 100%;
            display: flex;
            height: 30px;
            // line-height: 30px;
            align-items: center;
            padding:0 10px;
            font-size: 14px;
            cursor: pointer;
            &>div:first-child>div{
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
    .right{
        width:84%;
        margin-left: 1%;
    }
}
.hjbox{
    display:flex;justify-content:space-between;width:100%; align-items: center;
    padding-right: 20px;
    box-sizing: border-box;
    &>div{
        font-size: 14px;
        flex:0 0 auto;
        
       
    }
}
</style>
