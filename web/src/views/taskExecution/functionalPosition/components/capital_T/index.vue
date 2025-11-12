<template>
    <div style="width:100%;height:100%;overflow: auto;">
       <!-- -->
                <div style="display: flex;justify-content: space-between;">
                    <div>
                      预算费用:{{ visibleData.tj.budget}} /{{visibleData.tj.allBudget}}&nbsp;&nbsp;&nbsp;&nbsp;
                      <!-- 本年预算费用:{{visibleData.tj.budgetYear}}/{{visibleData.tj.allBudgetYear}}&nbsp;&nbsp;&nbsp;&nbsp;
                      本年实际发生费用:{{ visibleData.tj.actualIncurred}}/{{visibleData.tj.allActualIncurred}}&nbsp;&nbsp;&nbsp;&nbsp;
                      全年预计发生费用:{{ visibleData.tj.estimatedIncurred}}/{{visibleData.tj.allEstimatedIncurred}}&nbsp;&nbsp;&nbsp;&nbsp; -->
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
                        <el-button   type="primary"  @click="plBtn"  v-hasPermi="['budget:business:submit']" size="small">批量审核</el-button>
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
           
            <el-table  :data="visibleData.rightDataList" height='86%' row-key="deptId"
            @expand-change="handleNodeExpand"   :expand-row-keys="expandedRowKeys">
            <!-- <el-table :data="visibleData.rightDataList"   v-loading="loading"  height='75vh'  @selection-change="handleSelectionChange" > -->
                <el-table-column type="expand">
                  <template #default="props">
                   
                    <el-table :data="props.row.dataTable"  @selection-change="(selection)=>handleSelectionChange(props.$index,selection)"   style="width:98%;margin:0 auto" height='400px'  size='small'  >
                      <el-table-column  :selectable="selectable" type="selection" width="55" align="center"    />
                      <el-table-column  min-width="200" align="center" label='部门'  prop="deptName"  >
                              <!-- <template  #default="scope">
                                <el-select
                                  v-model="scope.row.deptId"
                                  placeholder="请选择"
                                  style="width: 150px"
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
                      <el-table-column  width="120" align="center" label='填报人'  prop="user_name"  ></el-table-column>
                      <el-table-column  width="200" align="center" label='状态'  prop="status"  >
                            <template #default="scope">
                              <dict-tag :options="budget_status" :value="scope.row.status" />
                            </template>
                      </el-table-column>
                    
                      <!-- <template v-if="visibleData.tableName=='naturalgasstations'">
                      
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
                      <el-table-column  width="200" align="center" label='项目类型'  prop="projectType" v-if="visibleData.tableName=='housing'"  >
                          <template  #default="scope">
                            <el-select
                              v-model="scope.row.projectType"
                              placeholder="请选择"
                              style="width: 150px"
                          >
                          
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

                      <el-table-column  width="200" align="center" label='项目名称'  prop="name"  >
                          <template  #default="scope">
                              <el-input v-model="scope.row.name"  />
                          </template>
                      </el-table-column>
                      <el-table-column  width="200" align="center" label='项目现状'  prop="situation"  >
                          <template  #default="scope">
                              <el-input v-model="scope.row.situation"  />
                          </template>
                      </el-table-column>
                      <el-table-column  width="200" align="center" label='建设内容、进度'  prop="contentProgress"  >
                          <template  #default="scope">
                              <el-input v-model="scope.row.contentProgress"  />
                          </template>
                      </el-table-column>
                      <el-table-column  width="200" align="center" label='投资总额'  prop="investment"  >
                          <template  #default="scope">
                              <el-input v-model="scope.row.investment"  />
                          </template>
                      </el-table-column>
                      <el-table-column  width="200" align="center" label='已结算资金'  prop="settled"  >
                          <template  #default="scope">
                              <el-input v-model="scope.row.settled"  />
                          </template>
                      </el-table-column>
                      <el-table-column  width="200" align="center" label='剩余资金'  prop="remaining"  >
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
                      <el-table-column  width="200" align="center" label='投资分析'  prop="analyze"  >
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
                      </template> -->
                    
                      <template v-if="visibleData.tableName=='housing'">
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
                      <el-table-column  width="200" align="center" label='项目名称'  prop="name"  ></el-table-column>
                      <el-table-column  width="200" align="center" label='项目状态'  prop="projectStatus"  >
                          <template  #default="scope">
                            <el-select
                              v-model="scope.row.projectStatus"
                              placeholder="请选择"
                              style="width: 150px"
                              disabled
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
                      <el-table-column  width="200" align="center" label='项目类型'  prop="projectType"   >
                        <template  #default="scope">
                          <el-select
                            v-model="scope.row.projectType"
                            placeholder="请选择"
                            style="width: 150px"
                            disabled
                        >
                    
                        <el-option
                              label="房屋建筑物-办公楼"
                              :value="1"
                           
                          />
                          <el-option
                            label="房屋建筑物-房屋装修"
                            :value="2"
                         
                        />
                        <el-option
                            label="房屋建筑物-仓库等"
                            :value="3"
                         
                        />
                        <el-option
                            label="房屋建筑物-土地"
                            :value="4"
                         
                        />
                        </el-select>
                      </template>
                    </el-table-column>
                      <el-table-column  width="200" align="center" label='项目现状'  prop="situation"  ></el-table-column>
                      <el-table-column width="200" label="数量" align="center" prop="quantity" />
                        
                      <el-table-column  width="200" align="center" label='建设内容、进度'  prop="contentProgress"  />
                  >
                      <el-table-column  width="200" align="center" label='投资分析'  prop="analysis"  />
                         
                      <el-table-column  width="200" align="center" label='投资总额'  prop="investment"  >
                        <template #default="scope">
                          ¥ {{ formatNumber(scope.row.investment) }}

                      </template> 
                      </el-table-column>
                      <el-table-column  width="200" align="center" :label='"截至"+(year-1)+"年底已结算资金(元)"'  prop="completedSettlement"  >
                        <template #default="scope">
                          ¥ {{ formatNumber(scope.row.completedSettlement) }}
                      </template>
                      </el-table-column>
                        
                 
                      <el-table-column  width="160" align="center" :label='year+"年实际结算资金(元)"'  prop="actualSettlement"  >
                        <template #default="scope">
                          ¥ {{ formatNumber(scope.row.actualSettlement) }}
                      </template>
                      </el-table-column>
                      
                      <el-table-column  width="160" align="center" :label='year+"结算资金明细"'  prop="details"  />
                  
                      <el-table-column width="160" :label="(year+1)+'年预计结算金额(元)'" align="center" prop="expectedSettlement" >
                        <template #default="scope">
                          ¥ {{ formatNumber(scope.row.expectedSettlement) }}
                      </template>
                      </el-table-column>
                        
                        
                     
                       
                      <el-table-column  width="160" align="center" :label='(year+2)+"年预计结算情况"'  prop="year1Settlement"  />
                         
                      <el-table-column  width="160" align="center" :label='(year+3)+"年预计结算情况"'  prop="year2Settlement"  />
                        
                      <el-table-column  width="160" align="center" :label='(year+4)+"年预计结算情况"'  prop="year3Settlement"  />
                         
                      <el-table-column  width="160" align="center" :label='(year+5)+"年预计结算情况"'  prop="year4Settlement"  />
                      </template>
                      <template v-if='visibleData.tableName=="pipeline"'>
                      
                        <el-table-column width="200" label="项目状态" align="center" prop="projectStatus" >
                          <template  #default="scope">
                            <el-select
                              v-model="scope.row.projectStatus"
                              placeholder="请选择"
                              style="width: 150px"
                              disabled
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
                      <el-table-column width="200" label="项目类型" align="center" prop="projectType" >
                        <template  #default="scope">
                          <el-select
                            v-model="scope.row.projectType"
                            placeholder="请选择"
                            style="width: 150px"
                             disabled
                        >
                          <el-option
                            
                              label="燃气管道.高压管网"
                              :value="1"
                          />
                          <el-option
                            
                            label="燃气管道.中压管网"
                            :value="2"
                        />
                        <el-option
                            
                            label="燃气管道.低压管网"
                            :value="3"
                        />
                        <el-option
                            
                            label="燃气管道.其他"
                            :value="4"
                        />
                        </el-select>
                      </template>
                    </el-table-column>
                        <el-table-column width="200" label="项目名称" align="center" prop="name" >
                         
                        </el-table-column>
                        <el-table-column width="200" label="项目现状" align="center" prop="situation" >
                          
                        </el-table-column>

                        <el-table-column width="200" label="建设内容、进度" align="center" prop="contentProgress" >
                         
                          </el-table-column>
                        <el-table-column width="200" label="投资概况投资分析" align="center" prop="analysis" >
                          
                        </el-table-column>
                        <el-table-column width="200" label="投资概况管网总长度（km）" align="center" prop="length" >
                          
                          </el-table-column>
                        <el-table-column width="200" label="投资概况投资总额" align="center" prop="investment" >
                          <template #default="scope">
                          ¥ {{ formatNumber(scope.row.investment) }}
                      </template>
                        </el-table-column>

                        <el-table-column width="200" :label="'截止'+(year-1)+'年底已完结长度（km）'" align="center" prop="completedLength" >
                          
                        </el-table-column>
                        <el-table-column width="200" :label="'截止'+(year-1)+'年底已完结资金(元)'" align="center" prop="completedSettlement" >
                          <template #default="scope">
                          ¥ {{ formatNumber(scope.row.completedSettlement) }}
                      </template>
                        </el-table-column>
                        <el-table-column width="200" :label="year+'年实际结算长度（km）'" align="center" prop="actualLength" >
                          
                        </el-table-column>
                        <el-table-column width="200" :label="year+'年实际结算金额(元)'" align="center" prop="actualSettlement" >
                          <template #default="scope">
                          ¥ {{ formatNumber(scope.row.actualSettlement) }}
                      </template>
                          </el-table-column>
                        <el-table-column width="200" :label="(year)+'年结算资金明细'" align="center" prop="details" >
                         
                        </el-table-column>
                        <el-table-column width="200" :label="(year+1)+'年预计结算长度（km）'" align="center" prop="expectedLength" >
                         
                        </el-table-column>
                        <el-table-column width="200" :label="(year+1)+'年预计结算金额(元)'" align="center" prop="expectedSettlement" >
                          <template #default="scope">
                          ¥ {{ formatNumber(scope.row.expectedSettlement) }}
                      </template>
                          </el-table-column>
                       
                        <el-table-column width="160"  :label='(year+2)+"预计结算长度（km）"' align="center" prop="year1Length" >
                            
                        </el-table-column>
                        <el-table-column width="160" :label="(year+2)+'年预计结算金额(元)'" align="center" prop="year1Settlement" >
                          <template #default="scope">
                          ¥ {{ formatNumber(scope.row.year1Settlement) }}
                      </template>
                        </el-table-column>
                        <el-table-column width="160" :label="(year+3)+'年预计结算长度（km）'" align="center" prop="year2Length" >
                           
                        </el-table-column>
                        <el-table-column width="160" :label="(year+3)+'年预计结算金额(元)'" align="center" prop="year2Settlement" >
                          <template #default="scope">
                          ¥ {{ formatNumber(scope.row.year2Settlement) }}
                      </template>
                        </el-table-column>
                        <el-table-column width="160" :label="(year+4)+'年预计结算长度（km）'" align="center" prop="year3Length" >
                           
                        </el-table-column>
                        <el-table-column width="160" :label="(year+4)+'年预计结算金额(元)'" align="center" prop="year3Settlement" >
                          <template #default="scope">
                          ¥ {{ formatNumber(scope.row.year3Settlement) }}
                      </template>
                        </el-table-column>
                        <el-table-column width="160" :label="(year+5)+'年预计结算长度（km）'" align="center" prop="year4Length" >
                           
                        </el-table-column>
                        <el-table-column width="160" :label="(year+5)+'年预计结算金额(元)'" align="center" prop="year4Settlement" >
                          <template #default="scope">
                          ¥ {{ formatNumber(scope.row.year4Settlement) }}
                      </template>
                        </el-table-column>
                        <!-- <el-table-column width="160" :label="(year+5)+'年预计结算（km）'" align="center" prop="year5Length" >
                          
                        </el-table-column>
                        <el-table-column width="160" :label="(year+5)+'年预计结算金额(元)'" align="center" prop="year5Settlement" >
                           
                        </el-table-column> -->
                      </template>
                      <template v-if="visibleData.tableName=='research'">
                        <el-table-column  width="200" align="center" label='项目类型'  prop="projectStatus"  >
                          <template  #default="scope">
                            <el-select
                              v-model="scope.row.projectStatus"
                              placeholder="请选择"
                              style="width: 150px"
                              disabled
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
                          <el-table-column  width="200" align="center" label='项目类别'  prop="projectType"  >
                          <template  #default="scope">
                            <el-select
                              v-model="scope.row.projectType"
                              placeholder="请选择"
                              style="width: 150px"
                              disabled
                          >
                            <el-option
                              
                                label="基础研究"
                                :value="1"
                            />
                            <el-option 
                              
                              label="应用研究"
                              :value="2"
                          />  <el-option
                              
                              label="试验发展"
                              :value="3"
                          />
                          </el-select>
                        </template>
                          </el-table-column>

                          <!-- <el-table-column  width="200" align="center" label='项目编号'  prop="number"  />
                            
                          <el-table-column label="数量" align="center" prop="quantity" /> -->
                          
                          <el-table-column  width="200" align="center" label='项目名称'  prop="name"  />
                          <el-table-column  width="200" align="center" label='协作单位'  prop="cooperativeUnit"></el-table-column>
                          <el-table-column label="计划开题时间" align="center" prop="startTime" width="180">
                            <template #default="scope">
                              <!-- <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span> -->
                              <el-date-picker
                                v-model="scope.row.startTime"
                                type="date"
                                placeholder="选择日期"
                                value-format="YYYY-MM-DD"
                                style="width: 100%;"
                                disabled
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
                                disabled
                              />
                          
                            </template>
                          </el-table-column>
                          <el-table-column  width="200" align="center" label='主要研究内容'  prop="researchContents"  />
                             
                          <el-table-column  width="200" align="center" label='项目预期主要成果形式'  prop="deliverables"  />
                              
                          <el-table-column  width="200" align="center" label='项目总预算金额(元)'  prop="investment"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.investment) }}
                          </template>
                          </el-table-column>
                          <el-table-column  width="200" align="center" :label='"截至"+(year-1)+"年已支出金额(元)"'  prop="completedSettlement"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.completedSettlement) }}
                          </template>
                          </el-table-column>
                             
                          <el-table-column width="200"  :label="year+'年预计支出总金额(元)'" align="center" prop="expectedSettlement" >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.expectedSettlement) }}
                          </template>

                          </el-table-column>
                          <el-table-column :label="year+1+'预算用金额(元)'" align="center" >
                          
                            <el-table-column  width="200" align="center" label='无形资产'  prop="intangibleAssets"  >
                              <template #default="scope">
                                ¥ {{ formatNumber(scope.row.intangibleAssets) }}
                              </template>
                            </el-table-column>
                            <el-table-column  width="200" align="center" label='固定资产'  prop="fixedAssets"  >
                               <template #default="scope">
                                ¥ {{ formatNumber(scope.row.fixedAssets) }}
                              </template>
                            </el-table-column>
                            <el-table-column  width="200" align="center" label='资产预算说明'  prop="fixedAssetsDescription"  >
                               
                            </el-table-column>
                            <el-table-column  width="200" align="center" label='费用小计'  prop="subtotalCosts"  >
                              <template #default="scope">
                                ¥ {{ formatNumber(scope.row.subtotalCosts) }}
                              </template>
                            </el-table-column>
                            <el-table-column  width="200" align="center" label='费用预算说明'  prop="subtotalCostsDescription"  >
                               
                            </el-table-column>
                          </el-table-column>
                          <el-table-column width="160" :label="(year+2)+'年（元）'" align="center" prop="year1Settlement" >
                              <template #default="scope">
                                ¥ {{ formatNumber(scope.row.year1Settlement) }}
                              </template>

                          </el-table-column>
                          <el-table-column width="160" :label="(year+3)+'年（元）'" align="center" prop="year2Settlement" >
                              <template #default="scope">
                                ¥ {{ formatNumber(scope.row.year2Settlement) }}
                              </template>

                          </el-table-column>
                          <el-table-column width="160" :label="(year+4)+'年（元）'" align="center" prop="year3Settlement" >
                              <template #default="scope">
                                ¥ {{ formatNumber(scope.row.year3Settlement) }}
                              </template>

                          </el-table-column>
                          <el-table-column width="160" :label="(year+5)+'年（元）'" align="center" prop="year4Settlement" >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.year4Settlement) }}
                            </template>

                          </el-table-column>
                          
                            
                      </template>
                      <template v-if="visibleData.tableName=='meter'">
                        
                        <el-table-column  width="200" align="center" label='项目类型'  prop="reportingType"  >
                          <template  #default="scope">
                            <el-select
                              v-model="scope.row.reportingType"
                              placeholder="请选择"
                              style="width: 150px"
                              disabled
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
                              disabled
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
                        <el-table-column  width="200" align="center" label='数量'  prop="quantity"  >
                              
                          </el-table-column>
                        <el-table-column  width="200" align="center" label='价格（元）'  prop="price"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.price) }}
                            </template>
                        </el-table-column>
                        <el-table-column  width="200" align="center" :label='(year+1)+"费用预算"'  prop="budget"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.budget) }}
                            </template>
                        </el-table-column>
                        <el-table-column  width="200" align="center" :label='"费用详情说明"'  prop="description"  >
                             
                        </el-table-column>
                        <el-table-column  width="200" align="center" label='职能部室审核后预算'  prop="auditBudget"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.auditBudget) }}
                            </template>
                        </el-table-column>
                        <el-table-column  width="200" align="center" label='审核调整原因'  prop="auditDescription"  >
                             
                        </el-table-column>
                        <el-table-column  width="200" align="center" :label='(year)+"年预算"'  prop="lastBudget"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.lastBudget) }}
                            </template>
                        </el-table-column>
                      
                        <el-table-column  width="200" align="center" :label='(year)+"年累计发生费用"'  prop="lastAccumulatedExpenses"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.lastAccumulatedExpenses) }}
                            </template>

                        </el-table-column>
                      
                        <!-- <el-table-column  width="200" align="center" :label='year+"调剂后预算额度"' prop="adjustmentBudget"  >
                              
                        </el-table-column>
                        <el-table-column  width="200" align="center" :label='year+"调剂明细"'  prop="adjustmentDescription"  >
                              
                        </el-table-column> -->

                        <el-table-column  width="200" align="center" :label='year+"全年预计发生费用"'  prop="projectedCosts"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.projectedCosts) }}
                            </template>
                        </el-table-column>
                      </template>
                      <template v-if="visibleData.tableName=='charity'">
                      
                        <el-table-column  width="200" align="center" :label='(year+1)+"费用预算"'  prop="budget"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.budget) }}
                            </template>
                        </el-table-column>
                      
                        <el-table-column  width="200" align="center" :label='"费用详情说明"'  prop="description"  >
                              
                        </el-table-column>
                      
                       
                      
                        <el-table-column  width="200" align="center" :label='(year)+"年预算"'  prop="lastBudget"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.lastBudget) }}
                            </template>
                        </el-table-column>
                
                        <el-table-column  width="200" align="center" :label='(year)+"年累计发生费用"'  prop="lastAccumulatedExpenses"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.lastAccumulatedExpenses) }}
                            </template>
                        </el-table-column>
                        
                     
                        <el-table-column  width="200" align="center" :label='year+"全年预计发生费用"'  prop="projectedCosts"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.projectedCosts) }}
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
                              disabled
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
                        <el-table-column  width="200" align="center" label='名称'  prop="name"  >
                             
                        </el-table-column>
                    
                        <el-table-column  width="200" align="center" label='数量'  prop="quantity"  >
                             
                        </el-table-column>
                        <el-table-column  width="200" align="center" label='价格'  prop="price"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.price) }}
                            </template>
                        </el-table-column>
                      
                        <el-table-column  width="200" align="center" :label='(year+1)+"费用预算"'  prop="budget"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.budget) }}
                            </template>
                        </el-table-column>
                      
                        <el-table-column  width="200" align="center" :label='"费用详情说明"'  prop="description"  >
                            <template #default="scope">
                              {{ scope.row.description }}
                            </template>
                        </el-table-column>
                      
                      
                      
                        <el-table-column  width="200" align="center" :label='(year)+"年预算"'  prop="lastBudget"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.lastBudget) }}
                            </template>
                        </el-table-column>
                      
                        <el-table-column  width="200" align="center"  :label='(year)+"年累计发生费用"'  prop="lastAccumulatedExpenses"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.lastAccumulatedExpenses) }}
                            </template>

                        </el-table-column>
                      
                        
                        <el-table-column  width="200" align="center" :label='year+"全年预计发生费用"'  prop="projectedCosts"  >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.projectedCosts) }}
                            </template>
                        </el-table-column>
                      </template>
                      <template v-if="visibleData.tableName=='station'">
                        <el-table-column width="200" label="项目状态" align="center" prop="projectStatus" >
                          <template  #default="scope">
                            <el-select
                              v-model="scope.row.projectStatus"
                              placeholder="请选择"
                              style="width: 150px"
                              disabled
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
                        <el-table-column width="200" label="项目类型" align="center" prop="projectType" >
                        <template  #default="scope">
                          <el-select
                            v-model="scope.row.projectType"
                            placeholder="请选择"
                            style="width: 150px"
                            disabled
                        >
                          <el-option
                            
                              label="CNG加气站"
                              :value="1"
                          />
                          <el-option
                            
                            label="LNG加气站"
                            :value="2"
                        />
                        <el-option
                            
                            label="L-CNG合建站"
                            :value="3"
                        />
                        <el-option
                            
                            label="LPG加气站"
                            :value="4"
                        />
                        <el-option
                            
                            label="门站"
                            :value="5"
                        />
                        <el-option
                            
                            label="高中压调压站"
                            :value="6"
                        />
                        <el-option
                            
                            label="气源厂|储配站"
                            :value="7"
                        />
                        <el-option
                            
                            label="其他一"
                            :value="8"
                        />
                        <el-option
                            
                            label="其他二"
                            :value="9"
                        />
                        <el-option
                            
                            label="其他三"
                            :value="10"
                        />
                        <el-option
                            
                            label="分布式光伏"
                            :value="11"
                        />
                        <el-option
                            
                            label="分布式能源"
                            :value="12"
                        />
                        <el-option
                            
                            label="交通能源"
                            :value="13"
                        />
                        </el-select>
                      </template>
                    </el-table-column>

                        <el-table-column width="160" label="项目名称" align="center" prop="name" >
                          
                        </el-table-column>
                        <el-table-column width="160" label="项目现状" align="center" prop="situation" >
                          
                        </el-table-column>
                        <el-table-column width="160" label="数量" align="center" prop="quantity" >
                          
                        </el-table-column>
                        <el-table-column width="160" label="建设内容、进度" align="center" prop="contentProgress" >
                         
                        </el-table-column>
                        <el-table-column width="160" label="投资总额" align="center" prop="investment" >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.investment) }}
                            </template>

                        </el-table-column>
                        <el-table-column width="160" :label="'截止'+(year-1)+'年已结算金额(元)'" align="center" prop="completedSettlement" >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.completedSettlement) }}
                            </template>

                        </el-table-column>
                        <el-table-column width="160" :label="year+'年实际结算金额'" align="center" prop="actualSettlement" >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.actualSettlement) }}
                            </template>

                        </el-table-column>
                        <el-table-column width="160" :label="year+'结算资金明细'" align="center" prop="details" >
                          
                        </el-table-column>
                        <el-table-column width="160" :label="(year+1)+'年预计结算金额(元)'" align="center" prop="expectedSettlement" >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.expectedSettlement) }}
                            </template>

                        </el-table-column>
                       
                        <el-table-column width="160" :label="year+2+'年'" align="center" prop="year1Settlement" >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.year1Settlement) }}
                            </template>

                        </el-table-column>
                        <el-table-column width="160" :label="year+3+'年'" align="center" prop="year2Settlement" >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.year2Settlement) }}
                            </template>

                        </el-table-column>
                        <el-table-column width="160" :label="year+4+'年'" align="center" prop="year3Settlement" >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.year3Settlement) }}
                            </template>

                        </el-table-column>
                        <el-table-column width="160" :label="year+5+'年'" align="center" prop="year4Settlement" >
                            <template #default="scope">
                              ¥ {{ formatNumber(scope.row.year4Settlement) }}
                            </template>
                        </el-table-column>
                      </template>
                      <template v-if='visibleData.tableName=="information_system"'>
                        <el-table-column label="项目状态" width="200" align="center" prop="projectStatus" >
                          <template  #default="scope">
                            <el-select
                              v-model="scope.row.projectStatus"
                              placeholder="请选择"
                              style="width: 150px"
                              disabled
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
                        <el-table-column label="项目名称"  width="160" align="center" prop="name" >
                          
                        </el-table-column>
                        <el-table-column label="数量" width="160" align="center" prop="quantity" >
                          
                        </el-table-column>
                        <el-table-column label="项目现状" width="160" align="center" prop="situation" >
                         
                        </el-table-column>
                        <el-table-column label="建设内容、进度" width="160" align="center" prop="contentProgress" >
                         
                        </el-table-column>
                        <el-table-column label="拟投运时间" width="200" align="center" prop="operationalTime" >
                        
                          <template  #default="scope">
                            <el-date-picker
                            style="width:150px"
                              v-model="scope.row.operationalTime"
                              type="date"
                              placeholder="请选择"
                              format="YYYY-MM-DD"
                              disabled
                            />
                          </template>
                        </el-table-column>
                        <el-table-column label="投资总额" width="160" align="center" prop="investment" >
                          <template #default="scope">
                            ¥ {{ formatNumber(scope.row.investment) }}
                          </template>
                          </el-table-column>
                        <el-table-column :label="'截止'+(year-1)+'年已结算资金(元)'" width="160" align="center" prop="completedSettlement" >
                          <template #default="scope">
                            ¥ {{ formatNumber(scope.row.completedSettlement) }}
                          </template>

                        </el-table-column>
                        <el-table-column :label="year+'年实际结算金额(元)'" width="160" align="center" prop="actualSettlement" >
                          <template #default="scope">
                            ¥ {{ formatNumber(scope.row.actualSettlement) }}
                          </template>

                        </el-table-column>
                        <el-table-column  :label="(year+1)+'年无形资产'" width="160" align="center" prop="intangibleAssets" >
                          <template #default="scope">
                            ¥ {{ formatNumber(scope.row.intangibleAssets) }}
                          </template>
                        </el-table-column>
                        <el-table-column :label="(year+1)+'固定资产'" width="160" align="center" prop="fixedAssets" >
                          <template #default="scope">
                            ¥ {{ formatNumber(scope.row.fixedAssets) }}
                          </template>
                        </el-table-column>
                        <el-table-column width="160" :label="(year+2)+'年(元)'" align="center" prop="year1Settlement" >
                          <template #default="scope">
                            ¥ {{ formatNumber(scope.row.year1Settlement) }}
                          </template>

                        </el-table-column>
                        <el-table-column width="160" :label="(year+3)+'年(元)'" align="center" prop="year2Settlement" >
                          <template #default="scope">
                            ¥ {{ formatNumber(scope.row.year2Settlement) }}
                          </template>

                        </el-table-column>
                        <el-table-column width="160" :label="(year+4)+'年(元)'" align="center" prop="year3Settlement" >
                          <template #default="scope">
                            ¥ {{ formatNumber(scope.row.year3Settlement) }}
                          </template>
                        </el-table-column>
                        <el-table-column width="160" :label="(year+5)+'年(元)'" align="center" prop="year4Settlement" >
                          <template #default="scope">
                            ¥ {{ formatNumber(scope.row.year4Settlement) }}
                          </template>
                        </el-table-column>

                      </template>
                      
                     

                      
                  
                
                    
                      <el-table-column  
                      label="操作"
                      align="center"
                      width="200"
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
                            <!--     v-hasPermi="['budget:financial:audit']" -->
                            <el-button
                        link
                        type="primary"
                        @click='spBtn(scope.row,props.$index,3)'
                        v-if="scope.row.id"
                        >查看审批</el-button>
                      </template>
                      </el-table-column>
                      </el-table>
                  </template>
                  </el-table-column>
              
              <el-table-column  width="140" align="center" label='企业名称'  prop="deptName"  />
              <el-table-column  width="140" align="center" label='未审核数量'  prop="reportNumber"  />
              <template v-if="visibleData.tableName=='housing'">
                <el-table-column  align="center" label="数量"  prop="quantity">

                </el-table-column>
                <el-table-column align="center" :label="(year)+'实际结算金额（含截至年底尚未发生预计数）(元)'" prop="actualSettlement">
                  <template #default="scope">
                    ¥ {{ formatNumber(scope.row.actualSettlement) }}
                  </template>

                </el-table-column>
                <el-table-column align="center" :label="(year+1)+'年预计结算金额（元）'" prop="expectedSettlement">
                  <template #default="scope">
                    ¥ {{ formatNumber(scope.row.expectedSettlement) }}
                  </template>

                </el-table-column>
              </template> 
              <template v-if="visibleData.tableName=='research'">
            

                <el-table-column  align="center" label="数量" prop="quantity">

                </el-table-column>
                <el-table-column align="center" :label="year+'年预计支出总金额（元）'" prop="expectedSettlement">
                  <template #default="scope">
                    ¥ {{ formatNumber(scope.row.expectedSettlement) }}
                  </template>

                </el-table-column>
                <el-table-column  align="center" label="无形资产小计" prop="intangibleAssets">
                  <template #default="scope">
                    ¥ {{ formatNumber(scope.row.intangibleAssets) }}
                  </template>
                </el-table-column>
                <el-table-column  align="center" label="固定资产小计" prop="fixedAssets">
                  <template #default="scope">
                    ¥ {{ formatNumber(scope.row.fixedAssets) }}
                  </template>
                </el-table-column>
              </template> 
              <template v-if="visibleData.tableName=='meter'">
                
                <el-table-column  align="center" label="数量" prop="quantity"></el-table-column>
                <!-- <el-table-column align="center" :label="(year)+'年全年预计采购金额（元）'" prop=""></el-table-column> -->
                <el-table-column align="center" :label="(year+1)+'年采购预算（元）'" prop="budget">
                  <template #default="scope">
                    ¥ {{ formatNumber(scope.row.budget) }}
                  </template>

                </el-table-column>
              </template> 
              <template v-if="visibleData.tableName=='charity'">
                <el-table-column  align="center" :label="(year+1)+'年预算费用'" prop="budget">
                  <template #default="scope">
                    ¥ {{ formatNumber(scope.row.budget) }}
                  </template>

                </el-table-column>
             

              </template> 
              <template v-if="visibleData.tableName=='lowvalue'">
                <el-table-column  align="center" :label="(year+1)+'年预算费用'" prop="budget">
                  <template #default="scope">
                    ¥ {{ formatNumber(scope.row.budget) }}
                  </template>
                </el-table-column>
                <el-table-column  align="center" :label="(year+1)+'年预算费用'" prop="budget">
                  <template #default="scope">
                    ¥ {{ formatNumber(scope.row.budget) }}
                  </template>

                </el-table-column>
              </template> 
              <template v-if="visibleData.tableName=='pipeline'">
                <el-table-column width="160" align="center" :label="year +'实际结算情况（含截至年底尚未发生预计数）'">
                  <el-table-column  align="center" label="结算长度（km）" prop="actualLength"></el-table-column>
                  <el-table-column  align="center" label="结算金额(元)" prop="actualSettlement">
                    <template #default="scope">
                      ¥ {{ formatNumber(scope.row.actualSettlement) }}
                    </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column width="160" align="center" :label="(year+1) +'预计结算情况（元/KM)'">
                  <el-table-column  align="center" label="结算长度（km）" prop="expectedLength"></el-table-column>
                  <el-table-column  align="center" label="结算金额(元)"  prop="expectedSettlement">
                    <template #default="scope">
                      ¥ {{ formatNumber(scope.row.expectedSettlement) }}
                    </template>
                  </el-table-column>
                </el-table-column>
              </template> 
              <template v-if="visibleData.tableName=='station'">
                <el-table-column  align="center" label="数量" prop="quantity"></el-table-column>
                <el-table-column align="center" :label="(year)+'实际结算金额（含截至年底尚未发生预计数）(元)'" prop="actualSettlement">
                  <template #default="scope">
                    ¥ {{ formatNumber(scope.row.actualSettlement) }}
                  </template>
                </el-table-column>
                <el-table-column align="center" :label="(year+1)+'年预计结算金额（元）'" prop="expectedSettlement">
                  <template #default="scope">
                    ¥ {{ formatNumber(scope.row.expectedSettlement) }}
                  </template>
                </el-table-column>
              </template> 
              <template v-if="visibleData.tableName=='information_system'">
                <el-table-column  align="center" label="数量" prop="quantity"></el-table-column>
                <el-table-column align="center" :label="(year)+'实际结算金额（含截至年底尚未发生预计数）(元)'" prop="actualSettlement">
                  <template #default="scope">
                    ¥ {{ formatNumber(scope.row.actualSettlement) }}
                  </template>
                </el-table-column>
                <el-table-column align="center" :label="(year+1)+'无形资产额'" prop="intangibleAssets">
                  <template #default="scope">
                    ¥ {{ formatNumber(scope.row.intangibleAssets) }}
                  </template>
                </el-table-column>
                <el-table-column align="center" :label="(year+1)+'固定资产小计'" prop="fixedAssets">
                  <template #default="scope">
                    ¥ {{ formatNumber(scope.row.fixedAssets) }}
                  </template>

                </el-table-column>
              </template> 
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
                <el-dialog v-model="dialogPlshVisible" width='60%' :title="dialogPlshVisibleType==3?'审批流程':'审核'" draggable append-to-body  :close-on-press-escape='false'>
          <el-form v-if="dialogPlshVisibleType==1"  :rules="infoRules" label-position="right" label-width="150" ref="plInfoForm" :model="infoForm">
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
            <div style="display:flex;justify-content:flex-end;width:100%;margin-top:40px" v-if="dialogPlshVisibleType==1">
               
                    <el-button type="primary" @click='plSave' >确定</el-button>
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
   
   <script setup>
   import { nextTick, onMounted, watch ,defineEmits} from "vue";
   import usePermissionStore from '@/store/modules/permission'
   import useTagsViewStore from '@/store/modules/tagsView'
   
   import {  deptTreeSelect } from "@/api/system/user";
   // getRightDataupdateStatus
   import {getDeptList,getShInfoList,itemInfoTj} from "@/api/taskExecution/index"
   import {getRightData,saveItem,deleteItem,updateStatus} from "@/api/taskExecution/capital/index"
   import {getDeptDataListZ} from "@/api/taskExecution/functionalPosition/index"
   import { toReactive } from "@vueuse/core";
   const { proxy } = getCurrentInstance();
   const { field_type,item_type ,budget_status} = proxy.useDict("field_type","item_type" ,'budget_status');
   import {useLog} from "../../useLog/index"
const {logList,getLogList}=useLog()
   const emit=defineEmits(['clickSpInfo'])
  const expandedRowKeys=ref()
   const route= useRoute();
   const loading = ref(true);
   const deptOptions = ref(undefined);
   const ids=ref([])
   const selectType=2
   const openInfoShow=ref(false)
   const openType=ref(1)
   const visibleData=reactive({
       leftIndex:0,
       tableName:"",
       id:'',
       leftTitle:"",
       rightTitle:'',
       type:1,  // type 1查看 2编辑（新增）
       leftDataList:[],
       rightDataList:[],
       rightTableList:[],
       query:"",
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
   const visibleQueryParams=reactive({
     pageNum: 1,
     pageSize: 10,
     total: 0,
     show:false
   })

   const plInfoForm=ref()
   const deptList=ref([])
   const year=ref()
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


   // 批量审核弹窗
const dialogPlshVisible=ref(false)
const dialogPlshVisibleType=ref()
watch(()=>infoForm.status,(newVal,oldVal)=>{
  if(newVal==3) infoForm.remark="通过" 
  else infoForm.remark=""
})
  //  onMounted(() => {
  //      // console.log(route)
  
  //      year.value=Number(route.query.year)
  //      getLeftList({taskId:route.query.id,selectType,reportingType:2}).then(r=>{
         
  //        r.data.forEach(item=> item['show']=true)
  //        visibleData.leftDataList=r.data||[]
  //        visibleData.tableName=r.data[visibleData.leftIndex].tableName
  //        if( r.data.length>0) {
  //          getRightTableList()
           
  //        } 
        
  //      })
  //  });
  function plSave(){

let obj={
  itemId:visibleData.leftDataList[visibleData.leftIndex].id,
  taskId:visibleData.id,
  ids: infoForm.id|| ids.value.join(','),
  status:infoForm.status,
  remark:infoForm.remark,
  tableName:visibleData.leftDataList[visibleData.leftIndex].tableName
}
let parentId= visibleData.rightDataList[infoForm.Pindex].parentId
plInfoForm.value.validate((valid) => {
  
  if (valid) {
    updateStatus(obj).then(async res=>{
      if(res.code!=200) return proxy.$message.error(res.msg)
      proxy.$message.success("提交成功")
      dialogPlshVisible.value=false
      await getRightDataList()
      // visibleData.rightDataList[infoForm.Pindex].loadDetails=true
      let re=await getRightData({taskId:visibleData.id,tableName:visibleData.leftDataList[visibleData.leftIndex].tableName,parentId,selectType,pageSize:999999})
      if(visibleData.rightDataList.length-1<infoForm.Pindex) return
      visibleData.rightDataList[infoForm.Pindex]['dataTable']=re.data||[]
      

     
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
ids: row.id,
status:2,
remark:'职能审核撤回',
tableName:visibleData.leftDataList[visibleData.leftIndex].tableName
}
let parentId= visibleData.rightDataList[pindex].parentId
updateStatus(obj).then(async res=>{
    if(res.code!=200) return proxy.$message.error(res.msg)
    proxy.$message.success("撤回成功")
    dialogPlshVisible.value=false
    await getRightDataList()
    // visibleData.rightDataList[infoForm.Pindex].loadDetails=true
  
 
    let re=await getRightData({taskId:visibleData.id,tableName:visibleData.leftDataList[visibleData.leftIndex].tableName,parentId,selectType,pageSize:999999})
    if(visibleData.rightDataList.length-1<pindex) return
    visibleData.rightDataList[pindex]['dataTable']=re.data||[]

    // visibleData.rightDataList[infoForm.Pindex].loadDetails=false
   
  })
}



 async function spBtn(row,pindex,type){
    await getLogList(visibleData.leftDataList[visibleData.leftIndex].id,row.id, visibleData.id)
if(!row.id && ids.value.length==0) return proxy.$message.error('请选择审核项')
infoForm.id=row.id||ids.value.join(',')
 infoForm.Pindex=pindex
 
 dialogPlshVisible.value=true
 infoForm.remark=''
 infoForm.status=""
 dialogPlshVisibleType.value=type
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
  dialogPlshVisibleType.value=1

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
    emit('clickSpInfo',row)
     
   }
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
  //  let r= await getRightTable({itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType})
  //     node.headTable=r.data||[]
    let res=await getRightData({taskId:visibleData.id,tableName:visibleData.leftDataList[visibleData.leftIndex].tableName,parentId:node.parentId,selectType,pageSize:999999})
    node.dataTable=res.data||[]
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
     proxy.download('fixed/tables/export', {
       taskId:visibleData.id,tableName:visibleData.leftDataList[visibleData.leftIndex].tableName,itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType:selectType,exportType:2
     }, `填报明细${new Date().getTime()}.xlsx`)
   }
   // 禁用选择
   function selectable(row, index){
   
    return row.status==3||row.status==5?false:true
   
   }

   function save(row,index){
     let obj={
       tableName:visibleData.leftDataList[visibleData.leftIndex].tableName,
       taskId:visibleData.id,
       ...row
     }
       saveItem(obj).then(res=>{
       if(res.code!=200) return proxy.$message.error(res.msg)
           proxy.$message.success("操作成功")
           
           getRightTableList()
       })
       
   
   
   
    
   
     
   }
   // 改变提交状态
   function changeStatus(){
      //  console.log(ids.value)
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
       ['deptId',"projectStatus","projectType","name","number","quantity","startTime","endTime","researchContents","deliverables","investment","completedSettlement","expectedSettlement","cooperativeUnit","intangibleAssets","fixedAssets","fixedAssetsDescription","subtotalCosts","subtotalCostsDescription","auditSettlement","auditDescription",],
       ['deptId','reportingType','projectType','quantity','price','budget','description','auditBudget','auditDescription','lastBudget','lastAccumulatedExpenses','adjustmentBudget','adjustmentDescription','projectedCosts',],
       ['deptId','budget','description','auditBudget','auditDescription','lastBudget','lastAccumulatedExpenses','adjustmentBudget','adjustmentDescription','projectedCosts',],
       ['deptId','reportingType','name','quantity','price','budget','description','auditBudget','auditDescription','lastBudget','lastAccumulatedExpenses','adjustmentBudget','adjustmentDescription','projectedCosts',],
       ['deptId','projectStatus','name','situation','contentProgress','analysis','length','investment','completedLength','completedSettlement','actualLength','actualSettlement','details','expectedLength','expectedSettlement','auditLength','auditSettlement','auditDescription','year1Length','year1Settlement','year2Length','year2Settlement','year3Length','year3Settlement','year4Length','year4Settlement'],
       ['deptId','projectStatus','name','situation','quantity','contentProgress','investment','completedSettlement','actualSettlement','details','expectedSettlement','auditSettlement','auditDescription','year1Settlement','year2Settlement','year3Settlement','year4Settlement'],
       ['deptId','projectStatus','projectType','name','situation','quantity','contentProgress','analysis','investment','completedSettlement','actualSettlement','expectedSettlement','details','auditSettlement','auditDescription','year1Settlement','year2Settlement','year3Settlement','year4Settlement',],
       ['deptId','projectStatus','name','quantity','situation','contentProgress','operationalTime','investment','completedSettlement','actualSettlement','intangibleAssets','fixedAssets','auditIntangibleAssets','auditFixedAssets','auditDescription',],
      ]
     let index=0
   
     switch(visibleData.tableName){
    //  case 'naturalgasstations':
    //    index=0
    //    break;
     case 'housing'://房屋建设、装修
       index=7
       break;
     case 'research'://科研计划申报表
       index=1
       break;
     case 'meter'://计量器具校准、维修、改造
       index=2
       break;
    case 'charity'://慈善公益费用专项费用
       index=3
       break;
    case 'lowvalue': //低值易耗品
       index=4
       break;
    case 'pipeline': //天然气管线
       index=5
       break;
    case 'station'://场站工程
      index=6
      break;
    case 'information_system': //信息系统建设
      index=8
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
     let r= await  getDeptDataListZ({taskId:visibleData.id,tableName:visibleData.leftDataList[visibleData.leftIndex].tableName,selectType,...visibleQueryParams})
             r.rows.forEach(item=>{
                item['headTable']=[]
                item['dataTable']=[]
            
              })
       
        visibleData.rightDataList=r.rows || []

       
       visibleQueryParams.total=r.total || 0
         
      
      
      
       loading.value = false
       
      let obj={taskId:visibleData.id,itemId:visibleData.leftDataList[visibleData.leftIndex].id,selectType}
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
    
   }

   /** 查询部门下拉树结构 */
   function getDeptTree() {
   deptTreeSelect().then(response => {
     deptOptions.value = response.data;
   });
   };
 
     /** 多选框选中数据 */
  function handleSelectionChange(pindex,selection) {
  
    infoForm.Pindex=pindex
  ids.value = selection.map((item) => item.id);

}
   // getDeptTree()
   function changeVisibleData(leftIndex,tableName,type,leftDataList,id,Numyear){
        visibleData.leftIndex=leftIndex
        visibleData.tableName=tableName
        visibleData.type=type
        visibleData.leftDataList=leftDataList
        visibleData.id=id
        expandedRowKeys.value=[]
        ids.value=[]
        year.value=Number(Numyear)
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
   