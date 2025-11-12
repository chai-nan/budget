<template>
 <div class="app-container box">
    <!-- -->
    <div class="hb" :style="{left:isopen?'16%':'1%' }"><el-icon @click="changeIsopen"><Fold v-if="isopen" /> <Expand v-else/></el-icon></div>
   
        <div class="left" :style="{ transition: 'all 0.2s ease',width:isopen? '15%':'0%',overflow:'hidden' ,opacity:isopen? '1':'0'}">
        
            <!-- 展开目录 -->
            
             <div class='title' >资本填报目录 <el-icon style="cursor: pointer;margin-left: 20%;" @click="dialogXzVisible.show=true"><WarningFilled /></el-icon></div>
             <el-input placeholder="搜索" v-model="visibleData.query" size="small" style="margin: 10px 5%;width:90%;;"></el-input>

              <div class="content">
                <template v-for="(item,index) in  visibleData.leftDataList" :key="item.id">
                  <div  v-if="item.show" @click="changeIndex(index,item.tableName)"  :class="visibleData.leftIndex==index? 'item action':'item'" >
                    <div>{{item.tableDisplayName}} </div>
                      <!-- <el-tag :type="item.isReporting==0?'error':'success'">{{item.isReporting==0?'未提交':'已提交'}}</el-tag> -->
                  </div>
                </template>
              </div>
        </div>

        
        <div class="right" :style="{width:isopen?'86%':'100%' }"> 

            <!-- <el-form
                :model="queryParams"
                ref="queryRef"
                v-show="showSearch"
                :inline="true"
                label-width="68px"
                >
               
                <el-form-item label="费用预算" prop="name">
                    <el-input
                    v-model="queryParams.name"
                    placeholder="请输入"
                    clearable
                    style="width: 240px"
                    @keyup.enter="handleQuery"
                    />
                </el-form-item>
                <el-form-item label="部门名称" style="width: 308px">
                    <el-tree-select
                      v-model="queryParams.deptId"
                      :data="deptOptions"
                      :props="{ value: 'id', label: 'label', children: 'children' }"
                      value-key="id"
                      placeholder="请选择归属部门"
                      check-strictly
                   />
                </el-form-item>
               
                <el-form-item>
                    <el-button type="primary" icon="Search" @click="handleQuery"
                    >搜索</el-button
                    >
                    <el-button icon="Refresh" @click="resetQuery">重置</el-button>
                </el-form-item>
            </el-form> -->
            <!-- <el-row :gutter="10" class="mb8">
                <right-toolbar
                    v-model:showSearch="showSearch"
                    @queryTable="getList"
                ></right-toolbar>
            </el-row> -->
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
            <el-table :data="visibleData.rightDataList"   v-loading="loading"  height='75vh'  @selection-change="handleSelectionChange" >
                    <el-table-column  :selectable="selectable" type="selection" width="55" align="center"    />
                    <el-table-column  width="200" align="center" label='部门'  prop="deptId"  >
                            <template  #default="scope">
                              <el-select
                                v-model="scope.row.deptId"
                                placeholder="请选择"
                                style="width: 150px"
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
                    <template v-if="visibleData.tableName=='naturalgasstations'||visibleData.tableName=='housing'">
                      <!-- <el-table-column  width="200" align="center" label='部门'  prop="deptId"  >
                        <template  #default="scope">
                          <el-select
                            v-model="scope.row.deptId"
                            placeholder="请选择"
                            style="width: 150px"
                        >
                          <el-option
                              v-for="dict in deptList"
                              :key="dict.deptId"
                              :label="dict.deptName"
                              :value="dict.deptId"
                          />
                        </el-select>
                      </template>
                    </el-table-column> -->
                    <el-table-column  width="200" align="center" label='项目类别'  prop="projectStatus"  >
                        <template  #default="scope">
                          <el-select
                            v-model="scope.row.projectStatus"
                            placeholder="请选择"
                            style="width: 150px"
                        >
                          <el-option
                            
                              label="续建项目"
                              :value="1"
                          />
                          <el-option
                            
                            label="新增项目"
                            :value="2"
                        />
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column  width="200" align="center" label='项目类型'  prop="projectType"  >
                        <template  #default="scope">
                          <el-select
                            v-model="scope.row.projectType"
                            placeholder="请选择"
                            style="width: 150px"
                        >
                          <el-option
                              label="天气热管线"
                              :value="1"
                               v-if="visibleData.tableName=='naturalgasstations'"
                          />
                          <el-option
                            label="场站"
                            :value="2"
                               v-if="visibleData.tableName=='naturalgasstations'"
                        />
                        <el-option
                              label="房屋建设"
                              :value="1"
                             v-if="visibleData.tableName=='housing'"
                          />
                          <el-option
                            label="房屋装修"
                            :value="2"
                              v-if="visibleData.tableName=='housing'"
                        />
                        </el-select>
                      </template>
                    </el-table-column>

                    <el-table-column  width="120" align="center" label='项目名称'  prop="name"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.name"  />
                        </template>
                    </el-table-column>
                    <el-table-column  width="120" align="center" label='项目现状'  prop="situation"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.situation"  />
                        </template>
                    </el-table-column>
                    <el-table-column  width="120" align="center" label='建设内容、进度'  prop="contentProgress"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.contentProgress"  />
                        </template>
                    </el-table-column>
                    <el-table-column  width="120" align="center" label='投资总额'  prop="investment"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.investment"  />
                        </template>
                    </el-table-column>
                    <el-table-column  width="120" align="center" label='已结算资金'  prop="settled"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.settled"  />
                        </template>
                    </el-table-column>
                    <el-table-column  width="120" align="center" label='剩余资金'  prop="remaining"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.remaining"  />
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" :label='year+"年结算资金"'  prop="settlement"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.settlement"  />
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" :label='year+"年预计结算金额"'  prop="expected"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.expected"  />
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" label='结算资金明细'  prop="details"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.details"  />
                        </template>
                    </el-table-column>
                    <el-table-column  width="120" align="center" label='投资分析'  prop="analyze"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.analyze"  />
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" :label='(year+1)+"年预计结算金额"'  prop="year1"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.year1"  />
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" :label='(year+2)+"年预计结算金额"'  prop="year2"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.year2"  />
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" :label='(year+3)+"年预计结算金额"'  prop="year3"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.year3"  />
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" :label='(year+4)+"年预计结算金额"'  prop="year4"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.year4"  />
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" label='管控部门'  prop="controlDept"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.controlDept"  />
                        </template>
                    </el-table-column>
                    </template>
                    <template v-if="visibleData.tableName=='research'">
                        <el-table-column  width="200" align="center" label='项目类别'  prop="projectType"  >
                        <template  #default="scope">
                          <el-select
                            v-model="scope.row.projectType"
                            placeholder="请选择"
                            style="width: 150px"
                        >
                          <el-option
                            
                              label="技术创新"
                              :value="1"
                          />
                          <el-option
                            
                            label="技术引进"
                            :value="2"
                        />  <el-option
                            
                            label="软课题研究"
                            :value="3"
                        />
                        </el-select>
                      </template>
                        </el-table-column>
                          <!-- <el-table-column label="立项单位" align="center" prop="deptId" /> -->
                          <!-- <el-table-column  width="200" align="center" label='部门'  prop="deptId"  >
                            <template  #default="scope">
                              <el-select
                                v-model="scope.row.deptId"
                                placeholder="请选择"
                                style="width: 150px"
                            >
                              <el-option
                                  v-for="dict in deptList"
                                  :key="dict.deptId"
                                  :label="dict.deptName"
                                  :value="dict.deptId"
                              />
                            </el-select>
                          </template>
                        </el-table-column> -->
                      <!-- <el-table-column  width="120" align="center" label='立项单位'  prop="name"  >
                        <template  #default="scope">
                             <el-input v-model="scope.row.name"  />
                        </template>
                    </el-table-column> -->
                        <el-table-column  width="120" align="center" label='项目名称'  prop="name"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.name"  />
                            </template>
                        </el-table-column>
                        <el-table-column  width="120" align="center" label='项目编号'  prop="number"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.number" />
                            </template>
                        </el-table-column>
                        <el-table-column label="计划开题时间" align="center" prop="startTime" width="180">
                          <template #default="scope">
                            <!-- <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span> -->
                             <el-date-picker
                              v-model="scope.row.startTime"
                              type="date"
                              placeholder="选择日期"
                              value-format="YYYY-MM-DD"
                              style="width: 100%;"
                            />
                          </template>
                        </el-table-column>
                        <el-table-column label="计划结题时间" align="center" prop="endTime" width="180">
                          <template #default="scope">
                            <el-date-picker
                              v-model="scope.row.endTime"
                              type="date"
                              placeholder="选择日期"
                              value-format="YYYY-MM-DD"
                              style="width: 100%;"
                            />
                        
                          </template>
                        </el-table-column>
                        <el-table-column  width="120" align="center" label='主要研究内容'  prop="researchContents"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.researchContents"  />
                            </template>
                        </el-table-column>
                        <el-table-column  width="120" align="center" label='项目预期主要成果形式'  prop="deliverables"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.deliverables"  />
                            </template>
                        </el-table-column>
                        <el-table-column  width="120" align="center" label='项目总预算金额'  prop="totalBudget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.totalBudget"  />
                            </template>
                        </el-table-column>
                        <el-table-column  width="120" align="center" :label='year+"项目经费概算(万元)"'  prop="budgetEstimate"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.budgetEstimate"  />
                            </template>
                        </el-table-column>
                        <el-table-column  width="120" align="center" label='承担单位'  prop="undertakingUnit"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.undertakingUnit"  />
                            </template>
                        </el-table-column>
                        
                        <el-table-column  width="120" align="center" label='协作单位'  prop="cooperativeUnit"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.cooperativeUnit"  />
                            </template>
                        </el-table-column>
                  
                        <el-table-column  width="120" align="center" label='无形资产'  prop="intangibleAssets"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.intangibleAssets"  />
                            </template>
                        </el-table-column>
                        <el-table-column  width="120" align="center" label='固定资产'  prop="fixedAssets"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.fixedAssets"  />
                            </template>
                        </el-table-column>

                        <el-table-column  width="120" align="center" label='费用小计'  prop="subtotalCosts"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.subtotalCosts"  />
                            </template>
                        </el-table-column>
                        <el-table-column  width="120" align="center" label='年费用预算'  prop="budget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.budget"  />
                            </template>
                        </el-table-column>
                        <!-- <el-table-column  width="120" align="center" label='职能部室审核后预算'  prop="auditBudget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.auditBudget"  />
                            </template>
                        </el-table-column>
                        <el-table-column  width="120" align="center" label='审核调整原因'  prop="auditDescription"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.auditDescription"  />
                            </template>
                        </el-table-column> -->

                        <el-table-column  width="120" align="center" label='上年预算'  prop="lastBudget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.lastBudget"  />
                            </template>
                        </el-table-column>
                        <el-table-column  width="120" align="center" label='上年累计发生费用'  prop="lastAccumulatedExpenses"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.lastAccumulatedExpenses"  />
                            </template>
                        </el-table-column>
                        <el-table-column  width="120" align="center" label='调剂后预算额度'  prop="adjustmentBudget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.adjustmentBudget"  />
                            </template>
                        </el-table-column>
                        <el-table-column  width="120" align="center" label='调剂明细'  prop="adjustmentDescription"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.adjustmentDescription"  />
                            </template>
                        </el-table-column>
                        <el-table-column  width="120" align="center" label='全年预计发生费用'  prop="projectedCosts"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.projectedCosts"  />
                            </template>
                        </el-table-column>
                      
                    </template>
                    <template v-if="visibleData.tableName=='meter'">
                      <!-- <el-table-column  width="200" align="center" label='部门'  prop="deptId"  >
                            <template  #default="scope">
                              <el-select
                                v-model="scope.row.deptId"
                                placeholder="请选择"
                                style="width: 150px"
                            >
                              <el-option
                                  v-for="dict in deptList"
                                  :key="dict.deptId"
                                  :label="dict.deptName"
                                  :value="dict.deptId"
                              />
                            </el-select>
                          </template>
                        </el-table-column> -->
                      <el-table-column  width="200" align="center" label='项目类型'  prop="reportingType"  >
                        <template  #default="scope">
                          <el-select
                            v-model="scope.row.reportingType"
                            placeholder="请选择"
                            style="width: 150px"
                        >
                          <el-option
                            
                              label="安全类"
                              :value="1"
                          />
                          <el-option
                            
                            label="非安全类"
                            :value="2"
                        />
                        </el-select>
                      </template>
                        </el-table-column>
                      <el-table-column  width="200" align="center" label='项目类别'  prop="projectType"  >
                        <template  #default="scope">
                          <el-select
                            v-model="scope.row.projectType"
                            placeholder="请选择"
                            style="width: 150px"
                        >
                          <el-option
                            
                              label="校准"
                              :value="1"
                          />
                          <el-option
                            
                            label="维修"
                            :value="2"
                        />
                        <el-option
                            
                            label="改造"
                            :value="3"
                        />
                        </el-select>
                      </template>
                        </el-table-column>
                      <el-table-column  width="120" align="center" label='数量'  prop="quantity"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.quantity"  />
                            </template>
                        </el-table-column>
                      <el-table-column  width="120" align="center" label='价格'  prop="price"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.price"  />
                            </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='费用预算'  prop="budget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.budget"  />
                            </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='费用详情说明'  prop="description"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.description"  />
                            </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='职能部室审核后预算'  prop="auditBudget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.auditBudget"  />
                            </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='审核调整原因'  prop="auditDescription"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.auditDescription"  />
                            </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='上年预算'  prop="lastBudget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.lastBudget"  />
                            </template>
                      </el-table-column>
                     
                      <el-table-column  width="120" align="center" label='上年累计发生费用'  prop="lastAccumulatedExpenses"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.lastAccumulatedExpenses"  />
                            </template>
                      </el-table-column>
                     
                      <el-table-column  width="120" align="center" label='调剂后预算额度'  prop="adjustmentBudget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.adjustmentBudget"  />
                            </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='调剂明细'  prop="adjustmentDescription"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.adjustmentDescription"  />
                            </template>
                      </el-table-column>

                      <el-table-column  width="120" align="center" label='全年预计发生费用'  prop="projectedCosts"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.projectedCosts"  />
                            </template>
                      </el-table-column>
                    </template>
                    <template v-if="visibleData.tableName=='charity'">
                    
                      <el-table-column  width="120" align="center" label='费用预算'  prop="budget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.budget"  />
                            </template>
                      </el-table-column>
                     
                      <el-table-column  width="120" align="center" label='费用详情说明'  prop="description"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.description"  />
                            </template>
                      </el-table-column>
                    
                      <el-table-column  width="120" align="center" label='职能部室审核后预算'  prop="auditBudget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.auditBudget"  />
                            </template>
                      </el-table-column>
                    
                      <el-table-column  width="120" align="center" label='审核调整原因'  prop="auditDescription"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.auditDescription"  />
                            </template>
                      </el-table-column>
                     
                      <el-table-column  width="120" align="center" label='上年预算'  prop="lastBudget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.lastBudget"  />
                            </template>
                      </el-table-column>
              
                      <el-table-column  width="120" align="center" label='上年累计发生费用'  prop="lastAccumulatedExpenses"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.lastAccumulatedExpenses"  />
                            </template>
                      </el-table-column>
                      
                      <el-table-column  width="120" align="center" label='调剂后预算额度'  prop="adjustmentBudget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.adjustmentBudget"  />
                            </template>
                      </el-table-column>
                 
                      <el-table-column  width="120" align="center" label='调剂明细'  prop="adjustmentDescription"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.adjustmentDescription"  />
                            </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='全年预计发生费用'  prop="projectedCosts"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.projectedCosts"  />
                            </template>
                      </el-table-column>
                    </template>
                    <template v-if="visibleData.tableName=='lowvalue'">
                      <el-table-column  width="200" align="center" label='项目类型'  prop="reportingType"  >
                        <template  #default="scope">
                          <el-select
                            v-model="scope.row.reportingType"
                            placeholder="请选择"
                            style="width: 150px"
                        >
                          <el-option
                            
                              label="工具购置类"
                              :value="1"
                          />
                          <el-option
                            
                            label="办公用品类"
                            :value="2"
                        /> <el-option
                            
                            label="计量器具类"
                            :value="3"
                        /><el-option
                            
                            label="信息类"
                            :value="4"
                        />
                        </el-select>
                      </template>
                        </el-table-column>
                      <el-table-column  width="120" align="center" label='名称'  prop="name"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.name"  />
                            </template>
                      </el-table-column>
                  
                      <el-table-column  width="120" align="center" label='数量'  prop="quantity"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.quantity"  />
                            </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='价格'  prop="price"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.price"  />
                            </template>
                      </el-table-column>
                     
                      <el-table-column  width="120" align="center" label='费用预算'  prop="budget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.budget"  />
                            </template>
                      </el-table-column>
                     
                      <el-table-column  width="120" align="center" label='费用详情说明'  prop="description"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.description"  />
                            </template>
                      </el-table-column>
                     
                      <el-table-column  width="120" align="center" label='职能部室审核后预算'  prop="auditBudget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.auditBudget"  />
                            </template>
                      </el-table-column>
                      
                      <el-table-column  width="120" align="center" label='审核调整原因'  prop="auditDescription"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.auditDescription"  />
                            </template>
                      </el-table-column>
                     
                      <el-table-column  width="120" align="center" label='上年预算'  prop="lastBudget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.lastBudget"  />
                            </template>
                      </el-table-column>
                     
                      <el-table-column  width="120" align="center" label='上年累计发生费用'  prop="lastAccumulatedExpenses"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.lastAccumulatedExpenses"  />
                            </template>
                      </el-table-column>
                     
                      <el-table-column  width="120" align="center" label='调剂后预算额度'  prop="adjustmentBudget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.adjustmentBudget"  />
                            </template>
                      </el-table-column>
                     
                      <el-table-column  width="120" align="center" label='调剂明细'  prop="adjustmentDescription"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.adjustmentDescription"  />
                            </template>
                      </el-table-column>
                      <el-table-column  width="120" align="center" label='全年预计发生费用'  prop="projectedCosts"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.projectedCosts"  />
                            </template>
                      </el-table-column>
                    </template>
                    
                <el-table-column  width="120" align="center" label='状态'  prop="status"  >
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
                            v-if='scope.row.status==1||scope.row.status==4'
                             v-hasPermi="['budget:business:add']"
                            >保存</el-button>
                      </template>
                       
                            <el-button
                            link
                            type="primary"
                            @click='clickSpInfo(scope.row,scope.$index)'
                            v-if="scope.row.id"
                            >查看审批</el-button>
                            <el-popconfirm title="确定要删除吗？" @confirm="clickDelete(scope.row,scope.$index)">
                              <template #reference>
                                <el-button
                                  link
                                  type="danger"
                                 v-if='scope.row.status==1||scope.row.status==4'
                                  v-hasPermi="['budget:business:delete']"
                              
                                  >删除</el-button>
                              </template>
                            </el-popconfirm>
                        
                           

                           
                    </template>
                    </el-table-column>
                </el-table>
                <div style="margin-top: 20px;">
                    <el-button
                    type="primary"
                    icon='Plus'
                @click="addOne"
                 v-if='!visibleQueryParams.show'
                    >新增一条</el-button>
                </div>
               
              
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
import { nextTick, onMounted, watch } from "vue";
import usePermissionStore from '@/store/modules/permission'
import useTagsViewStore from '@/store/modules/tagsView'

import {  deptTreeSelect } from "@/api/system/user";
// getRightDataupdateStatus
import {reportingLog,getXZ,updateXZ,getLeftList,getRightTable,getInfoForm,budgetSave,delTableItem,addRightData,getDeptList,getDeptActualInfo} from "@/api/taskExecution/index"
import {getRightData,saveItem,deleteItem,updateStatus} from "@/api/taskExecution/capital/index"
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
    tableName:"",
    id:route.query.id,
    leftTitle:"",
    rightTitle:'',
    type:1,  // type 1查看 2编辑（新增）
    leftDataList:[],
    rightDataList:[],
    rightTableList:[],
    query:"",

})
const visibleQueryParams=reactive({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  show:false
})

const deptList=ref([])
const logListShow=ref(false)
const year=ref(new Date().getFullYear())
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

onMounted(() => {
    // console.log(route)
    chageTitle()
   
    getXZ(route.query.id).then(res=>{
              dialogXzVisible.title=route.query.name
              dialogXzVisible.value=res.data.reportingExplain
              if(res.data.isTips==0)dialogXzVisible.show=true 
              else dialogXzVisible.show=false
            })
    year.value=Number(route.query.year)
    getLeftList({taskId:route.query.id,selectType,reportingType:2}).then(r=>{
      
      r.data.forEach(item=> item['show']=true)
      visibleData.leftDataList=r.data||[]
      visibleData.tableName=r.data[visibleData.leftIndex].tableName
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
    deleteItem({tableName:visibleData.leftDataList[visibleData.leftIndex].tableName,id:row.id}).then(res=>{
      if(res.code==200) {
      
      
        proxy.$message.error('删除成功！')
        // getLeftList({taskId:route.query.id,selectType,reportingType:2}).then(r=>{
        //   visibleData.leftDataList=r.data||[]
        //   visibleData.tableName=r.data[visibleData.leftIndex].tableName
        //   if( visibleData.leftDataList.length>0) {
            getRightTableList()
            
        //   } 
        
        // })
      }
    })
  }else{
    visibleData.rightDataList.splice(index,1)
  }


}
function clickSpInfo(row){

  reportingLog({itemId:visibleData.leftDataList[visibleData.leftIndex].id,id:row.id||0,taskId: route.query.id}).then(res=>{
    if(res.code!=200) return proxy.$message.error(res.msg)
    logList.value=res.data
    logListShow.value=true
  })
}
function handleExport() {
  proxy.download('fixed/tables/export', {
    taskId:visibleData.id,tableName:visibleData.leftDataList[visibleData.leftIndex].tableName
  }, `填报明细${new Date().getTime()}.xlsx`)
}
// 禁用选择
function selectable(row, index){

 return row.id!=''&&row.status==1|| row.status==4
}
function changeDept(deptid,index){
    if(!deptid) return
  
    getDeptActualInfo({itemId:visibleData.leftDataList[visibleData.leftIndex].id,
    taskId:visibleData.id,dept_id:deptid}).then(res=>{
    visibleData.rightDataList[index].actual_incurred=res.data.actual_incurred||0
    visibleData.rightDataList[index].estimated_incurred=res.data.estimated_incurred||0

        
    })

}
function save(row,index){
  let obj={
    tableName:visibleData.leftDataList[visibleData.leftIndex].tableName,
    taskId:visibleData.id,
    ...row
  }

  // console.log(visibleData.rightTableList)
  // return
 


    // if( obj.estimated_incurred<obj.actual_incurred) return  proxy.$message.error('全年预计发生费用必须>=本年实际发生额')
    saveItem(obj).then(res=>{
    if(res.code!=200) return proxy.$message.error(res.msg)
        proxy.$message.success("操作成功")
        
        getRightTableList()
    })
    



 

  
}
// 改变提交状态
function changeStatus(){
    console.log(ids.value)
  updateStatus({itemId:visibleData.leftDataList[visibleData.leftIndex].id,tableName:visibleData.leftDataList[visibleData.leftIndex].tableName,ids: ids.value.join(','),status:2}).then(res=>{
    if(res.code!=200) return proxy.$message.error(res.msg)
    proxy.$message.success("提交成功")
    // getLeftList({taskId:visibleData.id,selectType,reportingType:2}).then(r=>{
    //   visibleData.leftDataList=r.data||[]
    //   visibleData.tableName=r.data[visibleData.leftIndex].tableName
      // if( visibleData.leftDataList.length>0) 
       getRightTableList()
    // })
  })
}
// 新增一条
function addOne(){
  let arr=[
    ['deptId','projectStatus','projectType','name','situation','contentProgress','investment','settled','remaining','settlement','expected','details','analyze','controlDept',"year1","year2","year3","year4"],
    ['projectType','deptId','name','number','startTime','endTime','researchContents','deliverables','totalBudget','budgetEstimate','undertakingUnit','cooperativeUnit','intangibleAssets','fixedAssets','subtotalCosts','budget','auditBudget','auditDescription','lastBudget','lastAccumulatedExpenses','adjustmentBudget','adjustmentDescription','projectedCosts'],
    ['deptId','reportingType','projectType','quantity','price','budget','description','auditBudget','auditDescription','lastBudget','lastAccumulatedExpenses','adjustmentBudget','adjustmentDescription','projectedCosts',],
    ['deptId','budget','description','auditBudget','auditDescription','lastBudget','lastAccumulatedExpenses','adjustmentBudget','adjustmentDescription','projectedCosts',],
    ['deptId','reportingType','name','quantity','price','budget','description','auditBudget','auditDescription','lastBudget','lastAccumulatedExpenses','adjustmentBudget','adjustmentDescription','projectedCosts',],
  ]
  let index=0

  switch(visibleData.tableName){
    case 'naturalgasstations':
      index=0
      break;
    case 'housing':
      index=0
      break;
    case 'research':
      index=1
      break;
    case 'meter':
      index=2
      break;
      case 'charity':
      index=3
      break;
      case 'lowvalue':
      index=4
      break;
      
  }
  let obj={
    id:"",
    status:1,
  }

                    
  arr[index].forEach(item=>{
    obj[item]=''

  })
  
  visibleData.rightDataList.push(obj)
 
}
// 获取右边 table title 数据
function getRightTableList() {
  loading.value = true;
  // getRightTable({itemId:visibleData.leftDataList[visibleData.leftIndex].id}).then(r=>{
  
  //     visibleData.rightTableList=r.data||[]
      
      getRightDataList()
    // })
}
// 获取 table 内容数据
async function getRightDataList(){
    getDeptList({itemId:visibleData.leftDataList[visibleData.leftIndex].id}).then(res=>{
            deptList.value=res.data
            })
          
    if(route.query.type==1){
      visibleQueryParams.show=true
    }
    getRightData({taskId:visibleData.id,tableName:visibleData.leftDataList[visibleData.leftIndex].tableName,selectType,...visibleQueryParams}).then(r=>{
         
       visibleData.rightDataList=r.data || []
    
       visibleQueryParams.total=r.total || 0
      //  visibleQueryParams.show=true
      
    })
   
    // }else{
    //   getRightData({taskId:visibleData.id,tableName:visibleData.leftDataList[visibleData.leftIndex].tableName,selectType}).then(async r=>{
    //       console.log(r)
    //       let arr=r.data||[]
    //     //   let arr2=new Set(arr.map(item=>item.dept_id))
    //     // //  r.data||[]
    //     // for(const item of deptList.value){
    //     //   if(!arr2.has(item.deptId)){
    //     //     let res= await  getDeptActualInfo({itemId:visibleData.leftDataList[visibleData.leftIndex].id,
    //     //       taskId:visibleData.id,dept_id:item.deptId})
    //     //     let obj={
    //     //       id:"",
    //     //       dept_id:item.deptId,          
             
    //     //       status:1
             
    //     //     }
    //     //     visibleData.rightTableList.forEach(item=>{
    //     //       obj[item.fieldName]=''
    //     //     })
    //     //       arr.push(obj)
    //     //   }
    //     // }
    //     visibleData.rightDataList=arr
    //     //    visibleQueryParams.total=r.total
    //      visibleQueryParams.show=false
    //      visibleQueryParams.total=0
    // })
    // }
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
function changeIndex(index,name){
  loading.value = true;
  visibleData.leftIndex=index
  visibleData.tableName=name

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
