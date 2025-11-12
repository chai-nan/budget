<template>
  <div class="app-container box">
     <!-- -->
   
         <!-- <div class="left">
 
                 <div class="content">
                     <div @click="changeIndex(index)"  :class="visibleData.leftIndex==index? 'item action':'item'" v-for="(item,index) in  visibleData.leftDataList" :key="item.id">
                         <div><el-tooltip
                         effect="dark"
                         :content="item.tableDisplayName"
                          >{{item.tableDisplayName}} </el-tooltip></div>
                        
                     </div>
                 </div>
         </div> -->
         <div class="right">   
             <el-form
                 :model="queryParams"
                 ref="queryRef"
                 v-show="showSearch"
                 :inline="true"
                 label-width="68px"
                 >
                
                 <!-- <el-form-item label="费用预算" prop="budget">
                     <el-input
                     v-model="queryParams.budget"
                     placeholder="请输入"
                     clearable
                     style="width: 240px"
                     @keyup.enter="handleQuery"
                     />
                 </el-form-item> -->
                 <el-form-item label="部门名称" style="width: 308px"  prop="deptId">
                     <el-tree-select
                       v-model="queryParams.deptId"
                       :data="deptOptions"
                       :props="{ value: 'id', label: 'label', children: 'children' }"
                       value-key="id"
                       placeholder="请选择归属部门"
                       check-strictly
                    />
                 </el-form-item>
                 <el-form-item label='状态' prop="status" >
                   <el-select
                          
                      v-model="queryParams.status"
                      placeholder="请选择"
                      clearable
                      style="width: 240px"
                   
                   >
                      <el-option
                         v-for="dict in budget_status"
                         :key="dict.value"
                       :label="dict.label"
                       :value="dict.value"
                      />
                   </el-select>
                 </el-form-item>
 
                
                 <el-form-item>
                     <el-button type="primary" icon="Search" @click="handleQuery"
                     >搜索</el-button
                     >
                     <el-button icon="Refresh" @click="resetQuery">重置</el-button>
                 </el-form-item>
             </el-form>
             <!-- <el-row :gutter="10" class="mb8">
                 <right-toolbar
                     v-model:showSearch="showSearch"
                     @queryTable="getList"
                 ></right-toolbar>
             </el-row> -->
             <div style="margin-bottom:10px;display:flex;justify-content: flex-end;align-items: center">
                 <el-button   type="primary" @click="spBtn" >批量审核</el-button>
                 <el-button
                     type="warning"
                     plain
                     icon="Download"
                     >导出</el-button>
             </div>
             <el-table :data="visibleData.rightDataList"   max-height='75%'  @selection-change="handleSelectionChange" >
                     <el-table-column  type="selection" width="55" align="center"  :selectable="selectable"  />
                   
                     <el-table-column  width="200" align="center" label='填报人'  prop="user_name"  >
                         
                       </el-table-column>
                     <el-table-column  width="200" align="center" label='部门'  prop="dept_name"  >
                         <!-- <template  #default="scope">
                           <el-select
                             v-model="scope.row.dept_id"
                             placeholder="请选择"
                             clearable
                             style="width: 150px"
                             @change="changeDept(scope.row.dept_id,scope.$index)"
                             disabled
                            
                         >
                      <el-option
                         v-for="dict in deptList"
                         :key="dict.deptId"
                         :label="dict.deptName"
                         :value="dict.deptId"
                      />
                   </el-select>
                       </template> -->
                     </el-table-column>
                     <el-table-column  width="200" align="center" label='预算表名称'  prop="budget"  >
                         
                       </el-table-column>
                   
                     <el-table-column  width="200" align="center" label='预算费用（元）'  prop="budget"  >
                         
                     </el-table-column>
                     <el-table-column  width="200" align="center" label='本年预算（元）'  prop="budget_year"  >
                         
                     </el-table-column>
                     <el-table-column  width="200" align="center" label='本年实际发生额（元）'  prop="actual_incurred"  >
                         
                     </el-table-column>
                     <el-table-column  width="200" align="center" label='全年预计发生费用（元）'  prop="estimated_incurred"  >
                         
                     </el-table-column>
                     <template   v-for="(item,index) in  visibleData.rightTableList" :key='index'>
                       <el-table-column
                           :label="item.fieldDisplayName"
                           prop=""
                           align="center"
                           width="200"
                          
                       >
                       <template  #default="scope">
                           <span>{{scope.row[item.fieldName]}}</span>
                           <!-- <el-select
                           v-else
                      v-model="scope.row[item.fieldName]"
                      placeholder="请选择"
                      clearable
                      style="width: 150px"
                      disabled
                   >
                      <el-option
                         v-for="dict in item.dictDatas"
                         :key="dict.value"
                       :label="dict.dictLabel"
                       :value="dict.dictValue"
                      />
                   </el-select> -->
                       </template>
                     
                     </el-table-column>
                 </template>
                  <el-table-column  width="200" align="center" label='状态'  prop="budget"  >
                   <template #default="scope">
                     <dict-tag :options="budget_status" :value="scope.row.status" />
                   </template>
                     </el-table-column>
                     <el-table-column  width="200" align="center" label='提交时间'  prop="create_time"  >
                         
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
                             @click='spBtn(scope.row)'
                             v-if='scope.row.status==3'
                             >审批</el-button>
                            
                     </template>
                     </el-table-column>
                 </el-table>
                 <!-- <div style="margin-top: 20px;">
                     <el-button
                     type="primary"
                     icon='Plus'
                 @click="addOne"
                     >新增一条</el-button>
                 </div> -->
                
                 <!--  @click="" -->
                
                 <div style="width:98%;position: relative;">
                     <pagination
                     v-show="queryParams.total>0"
                     :total="queryParams.total"
                     v-model:page="queryParams.pageNum"
                     v-model:limit="queryParams.pageSize"
                     @pagination="getRightDataList"
                     />
                 </div>
         </div>
          <!-- 预选填报须知 -->
          <!-- <el-dialog v-model="dialogXzVisible.show" width='60%' :title="dialogXzVisible.title" draggable append-to-body @close="changeTips" :close-on-press-escape='false'>
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
         </el-dialog> -->
            <!-- 批量审核弹窗 -->
            <el-dialog v-model="dialogPlshVisible" width='60%' title="审核" draggable append-to-body  :close-on-press-escape='false'>
           <el-form  :rules="infoRules" label-position="right" label-width="150" ref="plInfoForm" :model="infoForm">
                     <div  style='width:90%;margin:0 auto 10px'>
                       <!-- <h3 style="color:#000;font-weight:bold">审核意见</h3> -->
                       <el-form-item label="审核意见" prop="status">
                         <el-radio-group v-model="infoForm.status" class="ml-4">
                         <el-radio label="5" size="large">通过</el-radio>
                         <el-radio label="6" size="large">不通过</el-radio>
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
 
   </div>
 </template>
 
 <script setup>
 import { use } from "echarts";
 import { nextTick, onMounted, watch } from "vue";
 import usePermissionStore from '@/store/modules/permission'
 import useTagsViewStore from '@/store/modules/tagsView'
 
 import {  deptTreeSelect } from "@/api/system/user";
 import {reportingLog,getXZ,updateXZ,getLeftList,getRightTable,getRightData,getInfoForm,budgetSave,delTableItem,updateStatus,addRightData,getDeptList,getDeptActualInfo} from "@/api/taskExecution/index"
 const { proxy } = getCurrentInstance();
 const { field_type,item_type,budget_status } = proxy.useDict("budget_status","field_type","item_type" );
 const router = useRouter();
 const route= useRoute();
 const PermissionStore=usePermissionStore()
 const routes = computed(() => PermissionStore.routes);
 const visitedViews = computed(() => useTagsViewStore().visitedViews);
 const loading = ref(true);
 const showSearch = ref(true);
 const deptOptions = ref(undefined);
 const plInfoForm =ref()
 const data = reactive({
   form: {},
   queryParams: {
     pageNum: 1,
     pageSize: 10,
     total: 10,
     name: undefined,
     //  roleKey: undefined,
     year: undefined,
     deptId:"",
     status:""
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
 
 const selectType=3
 const visibleData=reactive({
     leftIndex:0,
     id:route.query.id,
     leftTitle:"",
     rightTitle:'',
     type:1,  // type 1查看 2编辑（新增）
     leftDataList:[],
     rightDataList:[],
     rightTableList:[],
     budget:0,
     budget_year:0,
     actual_incurred:0,
     estimated_incurred:0,
 
 })
 const visibleQueryParams=reactive({
   pageNum: 1,
   pageSize: 10,
   total: 10,
   show:false
 })
 
 const deptList=ref([])
 const dialogPlshVisible=ref(false)
 const infoForm=reactive({
     type:1,  // type 1查看 2编辑（新增）
     name:"",
     id:'',
   
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
 onMounted(() => {
  if(!route.query.name||!route.query.id||!route.query.itemId||!route.query.deptid)return window.close()
     chageTitle()
    
     getXZ(route.query.id).then(res=>{
               dialogXzVisible.title=route.query.name
               dialogXzVisible.value=res.data.reportingExplain
               if(res.data.isTips==0)dialogXzVisible.show=true 
               else dialogXzVisible.show=false
             })
     getLeftList({taskId:route.query.id,selectType}).then(r=>{
       visibleData.leftDataList=r.data||[]
      
       if( visibleData.leftDataList.length>0) {
         getRightTableList()
         
       } 
      
     })
 });
 // 批量审核
 function plSave(){
 
 let obj={
   itemId:route.query.itemId,
   taskId:route.query.id,
   id: infoForm.id,
   status:infoForm.status,
   remark:infoForm.remark
 }
 plInfoForm.value.validate((valid) => {
   if (valid) {
     updateStatus(obj).then(res=>{
       if(res.code!=200) return proxy.$message.error(res.msg)
       proxy.$message.success("提交成功")
       getLeftList({taskId:route.query.id,selectType}).then(r=>{
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
 function spBtn(row){
  if(!row.id && ids.value.length==0) return proxy.$message.error('请选择审核项')
   infoForm.id=row.id||ids.value.join(',')
   dialogPlshVisible.value=true
   infoForm.remark=''
   infoForm.status=""
 
 
 }
 
 // 禁用选择
 function selectable(row, index){
  return row.status!=5
 }
 function changeDept(deptid,index){
     if(!deptid)return
   
     getDeptActualInfo({itemId:visibleData.leftDataList[visibleData.leftIndex].id,
     taskId:visibleData.id,dept_id:deptid}).then(res=>{
     visibleData.rightDataList[index].actual_incurred=res.data.actual_incurred||0
     visibleData.rightDataList[index].estimated_incurred=res.data.estimated_incurred||0
 
         
     })
 
 }
 function save(row){
   let obj={
     itemId:visibleData.leftDataList[visibleData.leftIndex].id,
     taskId:visibleData.id,
   
     ...row
   }
 
     if( obj.estimated_incurred<obj.actual_incurred) return  proxy.$message.error('全年预计发生费用必须>=本年实际发生额')
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
     getLeftList({taskId:visibleData.id,selectType}).then(r=>{
       visibleData.leftDataList=r.data||[]
      
       if( visibleData.leftDataList.length>0)  getRightTableList()
     })
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
     obj[item.field_type]=''
   })
   visibleData.rightDataList.push(obj)
 }
 // 获取右边 table title 数据
 function getRightTableList() {
   getRightTable({itemId:route.query.itemId, taskId:route.query.id}).then(r=>{
       visibleData.rightTableList=r.data||[]
       
       getRightDataList()
     })
 }
 // 获取 table 内容数据
 function getRightDataList(){
     // getDeptList({itemId:route.query.itemId}).then(res=>{
     //         deptList.value=res.data
     //         })
     
         getRightData({taskId:route.query.id,itemId:route.query.itemId,parentId:route.query.deptid,selectType,...queryParams.value}).then(r=>{
        
          visibleData.rightDataList=r.rows||[]
        
        queryParams.value.total=r.total
        
       //  visibleQueryParams.show=true
     })
     
  
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
 function changeIndex(index){
   visibleData.leftIndex=index
   getRightTableList()
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
 }
 /** 重置按钮操作 */
 function resetQuery() {
   proxy.resetForm("queryRef");
   handleQuery();
 }
 /** 搜索按钮操作 */
 function handleQuery() {
   queryParams.value.pageNum = 1;
   getRightTableList();
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
 getDeptTree()
 </script>
 <style lang="scss" scoped>
 .box{
     display: flex;
     justify-content: space-between;
     height: calc(100vh - 84px);
  
     .left{
         width:15%;
         border-right: 1px solid #eee;
         display: flex;
         // justify-content: center;
         flex-direction: column;
         align-items: center;
         .content{
         width: 80%;
         box-sizing: border-box;
         height: 95%;
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
         width:100%;
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
 