<template>
  <div class="home">
    
    <!-- <div style="display: flex; justify-content: flex-end;box-sizing: border-box;right: 20px;position: absolute;">
        <el-button size="small" @click="getSpShow" type="success">查看填报审核人员</el-button>
        
      </div>
    
      <el-dialog v-model="spOpen" width="1000px" append-to-body>
        <el-tabs type="border-card"  v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="填报人员" name="first">
               <el-image style="width: 400px;height: 300px;" :src="one" fit="scale-down"   :preview-src-list="[one]"/>
          </el-tab-pane>
          <el-tab-pane label="职能审核人员" name="second">
               <el-image style="width: 400px;height: 300px;" :src="two" fit="scale-down"   :preview-src-list="[two,three]"/>
               <el-image style="width: 400px;height: 300px;" :src="two" fit="scale-down"   :preview-src-list="[three,two]"/>
               
          </el-tab-pane>
        
        </el-tabs>
      </el-dialog> -->
     
      <div style="width: 400px;margin-bottom: 10px;">
        <el-select v-model="taskId">
          <el-option :label="item.name" :value="item.id" v-for="item in tasklist" :key="item.id"></el-option>
        </el-select>
        
      </div>
      <div class="top">
        <div style="width:49%">
          <h4>待办事项</h4>
          <div class="top_box">
            <template v-if="dbList.length>0">
              <div class="item" v-for="item,index in dbList" :key="index" @click="jump(item)">
                <div>{{item.name}}</div>
                <div style="width: 20%;;">{{ item.startTime }}</div>
                <div style=" width: 20%;">{{ item.statusName }} </div>
              </div>
            </template>
            <template v-if="unauditedWarningVOList.length>0">
              <div class="item" v-for="item,index in unauditedWarningVOList" :key="index">
                <el-tooltip :content="item.taskName" placement="top">
                  <div>{{item.taskName}}</div>
                </el-tooltip>
                <el-tooltip :content="item.companyName" placement="top">
                  <div style="width: 20%;">{{ item.companyName }}</div>
                </el-tooltip>
                <el-tooltip :content="item.deptName" placement="top">
                  <div style=" width: 20%;">{{ item.deptName }} </div>
                </el-tooltip>
                <el-tooltip :content="item.tableDisplayName" placement="top">
                  <div style=" width: 20%;">{{ item.tableDisplayName }} </div>
                </el-tooltip>
                <div style=" width: 10%;">未及时审核</div>
              </div>
            </template>
            <template v-if="reportWarningList.length>0">
              <div class="item" v-for="item,index in reportWarningList" :key="index">
                <el-tooltip :content="item.message" placement="top">
                  <div>{{item.message}}</div>
                </el-tooltip>
              </div>
            </template>
            <el-empty description="无" v-if="dbList.length<=0 && unauditedWarningVOList.length<=0 && reportWarningList.length<=0"/>
          
          </div>

        
        </div>
        <div style="width:49%">
          <h4>预算填报分析</h4>
          <div class="top_box" style="display: flex;flex-direction: column;justify-content:space-around;height:35vh;min-width: 300px;min-height:300px">
            <div style="height: 80px;" >
             

              <div class="top_box_item top_box_item1"   @click="jump({'statusName':'待填报'})">
                 <img :src="rw" alt="" style="width: 50px;height: 50px;margin-right:40px ;margin:0 20px  0 30px;">

                <div style="display: flex;flex-direction: column;">
                    <div>{{tj['已填报数量']}} (个) </div>
                    <div>已填报数量</div>
                </div>

                </div>

            </div>
           
            <div style="width: 100%;height: 80px;display: flex;justify-content: space-between;">
                <div class="top_box_item top_box_item1"  @click="jump({'statusName':'待职能岗审核'})">
                  <img :src="hr" alt="" style="width: 50px;height: 50px;margin-right:40px ;margin:0 20px  0 30px;">
                  <div style="display: flex;flex-direction: column;">

                  <div>{{tj['职能待审批']}}(个)</div>
                  <div>职能待审批</div>
                  </div>
                 </div>
                 <div class="top_box_item top_box_item2">
                  <img :src="tg" alt="" style="width: 50px;height: 50px;margin-right:40px ;margin:0 20px  0 30px;">
                  <div style="display: flex;flex-direction: column;">

                    <div>{{tj['职能已通过']}}(个)</div>
                    <div>职能已通过</div>
                  </div>
                 </div>
                 <div class="top_box_item top_box_item3">
                  <img :src="xx" alt="" style="width: 50px;height: 50px;margin-right:40px ;margin:0 20px  0 30px;">
                    <div style="display: flex;flex-direction: column;">
                      <div>{{tj['职能已驳回']}}(个)</div>
                      <div>职能已驳回</div>
                    </div>
                 </div>
            </div>
            <div style="width: 100%;height: 80px;display: flex;justify-content: space-between;">
                <div class="top_box_item top_box_item4" @click="jump({'statusName':'待业财审核'})">
                  <img :src="hr" alt="" style="width: 50px;height: 50px;margin-right:40px ;margin:0 20px  0 30px;">

                  <div style="display: flex;flex-direction: column;">
                  <div>{{tj['业财待审批']}}(个)</div>
                  <div>业财待审批</div>
                  </div>
                 </div>
                 <div class="top_box_item top_box_item5">
                  <img :src="tg" alt="" style="width: 50px;height: 50px;margin-right:40px ;margin:0 20px  0 30px;">

                  <div style="display: flex;flex-direction: column;">
                    <div>{{tj['业财已通过']}}(个)</div>
                    <div>业财已通过</div>
                  </div>
                 </div>
                 <div class="top_box_item top_box_item6" >
                    <img :src="xx" alt="" style="width: 50px;height: 50px;margin:0 20px  0 30px;">
                  <div style="display: flex;flex-direction: column;">
                    <div>{{tj['业财已驳回']}}(个)</div>
                    <div>业财已驳回</div>
                  </div>
                 </div>
                 
                 
            </div>
           
          </div>
        </div>
       
      </div>
      <div class="bottom">
        <div style="width:49%">
          <h4>消息通知</h4>
          <div class="top_box">
            <template v-if="tzList.length>0">
            <div class="item" v-for="item,index in tzList" :key="index" @click="clickTz(item)">
              <!-- <el-tooltip
                effect="dark"
                :content="item.message"
              > -->
              <div>{{item.parentName+'-'+item.deptName+'-'+item.message }}</div>
              <!-- </el-tooltip> -->
              <div>时间：{{ item.createdTime }}</div>
            </div> 
          </template>
            <el-empty description="无"  v-else/>
        
          </div>
        </div>
        <div style="width:49%;">
          <div style="display: flex;align-items: center;">
            <h4 style="width: 90%;">通知公告 </h4>
            <span style="font-size: 15px; color: deepskyblue; cursor: pointer; text-align: right;width: 100%;position: relative;" @click="getListAll">更多></span>
            
          </div>
          <div class="top_box">
             <template v-if="noticeList.length>0">
                <div class="notice"  @click="setInfo(item)"  v-for="item,index in noticeList" :key="index">
                <div>
                  <div>{{ item.noticeTitle }}</div>
                  <div v-html="item.noticeContent"></div>
                </div>
                <div>
                  {{ item.createTime }}
                </div>
                
              </div>
             </template>
             
             <el-empty description="无" v-else/>

          </div>
           
        
        </div>
       
      </div>
      
      <el-dialog
        v-model="isNoticeListShow"
       width="90%"
       fullscreen
       @close="close" 
      >
        <div class="top_box" style="height: 86vh; overflow: hidden;padding: 0;border: none;">
              <div class="notice" style="margin-bottom: 5px;" v-for="item,index in noticeList" :key="index" @click="setInfo(item)">
                <div>
                  <div>{{item.noticeTitle}}</div>
                  <div v-html="item.noticeContent"></div>
                </div>
                <div>
                  {{ item.createTime }}
                </div>
                
              </div>

            </div>
            <pagination
         v-show="total > 0"
         :total="total"
         v-model:page="pageNum"
         v-model:limit="pageSize"
         @pagination="getList"
      />
         
      </el-dialog>
      <el-dialog
            v-model="isNoticeInfoShow"
            width="80%"
            :title="info.title"
            append-to-body
      
          >
          <div>{{ info.time }}</div>
          <div v-html="info.info"> </div>
          
        </el-dialog>
    
  </div>
</template>

<script setup name="Index">

import Line from './components/index/line.vue'
import xx from '@/assets/icon/xx.png'
import rw from '@/assets/icon/rw.png'

import tg from '@/assets/icon/tg.png'

import hr from '@/assets/icon/hr.png'

import { ref, reactive, onMounted, watch, watchEffect } from 'vue'
import useUserStore from '@/store/modules/user'
import { listNotice, getNotice } from "@/api/system/notice";
import {taskList,queryBudget,queryDaiban,warningList,getTask,changeTask} from "@/api/index"
import { ElMessage, ElMessageBox } from 'element-plus'
import  one from '@/assets/1.png'
import  two from '@/assets/2.png'
import  three from '@/assets/3.png'
const { proxy ,ctx} = getCurrentInstance();

const { budget_status } = proxy.useDict("budget_status");
const router = useRouter();
const userStore = useUserStore()

const spOpen=ref(false)
const activeName = ref('first')
const isNoticeListShow = ref(false)
const noticeList=ref([])
const total=ref(0)
const  pageNum=ref(1)
const pageSize=ref(10)
const info=reactive({
  title:"测试",
  info:'',
  time:"2022-01-02",
})
const isNoticeInfoShow=ref(false)
const tasklist=ref([])
const taskId=ref()
const tj=ref({})
const dbList=ref([])
const tzList = ref([])

const unauditedWarningVOList = ref([])
const reportWarningList = ref([])
function clickTz(item){
  console.log(item)
  ElMessageBox.alert(item.parentName+'-'+item.deptName+'-'+item.message, item.createdTime, {
    // if you want to disable its autofocus
    // autofocus: false,
    confirmButtonText: '知道了',
    callback: (action) => {
      
    },
  })
}
function setInfo(item){
  info.title=item.noticeTitle
  info.time=item.createTime
  info.info=item.noticeContent
  isNoticeInfoShow.value=true
}

function handleClick(tab, event) {
  activeName.value=tab.name
}
function getSpShow(){
  spOpen.value=!spOpen.value
}
async function getListAll(){
  getList()
  pageNum.value=1

  isNoticeListShow.value=true

}
async function close(){
  let res= await listNotice({pageNum:1,pageSize:5})
      
    noticeList.value=res.rows
    total.value=res.total
}

async function getList (){
  let res= await listNotice({pageNum:pageNum.value,pageSize:pageSize.value})
      
    noticeList.value=res.rows
    total.value=res.total
}
function getTaskList(){
  taskList().then(res=> {
    tasklist.value=res.data
    if(tasklist.value.length>0){
      taskId.value=tasklist.value[0].id
    }
    })
}
function getQueryBudget(){
  queryBudget({taskId:taskId.value}).then(res=>{
      tj.value=res.data
  })
}
function getQueryDaiban(){
  queryDaiban().then(res=>{
    dbList.value = res.data.datas
    console.log(res.data.unauditedWarningVOList)
    unauditedWarningVOList.value = res.data.unauditedWarningVOList
    console.log(res.data.reportWarningList)
    reportWarningList.value = res.data.reportWarningList
    
  })
}
function getWarningList(){
  warningList().then(res=>{
    tzList.value=res.data
    
  })
}
function jump(item){
  let path=''
  console.log(item.statusName)
  switch(item.statusName){
    case '待填报':
      console.log(123)
      path='/taskExecution/business'
      break
    case '待业财审核':
      path='/taskExecution/finance'
      break
      case '待职能岗审核':
      path='/taskExecution/functionalPosition'
      break

  }
  router.push({path})
}
watch(taskId,()=>{
  getQueryBudget()
  
})
function getTaskInfo(){
 
  getTask().then(res=>{
    if( res.status==2) return
    
    ElMessageBox.confirm(
      res.data.name+'是否确认全部完成？',
    '是否完成任务',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(() => {
    
       changeTask({taskId:res.data.id}).then(r=>{
        ElMessage({
        type: 'success',
        message: '任务已完成',
      })
      })
      
    })
  })
  
}

getTaskList()
close()
getQueryDaiban()
getWarningList()
getTaskInfo()
</script>

<style scoped lang="scss">

.home {
  width:100%;
  min-height:calc(100vh - 84px);
  box-sizing: border-box;
  padding:10px 20px;
  // background: linear-gradient(to right, #9E7EF2,#629DF4,#3CB0F6, #0FC8F8);
background: #EBF1F5;

 // background:url('@/assets/index.jpg') ;
 // background-size: cover;
 position: relative;
  .top,.bottom{
    display: flex;
    justify-content: space-between;
    width: 100%;
    // align-items: flex-start;
  }
  .bottom{
    margin-top: 20px;
  }
  .top>div
  ,.bottom>div{
    // border: 1px solid #ccc;
  
    padding: 10px;
    box-sizing: border-box;
    box-shadow: 1px 1px 10px #e6e4e4;
    background: #fff;
    border-radius: 5px;
    backdrop-filter: blur(5px);
 



  }
  .top>div:hover,.bottom>div:hover{
    background: #fff;
    box-shadow: 1px 1px 10px #ccc;
  }

  .top_box{
    max-height: 35vh;
    width: 100%;
    // border: 1px solid #ccc;
    overflow-y: auto ;
    // padding: 10px;
    box-sizing: border-box;
    // &>div{
    //   margin-bottom: 10px;
    // }
    .top_box_item1{

background: url('../assets/icon/1.jpg');
}
    .top_box_item2{

     background: url('../assets/icon/2.jpg');
    }
    .top_box_item3{

background: url('../assets/icon/3.jpg');
}
.top_box_item4{

background: url('../assets/icon/4.jpg');
}
.top_box_item5{

background: url('../assets/icon/5.jpg');
}
.top_box_item6{

background: url('../assets/icon/6.jpg');
}
    .top_box_item{
      width: 30%;
      min-width: 200px;
      display: flex;
      // flex-direction: column;
      // justify-content:center;
      // align-content: center;
      height: 100%;
      // background: #f1f1f1;
     align-items: center;
   
     background-size: cover;
     border-radius: 10px;
      margin-right: 10px;
      // transition-property:transform;
      cursor: pointer;
      div{
        text-align: center;
        margin-top: 5px;
        
      }
      &:hover{
        // background: #ada6a6;
        // transform: scale(1.05);
        font-weight: 400;
        // color: #eee;
        letter-spacing: 4px;
       
        
      }
    
    
    }
   
    .notice{
      width:100%;
      display: flex;
      justify-content: space-between;
      align-items: center;
      box-sizing: border-box;
      border: 1px solid #eeecec;
      padding:10px 20px;
      box-sizing: border-box;
      font-size: 14px;
      cursor: pointer;
      &>div:first-child{
        width: 70%;
        &>div{
          overflow: hidden;
        white-space: normal;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 1;
        -webkit-box-orient: vertical;
        }
      
       
      }
    }
    .notice:hover{
      background: rgba( 51,146,255,.2) ;
    }
  }
  h4{
    padding:0;
    margin:10px;
    font-weight: bold;
    position: relative;
   
  }
  h4:after{
    content:"";
    position: absolute;
    width: 4px;
    height:86%;
    top: 7%;
    background: #f59760;
    left: -10px;
    
  }
 
  .item{
    display: flex;
    justify-content: space-between;
    padding:10px 20px;
    cursor: pointer;
    font-size: 14px;
    height: 40px;
    align-items: center;
      &>div:first-child{
        // flex: 1;
        max-width: 100%;
        min-width: 30%;
      
      }
      &>div{
        overflow: hidden;
        white-space: normal;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 1;
        -webkit-box-orient: vertical;
      }
  }
  .item:hover{
    background: rgba( 51,146,255,.2) ;
   
    
  }
}
</style>

