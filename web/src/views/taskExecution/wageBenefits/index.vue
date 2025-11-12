<!-- 工资福利预算填写 -->
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
        :data="dataList"
        @selection-change="handleSelectionChange"
      >
        <!-- <el-table-column type="selection" width="55" align="center" /> -->
        <el-table-column type="index" label="序号" width="55" />
        <el-table-column label="年度" align="center" prop="year" />
        <el-table-column label="任务名称" align="center" prop="name" />
        <el-table-column label="起始时间" align="center" prop="startTime" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="是否结束 " align="center" prop="completionStatus" >
          <template #default="scope">
           <dict-tag :options="completion_status" :value="scope.row.completionStatus" />
         </template>
        </el-table-column>
        <el-table-column label="预算年度 " align="center" prop="budgetYear" >
          <template #default="scope">
           
            <dict-tag :options="result_status" :value="scope.row.budgetYear" />
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
              
                 @click="jump(scope.row,1)"
             
                >查看</el-button
              >
              <!--   @click="changeDialogVisible(scope.row,1)" -->
           
            <!--  v-hasPermi -->
          
              <el-button
                link
                type="primary"
                 @click="jump(scope.row,2)"
                  v-if='scope.row.completionStatus==1'
                >填报</el-button
              >
              <!-- @click="changeDialogVisible(scope.row,2)" -->

            
            <el-button
                link
                type="primary"
                @click="clickRecovery(scope.row)"
                 v-if='scope.row.lastTask'
                >恢复</el-button
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
      <el-dialog v-model="dialogVisible" width='96%' draggable :close-on-press-escape='false' append-to-body>
        <div id='excelcontent' style="height:80vh"></div>
        <div style="display:flex;justify-content:flex-end;width:100%;margin-top:20px">
            
            <el-button type="primary" @click='save'>保存</el-button>
            <el-button type="info"  @click="()=>dialogVisible=false">取消</el-button>
                
    
        </div>

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
  import { reactive ,onMounted,nextTick} from "vue";
  import {getTask, listTask,updateTask,addTask} from "@/api/BM/task";
  import {recovery} from "@/api/taskExecution/wageBenefits/index";


  onMounted(()=>{
   
  })



  const router = useRouter();
  const { proxy } = getCurrentInstance();
  const { budget_status,result_status ,completion_status } = proxy.useDict("budget_status","result_status","completion_status");
  
  const selectType=1
  const dataList = ref([]);
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

  const dialogVisible=ref(false)
 
  const visibleData=reactive({
      id:"",
      type:1,  // type 1查看 2编辑（新增）
      leftDataList:[],
      rightDataList:[],
      rightTableList:[]
  })

  // 点击查看 或者处理
  function changeDialogVisible(row,type){
    visibleData.id=row.id
    visibleData.type=type
    dialogVisible.value=true

    // return
    nextTick(()=>{
        luckysheet.create({
        lang:'zh',
        container: 'excelcontent',
        title: '工资福利', // 设定表格名称
        data:[{name:'工资福利',data:[],row:72,column:17},{name:'研发人工成本',data:[],row:1,column:1},],
        // showsheetbar:false, //是否显示底部sheet页按钮
        enableAddRow:false,
        options:{
            // 其他配置
            userInfo:false, // 不展示用户信息
        },
        myFolderUrl:'',
        cellRightClickConfig:{
            insertRow: false, // 插入行
            insertColumn: false, // 插入列
            deleteRow: false, // 删除选中行
            deleteColumn: false, // 删除选中列
            deleteCell: false, // 删除单元格
            hideRow: false, // 隐藏选中行和显示选中行
            hideColumn: false, // 隐藏选中列和显示选中列
            matrix: false, // 矩阵操作选区
            sort: false, // 排序选区
            filter: false, // 筛选选区
            chart: false, // 图表生成
            image: false, // 插入图片
            link: false, // 插入链接
            data: false, // 数据验证
            cellFormat: false, // 设置单元格格式
            copyAs: false, // 复制为
        },
        showtoolbarConfig:{
            undoRedo: true, //撤销重做，注意撤消重做是两个按钮，由这一个配置决定显示还是隐藏
            paintFormat: true, //格式刷
            fontSize: true, // '字号大小'
            bold: true, // '粗体 (Ctrl+B)'
            italic: true, // '斜体 (Ctrl+I)'
            strikethrough: true, // '删除线 (Alt+Shift+5)'
            underline: true, // '下划线 (Alt+Shift+6)'
            textColor: true, // '文本颜色'
            fillColor: true, // '单元格颜色'
            border: true, // '边框'
            mergeCell: true, // '合并单元格'
            horizontalAlignMode: true, // '水平对齐方式'
            verticalAlignMode: true, // '垂直对齐方式'
            textWrapMode: true, // '换行方式'
            textRotateMode: true, // '文本旋转方式'
            postil:  true, //'批注'
            function: true, // '公式'
            frozenMode: true, // '冻结方式'
            sortAndFilter: true, // '排序和筛选'
        },
        showinfobar:false,
        showtoolbar: false,
        showsheetbarConfig:{
            add: false, //新增sheet  
            menu: false, //sheet管理菜单
           
        },
        sheetRightClickConfig:{
            delete: false, // 删除
            copy: false, // 复制
            rename: false, //重命名
           
            hide: false, //隐藏，取消隐藏
            move: false, //向左移，向右移
        },
        showstatisticBarConfig:{
            count: false, // 计数栏
        }
       
        
    })
    })
   
   
  }
   // 跳转
   function jump(row,type){
    // let cont=0
    // for (const r of visitedViews.value) {
    //   if (r.path === '/taskExecution/businessInfo') {
    //     r.title=row.name
    //     useTagsViewStore().updateVisitedView(r)
    //     // when query is different then update
        
    //   }
    // }
   router.push('/taskExecution/wageBenefitsInfo?id='+row.id+'&name='+row.name+'&type='+type)
   
  }
  function save(){
    let data= luckysheet.getLuckysheetfile()
    console.log(data)
  }
  function clickRecovery(row){
 
    recovery({taskId:row.id}).then((r)=>{
        if(r.code==200){
          proxy.$message.success("恢复详情成功")
          getList()
        }
    })

  }
  /** 查询列表 */
  function getList() {
    loading.value = true;
    let obj=proxy.addDateRange(queryParams.value, dateRange.value)
  
    listTask({...obj,selectType}).then(
      (response) => {
        dataList.value = response.rows;
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
  <style lang="" >
    
  </style>
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
   