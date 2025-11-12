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
    <el-table v-loading="loading" :data="dataList">
      <!--   @selection-change="handleSelectionChange" -->
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

      <el-table-column label="是否结束 " align="center" prop="completionStatus">
        <template #default="scope">
          <dict-tag :options="completion_status" :value="scope.row.completionStatus" />
        </template>
      </el-table-column>
      <el-table-column label="预算年度 " align="center" prop="budgetYear">
        <template #default="scope">
          <dict-tag :options="result_status" :value="scope.row.budgetYear" />
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" @click="clickHz(scope.row)">汇总</el-button>
          <el-button link type="primary" @click="clickHz2(scope.row)">导出</el-button>

          <el-button
            link
            type="primary"
            @click="jump(scope.row, 1)"
            v-hasPermi="['budget:business:list']"
            >查看</el-button
          >

          <!--  v-hasPermi -->
          <el-button
            link
            type="primary"
            @click="jump(scope.row, 2)"
            v-if="scope.row.completionStatus == 1"
            v-hasPermi="['budget:business:report']"
            >填报</el-button
          >
          <!-- <el-button
                link
                type="primary"
                @click="clickRecovery(scope.row)"
                 v-if='scope.row.lastTask'
                >恢复</el-button
              > -->
          <!--    @click="jump(scope.row,1)"-->
          <!--  v-hasPermi="['budget:business:list']" -->
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
    <!-- 汇总弹窗信息 -->
    <el-dialog
      v-model="hzData.show"
      width="80%"
      title=""
      draggable
      fullscreen
      :close-on-press-escape="false"
    >
      <el-tabs type="border-card" v-model="hzData.name">
        <!-- :before-leave='changeTabs' -->

        <el-tab-pane label="汇总" name="1">
          <div style="display: flex; justify-content: flex-end">
            <el-button
              type="warning"
              plain
              icon="Download"
              @click="handleExport"
              style="margin-bottom: 10px"
              size="small"
              >导出</el-button
            >
            <!--   v-hasPermi="['budget:subject:export']" -->
          </div>
          <tableList
            :tableData="hzData.tabdataList"
            :tableHeadData="hzData.tabheadData"
          />
        </el-tab-pane>

        <!-- <el-tab-pane  label="区域汇总" name='2'>   
            <div style="display: flex;justify-content: flex-end;">
                <el-button
                  type="warning"
                  plain
                  icon="Download"
                  @click="handleExport"
                  v-hasPermi="['budget:subject:export']"
                  style="margin-bottom:10px"
                   size="small"
                >导出</el-button>
              </div>
             <tableList :tableData='hzData.tabdataList' :tableHeadData='hzData.tabheadData'/>
          </el-tab-pane>
          <el-tab-pane label="销售费用预算" name='3'>          
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

<script setup name="business">
import { reactive } from "vue";
import { listTask } from "@/api/BM/task";
import useTagsViewStore from "@/store/modules/tagsView";
import { recovery } from "@/api/taskExecution/index";
import { saveVersion, budgetSummary2 } from "@/api/taskExecution/finance/index";
import tableList from "../finance/component/TabList.vue";
import { saveAs } from "file-saver";
import * as XLSX from "xlsx";
import { mergeArrays, moveKeysToStart, moveKeysToEnd } from "@/utils/utils.js";
const router = useRouter();
const { proxy } = getCurrentInstance();
const { result_status, completion_status } = proxy.useDict(
  "result_status",
  "completion_status"
);
const visitedViews = computed(() => useTagsViewStore().visitedViews);

const selectType = 1;
const dataList = ref([]);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
//   const single = ref(true);
//   const multiple = ref(true);
const total = ref(0);
const dateRange = ref([]);
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
function clickRecovery(row) {
  recovery({ taskId: row.id }).then((r) => {
    if (r.code == 200) {
      proxy.$message.success("恢复详情成功");
      getList();
    }
  });
}
function clickHz2(row) {
  proxy.download(
    "budget/reporting/exportTotal",
    {
      taskId: row.id,
    },
    `${new Date().getTime()}.xlsx`
  );
}
function clickHz(row) {
  hzData.id = row.id;
  budgetSummary2({ type: 1, taskId: hzData.id }).then((res) => {
    hzData.tabdataList = res.data.titleDate;
    hzData.tabheadData = res.data.titleName;
    hzData.name = "1";
    hzData.show = true;
  });
}
async function changeTabs(activeName, oldActiveName) {
  await budgetSummary2({ type: activeName, taskId: hzData.id }).then((res) => {
    hzData.tabdataList = res.data.titleDate;
    hzData.tabheadData = res.data.titleName;
    hzData.show = true;
  });
}
/** 导出按钮操作 */
function handleExport() {
  let data = mergeArrays(hzData.tabdataList, hzData.tabheadData);

  data = moveKeysToStart(data, ["费用名称", "费用类型"]);

  // 创建工作簿
  const ws = XLSX.utils.json_to_sheet(data);
  // 设置列宽自适应
  const columnWidths =
    data[0] &&
    Object.keys(data[0]).map((key) => ({
      wch: key.toString().length + 2, // 根据键名长度设置列宽
    }));

  ws["!cols"] = columnWidths;

  // 创建工作簿并添加工作表
  const wb = XLSX.utils.book_new();

  XLSX.utils.book_append_sheet(wb, ws, "Sheet1");
  // 生成Excel文件
  const wbout = XLSX.write(wb, { bookType: "xlsx", type: "binary" });

  // 字符串转ArrayBuffer
  function s2ab(s) {
    const buf = new ArrayBuffer(s.length);
    const view = new Uint8Array(buf);
    for (let i = 0; i !== s.length; ++i) view[i] = s.charCodeAt(i) & 0xff;
    return buf;
  }

  // 保存文件
  saveAs(new Blob([s2ab(wbout)], { type: "application/octet-stream" }), "汇总.xlsx");

  // proxy.download('budget/subject/export', {
  //   ...queryParams.value
  // }, `subject_${new Date().getTime()}.xlsx`)
}

// 跳转
function jump(row, type) {
  // let cont=0
  // for (const r of visitedViews.value) {
  //   if (r.path === '/taskExecution/businessInfo') {
  //     r.title=row.name
  //     useTagsViewStore().updateVisitedView(r)
  //     // when query is different then update

  //   }
  // }
  router.push(
    "/taskExecution/businessInfo?id=" +
      row.id +
      "&name=" +
      row.name +
      "&type=" +
      type +
      "&year=" +
      row.year
  );
}
function toLastView(visitedViews, view) {
  const latestView = visitedViews.slice(-1)[0];
  if (latestView) {
    router.push(latestView.fullPath);
  } else {
    // now the default is to redirect to the home page if there is no tags-view,
    // you can adjust it according to your needs.
    if (view.name === "Dashboard") {
      // to reload home page
      router.replace({ path: "/redirect" + view.fullPath });
    } else {
      router.push("/");
    }
  }
}
/** 查询角色列表 */
function getList() {
  loading.value = true;
  let obj = proxy.addDateRange(queryParams.value, dateRange.value);

  listTask({ ...obj, selectType }).then((response) => {
    dataList.value = response.rows;
    total.value = response.total;
    loading.value = false;
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
function handleSelectionChange(selection) {
  ids.value = selection.map((item) => item.roleId);
  // single.value = selection.length != 1;
  // multiple.value = !selection.length;
}

getList();
</script>
<style lang="scss" scoped></style>
