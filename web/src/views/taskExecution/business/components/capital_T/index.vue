<template>
    <div style="width:100%">
       <!-- -->
            <div style="margin-bottom:10px;display:flex;justify-content: flex-end;align-items: center">
                <el-button   type="primary" @click="changeStatus" v-if='visibleData.type==2'   v-hasPermi="['budget:business:submit']">提交审核</el-button>
                <el-button
                    type="warning"
                    plain
                    icon="Download"
                    @click='handleExport'
                  
                    v-hasPermi="['budget:business:export']"
                    >导出</el-button>
            </div>
            <el-table row-key="id" :data="visibleData.rightDataList" ref="tableRef"  v-loading="loading"  height='75vh'  @selection-change="handleSelectionChange" >
                    <el-table-column  :selectable="selectable" type="selection" width="55" align="center"    />
                    <el-table-column  min-width="200" align="center" label='部门'  prop="deptId"  >
                            <template  #default="scope">
                              <el-select
                                v-model="scope.row.deptId"
                                placeholder="请选择"
                                style="width: 150px"
                                :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
                            >
                              <el-option
                                  v-for="dict in deptList"
                                  :key="dict.deptId"
                                  :label="dict.parentName+'-'+dict.deptName"
                                  :value="dict.deptId"
                              />
                            </el-select>
                          </template>
                        </el-table-column>
                    <el-table-column  min-width="200" align="center" label='状态'  prop="status"  >
                        <template #default="scope">
                          <dict-tag :options="budget_status" :value="scope.row.status" />
                        </template>
                    </el-table-column>
                    <!-- <el-table-column  min-width="200" align="center" label='填报人'  prop="informant"  >
                      <template  #default="scope">
                            <el-input v-model="scope.row.informant"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                        </el-table-column> -->

                    <template v-if="visibleData.tableName=='naturalgasstations'">
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
                             :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
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
                

                    <el-table-column  width="200" align="center" label='项目名称'  prop="name"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.name }}</span>

                            <el-input v-else v-model="scope.row.name"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="200" align="center" label='项目现状'  prop="situation"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.situation }}</span>

                            <el-input v-else v-model="scope.row.situation"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="200" align="center" label='建设内容、进度'  prop="contentProgress"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.contentProgress}}</span>

                            <el-input v-else v-model="scope.row.contentProgress"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="200" align="center" label='投资总额'  prop="investment"  >
                        <template  #default="scope">
                           <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.investment}}</span>

                            <el-input v-else v-model="scope.row.investment"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                    </el-table-column>
                    <el-table-column  width="200" align="center" label='已结算资金(元)'  prop="settled"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.settled }}</span>

                            <el-input v-else v-model="scope.row.settled"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="200" align="center" label='剩余资金(元)'  prop="remaining"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.remaining }}</span>
                          
                            <el-input v-else v-model="scope.row.remaining"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" :label='year+"年结算资金(元)"'  prop="settlement"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.settlement}}</span>

                            <el-input v-else v-model="scope.row.settlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" :label='year+"年预计结算金额(元)"'  prop="expected"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{scope.row.expected }}</span>

                            <el-input v-else v-model="scope.row.expected"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" label='结算资金明细'  prop="details"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.details }}</span>

                            <el-input v-else v-model="scope.row.details"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                    </el-table-column>
                    <el-table-column  width="200" align="center" label='投资分析'  prop="analyze"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.analyze}}</span>

                            <el-input v-else v-model="scope.row.analyze"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" :label='(year+1)+"年预计结算金额(元)"'  prop="year1"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year1 }}</span>

                            <el-input v-else v-model="scope.row.year1"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" :label='(year+2)+"年预计结算金额(元)"'  prop="year2"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year2}}</span>

                            <el-input v-else v-model="scope.row.year2"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" :label='(year+3)+"年预计结算金额(元)"'  prop="year3"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year3 }}</span>

                            <el-input v-else v-model="scope.row.year3"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" :label='(year+4)+"年预计结算金额(元)"'  prop="year4"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year4}}</span>

                            <el-input v-else v-model="scope.row.year4"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" label='管控部门'  prop="controlDept"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{scope.row.controlDept }}</span>

                            <el-input v-else v-model="scope.row.controlDept"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    </template>
                  
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
                    <el-table-column  width="200" align="center" label='项目名称'  prop="name"  >
                        <template  #default="scope">
                         <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.name }}</span>

                            <el-input v-else v-model="scope.row.name"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="200" align="center" label='项目状态'  prop="projectStatus"  >
                        <template  #default="scope">
                          <el-select
                            v-model="scope.row.projectStatus"
                            placeholder="请选择"
                            style="width: 150px"
                             :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
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
                            :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
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

                    
                    <el-table-column  width="200" align="center" label='项目现状'  prop="situation"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.situation }}</span>

                            <el-input v-else v-model="scope.row.situation"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column width="200" label="数量" align="center" prop="quantity" >
                      <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.quantity }}</span>

                            <el-input v-else v-model="scope.row.quantity"  type="number"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                    <el-table-column  width="200" align="center" label='建设内容、进度'  prop="contentProgress"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.contentProgress }}</span>

                            <el-input v-else v-model="scope.row.contentProgress"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="200" align="center" label='投资分析'  prop="analysis"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.analysis }}</span>

                            <el-input v-else v-model="scope.row.analysis"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="200" align="center" label='投资总额'  prop="investment"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.investment }}</span>

                            <el-input v-else v-model="scope.row.investment"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="200" align="center" :label='"截至"+(year-1)+"年底已结算资金(元)"'  prop="completedSettlement"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.completedSettlement}}</span>

                            <el-input v-else v-model="scope.row.completedSettlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <!-- <el-table-column  width="200" align="center" label='剩余资金'  prop="remaining"  >
                        <template  #default="scope">
                            <el-input v-model="scope.row.remaining"  />
                        </template>
                    </el-table-column> -->
                    <el-table-column  width="160" align="center" :label='year+"年实际结算资金(元)"'  prop="actualSettlement"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.actualSettlement }}</span>

                            <el-input v-else v-model="scope.row.actualSettlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" :label='year+"结算资金明细"'  prop="details"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{scope.row.details }}</span>

                            <el-input v-else v-model="scope.row.details"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column width="160" :label="(year+1)+'年预计结算金额(元)'" align="center" prop="expectedSettlement" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.expectedSettlement }}</span>

                            <el-input v-else v-model="scope.row.expectedSettlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
   
                    
                    <el-table-column  width="160" align="center" :label='(year+2)+"年预计结算情况"'  prop="year1Settlement"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{scope.row.year1Settlement}}</span>

                            <el-input v-else v-model="scope.row.year1Settlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" :label='(year+3)+"年预计结算情况"'  prop="year2Settlement"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year2Settlement}}</span>

                            <el-input v-else v-model="scope.row.year2Settlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" :label='(year+4)+"年预计结算情况"'  prop="year3Settlement"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year3Settlement}}</span>

                            <el-input v-else v-model="scope.row.year3Settlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                    <el-table-column  width="160" align="center" :label='(year+5)+"年预计结算情况"'  prop="year4Settlement"  >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year4Settlement }}</span>

                            <el-input v-else v-model="scope.row.year4Settlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                    </el-table-column>
                   
                    </template>
                    <template v-if='visibleData.tableName=="pipeline"'>
                      <el-table-column width="200" label="项目状态" align="center" prop="projectStatus" >
                        <template  #default="scope">
                          <el-select
                            v-model="scope.row.projectStatus"
                            placeholder="请选择"
                            style="width: 150px"
                             :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
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
                             :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
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
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{scope.row.name }}</span>

                            <el-input v-else v-model="scope.row.name"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column width="200" label="项目现状" align="center" prop="situation" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{scope.row.situation }}</span>

                            <el-input v-else v-model="scope.row.situation"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column>

                      <el-table-column width="200" label="建设内容、进度" align="center" prop="contentProgress" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.contentProgress }}</span>

                            <el-input v-else v-model="scope.row.contentProgress"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                        </el-table-column>
                      <el-table-column width="200" label="投资概况投资分析" align="center" prop="analysis" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.analysis}}</span>
                          
                            <el-input v-else v-model="scope.row.analysis"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column width="200" label="投资概况管网总长度（km）" align="center" prop="length" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.length }}</span>

                            <el-input v-else v-model="scope.row.length"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                        </el-table-column>
                      <el-table-column width="200" label="投资概况投资总额" align="center" prop="investment" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.investment }}</span>

                            <el-input v-else v-model="scope.row.investment"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>

                      <el-table-column width="200" :label="'截止'+(year-1)+'年底已完结长度（km）'" align="center" prop="completedLength" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.completedLength}}</span>

                            <el-input v-else v-model="scope.row.completedLength"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column width="200" :label="'截止'+(year-1)+'年底已完结资金(元)'" align="center" prop="completedSettlement" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.completedSettlement }}</span>

                            <el-input v-else v-model="scope.row.completedSettlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column width="200" :label="year+'年实际结算长度（km）'" align="center" prop="actualLength" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{scope.row.actualLength }}</span>

                            <el-input v-else v-model="scope.row.actualLength"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column width="200" :label="year+'年实际结算金额(元)'" align="center" prop="actualSettlement" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.actualSettlement }}</span>

                            <el-input v-else v-model="scope.row.actualSettlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                        </el-table-column>
                      <el-table-column width="200" :label="(year)+'年结算资金明细'" align="center" prop="details" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.details }}</span>

                            <el-input v-else v-model="scope.row.details"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column width="200" :label="(year+1)+'年预计结算长度（km）'" align="center" prop="expectedLength" >
                        <template  #default="scope">
                        
                          <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{scope.row.expectedLength }}</span>

                            <el-input v-else v-model="scope.row.expectedLength"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column width="200" :label="(year+1)+'年预计结算金额(元)'" align="center" prop="expectedSettlement" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.expectedSettlement}}</span>

                            <el-input v-else v-model="scope.row.expectedSettlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                        </el-table-column>
                      
                   
                      <el-table-column width="160"  :label='(year+2)+"预计结算长度（km）"' align="center" prop="year1Length" >
                           <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year1Length}}</span>

                            <el-input v-else v-model="scope.row.year1Length"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="(year+2)+'年预计结算金额(元)'" align="center" prop="year1Settlement" >
                           <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year1Settlement}}</span>

                            <el-input v-else v-model="scope.row.year1Settlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="(year+3)+'年预计结算长度（km）'" align="center" prop="year2Length" >
                           <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year2Length }}</span>

                            <el-input v-else v-model="scope.row.year2Length"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="(year+3)+'年预计结算金额(元)'" align="center" prop="year2Settlement" >
                           <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year2Settlement }}</span>

                            <el-input v-else v-model="scope.row.year2Settlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="(year+4)+'年预计结算长度（km）'" align="center" prop="year3Length" >
                           <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year3Length }}</span>

                            <el-input v-else v-model="scope.row.year3Length"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="(year+4)+'年预计结算金额(元)'" align="center" prop="year3Settlement" >
                           <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year3Settlement }}</span>

                            <el-input v-else v-model="scope.row.year3Settlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="(year+5)+'年预计结算长度（km）'" align="center" prop="year4Length" >
                           <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year4Length }}</span>

                            <el-input v-else v-model="scope.row.year4Length"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="(year+5)+'年预计结算金额(元)'" align="center" prop="year4Settlement" >
                           <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year4Settlement}}</span>

                            <el-input v-else v-model="scope.row.year4Settlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column>
                      <!-- <el-table-column width="160" :label="(year+5)+'年预计结算长度（km）'" align="center" prop="year5Length" >
                           <template  #default="scope">
                            <el-input v-model="scope.row.year5Length"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="(year+5)+'年预计结算金额(元)'" align="center" prop="year5Settlement" >
                           <template  #default="scope">
                            <el-input v-model="scope.row.year5Settlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column> -->
                    </template> 
                    <template v-if="visibleData.tableName=='research'">
                      <el-table-column  width="200" align="center" label='项目类型'  prop="projectStatus"  >
                        <template  #default="scope">
                          <el-select
                            v-model="scope.row.projectStatus"
                            placeholder="请选择"
                            style="width: 150px"
                             :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
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
                             :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
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
                         
                   
                      
                        <!-- <el-table-column  width="200" align="center" label='项目编号'  prop="number"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.number"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                        </el-table-column>
                        <el-table-column label="数量" align="center" prop="quantity" >
                          <template  #default="scope">
                                <el-input v-model="scope.row.quantity"  type="number"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                        </el-table-column> -->
                        <el-table-column  width="200" align="center" label='项目名称'  prop="name"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.name}}</span>

                                <el-input v-else v-model="scope.row.name"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                        </el-table-column>
                        <el-table-column  width="200" align="center" label='协作单位'  prop="cooperativeUnit"  >
                              <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.cooperativeUnit }}</span>

                                  <el-input v-else v-model="scope.row.cooperativeUnit"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
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
                               :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
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
                               :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
                            />
                        
                          </template>
                        </el-table-column>
                        <el-table-column  width="200" align="center" label='主要研究内容'  prop="researchContents"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.researchContents }}</span>

                                <el-input v-else v-model="scope.row.researchContents"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                        </el-table-column>
                        <el-table-column  width="200" align="center" label='项目预期主要成果形式'  prop="deliverables"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.deliverables}}</span>

                                <el-input v-else v-model="scope.row.deliverables"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                        </el-table-column>
                        <el-table-column  width="200" align="center" label='项目总预算金额(元)'  prop="investment"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{scope.row.investment}}</span>

                                <el-input v-else v-model="scope.row.investment"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                        </el-table-column>
                        <el-table-column  width="200" align="center" :label='"截至"+(year -1)+"年已支出金额(元)"'  prop="completedSettlement"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.completedSettlement }}</span>

                                <el-input v-else v-model="scope.row.completedSettlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                        </el-table-column>
                        <el-table-column width="200"  :label="year+'年预计支出总金额(元)'" align="center" prop="expectedSettlement" >
                          <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.expectedSettlement}}</span>

                                <el-input v-else v-model="scope.row.expectedSettlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                            </template>
                        </el-table-column>
                        <el-table-column :label="year+1+'预算用金额(元)'" align="center" >
                          
                          <el-table-column  width="200" align="center" label='无形资产'  prop="intangibleAssets"  >
                              <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.intangibleAssets }}</span>

                                  <el-input v-else v-model="scope.row.intangibleAssets"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                              </template>
                          </el-table-column>
                          <el-table-column  width="200" align="center" label='固定资产'  prop="fixedAssets"  >
                              <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.fixedAssets}}</span>

                                  <el-input v-else v-model="scope.row.fixedAssets"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                              </template>
                          </el-table-column>
                          <el-table-column  width="200" align="center" label='资产预算说明'  prop="fixedAssetsDescription"  >
                              <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.fixedAssetsDescription }}</span>

                                  <el-input v-else v-model="scope.row.fixedAssetsDescription"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                              </template>
                          </el-table-column>
                          <el-table-column  width="200" align="center" label='费用小计'  prop="subtotalCosts"  >
                              <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.subtotalCosts }}</span>

                                  <el-input v-else v-model="scope.row.subtotalCosts"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                              </template>
                          </el-table-column>
                          <el-table-column  width="200" align="center" label='费用预算说明'  prop="subtotalCostsDescription"  >
                              <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{scope.row.subtotalCostsDescription }}</span>

                                  <el-input v-else v-model="scope.row.subtotalCostsDescription"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                              </template>
                          </el-table-column>
                         
                      
                        </el-table-column>
                        <el-table-column width="160" :label="(year+2)+'年（元）'" align="center" prop="year1Settlement" >
                              <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year1Settlement }}</span>

                                <el-input v-else v-model="scope.row.year1Settlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                            </template>
                          </el-table-column>
                          <el-table-column width="160" :label="(year+3)+'年（元）'" align="center" prop="year2Settlement" >
                              <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{scope.row.year2Settlement }}</span>

                                <el-input v-else v-model="scope.row.year2Settlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                            </template>
                          </el-table-column>
                          <el-table-column width="160" :label="(year+4)+'年（元）'" align="center" prop="year3Settlement" >
                              <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year3Settlement}}</span>

                                <el-input v-else v-model="scope.row.year3Settlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                            </template>
                          </el-table-column>
                          <el-table-column width="160" :label="(year+5)+'年（元）'" align="center" prop="year4Settlement" >
                              <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year4Settlement }}</span>

                                <el-input v-else v-model="scope.row.year4Settlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
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
                            :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
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
                             :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
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
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.quantity}}</span>

                                <el-input v-else v-model="scope.row.quantity" type="number"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                        </el-table-column>
                      <el-table-column  width="200" align="center" label='价格（元）'  prop="price"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.price }}</span>

                                <el-input v-else v-model="scope.row.price" type="number"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                      </el-table-column>
                      <el-table-column  width="200" align="center" :label='(year+1)+"费用预算"'  prop="budget"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.budget }}</span>

                                <el-input v-else v-model="scope.row.budget"  type="number"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                      </el-table-column>
                      <el-table-column  width="200" align="center" :label='"费用详情说明"'  prop="description"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{scope.row.description }}</span>

                                <el-input v-else v-model="scope.row.description"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                      </el-table-column>
                      
                      
                      <el-table-column  width="200" align="center" :label='(year)+"年预算"'  prop="lastBudget"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.lastBudget }}</span>

                                <el-input v-else v-model="scope.row.lastBudget"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                            </template>
                      </el-table-column>
                    
                      <el-table-column  width="200" align="center" :label='(year)+"年累计发生费用"'  prop="lastAccumulatedExpenses"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{scope.row.lastAccumulatedExpenses }}</span>

                                <el-input v-else v-model="scope.row.lastAccumulatedExpenses"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                            </template>
                      </el-table-column>
                    
                      <!-- <el-table-column  width="200" align="center" :label='year+"调剂后预算额度"'  prop="adjustmentBudget"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.adjustmentBudget"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                      </el-table-column>
                      <el-table-column  width="200" align="center" :label='year+"调剂明细"'  prop="adjustmentDescription"  >
                            <template  #default="scope">
                                <el-input v-model="scope.row.adjustmentDescription"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                            </template>
                      </el-table-column> -->

                      <el-table-column  width="200" align="center" :label='year+"全年预计发生费用"'  prop="projectedCosts"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.projectedCosts}}</span>

                                <el-input v-else v-model="scope.row.projectedCosts"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                            </template>
                      </el-table-column>
                    </template>
                    <template v-if="visibleData.tableName=='charity'">
                      <el-table-column  width="200" align="center" :label='(year+1)+"费用预算"'  prop="budget"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.budget}}</span>

                                <el-input v-else v-model="scope.row.budget"  type="number"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                      </el-table-column>
                      <el-table-column  width="200" align="center" :label='"费用详情说明"'  prop="description"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.description }}</span>

                                <el-input v-else v-model="scope.row.description"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                            </template>
                      </el-table-column>
                      
                      <el-table-column  width="200" align="center" :label='(year)+"年预算"'  prop="lastBudget"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.lastBudget }}</span>

                                <el-input v-else v-model="scope.row.lastBudget"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                            </template>
                      </el-table-column>
                      <el-table-column  width="200" align="center" :label='(year)+"年累计发生费用"'  prop="lastAccumulatedExpenses"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.lastAccumulatedExpenses}}</span>

                                <el-input v-else v-model="scope.row.lastAccumulatedExpenses"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                            </template>
                      </el-table-column>
                    
                      <el-table-column  width="200" align="center" :label='year+"全年预计发生费用"'  prop="projectedCosts"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.projectedCosts }}</span>

                                <el-input v-else v-model="scope.row.projectedCosts"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
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
                             :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
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
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.name}}</span>

                                <el-input v-else v-model="scope.row.name"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                      </el-table-column>
                  
                      <el-table-column  width="200" align="center" label='数量'  prop="quantity"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.quantity }}</span>

                                <el-input v-else v-model="scope.row.quantity" type="number"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                      </el-table-column>
                      <el-table-column  width="200" align="center" label='价格'  prop="price"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{scope.row.price }}</span>

                                <el-input v-else v-model="scope.row.price" type="number"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                      </el-table-column>
                    
                      <el-table-column  width="200" align="center" :label='(year+1)+"费用预算"'  prop="budget"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.budget}}</span>

                                <el-input v-else v-model="scope.row.budget" type="number"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                      </el-table-column>
                    
                      <el-table-column  width="200" align="center" :label='"费用详情说明"'  prop="description"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.description }}</span>

                                <el-input v-else v-model="scope.row.description"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                            </template>
                      </el-table-column>
                    
                     
                    
                      <el-table-column  width="200" align="center" :label='(year)+"年预算"'  prop="lastBudget"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.lastBudget}}</span>

                                <el-input v-else v-model="scope.row.lastBudget"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                            </template>
                      </el-table-column>
                    
                      <el-table-column  width="200" align="center" :label='(year)+"年累计发生费用"'  prop="lastAccumulatedExpenses"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.lastAccumulatedExpenses }}</span>

                                <el-input v-else v-model="scope.row.lastAccumulatedExpenses"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                            </template>
                      </el-table-column>
                    
                    
                      <el-table-column  width="200" align="center" :label='year+"全年预计发生费用"'  prop="projectedCosts"  >
                            <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.projectedCosts }}</span>

                                <el-input v-else v-model="scope.row.projectedCosts"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
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
                             :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
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
                             :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
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
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.name}}</span>

                            <el-input v-else v-model="scope.row.name"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>

                     
                      <el-table-column width="160" label="项目现状" align="center" prop="situation" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.situation}}</span>

                            <el-input v-else v-model="scope.row.situation"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                        
                      </el-table-column>
                      <el-table-column width="160" label="数量" align="center" prop="quantity" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.quantity }}</span>

                            <el-input v-else v-model="scope.row.quantity" type="number"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column width="160" label="建设内容、进度" align="center" prop="contentProgress" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.contentProgress }}</span>

                            <el-input v-else v-model="scope.row.contentProgress"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      
                      <el-table-column width="160" label="投资总额" align="center" prop="investment" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.investment }}</span>

                            <el-input v-else v-model="scope.row.investment"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="'截止'+(year-1)+'年已结算金额(元)'" align="center" prop="completedSettlement" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{scope.row.completedSettlement }}</span>

                            <el-input v-else v-model="scope.row.completedSettlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="year+'年实际结算金额(元)'" align="center" prop="actualSettlement" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.actualSettlement }}</span>

                            <el-input v-else v-model="scope.row.actualSettlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="year+'结算资金明细'" align="center" prop="details" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.details }}</span>

                            <el-input v-else v-model="scope.row.details"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="(year+1)+'年预计结算金额(元)'" align="center" prop="expectedSettlement" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.expectedSettlement }}</span>

                            <el-input v-else v-model="scope.row.expectedSettlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column>
                     
                      <el-table-column width="160" :label="year+2+'年'" align="center" prop="year1Settlement" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year1Settlement }}</span>

                            <el-input v-else v-model="scope.row.year1Settlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="year+3+'年'" align="center" prop="year2Settlement" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year2Settlement }}</span>

                            <el-input v-else v-model="scope.row.year2Settlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="year+4+'年'" align="center" prop="year3Settlement" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year3Settlement }}</span>

                            <el-input v-else v-model="scope.row.year3Settlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="year+5+'年'" align="center" prop="year4Settlement" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year4Settlement}}</span>

                            <el-input v-else v-model="scope.row.year4Settlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
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
                             :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
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
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.name }}</span>

                            <el-input v-else v-model="scope.row.name"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column label="数量" width="160" align="center" prop="quantity" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.quantity }}</span>

                            <el-input v-else v-model="scope.row.quantity"  type="number"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column label="项目现状" width="160" align="center" prop="situation" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.situation }}</span>

                            <el-input v-else v-model="scope.row.situation"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column>
                      <el-table-column label="建设内容、进度" width="160" align="center" prop="contentProgress" >
                        <template  #default="scope">
                      <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.contentProgress }}</span>

                            <el-input v-else v-model="scope.row.contentProgress"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                        </template>
                      </el-table-column>
                      <el-table-column label="拟投运时间" width="200" align="center" prop="operationalTime" >
                      
                        <template  #default="scope">
                          <el-date-picker
                          style="width:150px"
                            v-model="scope.row.operationalTime"
                            type="date"
                            placeholder="请选择"
                            format="YYYY-MM-DD"
                             :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"
                          />
                        </template>
                      </el-table-column>
                      <el-table-column label="投资总额" width="160" align="center" prop="investment" >
                        <template  #default="scope">
                          <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.investment }}</span>
                          <el-input v-else v-model="scope.row.investment"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                         </template>
                        </el-table-column>
                      <el-table-column :label="'截止'+(year-1)+'年已结算资金(元)'" width="160" align="center" prop="completedSettlement" >
                        <template  #default="scope">
                          <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.completedSettlement }}</span>

                            <el-input v-else v-model="scope.row.completedSettlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                         </template>
                      </el-table-column>
                      <el-table-column :label="year +'实际结算金额(元)'" width="160" align="center" prop="actualSettlement" >
                        <template  #default="scope">
                          <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.actualSettlement }}</span>

                            <el-input v-else v-model="scope.row.actualSettlement"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                         </template>
                      </el-table-column>
                      <el-table-column :label="(year+1)+'年无形资产'" width="160" align="center" prop="intangibleAssets" >
                        <template  #default="scope">
                          <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.intangibleAssets }}</span>

                            <el-input v-else v-model="scope.row.intangibleAssets"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                         </template>
                      </el-table-column>
                      <el-table-column :label="(year+1)+'固定资产'" width="160" align="center" prop="fixedAssets" >
                        <template  #default="scope">
                          <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.fixedAssets }}</span>

                            <el-input v-else v-model="scope.row.fixedAssets"   :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6"/>
                         </template>
                      </el-table-column>
                      <el-table-column width="160" :label="(year+2)+'年(元)'" align="center" prop="year1Settlement" >
                        <template  #default="scope">
                          <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year1Settlement }}</span>

                            <el-input v-else v-model="scope.row.year1Settlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="(year+3)+'年(元)'" align="center" prop="year2Settlement" >
                        <template  #default="scope">
                          <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year2Settlement }}</span>

                            <el-input v-else v-model="scope.row.year2Settlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="(year+4)+'年(元)'" align="center" prop="year3Settlement" >
                        <template  #default="scope">
                          <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year3Settlement }}</span>

                            <el-input v-else v-model="scope.row.year3Settlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
                        </template>
                      </el-table-column>
                      <el-table-column width="160" :label="(year+5)+'年(元)'" align="center" prop="year4Settlement" >
                        <template  #default="scope">
                          <span  v-if="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6">{{ scope.row.year4Settlement }}</span>

                            <el-input v-else v-model="scope.row.year4Settlement"  :disabled="scope.row.status==2||scope.row.status==3||scope.row.status==5||scope.row.status==6" />
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
                      <template v-if="!visibleQueryParams.show">
                        <el-button
                            link
                            type="success"
                            @click='save(scope.row,scope.$index)'
                           v-if='(scope.row.status==1||scope.row.status==4) &&visibleData.type==2'
                            v-hasPermi="['budget:business:add']"
                            >保存</el-button>
                      </template>
                      <template v-if="!visibleQueryParams.show">
                    <el-button
                        link
                        type="success"
                          @click='recalls(scope.row.id,scope.$index)'
                        v-if='scope.row.status==2 && visibleData.type==2'
                        v-hasPermi="['budget:business:add']"
                        >撤回</el-button>
                    </template>
                        <el-button
                        link
                        type="primary"
                        @click='clickSpInfo(scope.row,scope.$index)'
                        v-if="scope.row.id"
                        >查看审批</el-button>
                        <el-popconfirm   v-hasPermi="['budget:business:delete']" title="确定要删除吗？" @confirm="clickDelete(scope.row,scope.$index)"  v-if='(scope.row.status==1||scope.row.status==4) && visibleData.type==2'>
                          <template #reference>
                            <el-button
                              link
                              type="danger"
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
                v-if='visibleData.type==2'
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
   </template>
   
   <script setup >
   import { nextTick, onMounted, watch } from "vue";
   import usePermissionStore from '@/store/modules/permission'
   import useTagsViewStore from '@/store/modules/tagsView'
   
   import {  deptTreeSelect } from "@/api/system/user";
   // getRightDataupdateStatus
   import {getDeptList} from "@/api/taskExecution/index"
   import {getRightData,saveItem,deleteItem,updateStatus} from "@/api/taskExecution/capital/index"
   const { proxy } = getCurrentInstance();
   const { field_type,item_type ,budget_status} = proxy.useDict("field_type","item_type" ,'budget_status');
   const emit=defineEmits(['clickSpInfo'])

   const route= useRoute();
   const loading = ref(true);
   const deptOptions = ref(undefined);
   const ids=ref([])
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
       cacheAddList:[],
   
   })
   const visibleQueryParams=reactive({
     pageNum: 1,
     pageSize: 10,
     total: 0,
     show:false
   })
   const tableRef=ref(null)
   const deptList=ref([])
   const year=ref(Number(route.query.year))

   
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
       visibleData.cacheAddList= visibleData.cacheAddList.filter((item)=>item.cid!=row.cid)
     }
   
   
   }
   function clickSpInfo(row){
    emit('clickSpInfo',row)
     
   }
   function handleExport() {
     proxy.download('fixed/tables/export', {
       taskId:visibleData.id,tableName:visibleData.leftDataList[visibleData.leftIndex].tableName,itemId:visibleData.leftDataList[visibleData.leftIndex].id
     }, `填报明细${new Date().getTime()}.xlsx`)
   }
   // 禁用选择
   function selectable(row, index){
   
    return row.id!=''&&row.status==1|| row.status==4
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
           visibleData.cacheAddList= visibleData.cacheAddList.filter((item)=>item.cid!=row.cid)
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
   function recalls(id){
    updateStatus({itemId:visibleData.leftDataList[visibleData.leftIndex].id,tableName:visibleData.leftDataList[visibleData.leftIndex].tableName,ids: id,status:1}).then(res=>{
       if(res.code!=200) return proxy.$message.error(res.msg)
       proxy.$message.success("撤回成功")
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
    let cid= proxy.getUUid()
     let arr=[
       ['deptId','projectStatus','projectType','name','situation','contentProgress','investment','settled','remaining','settlement','expected','details','analyze','controlDept',"year1","year2","year3","year4"],
       ['deptId',"projectStatus","projectType","name","number","quantity","startTime","endTime","researchContents","deliverables","investment","completedSettlement","expectedSettlement","cooperativeUnit","intangibleAssets","fixedAssets","fixedAssetsDescription","subtotalCosts","subtotalCostsDescription","auditSettlement","auditDescription",],
       ['deptId','reportingType','projectType','quantity','price','budget','description','auditBudget','auditDescription','lastBudget','lastAccumulatedExpenses','adjustmentBudget','adjustmentDescription','projectedCosts',],
       ['deptId','budget','description','auditBudget','auditDescription','lastBudget','lastAccumulatedExpenses','adjustmentBudget','adjustmentDescription','projectedCosts',],
       ['deptId','reportingType','name','quantity','price','budget','description','auditBudget','auditDescription','lastBudget','lastAccumulatedExpenses','adjustmentBudget','adjustmentDescription','projectedCosts',],
       ['deptId','projectStatus','projectType','name','situation','contentProgress','analysis','length','investment','completedLength','completedSettlement','actualLength','actualSettlement','details','expectedLength','expectedSettlement','auditLength','auditSettlement','auditDescription','year1Length','year1Settlement','year2Length','year2Settlement','year3Length','year3Settlement','year4Length','year4Settlement'],
       ['deptId','projectStatus','projectType','name','situation','quantity','contentProgress','investment','completedSettlement','actualSettlement','details','expectedSettlement','auditSettlement','auditDescription','year1Settlement','year2Settlement','year3Settlement','year4Settlement'],
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
       cid,
       status:1,
     }
   
                       
     arr[index].forEach(item=>{
       obj[item]=''
   
     })
     
     obj.projectStatus=1
     obj.projectType=1
     obj.reportingType=1 
     visibleData.rightDataList.push(obj)
     visibleData.cacheAddList.push(obj)
     nextTick(()=>{
      tableRef.value.setScrollTop(visibleData.rightDataList.length*100) 
     })
    
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
         
      
       }else{
        visibleQueryParams.show=false
       }
       getRightData({itemId:visibleData.leftDataList[visibleData.leftIndex].id,taskId:visibleData.id,tableName:visibleData.leftDataList[visibleData.leftIndex].tableName,selectType,...visibleQueryParams}).then(r=>{
            
      
          visibleData.rightDataList=[...r.data,...visibleData.cacheAddList] || []
       
          visibleQueryParams.total=r.total || 0
        
          loading.value = false
       })
      
      
      
      
      
    
   }

   /** 查询部门下拉树结构 */
   function getDeptTree() {
   deptTreeSelect().then(response => {
     deptOptions.value = response.data;
   });
   };
 
     /** 多选框选中数据 */
     function handleSelectionChange(selection) {
       ids.value = selection.map((item) => item.id);
       // single.value = selection.length != 1;
       // multiple.value = !selection.length;
     }
   // getDeptTree()
   function changeVisibleData(leftIndex,tableName,type,leftDataList){
        visibleData.leftIndex=leftIndex
        visibleData.tableName=tableName
        visibleData.type=type
        visibleData.leftDataList=leftDataList
        visibleData.cacheAddList=[]
        visibleData.rightDataList=[]
       
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
   