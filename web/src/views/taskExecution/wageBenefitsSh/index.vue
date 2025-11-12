<!-- 业财部门 -->
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
        <!-- <el-form-item label="任务时间" style="width: 308px">
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
       
      >
      <!--  @selection-change="handleSelectionChange" -->
        <!-- <el-table-column type="selection" width="55" align="center" /> -->
        <el-table-column type="index" label="序号" width="55" />
        
        <el-table-column label="年度" align="center" prop="year" />
        <el-table-column label="任务名称" align="center" prop="name" />
        <el-table-column label="起始时间" align="center" prop="startTime" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
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
          <!-- <el-tooltip
            content="查看"
            placement="top"
          
          >
            <el-button
              link
              type="primary"
              @click="changeDialogVisible(scope.row,1)"
           
              >查看</el-button
            >
          </el-tooltip> -->
          <!--  v-hasPermi -->
         
            <el-button
              link
              type="primary"
              @click="changeDialogVisible(scope.row,2)"
             
              >审查</el-button
            >
            <el-button
              link
              type="primary"
              @click="exportAll(scope.row)"
              >导出</el-button
            >
       
            <el-button
              link
              type="primary"
             @click='clickHz(scope.row)'
              v-hasPermi="['budget:financial:summary']"
              >汇总</el-button
            >
         
        
            <el-button
              link
              type="primary"
              @click="showBB(scope.row)"
                v-hasPermi="['budget:financial:version']"
              >版本生成</el-button
            >
            <el-dropdown>
            <div style="height: 23px;display: flex;align-items: center;font-size: 14px;margin-left: 12px;color: #409eff;">
              更多
              <el-icon >
                <arrow-down />
              </el-icon>
            </div>
           
            <template #dropdown>
              <el-dropdown-menu>
              
                    <el-dropdown-item>
                          <el-popconfirm title="确认全部通过?" @confirm="allIsPass(scope.row,5)">
                            <template #reference>
                          全部通过
                        </template>
                      </el-popconfirm>
                    </el-dropdown-item>
                    <el-dropdown-item>
                      <el-popconfirm title="确认全部拒绝?" @confirm="allIsPass(scope.row,6)">
                        <template #reference>
                      全部拒绝
                        </template>
                      </el-popconfirm>
                    </el-dropdown-item>

               
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        
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
      <!-- 查看 审批 -->
      <el-dialog fullscreen v-model="dialogVisible" width='90%' draggable :close-on-press-escape='false'  row-key="deptId" @expand-change="handleNodeExpand"   :expand-row-keys="expandedRowKeys">
        <div class="dialogBox">
            <!-- <div class="left">
              <div class='title'><div>{{visibleData.leftTitle}} </div><el-icon style="cursor: pointer;" @click="dialogXzVisible.show=true"><WarningFilled /></el-icon></div>
                <el-input placeholder="搜索" v-model="visibleData.query" size="small" style="margin: 10px 5%;width:90%;;"></el-input>

                <div class="content">
                  <template v-for="(item,index) in  visibleData.leftDataList" :key="item.id">
                    <div @click="changeIndex(index)" v-if="item.show" :class="visibleData.leftIndex==index? 'item action':'item'">
                        <div>{{item.deptName}}   </div>
                     
  
                    </div>
                  </template>
                </div>
  
            </div> -->
            <div class="right">
                <div class='btnBox' >
  
                 
                    <!-- 预算费用:{{ visibleData.tj.budget}} &nbsp;&nbsp;&nbsp;&nbsp;
                    本年预算费用:{{visibleData.tj.budgetYear}}&nbsp;&nbsp;&nbsp;&nbsp;
                    本年实际发生费用:{{ visibleData.tj.actualIncurred}}&nbsp;&nbsp;&nbsp;&nbsp;
                    全年预计发生费用:{{ visibleData.tj.estimatedIncurred}}&nbsp;&nbsp;&nbsp;&nbsp;
                     -->
  
                     <div>
                        <el-button type="primary" size="small" style="margin-bottom: 10px" @click="plBtn">批量审批</el-button>
                      </div>
                </div> 
                  
                <el-table   :data="visibleData.rightDataList"  height='100%' width='100%'  row-key="deptId" @expand-change="handleNodeExpand"   :expand-row-keys="expandedRowKeys">
                    <el-table-column type="expand" fixed="left">
                        <template #default="props">
                            <div style="width:100%;margin: 0 auto;"> 
                                <el-table  @selection-change="(selection)=>handleSelectionChange(props.$index,selection)"  :data="props.row.dataTable"  height='50vh' width="100%" >
                            <el-table-column  type="selection" width="55" align="center"  :selectable="selectable"  />

                            <!-- :summary-method="getSummaries" show-summary  -->
                                <el-table-column  width="140" align="center" label='单位名称'  prop="deptName"  >
                                <template  #default="scope">
                                        {{ scope.row. deptName}}
                                </template>
                                </el-table-column>
                                <el-table-column  width="120" align="center" label='状态'  prop="status"  >
                                    <template #default="scope">
                                        <dict-tag :options="budget_status" :value="scope.row.status" />
                                    </template>
                                </el-table-column>

                                <!-- 修改后: 为所有数字列添加千分位格式化显示 -->
                                <el-table-column  width="140" align="center" label='工资总额（员工）'  prop="staff">
                                    <template #default="scope">
                                        ¥ {{ formatNumber(scope.row.staff) }}
                                    </template>
                                </el-table-column>       
                                <el-table-column  width="130" align="center" label='劳务派遣'  prop="labor">
                                    <template #default="scope">
                                        ¥ {{ formatNumber(scope.row.labor) }}
                                    </template>
                                </el-table-column>
                                <el-table-column  width="130" align="center" label='福利费'  prop="welfare">
                                    <template #default="scope">
                                        ¥ {{ formatNumber(scope.row.welfare) }}
                                    </template>
                                </el-table-column>  
                                <el-table-column  width="130" align="center" label='教育经费'  prop="education">
                                    <template #default="scope">
                                        ¥ {{ formatNumber(scope.row.education) }}
                                    </template>
                                </el-table-column>       
                                <el-table-column  width="130" align="center" label='工会经费'  prop="unionFunds">
                                    <template #default="scope">
                                        ¥ {{ formatNumber(scope.row.unionFunds) }}
                                    </template>
                                </el-table-column>
                                          
                                <el-table-column  width="130" align="center" label='养老保险'  prop="endowment">
                                    <template #default="scope">
                                        ¥ {{ formatNumber(scope.row.endowment) }}
                                    </template>
                                </el-table-column>

                                <el-table-column  width="130" align="center" label='工伤保险'  prop="workInjury">
                                    <template #default="scope">
                                        ¥ {{ formatNumber(scope.row.workInjury) }}
                                    </template>
                                </el-table-column>

                                <el-table-column  width="130" align="center" label='失业保险'  prop="unemployment">
                                    <template #default="scope">
                                        ¥ {{ formatNumber(scope.row.unemployment) }}
                                    </template>
                                </el-table-column>

                                <el-table-column  width="130" align="center" label='医疗保险'  prop="medical">
                                    <template #default="scope">
                                        ¥ {{ formatNumber(scope.row.medical) }}
                                    </template>
                                </el-table-column>

                                <el-table-column  width="130" align="center" label='生育保险'  prop="maternity">
                                    <template #default="scope">
                                        ¥ {{ formatNumber(scope.row.maternity) }}
                                    </template>
                                </el-table-column>

                                <el-table-column  width="130" align="center" label='社会保险费-单位部分'  prop="social">
                                    <template #default="scope">
                                        ¥ {{ formatNumber(scope.row.social) }}
                                    </template>
                                </el-table-column>

                                <el-table-column  width="130" align="center" label='住房公积金-单位部分'  prop="provident">
                                    <template #default="scope">
                                        ¥ {{ formatNumber(scope.row.provident) }}
                                    </template>
                                </el-table-column>

                                <el-table-column  width="130" align="center" label='企业年金-单位部分'  prop="annuity">
                                    <template #default="scope">
                                        ¥ {{ formatNumber(scope.row.annuity) }}
                                    </template>
                                </el-table-column>

                                <el-table-column  width="130" align="center" label='研发经费'  prop="research">
                                    <template #default="scope">
                                        ¥ {{ formatNumber(scope.row.research) }}
                                    </template>
                                </el-table-column>
                                <el-table-column  align="center"   width="200" label='总计'  prop="zj"  fixed="right">
                                    <template #default="scope">
                                        ¥ {{ formatNumber(scope.row.staff + scope.row.labor + scope.row.welfare + scope.row.education + scope.row.unionFunds +  scope.row.endowment + scope.row.workInjury + scope.row.unemployment + scope.row.medical + scope.row.maternity + scope.row.social + scope.row.provident + scope.row.annuity + scope.row.research) }}
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
                                                @click='spBtn(scope.row,props.$index)'
                                                v-if='scope.row.status==2'
                                            
                                                >审批</el-button>
                                            
                                                
                                        </template>
                                        </el-table-column>
                                
                                </el-table>
                            </div>
                         
                        </template>
                    </el-table-column>
                    <el-table-column  width="140" fixed="left" align="center" label='公司名称'  prop="deptName"  >
                    <!-- <template #default="scope">
                        
                    </template> -->
                </el-table-column>       
                <!-- 修改后: 为所有数字列添加千分位格式化显示 -->
                <el-table-column  width="140" align="center" label='工资总额（员工）'  prop="staff">
                    <template #default="scope">
                        ¥ {{ formatNumber(scope.row.staff) }}
                    </template>
                </el-table-column>       
                <el-table-column  width="130" align="center" label='劳务派遣'  prop="labor">
                    <template #default="scope">
                        ¥ {{ formatNumber(scope.row.labor) }}
                    </template>
                </el-table-column>
                <el-table-column  width="130" align="center" label='福利费'  prop="welfare">
                    <template #default="scope">
                        ¥ {{ formatNumber(scope.row.welfare) }}
                    </template>
                </el-table-column>  
                <el-table-column  width="130" align="center" label='教育经费'  prop="education">
                    <template #default="scope">
                        ¥ {{ formatNumber(scope.row.education) }}
                    </template>
                </el-table-column>       
                <el-table-column  width="130" align="center" label='工会经费'  prop="unionFunds">
                    <template #default="scope">
                        ¥ {{ formatNumber(scope.row.unionFunds) }}
                    </template>
                </el-table-column>
                          
                <el-table-column  width="130" align="center" label='养老保险'  prop="endowment">
                    <template #default="scope">
                        ¥ {{ formatNumber(scope.row.endowment) }}
                    </template>
                </el-table-column>

                <el-table-column  width="130" align="center" label='工伤保险'  prop="workInjury">
                    <template #default="scope">
                        ¥ {{ formatNumber(scope.row.workInjury) }}
                    </template>
                </el-table-column>

                <el-table-column  width="130" align="center" label='失业保险'  prop="unemployment">
                    <template #default="scope">
                        ¥ {{ formatNumber(scope.row.unemployment) }}
                    </template>
                </el-table-column>

                <el-table-column  width="130" align="center" label='医疗保险'  prop="medical">
                    <template #default="scope">
                        ¥ {{ formatNumber(scope.row.medical) }}
                    </template>
                </el-table-column>

                <el-table-column  width="130" align="center" label='生育保险'  prop="maternity">
                    <template #default="scope">
                        ¥ {{ formatNumber(scope.row.maternity) }}
                    </template>
                </el-table-column>

                <el-table-column  width="130" align="center" label='社会保险费-单位部分'  prop="social">
                    <template #default="scope">
                        ¥ {{ formatNumber(scope.row.social) }}
                    </template>
                </el-table-column>

                <el-table-column  width="130" align="center" label='住房公积金-单位部分'  prop="provident">
                    <template #default="scope">
                        ¥ {{ formatNumber(scope.row.provident) }}
                    </template>
                </el-table-column>

                <el-table-column  width="130" align="center" label='企业年金-单位部分'  prop="annuity">
                    <template #default="scope">
                        ¥ {{ formatNumber(scope.row.annuity) }}
                    </template>
                </el-table-column>

                <el-table-column  width="130" align="center" label='研发经费'  prop="research">
                    <template #default="scope">
                        ¥ {{ formatNumber(scope.row.research) }}
                    </template>
                </el-table-column>
                <el-table-column  align="center"   width="200" label='总计'  prop="zj"  fixed="right">
                    <template #default="scope">
                        ¥ {{ formatNumber(scope.row.staff + scope.row.labor + scope.row.welfare + scope.row.education + scope.row.unionFunds +  scope.row.endowment + scope.row.workInjury + scope.row.unemployment + scope.row.medical + scope.row.maternity + scope.row.social + scope.row.provident + scope.row.annuity + scope.row.research) }}
                    </template>
                </el-table-column>
                    
                </el-table>
              
                
              
                <!-- <div style="width:98%;position: relative;">
                    <pagination
                    v-show="visibleQueryParams.total > 0"
                    :total="visibleQueryParams.total"
                    v-model:page="visibleQueryParams.pageNum"
                    v-model:limit="visibleQueryParams.pageSize"
                    />
                </div> -->
                
  
            </div>
        </div>
        <!-- 新增编辑 查看等操作 -->
        <el-dialog  v-model="dialogFormVisible" width='60%' :title="infoForm.type==1?'查看':'审核'" draggable append-to-body  :close-on-press-escape='false'>
            <div  style="max-height:80vh;overflow-y: auto; overflow-x: hidden;;width:100%;box-sizing:border-box" >
                <el-form  :rules="infoRules" label-position="right" label-width="150" ref="refInfoForm" :model="infoForm">
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
                    <div v-if='infoForm.type==2' style='width:90%;margin:0 auto 10px'>
                    <h3 style="color:#000;font-weight:bold">确认意见</h3>
                    <el-form-item label="" prop="status">
                      <el-radio-group v-model="infoForm.status" class="ml-4">
                      <el-radio label="5" size="large">通过</el-radio>
                      <el-radio label="6" size="large">不通过</el-radio>
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item label="确认意见" prop="remark">
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
               
            </div>
            <div style="display:flex;justify-content:flex-end;width:100%">
                <div v-if='infoForm.type==2'>
                    <el-button type="primary"  @click='save'>确定</el-button>
                    <el-button type="info"  @click="()=>dialogFormVisible=false">取消</el-button>
                </div>
                <el-button v-else type="info" @click="()=>dialogFormVisible=false">关闭</el-button>
            </div>
        </el-dialog>
  
      
          <!-- 预选填报须知 -->
           <!-- @close="changeTips" -->
          <el-dialog v-model="dialogXzVisible.show" width='60%' :title="dialogXzVisible.title" draggable append-to-body  @close="changeTips" :close-on-press-escape='false'>
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
        <el-dialog v-model="dialogPlshVisible" width='60%' title="审核" draggable append-to-body  :close-on-press-escape='false' >
          <el-form  :rules="infoRules" label-position="right" label-width="150" ref="plInfoForm" :model="infoForm">
                    <div  style='width:90%;margin:0 auto 10px'>
                      <!-- <h3 style="color:#000;font-weight:bold">确认意见</h3> -->
                      <el-form-item label="审核意见" prop="status">
                        <el-radio-group v-model="infoForm.status" class="ml-4">
                        <el-radio label="5" size="large">通过</el-radio>
                        <el-radio label="6" size="large">不通过</el-radio>
                      </el-radio-group>
                    </el-form-item>
                    <el-form-item label="确认意见" prop="remark">
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
      <!-- 版本控制弹窗 -->
      <el-dialog v-model="dialogBbVisibleData.show" width='30%' title="生成版本" draggable    :close-on-press-escape='false'>
        <el-input v-model="dialogBbVisibleData.value" placeholder="版本名称" />
        <div style="display:flex;justify-content:flex-end;width:100%;margin-top:20px">
              
          <el-button type="primary" @click='subSaveVersion'>确定</el-button>
          <el-button type="info"  @click="()=>dialogBbVisibleData.show=false">取消</el-button>
              
  
          </div>
      </el-dialog>
      <!-- 汇总弹窗信息 -->
      <el-dialog fullscreen v-model="hzData.show" width='80%' title="" draggable top="40vh"   :close-on-press-escape='false'>
        <el-tabs type="border-card" v-model='hzData.name'  :before-leave='changeTabs'>
          
          <el-tab-pane label="股份、供气汇总" name='1'>
  
              <tableList :tableData='hzData.tabdataList' :tableHeadData='hzData.tabheadData'/>
           
          </el-tab-pane>
          
  
          <el-tab-pane  label="区域汇总" name='2'>           
             <tableList :tableData='hzData.tabdataList' :tableHeadData='hzData.tabheadData'/>
          </el-tab-pane>
          <!-- <el-tab-pane label="销售费用预算" name='3'>          
              <tableList :tableData='hzData.tabdataList' :tableHeadData='hzData.tabheadData'/>
          </el-tab-pane>
          <el-tab-pane label="管理费用预算" name='4'>
            <tableList :tableData='hzData.tabdataList' :tableHeadData='hzData.tabheadData'/>
            
          </el-tab-pane>
          <el-tab-pane label="研发费用预算" name='5'>
            <tableList :tableData='hzData.tabdataList' :tableHeadData='hzData.tabheadData'/>
          </el-tab-pane> -->
  
        </el-tabs>
      
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
  import {  deptTreeSelect } from "@/api/system/user";
  import {itemAuditField,reportingLog,getXZ,updateXZ,getRightTable,getInfoForm} from "@/api/taskExecution/index"
  import {saveVersion,budgetSummary,chageAllStatus} from '@/api/taskExecution/finance/index'
  // getRightData updateStatus
  import {getTask, listTask,updateTask,addTask} from "@/api/BM/task";
  import { defineComponent, reactive } from "vue";
  import { toReactive } from "@vueuse/core";
  import tableList from '../finance/component/TabList.vue'
  import {getDeptDataList} from "@/api/taskExecution/functionalPosition/index"
import {getLeftList,getRightData,updateStatus,getRightList} from "@/api/taskExecution/wageBenefits/index" 



  const router = useRouter();
  const { proxy } = getCurrentInstance();
  const { budget_status,result_status ,completion_status } = proxy.useDict("budget_status","result_status","completion_status");
  const deptList=ref([])
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
    // sums[1]=""

  sums[sums.length-1]=""
  // sums[sums.length-2]=""

  return sums
}
  // 弹窗分页
  const dialogVisible=ref(false)
  const visibleQueryParams=reactive({
  pageNum: 1,
  pageSize: 10,
  total: 10,
  deptId: undefined,
  status:""
  })

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
      submit:0,
      budget:0,
      budgetYear:0,
      actualIncurred:0,
      estimatedIncurred:0,
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
  const dialogXzVisible=reactive({
      title:"",
      show:false,
      value:"",
      checked:false
  })
  // 汇总 数据
  const hzData=reactive({
      id:'',
      show:false,
      name:'1',
      tabheadData:[{name:'id',key:'key1'},{name:'2024',key:'name',children:[{name:'2',key:'name-1'},{name:'3',key:'name-2'}]},{id:2,name:'测试',key:'name-3'}] ,
      tabdataList:[{key1:'我是第一个',name:'diyige','name-1':'第二个','name-2':'第三个','name-3':'第四个'}],
     
  })
  function clickHz(row){
    hzData.id=row.id
    budgetSummary({type:1,taskId:hzData.id}).then(res=>{
      hzData.tabdataList=res.data.titleDate
      hzData.tabheadData=res.data.titleName
      hzData.name='1'
      hzData.show=true
    })
  
  
  }
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
  function exportAll(row){
    proxy.download('fixed/wages/export', {
      //  deptParentId:visibleData.rightDataList[visibleData.leftIndex].deptId
      selectType:3
     }, `填报${new Date().getTime()}.xlsx`)
  
  }
  watch(()=>infoForm.status,(newVal,oldVal)=>{
    console.log(newVal)
  if(newVal==5) infoForm.remark="通过"
   else infoForm.remark=""
})
  function changeTips(){
  if(dialogXzVisible.checked){
    updateXZ(visibleData.id)
  }
 
}
  async function changeTabs(activeName,oldActiveName){
    hzData.tabdataList=[]
    hzData.tabheadData=[]
    await budgetSummary({type:activeName,taskId:hzData.id}).then(res=>{
      hzData.tabdataList=res.data.titleDate
      hzData.tabheadData=res.data.titleName
      hzData.show=true
      
    })
  
  
  }
  
  // 
  function showBB(row){
    dialogBbVisibleData.id=row.id
    dialogBbVisibleData.show=true
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
    let r=await getLeftList({parentId:node.deptId})
        //    res.data.forEach(item=> item['show']=true)
           deptList.value=r.data
           
    let res=await getRightData({taskId:visibleData.id,deptParentId:node.deptId,selectType})
    
      node.dataTable=res.data||[]
      node.loadDetails=false
        // node.hasOpen=true
        // console.log(node)

  // }else{
    // node.hasOpen=false
    // node.loadDetails=false  
    // console.log(node)
  // }
  


  
}
function spBtn(row,pindex){
    
  if(!row.id && ids.value.length==0) return proxy.$message.error('请选择审核项')
   infoForm.id=row.id||ids.value.join(',')
   dialogPlshVisible.value=true
   visibleData.leftIndex=pindex
   infoForm.Pindex=pindex
   infoForm.remark=''
   infoForm.status=""
 
 
 }
  // 跳转
  function jumpinfo(row){
    window.open('/taskExecution/financeInfo?id='+visibleData.id+'&itemId='+visibleData.leftDataList[visibleData.leftIndex].id+'&deptid='+row.deptId+'&name='+visibleData.leftTitle+'-'+visibleData.leftDataList[visibleData.leftIndex].tableDisplayName+"-"+row.deptName)
  // router.push('/taskExecution/financeInfo?id='+visibleData.id+'&itemId='+visibleData.leftDataList[visibleData.leftIndex].id+'&deptid='+row.deptId+'&name='+visibleData.leftTitle+'-'+visibleData.leftDataList[visibleData.leftIndex].tableDisplayName+"-"+row.deptName)

}
  // 版本生成
  function subSaveVersion(){
    saveVersion({taskId:dialogBbVisibleData.id,name:dialogBbVisibleData.value}).then(res=>{
      console.log(res)
      if(res.code!=200) return proxy.$message.error(res.msg)
      proxy.$message.success("提交成功:"+res.msg)
      dialogBbVisibleData.show=false
  
    })
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
  deptParentId:visibleData.rightDataList[visibleData.leftIndex].deptId,
  taskId:visibleData.id,
  ids: infoForm.id|| ids.value.join(','),
  status:infoForm.status,
  remark:infoForm.remark
}
plInfoForm.value.validate((valid) => {
  if (valid) {
    updateStatus(obj).then(async res=>{
      if(res.code!=200) return proxy.$message.error(res.msg)
      proxy.$message.success("提交成功")
      dialogPlshVisible.value=false
      await  getRightDataList()
      // visibleData.rightDataList[infoForm.Pindex].loadDetails=true
      // let r= await getRightTable({itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType})
      // visibleData.rightDataList[infoForm.Pindex].headTable=r.data||[]
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
   return row.status==5 ||row.status==6?false:true
  }
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
  function changeDialogVisible(row,type){
      // type 1查看 2编辑（新增）
      visibleData.id=row.id
      visibleData.leftTitle=row.name
      visibleData.type=type
      dialogVisible.value=true
      visibleQueryParams.deptId=""
      expandedRowKeys.value=[]
      getLeftList({taskId:row.id,selectType,level:1}).then(r=>{
        // r.data.forEach(item=> item['show']=true)
        // visibleData.leftDataList=r.data||[]
     
        // if( visibleData.leftDataList.length>0)  
        getRightDataList()
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
  async function allIsPass(row,status){
  let res=await chageAllStatus ({taskId:row.id,status,type:2})
  if(res.code!=200) return
    proxy.$modal.msgSuccess("操作成功")
    
    getList()
  
  
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
          getLeftList({taskId:visibleData.id,selectType,reportingType:1,level:1}).then(r=>{
            r.data.forEach(item=> item['show']=true)
            visibleData.leftDataList=r.data||[]
            dialogFormVisible.value=false
            if( visibleData.leftDataList.length>0)  getRightDataList()
          })
        })
       
      } else {
        console.log('error submit!')
        return false
      }
    })
  
  
   
  
    
  }
  function changeInfoFormType(row,type){
     // type 1查看 2编辑（新增）
     infoForm.type=type
      infoForm.id=row&&row.id||""
  let obj={itemId:visibleData.leftDataList[visibleData.leftIndex].id,id:row.id||"",selectType,taskId: visibleData.id}
    itemAuditField(obj).then(res=>{
      infoForm.status=res.data[0].value==3?'5':res.data[0].value
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
  // 点击左侧更换下标
  function changeIndex(index){
    visibleData.leftIndex=index
    expandedRowKeys.value=[]
    // getRightTableList()
    getRightDataList()
  }
  // 获取左边 table title 数据
  function getRightTableList() {
  
    getRightTable({taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType,...visibleQueryParams}).then(r=>{
        visibleData.rightTableList=r.data||[]
        console.log( visibleData.rightTableList)
      
        getRightDataList()
      })
  }
  // 获取 table 内容数据 
  async  function getRightDataList(){

let res=await getRightList({selectType:2,taskId:visibleData.id})
res.data.forEach(item=> item['dataTable']=[])

visibleData.rightDataList=res.data||[]
if(expandedRowKeys.value.length>0){
    // let r=await getLeftList({parentId:visibleData.leftDataList[visibleData.leftIndex].deptId})
    //    res.data.forEach(item=> item['show']=true)
    

     let res=await getRightData({taskId:visibleData.id,deptParentId:visibleData.rightDataList[visibleData.leftIndex].deptId,selectType,pageSize:999999})
        
        visibleData.rightDataList[visibleData.leftIndex].dataTable=res.data||[]
        
        visibleQueryParams.total=res.total ||0
        visibleQueryParams.show=true
}

 
}
  async function getRightDataList1(){
    // getDeptDataList({taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType,...visibleQueryParams}).then(r=>{
    //   r.rows.forEach(item=>{
    //     item['headTable']=[]
    //     item['dataTable']=[]
    //     item['loadDetails']=true
    //     // item['hasOpen']=false
    //   })
    //      visibleData.rightDataList=r.rows||[]
    //      visibleQueryParams.total=r.total
        
    //   })
    let res=await getLeftList({parentId:visibleData.leftDataList[visibleData.leftIndex].deptId})
        res.data.forEach(item=> item['show']=true)
        deptList.value=res.data



        let r=   await  getRightData({taskId:visibleData.id,deptParentId:visibleData.leftDataList[visibleData.leftIndex].deptId,selectType,...visibleQueryParams})
         
      visibleData.rightDataList=r.data||[]
   
      visibleQueryParams.total=r.total ||0
      visibleQueryParams.show=true
      visibleData.rightDataList.forEach(row=>{
    
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
      // itemInfoTj({taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType}).then(res=>{
      //   visibleData.tj.audit=res.data.audit||0
      //   visibleData.tj.count=res.data.count||0
      //   visibleData.tj.submit=res.data.submit||0
      //   visibleData.tj.budget=res.data.budget||0
      //   visibleData.tj.budgetYear=res.data.budgetYear||0
      //   visibleData.tj.actualIncurred=res.data.actualIncurred||0
      //   visibleData.tj.estimatedIncurred=res.data.estimatedIncurred||0



      //  })
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
  function handleSelectionChange(Pindex,selection) {
    infoForm.id=''
    infoForm.Pindex=Pindex
    ids.value = selection.map((item) => item.id);
    // single.value = selection.length != 1;
    // multiple.value = !selection.length;
  }
  
  
  
  
  
  // getDeptTree();
  getList();
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
    width:100%;
    height: 90vh;
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
   