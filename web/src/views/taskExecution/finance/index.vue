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
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
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
    <el-table v-loading="loading" :data="roleList">
      <!--  @selection-change="handleSelectionChange" -->
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column type="index" label="序号" width="55" />

      <el-table-column label="年度" align="center" prop="year" />
      <el-table-column
        label="任务名称"
        align="center"
        prop="name"
        show-overflow-tooltip
      />
      <el-table-column label="起始时间" align="center" prop="startTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.startTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="预算年份" align="center" prop="budgetYear">
        <template #default="scope">
          <dict-tag :options="result_status" :value="scope.row.budgetYear" />
        </template>
      </el-table-column>
      <!-- <el-table-column label="审批数量" align="center" prop="name" /> -->

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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

          <el-button link type="primary" @click="changeDialogVisible(scope.row, 2)"
            >审查</el-button
          >

          <el-button
            link
            type="primary"
            @click="clickHz(scope.row)"
            v-hasPermi="['budget:financial:summary']"
            >汇总</el-button
          >
          <el-button link type="primary" @click="exportAll(scope.row)">导出</el-button>

          <el-button
            link
            type="primary"
            @click="showBB(scope.row)"
            v-hasPermi="['budget:financial:version']"
            >版本生成</el-button
          >
          <el-button
                link
                type="primary"
                @click="RejectoOtside(scope.row)"
                v-if='scope.row.lastTask' 
                >选择表驳回</el-button
              >
       
          <el-dropdown>
            <div
              style="
                height: 23px;
                display: flex;
                align-items: center;
                font-size: 14px;
                margin-left: 12px;
                color: #409eff;
              "
            >
              更多
              <el-icon>
                <arrow-down />
              </el-icon>
            </div>

            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>
                  <el-popconfirm title="确认全部通过?" @confirm="allIsPass(scope.row, 5)">
                    <template #reference> 全部通过 </template>
                  </el-popconfirm>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-popconfirm title="确认全部拒绝?" @confirm="allIsPass(scope.row, 6)">
                    <template #reference> 全部拒绝 </template>
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
    <!-- 弹窗表驳回 -->
    <el-dialog
     v-model="bform.show"
      width="80%"
      title="选择驳回表"
      draggable
      top="40vh"
      :close-on-press-escape="false"
    >
    <el-table  :data="bform.itemList"  ref="Btable" height="60vh" @selection-change="handleSelectionBChange">
   
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="预算表名称" align="center" prop="tableDisplayName" />
      <el-table-column label="费用科目" align="center" prop="subjectName" />
    </el-table>
    
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="clickBsubmit" type="primary">确定</el-button>
        <el-button  @click="bform.show=false">
          取消
        </el-button>
      </span>
    </template>
    </el-dialog>

    <el-dialog
      v-model="dialogVisible"
      fullscreen
      draggable
      :close-on-press-escape="false"
    >
      <div class="dialogBox">
        <Transition>
          <div v-if="isopen" class="left">
            <div class="title">
              <el-tooltip effect="dark" :content="visibleData.leftTitle">
                <div>{{ visibleData.leftTitle }}</div>
              </el-tooltip>
              <el-icon style="cursor: pointer" @click="dialogXzVisible.show = true"
                ><WarningFilled
              /></el-icon>
            </div>
            <el-input
              placeholder="搜索"
              v-model="visibleData.query"
              size="small"
              style="margin: 10px 5%; width: 90%"
            ></el-input>

            <div class="content">
              <template v-for="(item, index) in visibleData.leftDataList" :key="item.id">
                <div
                  @click="changeIndex(index, item.tableName, item.reportingType)"
                  v-if="item.show"
                  :class="visibleData.leftIndex == index ? 'item action' : 'item'"
                >
                  <el-tooltip effect="dark">
                    <template #content>
                      {{ item.tableDisplayName }} — {{ item.userName }}<br />
                      <div v-text="item.remark"></div
                    ></template>
                    <el-icon style="margin-right: 5px"><QuestionFilled /></el-icon>
                  </el-tooltip>

                  <div>{{ item.tableDisplayName }}</div>
                  <!-- <el-tag :type="item.isConfirm==0?'success':'error'">{{item.isConfirm==0?'已审核':'未审核'}}</el-tag> -->
                </div>
              </template>
            </div>
          </div>
        </Transition>
        <div class="right" :style="isopen ? '' : 'width:100%'">
          <el-icon style="cursor: pointer" @click="changeIsopen"
            ><Fold v-if="isopen" /> <Expand v-else
          /></el-icon>

          <!-- <div class='title'>{{visibleData.rightTitle}}</div> -->
          <template v-if="false">
            <div class="btnBox">
              <!-- <div style="width:200px;margin-right:20px">
                    <el-select v-model="visibleQueryParams.status" placeholder="Select" size="mini">
                      <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      />
                    </el-select>
                    </div> -->
              <!-- <div style="width:200px;margin-right:20px">
                      <el-tree-select
                        v-model="visibleQueryParams.deptId"
                        :data="deptOptions"
                        :props="{ value: 'id', label: 'label', children: 'children' }"
                        value-key="id"
                        placeholder="请选择归属部门"
                        check-strictly
                     />
                    </div>
                    <el-button
                    type="primary"
                    plain
                    @click="getRightDataList"
                    >查询</el-button> -->
              <!-- <el-button
                    type="info"
                    plain
                   v-if='visibleData.type==2'
                   @click="changePLsh"
                    >批量确认</el-button> -->
              <div style="width: 100%; display: flex; justify-content: space-between">
                <div>
                  预算费用:{{ visibleData.tj.budget }} /{{
                    visibleData.tj.allBudget
                  }}&nbsp;&nbsp;&nbsp;&nbsp; 本年预算费用:{{
                    visibleData.tj.budgetYear
                  }}/{{ visibleData.tj.allBudgetYear }}&nbsp;&nbsp;&nbsp;&nbsp;
                  本年实际发生费用:{{ visibleData.tj.actualIncurred }}/{{
                    visibleData.tj.allActualIncurred
                  }}&nbsp;&nbsp;&nbsp;&nbsp; 全年预计发生费用:{{
                    visibleData.tj.estimatedIncurred
                  }}/{{ visibleData.tj.allEstimatedIncurred }}&nbsp;&nbsp;&nbsp;&nbsp;
                </div>
                <div>
                  <el-button
                    type="primary"
                    size="small"
                    style="margin-bottom: 10px"
                    @click="plBtn"
                    >批量审批</el-button
                  >
                </div>
              </div>
            </div>
            <!-- @selection-change="handleSelectionChange" -->
            <el-table
              :data="visibleData.rightDataList"
              height="88%"
              row-key="deptId"
              @expand-change="handleNodeExpand"
              :expand-row-keys="expandedRowKeys"
            >
              <!-- <el-table-column :selectable="selectable" type="selection" width="55" align="center"  >
                    </el-table-column> -->
              <el-table-column type="expand">
                <template #default="props">
                  <el-table
                    :data="props.row.dataTable"
                    @selection-change="
                      (selection) => handleSelectionChange(props.$index, selection)
                    "
                    style="width: 98%; margin: 0 auto"
                    height="400px"
                    size="small"
                  >
                    <el-table-column
                      type="selection"
                      width="55"
                      align="center"
                      :selectable="selectable"
                    />
                    <el-table-column
                      width="120"
                      align="center"
                      fixed="left"
                      label="填报人"
                      prop="user_name"
                    />
                    <el-table-column
                      width="120"
                      align="center"
                      fixed="left"
                      label="部门"
                      prop="dept_name"
                    />
                    <el-table-column
                      width="140"
                      align="center"
                      label="状态"
                      prop="budget"
                    >
                      <template #default="scope">
                        <dict-tag :options="budget_status" :value="scope.row.status" />
                      </template>
                    </el-table-column>
                    <!-- <el-table-column  width="200" align="center" label='预算表名称'  prop="budget"  /> -->
                    <el-table-column
                      width="140"
                      align="center"
                      label="预算费用（元）"
                      prop="budget"
                    />
                    <el-table-column
                      width="140"
                      align="center"
                      label="本年预算（元）"
                      prop="budget_year"
                    />
                    <el-table-column
                      width="140"
                      align="center"
                      label="本年实际发生额（元）"
                      prop="actual_incurred"
                    />
                    <el-table-column
                      width="140"
                      align="center"
                      label="全年预计发生费用（元）"
                      prop="estimated_incurred"
                    />
                    <template v-for="(item, index) in props.row.headTable" :key="index">
                      <el-table-column
                        :label="item.fieldDisplayName"
                        prop=""
                        align="center"
                        width="150"
                      >
                        <template #default="scope">
                          <span>{{ scope.row[item.fieldName] }}</span>
                        </template>
                      </el-table-column>
                    </template>

                    <el-table-column
                      width="140"
                      align="center"
                      label="提交时间"
                      prop="create_time"
                    >
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
                          @click="spBtn(scope.row, props.$index)"
                          v-if="scope.row.status == 3"
                          v-hasPermi="['budget:financial:audit']"
                          >审批</el-button
                        >
                      </template>
                    </el-table-column>
                  </el-table>
                </template>
              </el-table-column>
              <el-table-column
                width="140"
                align="center"
                label="企业名称"
                prop="deptName"
              />

              <el-table-column
                width="140"
                align="center"
                label="预算费用（元）"
                prop="budget"
              >
              </el-table-column>
              <el-table-column
                width="140"
                align="center"
                label="本年预算（元）"
                prop="budgetYear"
              >
              </el-table-column>
              <el-table-column
                align="center"
                label="本年实际发生额（元）"
                prop="actualIncurred"
              >
              </el-table-column>
              <el-table-column
                align="center"
                label="全年预计发生费用（元）"
                prop="estimatedIncurred"
              >
              </el-table-column>
              <template v-for="(item, index) in visibleData.rightTableList" :key="index">
                <el-table-column
                  :label="item.fieldDisplayName"
                  prop=""
                  :show-overflow-tooltip="true"
                  v-if="item.fieldDisplay == 0"
                  min-width="100"
                >
                  <template #default="scope">
                    <span> {{ scope.row[item.fieldName] }}</span>
                  </template>
                </el-table-column>
              </template>
              <!-- <el-table-column
                            label="状态"
                            prop="status"
                            :show-overflow-tooltip="true"
                            min-width='120'
                        >
                        <template  #default="scope">
                          <dict-tag :options="budget_status" :value="scope.row.status " />
                        </template>
                      
                      </el-table-column> -->

              <!-- <el-table-column  
                    label="操作"
                    align="center"
                    fixed='right'
                    width='120'
                    >
                    <template #default="scope">
                        <el-button
                            link
                            type="primary"
                         
                            @click='jumpinfo(scope.row)'
                            >查看并审核</el-button> -->
              <!--    @click='changeInfoFormType(scope.row,1)' -->
              <!-- <el-button
                            link
                            type="primary"
                            v-if='scope.row.status==3&&visibleData.type==2'
                            @click='changeInfoFormType(scope.row,2)'
                            >确认</el-button> -->

              <!-- </template>
                    </el-table-column> -->
            </el-table>
            <div style="width: 98%; position: relative">
              <pagination
                v-show="visibleQueryParams.total > 0"
                :total="visibleQueryParams.total"
                v-model:page="visibleQueryParams.pageNum"
                v-model:limit="visibleQueryParams.pageSize"
              />
            </div>
          </template>

          <businessT ref="businessTRef" v-show="visibleData.reportingType == 1" />
          <capitalT ref="capitalTRef" v-show="visibleData.reportingType == 2" />
        </div>
      </div>
      <!-- 新增编辑 查看等操作 -->
      <el-dialog
        v-model="dialogFormVisible"
        width="60%"
        :title="infoForm.type == 1 ? '查看' : '审核'"
        draggable
        append-to-body
        :close-on-press-escape="false"
      >
        <div
          style="
            max-height: 80vh;
            overflow-y: auto;
            overflow-x: hidden;
            width: 100%;
            box-sizing: border-box;
          "
        >
          <el-form
            :rules="infoRules"
            label-position="right"
            label-width="150"
            ref="refInfoForm"
            :model="infoForm"
          >
            <div class="formBox">
              <div v-for="(item, index) in infoForm.List" :key="index">
                <el-form-item
                  :label="item.fieldDisplayName"
                  :prop="'List.' + index + '.value'"
                  :rules="item.rules"
                >
                  {{ item[item.fieldName] }}
                  <el-input
                    v-model="item.value"
                    disabled
                    v-if="item.fieldName != 'cost_description'"
                  />

                  <el-input
                    v-model="item.value"
                    v-else
                    maxlength="300"
                    placeholder=""
                    show-word-limit
                    type="textarea"
                    disabled
                  />
                </el-form-item>
              </div>
            </div>
            <div v-if="infoForm.type == 2" style="width: 90%; margin: 0 auto 10px">
              <h3 style="color: #000; font-weight: bold">确认意见</h3>
              <el-form-item label="" prop="status">
                <el-radio-group v-model="infoForm.status" class="ml-4">
                  <el-radio label="5" size="large">通过</el-radio>
                  <el-radio label="6" size="large">不通过</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="确认意见" prop="remark">
                <el-input
                  v-model="infoForm.remark"
                  maxlength="300"
                  placeholder=""
                  show-word-limit
                  type="textarea"
                  :rows="5"
                  resize="none"
                />
              </el-form-item>
            </div>
          </el-form>
          <div
            v-if="infoForm.logList.length > 0 && infoForm.type == 1"
            style="width: 80%; margin: 0 auto 10px"
          >
            <h3 style="color: #000; font-weight: bold">审批流程</h3>
            <el-timeline>
              <template v-for="(item, index) in infoForm.logList" :key="index">
                <el-timeline-item :timestamp="item.deptName" placement="top">
                  <el-card>
                    <div style="display: flex; width: 100%">
                      <div
                        style="
                          display: flex;
                          justify-content: space-between;
                          width: 100%;
                          height: 40px;
                          align-items: center;
                        "
                      >
                        <div style="display: flex; align-items: center">
                          <el-image
                            style="width: 40px; height: 40px; border-radius: 50%"
                            src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
                            fit="cover"
                          />
                          <div style="margin-left: 10px">{{ item.userName }}</div>
                        </div>
                        <div style="color: #02a7f0; font-weight: bold"></div>
                        <div>{{ item.time }}</div>
                      </div>
                    </div>
                    <div style="margin-left: 50px">
                      <div
                        style="margin-bottom: 10px"
                        v-if="item.status == 1 || item.status == 4"
                      >
                        {{ item.remark }}
                      </div>
                      <div
                        style="margin-bottom: 10px"
                        v-if="item.status == 2 || item.status == 3"
                      >
                        审批结果：<span
                          :style="{
                            color:
                              item.status == 2 ? '02a7f0' : item.status == 3 ? 'red' : '',
                          }"
                          >{{
                            item.status == 2
                              ? "审批通过"
                              : item.status == 3
                              ? "审批驳回"
                              : ""
                          }}</span
                        >
                      </div>
                      <div
                        style="margin-bottom: 10px"
                        v-if="item.status == 2 || item.status == 3"
                      >
                        审批意见：<span style="color: #02a7f0">{{ item.remark }}</span>
                      </div>
                    </div>
                  </el-card>
                </el-timeline-item>
              </template>
            </el-timeline>
          </div>
        </div>
        <div style="display: flex; justify-content: flex-end; width: 100%">
          <div v-if="infoForm.type == 2">
            <el-button type="primary" @click="save">确定</el-button>
            <el-button type="info" @click="() => (dialogFormVisible = false)"
              >取消</el-button
            >
          </div>
          <el-button v-else type="info" @click="() => (dialogFormVisible = false)"
            >关闭</el-button
          >
        </div>
      </el-dialog>

      <!-- 预选填报须知 -->
      <!-- @close="changeTips" -->
      <el-dialog
        v-model="dialogXzVisible.show"
        width="60%"
        :title="dialogXzVisible.title"
        draggable
        append-to-body
        @close="changeTips"
        :close-on-press-escape="false"
      >
        <el-input
          v-model="dialogXzVisible.value"
          type="textarea"
          :readonly="true"
          resize="none"
          :rows="20"
        />
        <div style="display: flex; justify-content: flex-end; width: 100%">
          <el-checkbox
            v-model="dialogXzVisible.checked"
            label="下次不在提示"
            size="large"
          />
        </div>
      </el-dialog>
      <!-- 批量审核弹窗 -->
      <el-dialog
        v-model="dialogPlshVisible"
        width="60%"
        title="审核"
        draggable
        append-to-body
        :close-on-press-escape="false"
      >
        <el-form
          :rules="infoRules"
          label-position="right"
          label-width="150"
          ref="plInfoForm"
          :model="infoForm"
        >
          <div style="width: 90%; margin: 0 auto 10px">
            <!-- <h3 style="color:#000;font-weight:bold">确认意见</h3> -->
            <el-form-item label="审核意见" prop="status">
              <el-radio-group v-model="infoForm.status" class="ml-4">
                <el-radio label="5" size="large">通过</el-radio>
                <el-radio label="6" size="large">不通过</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="确认意见" prop="remark">
              <el-input
                v-model="infoForm.remark"
                maxlength="300"
                placeholder=""
                show-word-limit
                type="textarea"
                :rows="5"
                resize="none"
              />
            </el-form-item>
          </div>
        </el-form>
        <div
          style="display: flex; justify-content: flex-end; width: 100%; margin-top: 40px"
        >
          <el-button type="primary" @click="plSave">确定</el-button>
          <el-button type="info" @click="() => (dialogPlshVisible = false)"
            >取消</el-button
          >
        </div>
      </el-dialog>
    </el-dialog>
    <!-- 版本控制弹窗 -->
    <el-dialog
      v-model="dialogBbVisibleData.show"
      width="30%"
      title="生成版本"
      draggable
      top="20vh"
      :close-on-press-escape="false"
    >
      <el-input v-model="dialogBbVisibleData.value" placeholder="版本名称" />
      <div
        style="display: flex; justify-content: flex-end; width: 100%; margin-top: 20px"
      >
        <el-button type="primary" @click="subSaveVersion">确定</el-button>
        <el-button type="info" @click="() => (dialogBbVisibleData.show = false)"
          >取消</el-button
        >
      </div>
    </el-dialog>
    <!-- 汇总弹窗信息 -->
    <el-dialog
      fullscreen
      v-model="hzData.show"
      width="80%"
      title=""
      draggable
      top="40vh"
      :close-on-press-escape="false"
    >
      <el-tabs type="border-card" v-model="hzData.name" :before-leave="changeTabs">
        <el-tab-pane label="股份、供气汇总" name="1">
          <el-button type="warning" plain icon="Download" @click="handleExport(1)"
            >导出</el-button
          >
          <tableList
            :tableData="hzData.tabdataList"
            :tableHeadData="hzData.tabheadData"
          />
        </el-tab-pane>
        <el-tab-pane label="区域汇总" name="2">
          <el-button type="warning" plain icon="Download" @click="handleExport(2)"
            >导出</el-button
          >
          <tableList
            :tableData="hzData.tabdataList"
            :tableHeadData="hzData.tabheadData"
          />
        </el-tab-pane>
        <el-tab-pane label="销售费用预算" name="3">
          <el-button type="warning" plain icon="Download" @click="handleExport(3)"
            >导出</el-button
          >
          <tableList
            :tableData="hzData.tabdataList"
            :tableHeadData="hzData.tabheadData"
          />
        </el-tab-pane>
        <el-tab-pane label="管理费用预算" name="4">
          <el-button type="warning" plain icon="Download" @click="handleExport(4)"
            >导出</el-button
          >
          <tableList
            :tableData="hzData.tabdataList"
            :tableHeadData="hzData.tabheadData"
          />
        </el-tab-pane>
        <el-tab-pane label="研发费用预算" name="5">
          <el-button type="warning" plain icon="Download" @click="handleExport(5)"
            >导出</el-button
          >
          <tableList
            :tableData="hzData.tabdataList"
            :tableHeadData="hzData.tabheadData"
          />
        </el-tab-pane>
        <el-tab-pane label="制造费用预算" name="6">
          <el-button type="warning" plain icon="Download" @click="handleExport(6)"
            >导出</el-button
          >
          <tableList
            :tableData="hzData.tabdataList"
            :tableHeadData="hzData.tabheadData"
          />
        </el-tab-pane>
        <el-tab-pane label="资本性支出汇总" name="10">
          <el-button type="warning" plain icon="Download" @click="handleExport(10)"
            >部门明细</el-button
          >
          <tableList
            :tableData="hzData.tabdataList"
            :tableHeadData="hzData.tabheadData"
          />
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>
</template>

<script setup name="finance">
  import {listItemAll,rejectByItem} from "@/api/BM/item";
  

import {
  addRole,
  changeRoleStatus,
  dataScope,
  delRole,
  getRole,
  listRole,
  updateRole,
} from "@/api/system/role";
import {
  changeUserStatus,
  listUser,
  resetUserPwd,
  delUser,
  getUser,
  updateUser,
  addUser,
  deptTreeSelect,
} from "@/api/system/user";
import {
  itemInfoTj,
  itemAuditField,
  reportingLog,
  getXZ,
  updateXZ,
  getLeftList,
  getRightTable,
  getRightData,
  getInfoForm,
  budgetSave,
  delTableItem,
  updateStatus,
} from "@/api/taskExecution/index";
import {
  saveVersion,
  budgetSummary,
  budgetSummary1,
  chageAllStatus,
} from "@/api/taskExecution/finance/index";
import { nextTick, toRefs} from "vue";
import { getTask, listTask, updateTask, addTask,relaunchTask } from "@/api/BM/task";
import { defineComponent, reactive, ref } from "vue";
import { toReactive } from "@vueuse/core";

import { getDeptDataList } from "@/api/taskExecution/functionalPosition/index";
import tableList from "./component/TabList.vue";
import businessT from "./component/business_T/index.vue";
import capitalT from "./component/capital_T/index.vue";
import { saveAs } from "file-saver";
import * as XLSX from "xlsx";
import { mergeArrays, moveKeysToStart, moveKeysToEnd } from "@/utils/utils.js";
// const businessT=defineAsyncComponent(() => import("./component/business_T/index.vue"))
const businessTRef = ref();
// const capitalT=defineAsyncComponent(()=>import("./component/capital_T/index.vue"))
const capitalTRef = ref();

import { useIsopen } from "../functionalPosition/useIsopen/index";
const { isopen, changeIsopen } = useIsopen();

const router = useRouter();
const { proxy } = getCurrentInstance();
const { budget_status, result_status, completion_status } = proxy.useDict(
  "budget_status",
  "result_status",
  "completion_status"
);

const roleList = ref([]);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const dateRange = ref([]);
const selectType = 3;
const refInfoForm = ref();
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


const dialogBbVisibleData = reactive({
  show: false,
  value: "",
  id: "",
});
// 弹窗分页
const dialogVisible = ref(false);
const visibleQueryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  total: 10,
  deptId: undefined,
  status: "",
});

const deptOptions = ref(undefined);
const visibleData = reactive({
  leftIndex: 0,
  id: "",
  leftTitle: "",
  reportingType: 1,
  year: "",
  rightTitle: "xxxx部门-票据印刷费-预算申请",
  type: 1, // type 1查看 2编辑（新增）
  leftDataList: [],
  rightDataList: [],
  rightTableList: [],
  tj: {
    audit: 0,
    count: 0,
    submit: 0,
    budget: 0,
    budgetYear: 0,
    actualIncurred: 0,
    estimatedIncurred: 0,
    allActualIncurred: 0,
    allBudget: 0,
    allBudgetYear: 0,
    allCount: 0,
    allEstimatedIncurred: 0,
  },
  query: "",
});
const dialogFormVisible = ref(false);



const infoForm = reactive({
  type: 1, // type 1查看 2编辑（新增）
  name: "",
  id: "",
  List: [],
  logList: [],
  remark: "",
  status: 3,
  Pindex: "",
});
const infoRules = reactive({
  status: [
    {
      required: true,
      trigger: "change",
      message: "审核状态不能为空",
    },
  ],
  remark: [
    {
      required: true,
      trigger: "blur",
      message: "审核意见不能为空",
    },
  ],
});
const dialogXzVisible = reactive({
  title: "",
  show: false,
  value: "",
  checked: false,
});
// 汇总 数据
const hzData = reactive({
  id: "",
  show: false,
  name: "1",
  tabheadData: [
    { name: "id", key: "key1" },
    {
      name: "2024",
      key: "name",
      children: [
        { name: "2", key: "name-1" },
        { name: "3", key: "name-2" },
      ],
    },
    { id: 2, name: "测试", key: "name-3" },
  ],
  tabdataList: [
    {
      key1: "我是第一个",
      name: "diyige",
      "name-1": "第二个",
      "name-2": "第三个",
      "name-3": "第四个",
    },
  ],
});

const bform=reactive({
  show:false,
  actlist:[],
  id:"",
  itemList:[]
})
const Btable=ref()
function RejectoOtside(row){
  bform.id=row.id
  bform.show=true
  bform.actlist=[]
  getBList()
  nextTick(()=>{
    Btable.value.clearSelection()
  })
  
  
}
function handleSelectionBChange(selection){
  bform.actlist = selection.map(item => item.id);
}

async function clickBsubmit(){
 
  if(bform.actlist.length==0) return  proxy.$modal.msgError("请选择要驳回的表");
  let res= await rejectByItem({taskId:bform.id,itemIds:bform.actlist,selectType})
  if(res.code!=200) return;
  proxy.$modal.msgSuccess("操作成功");
  bform.show=false
  getList()
}

function plBtn() {
  if (ids.value.length == 0) {
    proxy.$modal.msgError("请选择要审核的数据");
    return;
  }

  infoForm.id = "";
  dialogPlshVisible.value = true;
  infoForm.remark = "";
  infoForm.status = "";
}

function clickHz(row) {
  hzData.id = row.id;
  budgetSummary({ type: 1, taskId: hzData.id }).then((res) => {
    hzData.tabdataList = res.data.titleDate;
    hzData.tabheadData = res.data.titleName;
    hzData.name = "1";
    hzData.show = true;
  });
}

watch(
  [
    () => visibleData.leftIndex,
    () => visibleData.tableName,
    () => visibleData.type,
    () => visibleData.leftDataList,
  ],
  (newValue, oldValue) => {
    if (
      businessTRef.value &&
      businessTRef.value.changeVisibleData &&
      visibleData.reportingType == 1
    )
      businessTRef.value.changeVisibleData(
        visibleData.leftIndex,
        visibleData.tableName,
        visibleData.type,
        visibleData.leftDataList,
        visibleData.id,
        visibleData.year
      );
    if (
      capitalTRef.value &&
      capitalTRef.value.changeVisibleData &&
      visibleData.reportingType == 2
    )
      capitalTRef.value.changeVisibleData(
        visibleData.leftIndex,
        visibleData.tableName,
        visibleData.type,
        visibleData.leftDataList,
        visibleData.id,
        visibleData.year
      );
  },
  { deep: true }
);
function changeTips() {
  if (dialogXzVisible.checked) {
    updateXZ(visibleData.id);
  }
}
async function allIsPass(row, status) {
  let res = await chageAllStatus({ taskId: row.id, status, type: 1 });
  if (res.code != 200) return;
  proxy.$modal.msgSuccess("操作成功");

  getList();
}
async function changeTabs(activeName, oldActiveName) {
  hzData.tabdataList = [];
  hzData.tabheadData = [];
  if (activeName == "10") {
    await budgetSummary1({ type: activeName, taskId: hzData.id }).then((res) => {
      hzData.tabdataList = res.data.titleDate;
      hzData.tabheadData = res.data.titleName;
      hzData.show = true;
    });

    return;
  }
  await budgetSummary({ type: activeName, taskId: hzData.id }).then((res) => {
    hzData.tabdataList = res.data.titleDate;
    hzData.tabheadData = res.data.titleName;
    hzData.show = true;
  });
}
function exportAll(row) {
  proxy.download(
    "budget/reporting/exportAllTotal",
    {
      taskId: row.id,
    },
    `${new Date().getTime()}.xlsx`
  );
}
function handleExport(type) {
  let name = "";
  switch (type) {
    case 1:
      name = "股份供气汇总";
      break;
    case 2:
      name = "区域汇总";
      break;

    case 3:
      name = "销售费用预算";
      break;
    case 4:
      name = "管理费用预算";
      break;
    case 5:
      name = "研发费用预算";
      break;
    case 6:
      name = "制造费用预算";
      break;
  }

  if (type == 10) {
    proxy.download(
      "budget/statistics/exportTotal",
      {
        taskId: hzData.id,
      },
      `资本性支出各单位明细${new Date().getTime()}.xlsx`
    );
  }
  // else if(type==1||type==2){

  //   let data = mergeArrays( hzData.tabdataList,  hzData.tabheadData)
  //   data=moveKeysToStart(data,['费用名称', '费用类型'])
  //   if(type==2) data=moveKeysToEnd(data,['郑州区域预算汇总'])

  //   // 将数据转换为工作表
  //   const worksheet = XLSX.utils.json_to_sheet(data);

  //   // 创建工作簿并添加工作表
  //   const workbook = XLSX.utils.book_new();
  //   XLSX.utils.book_append_sheet(workbook, worksheet, 'Sheet1');

  //   // 生成Excel文件并导出
  //   const excelBuffer = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
  //   const dataBlob = new Blob([excelBuffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8' });
  //   saveAs(dataBlob, name+'.xlsx');
  // }
  else {
// 1. 克隆表格避免修改原始页面显示
const originalTable = document.querySelector("#Table");
    const clonedTable = originalTable.cloneNode(true);

    // 2. 处理所有包含千分位/货币符号的单元格
    const cells = clonedTable.querySelectorAll('td, th'); // 包含表头和内容单元格
    cells.forEach(cell => {
      const cellValue = cell.textContent.trim();
      // 匹配格式：可能包含¥、空格、逗号分隔的数字（如¥ 38,861,100.00）
      if (/^¥?\s*[\d,]+(\.\d+)?$/.test(cellValue)) {
        // 移除¥、逗号和空格，保留纯数字
        const cleanValue = cellValue.replace(/[¥, ]/g, '');
        cell.textContent = cleanValue;
      }
    });

    // 3. 使用处理后的克隆表格生成Excel
    let wb = XLSX.utils.table_to_book(clonedTable, {
      sheet: "Sheet1",
      raw: true, // 确保以原始值处理，避免自动格式化
    });
    let wbout = XLSX.write(wb, { bookType: "xlsx", bookSST: true, type: "array" });
    const dataBlob = new Blob([wbout], {
      type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8",
    });
    saveAs(dataBlob, name + ".xlsx");
  }
}

//
function showBB(row) {
  dialogBbVisibleData.id = row.id;
  dialogBbVisibleData.show = true;
}
const expandedRowKeys = ref([]);
async function handleNodeExpand(node, expandedRows) {
  node = toReactive(node);
  ids.value = [];
  if (expandedRows.length) {
    // 如果有行被展开，则只保留当前行的expandedRowKeys
    expandedRowKeys.value = [node.deptId];
  } else {
    // 如果没有行被展开，清空expandedRowKeys
    expandedRowKeys.value = [];
  }
  if (expandedRowKeys.value.length <= 0) return;
  node.loadDetails = true;
  // if(node.hasOpen==false){
  let r = await getRightTable({
    itemId: visibleData.leftDataList[visibleData.leftIndex].id,
    selectType,
    taskId: visibleData.id,
  });
  node.headTable = r.data || [];
  let res = await getRightData({
    taskId: visibleData.id,
    itemId: visibleData.leftDataList[visibleData.leftIndex].id,
    parentId: node.deptId,
    selectType,
    pageSize: 999999,
  });
  node.dataTable = res.rows || [];
  node.loadDetails = false;
  // node.hasOpen=true
  // console.log(node)

  // }else{
  // node.hasOpen=false
  // node.loadDetails=false
  // console.log(node)
  // }
}
function spBtn(row, pindex) {
  if (!row.id && ids.value.length == 0) return proxy.$message.error("请选择审核项");
  infoForm.id = row.id || ids.value.join(",");
  dialogPlshVisible.value = true;
  infoForm.Pindex = pindex;
  infoForm.remark = "";
  infoForm.status = "";
}
// 跳转
function jumpinfo(row) {
  window.open(
    "/taskExecution/financeInfo?id=" +
      visibleData.id +
      "&itemId=" +
      visibleData.leftDataList[visibleData.leftIndex].id +
      "&deptid=" +
      row.deptId +
      "&name=" +
      visibleData.leftTitle +
      "-" +
      visibleData.leftDataList[visibleData.leftIndex].tableDisplayName +
      "-" +
      row.deptName
  );
  // router.push('/taskExecution/financeInfo?id='+visibleData.id+'&itemId='+visibleData.leftDataList[visibleData.leftIndex].id+'&deptid='+row.deptId+'&name='+visibleData.leftTitle+'-'+visibleData.leftDataList[visibleData.leftIndex].tableDisplayName+"-"+row.deptName)
}
// 版本生成
function subSaveVersion() {
  saveVersion({ taskId: dialogBbVisibleData.id, name: dialogBbVisibleData.value }).then(
    (res) => {
      if (res.code != 200) return proxy.$message.error(res.msg);
      proxy.$message.success("提交成功:" + res.msg);
      dialogBbVisibleData.show = false;
    }
  );
}
// 批量审核
const dialogPlshVisible = ref(false);
const plInfoForm = ref();
function changePLsh() {
  if (ids.value.length == 0) {
    proxy.$modal.msgError("请选择要审核的数据");
    return;
  }
  infoForm.status = "5";
  dialogPlshVisible.value = true;
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
function plSave() {
  let obj = {
    itemId: visibleData.leftDataList[visibleData.leftIndex].id,
    taskId: visibleData.id,
    id: infoForm.id || ids.value.join(","),
    status: infoForm.status,
    remark: infoForm.remark,
  };
  plInfoForm.value.validate((valid) => {
    if (valid) {
      updateStatus(obj).then(async (res) => {
        if (res.code != 200) return proxy.$message.error(res.msg);
        proxy.$message.success("提交成功");
        dialogPlshVisible.value = false;
        await getRightDataList();
        // businessTRef.value.changeVisibleData(visibleData.leftIndex,visibleData.tableName,visibleData.type,visibleData.leftDataList,visibleData.id)
        visibleData.rightDataList[infoForm.Pindex].loadDetails = true;
        let r = await getRightTable({
          itemId: visibleData.leftDataList[visibleData.leftIndex].id,
          selectType,
          taskId: visibleData.id,
        });
        visibleData.rightDataList[infoForm.Pindex].headTable = r.data || [];
        let re = await getRightData({
          taskId: visibleData.id,
          itemId: visibleData.leftDataList[visibleData.leftIndex].id,
          parentId: visibleData.rightDataList[infoForm.Pindex].deptId,
          selectType,
          pageSize: 999999,
        });
        visibleData.rightDataList[infoForm.Pindex].dataTable = re.rows || [];
        visibleData.rightDataList[infoForm.Pindex].loadDetails = false;
      });
    } else {
      console.log("error submit!");
      return false;
    }
  });
}
// 禁用选择
function selectable(row, index) {
  // console.log(visibleData.rightDataList[index].status)
  return row.status == 5 ? false : true;
}
watch(
  () => visibleData.query,
  (newValue, oldValue) => {
    if (!newValue) {
      visibleData.leftDataList.forEach((item) => (item["show"] = true));
    } else {
      visibleData.leftDataList.forEach((item) => {
        if (!item.tableDisplayName.toLowerCase().includes(newValue.toLowerCase())) {
          item["show"] = false;
        }
      });
    }
  }
);
function changeDialogVisible(row, type) {
  // type 1查看 2编辑（新增）
  visibleData.id = row.id;
  visibleData.leftTitle = row.name;
  visibleData.leftIndex = 0;
  visibleData.type = type;
  visibleData.year = row.year;
  dialogVisible.value = true;
  visibleQueryParams.deptId = "";
  expandedRowKeys.value = [];
  getLeftList({ taskId: row.id, selectType }).then((r) => {
    r.data.forEach((item) => (item["show"] = true));
    visibleData.leftDataList = r.data || [];
    visibleData.reportingType =
      visibleData.leftDataList[visibleData.leftIndex].reportingType;
    visibleData.tableName = visibleData.leftDataList[visibleData.leftIndex].tableName;
    // if( visibleData.leftDataList.length>0)  getRightDataList()
    // businessTRef.value.changeVisibleData(visibleData.leftIndex,visibleData.tableName,visibleData.type,visibleData.leftDataList,visibleData.id)
  });
  switch (type) {
    case 1:
      // console.log(type)
      break;
    case 2:
      getXZ(row.id).then((res) => {
        dialogXzVisible.title = row.name;
        dialogXzVisible.value = row.reportingExplain;
        if (res.data.isTips == 0) dialogXzVisible.show = true;
        else dialogXzVisible.show = false;
      });
      // console.log(type)
      break;
    default:
      console.log("默认");
  }
}
// 提交表单保存数据
function save() {
  let obj = {
    itemId: visibleData.leftDataList[visibleData.leftIndex].id,
    taskId: visibleData.id,
    id: infoForm.id,
    status: infoForm.status,
    remark: infoForm.remark,
  };

  refInfoForm.value.validate((valid) => {
    if (valid) {
      updateStatus(obj).then((res) => {
        if (res.code != 200) return proxy.$message.error(res.msg);
        proxy.$message.success("提交成功");
        getLeftList({ taskId: visibleData.id, selectType, reportingType: 1 }).then(
          (r) => {
            r.data.forEach((item) => (item["show"] = true));
            visibleData.leftDataList = r.data || [];
            dialogFormVisible.value = false;
            // if( visibleData.leftDataList.length>0)  getRightDataList()
            businessTRef.value.changeVisibleData(
              visibleData.leftIndex,
              visibleData.tableName,
              visibleData.type,
              visibleData.leftDataList,
              visibleData.id
            );
          }
        );
      });
    } else {
      console.log("error submit!");
      return false;
    }
  });
}
function changeInfoFormType(row, type) {
  // type 1查看 2编辑（新增）
  infoForm.type = type;
  infoForm.id = (row && row.id) || "";
  let obj = {
    itemId: visibleData.leftDataList[visibleData.leftIndex].id,
    id: row.id || "",
    selectType,
    taskId: visibleData.id,
  };
  itemAuditField(obj).then((res) => {
    infoForm.status = res.data[0].value == 3 ? "5" : res.data[0].value;
    infoForm.remark = res.data[1].value;
  });
  getInfoForm(obj).then((res) => {
    res.data.forEach((item) => {
      switch (item.fieldName) {
        case "year":
          item.disabled = true;
          break;
        case "budget_year":
          item.disabled = true;
          break;
        case "actual_incurred":
          item.disabled = true;
          break;
        default:
          item.disabled = false;
      }
      item.rules = [
        {
          required: item.fieldRequired == 0,
          trigger: "blur",
          message: "不能为空",
        },
      ];
      if (item.fieldType != 1)
        item.rules.push({
          validator: (rule, value, callback) => {
            if (!value && item.fieldRequired == 0) {
              return callback(new Error("不能为空"));
            }
            const num = Number(value);
            if (isNaN(num)) {
              return callback(new Error("请输入有效的数字"));
            }

            callback();
          },
          trigger: ["blur", "change"],
        });
    });
    let sindex = res.data.findIndex((item) => item.fieldName == "cost_description");
    let info = res.data.splice(sindex, 1);
    infoForm.List = res.data.concat(info);
    dialogFormVisible.value = true;
  });
  reportingLog({
    itemId: visibleData.leftDataList[visibleData.leftIndex].id,
    id: row.id || 0,
    taskId: visibleData.id,
  }).then((res) => {
    if (res.code != 200) return proxy.$message.error(res.msg);
    infoForm.logList = res.data;
  });
}
// 点击左侧更换下标
function changeIndex(index, tableName, reportingType) {
  visibleData.leftIndex = index;
  expandedRowKeys.value = [];
  expandedRowKeys.value = [];
  visibleData.tableName = tableName;
  visibleData.reportingType = reportingType;
  // getRightTableList()
  // getRightDataList()
}
// 获取左边 table title 数据
function getRightTableList() {
  getRightTable({
    itemId: visibleData.leftDataList[visibleData.leftIndex].id,
    taskId: visibleData.id,
    selectType,
    ...visibleQueryParams,
  }).then((r) => {
    visibleData.rightTableList = r.data || [];
    // console.log( visibleData.rightTableList)

    // getRightDataList()
  });
}
// 获取 table 内容数据
function getRightDataList() {
  getDeptDataList({
    taskId: visibleData.id,
    itemId: visibleData.leftDataList[visibleData.leftIndex].id,
    selectType,
    ...visibleQueryParams,
  }).then((r) => {
    r.rows.forEach((item) => {
      item["headTable"] = [];
      item["dataTable"] = [];
      item["loadDetails"] = true;
      // item['hasOpen']=false
    });
    visibleData.rightDataList = r.rows || [];
    visibleQueryParams.total = r.total;
  });
  itemInfoTj({
    taskId: visibleData.id,
    itemId: visibleData.leftDataList[visibleData.leftIndex].id,
    selectType,
  }).then((res) => {
    visibleData.tj.audit = res.data.audit || 0;
    visibleData.tj.count = res.data.count || 0;
    visibleData.tj.submit = res.data.submit || 0;
    visibleData.tj.budget = res.data.budget || 0;
    visibleData.tj.budgetYear = res.data.budgetYear || 0;
    visibleData.tj.actualIncurred = res.data.actualIncurred || 0;
    visibleData.tj.estimatedIncurred = res.data.estimatedIncurred || 0;
    visibleData.tj.allActualIncurred = res.data.allActualIncurred || 0;
    visibleData.tj.allBudget = res.data.allBudget || 0;
    visibleData.tj.allBudgetYear = res.data.allBudgetYear || 0;
    visibleData.tj.allCount = res.data.allCount || 0;
    visibleData.tj.allEstimatedIncurred = res.data.allEstimatedIncurred || 0;
  });
}
/** 查询角色列表 */
function getList() {
  loading.value = true;
  listTask(proxy.addDateRange(queryParams.value, dateRange.value)).then((response) => {
    roleList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}
/** 查询部门下拉树结构 */
function getDeptTree() {
  deptTreeSelect().then((response) => {
    deptOptions.value = response.data;
  });
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
function handleSelectionChange(Pindex, selection) {
  infoForm.Pindex = Pindex;
  ids.value = selection.map((item) => item.id);

  // single.value = selection.length != 1;
  // multiple.value = !selection.length;
}

// getDeptTree();
/** 查询预算配置列表 */
function getBList() {
  getLeftList({ taskId: bform.id, selectType }).then((r) => {
    bform.itemList = r.data || [];
  });

}
getList();

</script>
<style lang="scss" scoped>
.v-enter-active,
.v-leave-active {
  transition: opacity 0.2s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}
:deep(.el-table__inner-wrapper) {
  height: 100% !important;
}
.dialogBox {
  display: flex;
  height: 94vh;
  justify-content: space-between;
  & > .left {
    width: 15%;
    height: 100%;
    box-sizing: border-box;
    // border: 1px solid #ddd;
    color: #000;
    .title {
      border-bottom: 1px solid #ccc;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    .content {
      width: 80%;
      box-sizing: border-box;
      height: 90%;
      // border: 1px solid #ccc;
      margin: 0px auto 0;
      overflow-y: auto;
      .item {
        width: 100%;
        display: flex;
        height: 30px;
        // line-height: 30px;
        align-items: center;
        padding: 0 10px;
        font-size: 14px;
        cursor: pointer;
        & > div {
          width: 100%;
          white-space: nowrap;
          overflow: hidden;
          word-break: break-all;
          text-overflow: ellipsis;
          // margin-right: 10px;
        }
      }
      .action {
        background: #eef3ff;
      }
    }
  }
  & > .right {
    width: 85%;
    height: 100%;
    box-sizing: border-box;
    // border: 1px solid #ddd;
    .countBox {
      display: flex;
      margin: 10px 0;
      & > div {
        margin-left: 20px;
      }
    }
    .btnBox {
      display: flex;
      // justify-content: flex-end;
      padding: 10px;
      box-sizing: border-box;
    }
  }
  .title {
    width: 90%;
    box-sizing: border-box;
    margin: 0 auto;
    padding: 10px 10px;
    font-weight: bold;
    font-size: 15px;
    & > div {
      width: 90%;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
}
.formBox {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  & > div {
    width: 49%;
  }
  & > div:last-child {
    width: 100%;
  }
}

.el-form-item__error {
  display: block !important;
}
</style>
