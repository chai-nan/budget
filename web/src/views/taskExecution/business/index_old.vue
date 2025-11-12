<!-- 业务部门预算填写 -->
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
      <!-- <el-form-item label="任务名称" prop="name">
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
      </el-form-item> -->
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
      @selection-change="handleSelectionChange"
    >
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
         
            <el-button
              link
              type="primary"
              @click="changeDialogVisible(scope.row,1)"
           
              >查看</el-button
            >
          
          <!--  v-hasPermi -->
          
            <el-button
              link
              type="primary"
              @click="changeDialogVisible(scope.row,2)"
              >处理</el-button
            >
         
          
              <el-button
                link
                type="primary"
                @click="jump(scope.row,2)"
                >跳转详情</el-button
              >
           
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
                    <div @click="changeIndex(index)" :class="visibleData.leftIndex==index? 'item action':'item'" v-for="(item,index) in  visibleData.leftDataList" :key="item.id">
                        <div><el-tooltip
                        effect="dark"
                        :content="item.tableDisplayName"
                         >{{item.tableDisplayName}} </el-tooltip></div>
                        <el-tag :type="item.isReporting==0?'error':'success'">{{item.isReporting==0?'未提交':'已提交'}}</el-tag>
                    </div>
                </div>

            </div>
            <div class="right">
                <div class='title'>{{visibleData.rightTitle}}</div>
                <div class='btnBox' v-if='visibleData.type==2'>
                    <el-button
                    type="primary"
                    plain
                   @click='changeInfoFormType("",2)'
                    >新增</el-button>
                    <!-- <el-button
                    type="info"
                    plain
                  
                    >批量提交</el-button> -->
                </div>
                <!--  :data="visibleData.rightTableList" -->
                <el-table :data="visibleData.rightDataList"   max-height='75%'  @selection-change="handleSelectionChange">
                    <!-- <el-table-column type="selection" width="55" align="center"    v-if='visibleData.rightTableList.length>0'/> -->
                    <template   v-for="(item,index) in  visibleData.rightTableList" :key='index'>
                      <el-table-column
                          :label="item.fieldDisplayName"
                          prop=""
                          
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
                    <el-table-column  
                    label="操作"
                    align="center"
                    v-if='visibleData.rightTableList.length>0'
                    >
                    <template #default="scope">
                     
                        <el-button
                            link
                            type="primary"
                            @click='changeInfoFormType(scope.row,1)'
                            >查看</el-button>
                            <el-button
                            link
                            type="primary"
                            v-if='visibleData.type==2&&scope.row.status==1||scope.row.status==4'
                            @click='changeInfoFormType(scope.row,2)'
                            >编辑</el-button>
                            <el-button
                            link
                            type="primary"
                            v-if='visibleData.type==2&&scope.row.status==1||scope.row.status==4'
                            @click="changeStatus(scope.row)"
                            >提交</el-button>
                            <el-popconfirm  v-if='visibleData.type==2&&scope.row.status==1||scope.row.status==4' title="确定要删除么？" @confirm="deleteItem(scope.row)"
   >
                            <template #reference>
                              <el-button
                                    link
                                    type="primary"
                                  
                                    >删除</el-button>
                            </template>
                          </el-popconfirm>
                           
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
                

            </div>
        </div>
        <!-- 新增编辑 查看等操作 -->
        <el-dialog v-model="dialogFormVisible" width='60%' :title="infoForm.type==1?'查看':'新增/编辑'" draggable append-to-body  :close-on-press-escape='false'>
            <div  style="max-height:80vh;overflow-y: auto; overflow-x: hidden;;width:100%;box-sizing:border-box" >
                <el-form  :disabled='infoForm.type==1'  label-position="right" label-width="200" ref="refInfoForm" :model="infoForm">
                    <div class="formBox">
                        <div v-for="(item,index) in infoForm.List" :key="index">
                            <el-form-item :label="item.fieldDisplayName" :prop="'List.'+index+'.value'" :rules='item.rules'>
                              {{item[item.fieldName]}}
                                <el-input v-model="item.value" :disabled="item.disabled"  v-if='item.fieldName!="cost_description"'/>
                                 
                                <el-input  v-model="item.value" v-else  maxlength="300"
                                  placeholder=""
                                  show-word-limit
                                  type="textarea"
                                  />
                            </el-form-item>
                           
                        </div>
                    </div>
                </el-form>
                <div v-if='infoForm.logList.length>0' style='width:80%;margin:0 auto 10px'>
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
                    <el-button type="primary" @click='save'>保存</el-button>
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
        
     
    </el-dialog>
  </div>
</template>
 
 <script setup name="business">
import {
  addRole,
  changeRoleStatus,
  dataScope,
  delRole,
  getRole,
  listRole,
  updateRole,
  deptTreeSelect,
} from "@/api/system/role";
import { reactive } from "vue";
import {getTask, listTask,updateTask,addTask} from "@/api/BM/task";
import {reportingLog,getXZ,updateXZ,getLeftList,getRightTable,getRightData,getInfoForm,budgetSave,delTableItem,updateStatus} from "@/api/taskExecution/index"

const router = useRouter();
const { proxy } = getCurrentInstance();
const { budget_status,result_status ,completion_status } = proxy.useDict("budget_status","result_status","completion_status");

const selectType=1
const roleList = ref([]);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const dateRange = ref([]);

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
const refInfoForm=ref()
const dialogVisible=ref(false)
// 弹窗分页
const visibleQueryParams=reactive({
  pageNum: 1,
  pageSize: 10,
  total: 10,
})
const visibleData=reactive({
    leftIndex:0,
    id:"",
    leftTitle:"",
    rightTitle:'',
    type:1,  // type 1查看 2编辑（新增）
    leftDataList:[],
    rightDataList:[],
    rightTableList:[]
})
const dialogFormVisible=ref(false)
const infoForm=reactive({
    type:1,  // type 1查看 2编辑（新增）
    name:"",
    id:'',
    List:[],
    logList:[]
})
// const  infoRules=reactive({
//     name:[{
//       required: true,
//       trigger: 'blur',
//       message: '请填写预算年份',
//     },]
// })

const dialogXzVisible=reactive({
    title:"",
    show:false,
    value:"",
    checked:false
})
// 跳转
function jump(row){
  router.push('/taskExecution/businessInfo?id='+row.id)
}
// 改变提交状态
function changeStatus(row){
  updateStatus({itemId:visibleData.leftDataList[visibleData.leftIndex].id,id:row.id||"",status:2}).then(res=>{
    if(res.code!=200) return proxy.$message.error(res.msg)
    proxy.$message.success("提交成功")
    getLeftList({taskId:visibleData.id,selectType}).then(r=>{
      visibleData.leftDataList=r.data||[]
     
      if( visibleData.leftDataList.length>0)  getRightTableList()
    })
  })
}
// 提交表单保存数据
function save(){
  let obj={
    itemId:visibleData.leftDataList[visibleData.leftIndex].id,
    taskId:visibleData.id,
    id: infoForm.id
  }
   infoForm.List.map((item)=>{
    obj[item.fieldName]=item.value
  })

  refInfoForm.value.validate((valid) => {
    if (valid) {
      if( obj.estimated_incurred<obj.actual_incurred) return  proxy.$message.error('全年预计发生费用必须>=本年实际发生额')
      budgetSave(obj).then(res=>{
        if(res.code!=200) return proxy.$message.error(res.msg)
          proxy.$message.success("操作成功")
          dialogFormVisible.value=false
          getRightTableList()
        })
    } else {
      console.log('error submit!')
      return false
    }
  })


 

  
}

// 点击左侧更换下标
function changeIndex(index){
  visibleData.leftIndex=index
  getRightTableList()
}
// 获取右边 table title 数据
function getRightTableList() {
  getRightTable({itemId:visibleData.leftDataList[visibleData.leftIndex].id}).then(r=>{
      visibleData.rightTableList=r.data||[]
      getRightDataList()
    })
}
// 获取 table 内容数据
function getRightDataList(){
  getRightData({taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,...visibleQueryParams,selectType}).then(r=>{
       visibleData.rightDataList=r.rows||[]
       visibleQueryParams.total=r.total
    })
}
// 点击查看 或者处理
function changeDialogVisible(row,type){
    // type 1查看 2编辑（新增）
    visibleData.id=row.id
    visibleData.leftTitle=row.name
    visibleData.type=type
    dialogVisible.value=true
    getLeftList({taskId:visibleData.id,selectType}).then(r=>{
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
function changeTips(){
  if(dialogXzVisible.checked){
    updateXZ(visibleData.id)
  }
}
// 添加弹窗列表编辑
function changeInfoFormType(row,type){
   // type 1查看 2编辑（新增）
     infoForm.type=type
    infoForm.id=row&&row.id||""

   
  getInfoForm({itemId:visibleData.leftDataList[visibleData.leftIndex].id,id:row.id||"",selectType,taskId: visibleData.id}).then((res)=>{
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
// 删除
function deleteItem(row){
  delTableItem({itemId:visibleData.leftDataList[visibleData.leftIndex].id,id:row.id}).then((res)=>{
    if(res.code!=200) return proxy.$message.error(res.msg)
    proxy.$message.success('删除成功')
    getRightTableList()
  })

}

/** 查询角色列表 */
function getList() {
  loading.value = true;
  let obj=proxy.addDateRange(queryParams.value, dateRange.value)

  listTask({...obj,selectType}).then(
    (response) => {
      roleList.value = response.rows;
      total.value = response.total;
      loading.value = false;
      
    }
  );
}

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
  ids.value = selection.map((item) => item.roleId);
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
 