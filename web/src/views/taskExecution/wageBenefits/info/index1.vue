<template>
 <div class="app-container box">
    <!-- -->
    <div class="hb" :style="{left:isopen?'16%':'1%' }"><el-icon @click="changeIsopen"><Fold v-if="isopen" /> <Expand v-else/></el-icon></div>
   
        <div class="left" :style="{ transition: 'all 0.2s ease',width:isopen? '15%':'0%',overflow:'hidden' ,opacity:isopen? '1':'0'}">
        
            <!-- 展开目录 -->
            
             <div class='title' >工资填报 <el-icon style="cursor: pointer;margin-left: 20%;" @click="dialogXzVisible.show=true"><WarningFilled /></el-icon></div>
             <el-input placeholder="搜索" v-model="visibleData.query" size="small" style="margin: 10px 5%;width:90%;;"></el-input>

              <div class="content">
                <template v-for="(item,index) in  visibleData.leftDataList" :key="item.id">
                  <div  v-if="item.show" @click="changeIndex(index)"  :class="visibleData.leftIndex==index? 'item action':'item'" >
                    <div>{{item.deptName}} </div>
                      <!-- <el-tag :type="item.isReporting==0?'error':'success'">{{item.isReporting==0?'未提交':'已提交'}}</el-tag> -->
                  </div>
                </template>
              </div>
        </div>

        
        <div class="right" :style="{width:isopen?'86%':'100%' }">   
          
            <div style="margin-bottom:10px;display:flex;justify-content: flex-end;align-items: center">
                <el-button   type="primary" @click="changeStatus" v-if='!visibleQueryParams.show'   v-hasPermi="['budget:business:submit']">提交审核</el-button>
                <el-button
                    type="warning"
                    plain
                    icon="Download"
                    @click='handleExport'
                  
                      v-hasPermi="['budget:business:export']"
                    >导出</el-button>
            </div>
            <el-table :summary-method="getSummaries" show-summary :data="visibleData.rightDataList"   v-loading="loading"  height='80vh'  @selection-change="handleSelectionChange" >
                    <el-table-column  :selectable="selectable" type="selection" width="55" align="center"    />
                    <el-table-column  width="200" align="center" label='单位名称'  prop="deptId"  fixed='left' >
                        <template  #default="scope">
                          <el-select
                            v-model="scope.row.deptId"
                            placeholder="请选择"
                            disabled
                            style="width: 150px"
                            @change="changeDept(scope.row.deptId,scope.$index)"
                            
                           
                        >
                     <el-option
                        v-for="dict in deptList"
                        :key="dict.deptId"
                        :label="dict.deptName"
                        :value="dict.deptId"
                     />
                  </el-select>
                      </template>
                    </el-table-column>
                  
                    <el-table-column  width="140" align="center" label='工资总额（员工）'  prop="staff"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.staff"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="120" align="center" label='劳务派遣'  prop="labor"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.labor" :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5"/>

                        </template>
                    </el-table-column>
                    <el-table-column  width="120" align="center" label='合计'  prop="hj"  >
                       
                    </el-table-column>
                    <el-table-column  width="120" align="center" label='福利费'  prop="welfare"  >
                        <template  #default="scope">
                              <el-input v-model="scope.row.welfare"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5"/>
                          </template>
                    </el-table-column>
                    <el-table-column  width="120" align="center" label='教育经费'  prop="education"  >
                        <template  #default="scope">
                              <el-input v-model="scope.row.education"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5"/>
                          </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='工会经费'  prop="unionFunds"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.unionFunds" :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5" />
                        </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='养老保险'  prop="endowment"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.endowment" :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5" />
                        </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='工伤保险'  prop="workInjury"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.workInjury"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5"/>
                        </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='失业保险'  prop="unemployment"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.unemployment"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5"/>
                        </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='医疗保险'  prop="medical"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.medical" :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5" />
                        </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='生育保险'  prop="maternity"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.maternity" :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5" />
                        </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='社会保险费-单位部分'  prop="social"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.social" :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5" />
                        </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='住房公积金-单位部分'  prop="provident"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.provident" :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5" />
                        </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='企业年金-单位部分'  prop="annuity"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.annuity"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5"/>
                        </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='研发经费'  prop="research"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.research" :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5" />
                        </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='总计'  prop="zj"  >
                        
                      </el-table-column>
                  
                    <el-table-column  width="120" align="center" fixed="right" label='状态'  prop="budget"  >
                          <template #default="scope">
                            <dict-tag :options="budget_status" :value="scope.row.status" />
                          </template>
                    </el-table-column>
               
                   
                    <el-table-column  
                    label="操作"
                    align="center"
                    width="200"
                     fixed="right"
                    >
                    <template #default="scope">
                      <template v-if="!visibleQueryParams.show">
                        <el-button
                            link
                            type="success"
                            @click='save(scope.row,scope.$index)'
                            v-if='scope.row.status==1||scope.row.status==6'
                             v-hasPermi="['budget:business:add']"
                            >保存</el-button>
                      </template>
                       
                            <el-button
                            link
                            type="primary"
                            @click='clickSpInfo(scope.row,scope.$index)'
                            v-if="scope.row.id"
                            >查看审批</el-button>
                            <!-- <el-popconfirm title="确定要删除吗？" @confirm="clickDelete(scope.row,scope.$index)">
                              <template #reference>
                                <el-button
                                  link
                                  type="danger"
                                 v-if='scope.row.status==1||scope.row.status==4'
                                  v-hasPermi="['budget:business:delete']"
                              
                                  >删除</el-button>
                              </template>
                            </el-popconfirm> -->
                    </template>
                    </el-table-column>

                </el-table>
              <div style="width:98%;position: relative;" v-if="visibleQueryParams.total>0" >
                  <pagination
                  :total="visibleQueryParams.total"
                  v-model:page="visibleQueryParams.pageNum"
                  v-model:limit="visibleQueryParams.pageSize"
                  @pagination="getRightDataList" 
                  />
                  <!-- v-if="visibleQueryParams.show"     -->
              </div>
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
                    <!-- <h3 style="color:#000;font-weight:bold">审批流程</h3>
                    <el-timeline>
                      <template v-for="(item,index) in logList" :key="index">
                        <el-timeline-item :timestamp="item.deptName" placement="top">
                          <el-card>
                            <h4>{{item.userName}}</h4>
                            <p>{{item.createTime}}</p>
                            <p>{{item.remark}}</p>
                          </el-card>
                        </el-timeline-item>
                      </template>
                    </el-timeline> -->
                    <!-- <h3 style="color:#000;font-weight:bold">审批流程</h3> -->
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
                  <el-empty description="暂无流程" v-else />
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
import {deleteItem,reportingLog,getXZ,updateXZ,getRightTable,getInfoForm,budgetSave,delTableItem,addRightData,getDeptList,getDeptActualInfo} from "@/api/taskExecution/index"
import {getLeftList,getRightData,saveItem,updateStatus} from "@/api/taskExecution/wageBenefits/index"

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
    query:"",

})
const visibleQueryParams=reactive({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  show:false
})
 const {rightDataList} = toRefs(visibleData)
const deptList=ref([])
const logListShow=ref(false)
watch(()=>visibleData.query,(newValue, oldValue)=>{
  if(!newValue) {
    visibleData.leftDataList.forEach(item=>item['show']=true)
  }else{
    visibleData.leftDataList.forEach(item=>{
     
      if(!item.deptName.toLowerCase().includes(newValue.toLowerCase())){
        item['show']=false
      }
    })
  }

})



onMounted(() => {
    // console.log(route)
    chageTitle()
   
    getXZ(route.query.id).then(res=>{
              dialogXzVisible.title=route.query.name
              dialogXzVisible.value=res.data.reportingExplain
              if(res.data.isTips==0)dialogXzVisible.show=true 
              else dialogXzVisible.show=false
            })
           
    getLeftList({taskId:route.query.id,level:1}).then(r=>{
      
      r.data.forEach(item=> item['show']=true)
      visibleData.leftDataList=r.data||[]
      if( r.data.length>0) {
        getRightTableList()
        
      } 
     
    })
});

function changeIsopen(){
  isopen.value=!isopen.value
}
function clickDelete(row,index){
  if(row.id) {
    deleteItem({itemId:1,id:row.id}).then(res=>{
      if(res.code==200) {
      
      
        proxy.$message.error('删除成功！')
    //     getLeftList({taskId:route.query.id,selectType}).then(r=>{
    //   visibleData.leftDataList=r.data||[]
     
    //   if( visibleData.leftDataList.length>0) {
      
        
    //   } 
     
    // })
      }
    })
  }else{
    visibleData.rightDataList.splice(index,1)
  }


}
function clickSpInfo(row){


  reportingLog({itemId:1,id:row.id||0,taskId: route.query.id}).then(res=>{
    if(res.code!=200) return proxy.$message.error(res.msg)
    logList.value=res.data
    logListShow.value=true
  })
}
function handleExport() {
  proxy.download('fixed/wages/export', {
    deptParentId:visibleData.leftDataList[visibleData.leftIndex].deptId
  }, `填报明细${new Date().getTime()}.xlsx`)
}
function getSummaries(param){
  const { columns, data } = param
  const sums = []
  columns.forEach((column, index) => {
    
  
    const values = data.map((item) => {
      // console.log(column.property )
    return  Number(item[column.property] )
    })
   
    if (!values.every((value) => Number.isNaN(value))) {
      sums[index] = ` ${values.reduce((prev, curr) => {
        const value = Number(curr)
        if (!Number.isNaN(value)) {
          return prev + curr
        } else {
          return prev
        }
      }, 0)}`
    } else {
      sums[index] = '--'
    }
  })
  // if (index === 0||index === 1) {
  //     sums[index] = h('div', { style: { textDecoration: 'underline' } }, [
  //       '',
  //     ])
  //     return
  //   }
    sums[0]=""
    sums[1]=""

  sums[sums.length-1]=""
  sums[sums.length-2]=""

  return sums
}
// 禁用选择
function selectable(row, index){

 return row.id!=''&&row.status==1|| row.status==6
}
function changeDept(deptid,index){
    if(!deptid)return
  
    getDeptActualInfo({itemId:1,
    taskId:visibleData.id,dept_id:deptid}).then(res=>{
    visibleData.rightDataList[index].actual_incurred=res.data.actual_incurred||0
    visibleData.rightDataList[index].estimated_incurred=res.data.estimated_incurred||0

        
    })

}
function save(row,index){
  let deptParentId=visibleData.leftDataList[visibleData.leftIndex].deptId
  let obj={
    taskId:Number(visibleData.id) ,
    ...row,
    deptParentId,
  }
 

  saveItem(obj).then(res=>{
    if(res.code!=200) return proxy.$message.error(res.msg)
        proxy.$message.success("操作成功")
        
        getRightTableList()
    })
    



 

  
}
// 改变提交状态
function changeStatus(){
  updateStatus({ids: ids.value.join(','),status:2}).then(res=>{
    if(res.code!=200) return proxy.$message.error(res.msg)
    proxy.$message.success("提交成功")
    getLeftList({taskId:route.query.id,level:1}).then(r=>{
      
      r.data.forEach(item=> item['show']=true)
      visibleData.leftDataList=r.data||[]
      if( r.data.length>0) {
        getRightTableList()
        
      } 
     
    })
  })
}
// 新增一条
function addOne(){
  //   let obj={
  //       id:"",
  //       dept_id:"",          
  //       budget:"",
  //       budget_year:"",
  //       actual_incurred:"",
  //       estimated_incurred:"",
  //       status:1
  //   }
  // visibleData.rightTableList.forEach(item=>{
  //   obj[item.fieldName]=''

  // })
  
  // visibleData.rightDataList.push(obj)
 
}
// 获取右边 table title 数据
function getRightTableList() {
  loading.value = true;
  // getRightTable({itemId:visibleData.leftDataList[visibleData.leftIndex].id}).then(r=>{
  
      // visibleData.rightTableList=r.data||[]
      
      getRightDataList()
    // })
}
// 获取 table 内容数据
async function getRightDataList(){
  let res=await getLeftList({parentId:visibleData.leftDataList[visibleData.leftIndex].deptId})
        res.data.forEach(item=> item['show']=true)
        deptList.value=res.data
           
          
    if(route.query.type==1){
      let r=   await  getRightData({taskId:visibleData.id,deptParentId:visibleData.leftDataList[visibleData.leftIndex].deptId,selectType,...visibleQueryParams})
         
          r.data.forEach((item)=>{
            item['hj']=0
            item['zj']=0
          })
       visibleData.rightDataList=r.data||[]
    
       visibleQueryParams.total=r.total ||0
       visibleQueryParams.show=true
      
  
   
    }else{
      let r=   await  getRightData({taskId:visibleData.id,deptParentId:visibleData.leftDataList[visibleData.leftIndex].deptId,selectType,...visibleQueryParams})
        
          let arr=r.data||[]
          let arr2=new Set(arr.map(item=>item.deptId))
        //  r.data||[]
        for(const item of deptList.value){
          if(!arr2.has(item.deptId)){
            // let res= await  getDeptActualInfo({itemId:visibleData.leftDataList[visibleData.leftIndex].id,
            //   taskId:visibleData.id,dept_id:item.deptId})
            let obj={
              id:"",
              deptId:item.deptId,
              staff:0,
              labor:0,
              welfare:0,
              education:0,
              unionFunds:0,
              endowment:0,
              workInjury:0,
              medical:0,
              unemployment:0,
              maternity:0,
              social:0,
              provident:0,
              annuity:0,
              research:0,
              status:1,
              hj:0,
              zj:0
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
  
    }
   
    loading.value = false
  
    rightDataList.value.forEach(row=>{
    
      // watch([() => row.budget, () => row.budget_year], ([newbudget, newbudget_year]) => {
      //   console.log(newbudget_year,newbudget)
      //   row.hj = newbudget + newbudget_year;
      // }, { immediate: true });
      watchEffect(() => {
        row.hj =Number(row.staff)  + Number(row.labor) 
      });
      watchEffect(() => {

        row.zj =Number(row.staff)  + Number(row.labor) + Number(row.welfare)+ Number(row.education)+ Number(row.unionFunds)+ Number(row.endowment)+ Number(row.workInjury)+ Number(row.unemployment)+ Number(row.medical)+ Number(row.maternity)+ Number(row.social)+ Number(row.provident)+ Number(row.annuity)+ Number(row.research)
      });

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
  loading.value = true;
  visibleData.leftIndex=index
  visibleData.rightDataList=[]
  visibleData.rightTableList=[]
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
    display: flex;
    justify-content: space-between;
    height: calc(100vh - 84px);
    position: relative;

    .hb{
      position: absolute;
      font-size: 20px;
      top: 5px;
      left: 16%;
      cursor: pointer;
      transition: all 0.2s ease;
    }
    .left{
      
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
       }
      
        width:15%;
        // border-right: 1px solid #eee;
        display: flex;
        // justify-content: center;
        flex-direction: column;
        align-items: center;
        .content{
        width: 80%;
        box-sizing: border-box;
        height: 91%;
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
    .right{
        width:84%;
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
