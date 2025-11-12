<template>
  <!-- <div
    class="ls"
    :class="zoomStore.zoomState ? 'big' : 'little'"
    :key="zoomStore.zoomState"
  > -->
  <div class="ls-view ls-subPage">
    <div class="ls-subPage_title">
      <p>{{ `${props.params.name}相关费用` }}</p>
      <span @click="goBack">返回</span>
    </div>
    <div class="ls-subPage_infos boxLayout">
      <div v-for="item in costInfo" :key="item.name">
        <p>{{ item.title }}</p>
        <p>
          <ScrollNum
            :num="item.value"
            unit="万元"
            size="28"
            color="#fffc19"
            bold
            :key="item.value"
          />
        </p>
      </div>
    </div>

    <div class="ls-subPage_table boxLayout1">
      <div>
        <LsBox title="实际费用分析">
          <template #content>
            <ECharts
              :options="sjfyfxChartData"
              width="100%"
              height="100%"
            ></ECharts>
          </template>
        </LsBox>
      </div>
      <div class="list">
        <div class="zhao">
          <el-table height="100%" :data="tableData" @row-click="handleRowClick" show-summary :summary-method="mainSummaryMethod" sum-text="合计" style="cursor: pointer;  width: 100%;" class="dynamic-table"
          :fit="true"
          >
            <el-table-column
              v-for="item in tableConfig"
              :key="item"
              :prop="item.dict"
              :label="item.dict === tableConfig[0].dict ? item.title : `${item.title}（元）`"
              align="center"
              :min-width="item.dict === tableConfig[0].dict ? '150' : '130'">
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>
    <!-- 表格详情弹窗 -->
    <el-dialog v-model="dialogVisible" align-center :title="`实际费用详情（${kmmc} - ${cname}）`" width="80%" :before-close="handleClose" class="custom-dialog">
      <el-table :data="tableDetailInfoData" height="400" border class="custom-table" show-summary :summary-method="detailSummaryMethod" sum-text="合计">
        <el-table-column prop="zzrq" label="总账日期" align="center" />
        <el-table-column prop="kjkmsm" label="科目描述" align="center" />
        <el-table-column prop="rjztsm" label="日记账头说明" align="center" />
        <el-table-column prop="zy" label="摘要" align="center" />
        <el-table-column prop="total" label="借方金额（元）" align="center">
          <template #default="scope">
            {{ scope.row.total?.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}
          </template>
        </el-table-column>
        <el-table-column prop="bwddf" label="贷方金额（元）" align="center">
          <template #default="scope">
            {{ scope.row.bwddf?.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
  <!-- </div> -->
</template>

<script setup>
import LsBox from "@/views/largeScreen/components/boxTitle";
import ECharts from "@/components/ECharts/index.vue";
import { yszcChart, zctbChart, sjfyfxChart } from "@/views/largeScreen/chartConfig.js";
import getPie3D from "@/views/largeScreen/threedPieChart.js";
import getPie3DSub from "@/views/largeScreen/subPage/threePie.js";
import { getSubPageData, getTableDetailInfo } from "@/api/largeScreen/large.js";
import { threeOption } from "@/views/largeScreen/subPage/threePie.js";
import ScrollNum from "@/views/largeScreen/components/scrollNum/index.vue";
import { defineEmits } from "vue";
// 定义可以触发的事件
const emit = defineEmits(["child-event"]);

const props = defineProps({
  params: {
    type: Object,
  },
});

//金额信息
const costInfo = ref([]);
//预算分析饼图
const ysfxChartData = ref([]);
const tbCost = ref(0);

//支出同比分析饼图
const zctbChartData = ref([]);

//预算支出进度占比
const threeOptionData = ref([]);
const costRatio = ref(0);

//预算金额和支出金额分析
const yszcChartData = ref([]);

//实际费用分析
const sjfyfxChartData = ref([]);

//表格数据
const tableConfig = ref([]);
const tableData = ref([]);

// 表格实际费用详情
const tableDetailInfoData = ref([]);

// 弹窗控制
const dialogVisible = ref(false);

// 关闭弹窗
const handleClose = (done) => {
  done();
};

//返回
const goBack = () => {
  // 触发事件并传递参数
  emit("child-event", { flag: false });
};

const kmmc = ref('');
const cname = ref('');

//处理表格行点击事件
const handleRowClick = (row, column, event) => {
  // 获取当前单元格的值
  const cellValue = row[column.property];
  
  // 检查是否为数值且大于零
  const numericValue = parseFloat(cellValue?.toString().replace(/,/g, ''));
  if (isNaN(numericValue)) {
    return; // 数据不大于零时不允许点击
  }
  
  // 获取行表头
  const rowHeader = row;
  // 获取列表头
  const columnHeader = tableConfig.value.find(item => item.dict === column.property);

  const params = {
    taskId: props.params.taskId,
    companyId: props.params.firmId,  
    deptName: rowHeader.data0,
    subjectName: columnHeader.title
  }

  getTableDetailInfo(params).then(res => {
    if (res.code === 200) {
      kmmc.value = rowHeader.data0;
      cname.value = columnHeader.title;
      tableDetailInfoData.value = res.data
      dialogVisible.value = true; // 显示弹窗
    }
  })
  
  // 在这里可以添加其他处理逻辑，例如触发事件或更新状态
};

// 合计逻辑 - 第一个表格（动态表格）：按列求和，支持千分位字符串
const mainSummaryMethod = ({ columns, data }) => {
  const sums = [];
  columns.forEach((column, index) => {
    if (index === 0) {
      sums[index] = '合计';
      return;
    }
    const values = data.map((item) => {
      const raw = item[column.property];
      if (raw === undefined || raw === null || raw === '') return 0;
      if (typeof raw === 'number') return raw;
      const num = parseFloat(String(raw).replace(/,/g, ''));
      return isNaN(num) ? 0 : num;
    });
    const total = values.reduce((acc, cur) => acc + cur, 0);
    sums[index] = new Intl.NumberFormat('zh-CN', { minimumFractionDigits: 2, maximumFractionDigits: 2 }).format(total);
  });
  return sums;
};

// 合计逻辑 - 第二个表格（明细弹窗）：仅汇总最后一列 total
const detailSummaryMethod = ({ columns, data }) => {
  const sums = [];
  const lastIndex = columns.length - 1;
  columns.forEach((column, index) => {
    if (index === 0) {
      sums[index] = '合计';
      return;
    }
    if ((index === lastIndex || index === lastIndex - 1) && (column.property === 'total' || column.property === 'bwddf')) {
      const totalJ = data.reduce((acc, row) => {
        const val = row.total;
        if (val === undefined || val === null || val === '') return acc;
        if (typeof val === 'number') return acc + val;
        const num = parseFloat(String(val).replace(/,/g, ''));
        return acc + (isNaN(num) ? 0 : num);
      }, 0);
      const totalD = data.reduce((acc, row) => {
        const val = row.bwddf;
        if (val === undefined || val === null || val === '') return acc;
        if (typeof val === 'number') return acc + val;
        const num = parseFloat(String(val).replace(/,/g, ''));
        return acc + (isNaN(num) ? 0 : num);
      }, 0);
      sums[lastIndex - 1] = new Intl.NumberFormat('zh-CN', { minimumFractionDigits: 2, maximumFractionDigits: 2 }).format(totalJ);
      sums[lastIndex] = new Intl.NumberFormat('zh-CN', { minimumFractionDigits: 2, maximumFractionDigits: 2 }).format(totalD);
    } else {
      sums[index] = '';
    }
  });
  return sums;
};

const numNullAll=(data)=> {
  // 检查所有数据是否都为0
  const allZero = data.every(item => item.value === 0);
  // 如果所有数据都为0，设置灰色
  if (allZero) {
    data.forEach(item => {
      item.value = 0.12138
      item.itemStyle = { color: '#ccc' }; // 设置灰色
    });
  }
  return data
}

//获取子页面数据
const getSubPage = async () => {
  const params = {
    companyId: props.params.firmId,
    taskId: props.params.taskId,
    type: props.params.id,
  };
  // console.log(params, "接口参数");
  try {
    const res = await getSubPageData(params);
    // console.log(res, "子页面数据");
    if (res.code === 200) {
      //数据解构
      const { top, ysfx, zctbfx, yszcjdzb, ysjezcjefx, lastAndCurrentYearQuarterly ,tables } = res.data;
      //金额信息
      // console.log(top, "看看金额");

      costInfo.value = [
        {
          title: "本年预算金额",
          value: top.dqystbje,
        },
        {
          title: "本年实际发生金额",
          value: top.dqsjfsje,
        },
        {
          title: "上年预算金额",
          value: top.syndysje,
        },
        {
          title: "上年实际发生金额",
          value: top.syndsjfsje,
        },
      ];

      //预算分析饼图
      tbCost.value = ysfx[0].value - ysfx[1].value;
      let ysfxData = ysfx.map((item, index) => ({
        name: item.name,
        value: item.value,
        itemStyle: {
          color: ["#00a8ff", "#00d7e9"][index],
        },
      }));
      ysfxChartData.value = getPie3D(numNullAll(ysfxData), 0, 0.8, 16);
      //支出同比分析饼图
      zctbChart.series[0].data = zctbfx;
      const lastYearExpense = zctbfx[0].value; // 去年支出
      const thisYearExpense = zctbfx[1].value; // 今年支出
      if (lastYearExpense && thisYearExpense) {
        // 计算同比变化百分比
        const yearOverYearPercentage =
          ((thisYearExpense - lastYearExpense) / lastYearExpense) * 100;
        // console.log(`同比变化百分比为：${yearOverYearPercentage.toFixed(2)}%`);

        zctbChart.title[1].text = `${yearOverYearPercentage.toFixed(2)}%`;
      } else {
        zctbChart.title[1].text = "0%";
      }
      zctbChart.title[0].text = "同比";

      zctbChartData.value = zctbChart;

      // //预算支出进度占比
      const predictNum = yszcjdzb[0].value; // 预算金额
      const realityNum = yszcjdzb[1].value; // 实际费用
      //计算实际费用占预算的百分比
      costRatio.value = ((realityNum / predictNum) * 100).toFixed(2);

      let yszcData = yszcjdzb.map((item, index) => ({
        name: item.name,
        val: item.value, //存储数据的地方
        itemStyle: {
          color: ["rgba(255, 196, 0, 0.5)", "rgba(11, 127, 193, 0.5)"][index],
        },
      }));

      let total = 0;
      yszcData.forEach((item) => {
        total += item.val;
      });
      const series = getPie3DSub(
        yszcData.map((item) => {
          item.value = Number(((item.val / total) * 100).toFixed(2));
          return item;
        }),
        0.8,
        240,
        28,
        26,
        1
      );
      // console.log(series, "页面的数据");
      threeOption.legend.data = yszcData.map((item) => item.name);
      threeOption.series = series;
      // console.log(threeOption, "配置项");
      threeOptionData.value = threeOption;

      //预算金额和支出金额分析柱图
      yszcChart.xAxis.data = ysjezcjefx.months;
      yszcChart.series[0].data = ysjezcjefx.ysje;
      yszcChart.series[1].data = ysjezcjefx.zcje;
      yszcChartData.value = yszcChart;

      //实际费用分析
      sjfyfxChart.legend.data = lastAndCurrentYearQuarterly.map((item) => item.year);

      sjfyfxChart.series[0].data = lastAndCurrentYearQuarterly[0].quarterly;
      sjfyfxChart.series[0].name = lastAndCurrentYearQuarterly[0].year;
      sjfyfxChart.series[1].data = lastAndCurrentYearQuarterly[1].quarterly;
      sjfyfxChart.series[1].name = lastAndCurrentYearQuarterly[1].year;

      sjfyfxChartData.value = sjfyfxChart;


      //表格数据
      tableConfig.value = tables.names.map((name, index) => ({
        title: name,
        dict: `data${index}`,
      }));
      // tableData.value = tables.valus;

      let tData = [];
      // console.log(tables, "有数据吗");

      tables.valus.map((item, index) => {
        // console.log(item, "是数据吗");

        let obj = item.reduce((acc, item, index) => {
          // 对数值类型的数据应用千分位格式化
          if (typeof item === 'number') {
            // 保留两位小数并格式化
            acc[`data${index}`] = new Intl.NumberFormat('zh-CN', { minimumFractionDigits: 2, maximumFractionDigits: 2 }).format(item);
          } else {
            acc[`data${index}`] = item;
          }
          return acc;
        }, {});
        tData.push(obj);
      });
      console.log(tables, "看看数据");
      console.log(tableConfig, "看看数据");
      console.log(tData, "////////?????????????????????????????");
      tableData.value = tData;
    }
  } catch (error) {
    console.error("获取数据失败:", error);
  }
};

getSubPage();
</script>

<style lang="scss" scoped>

/* 穿透scoped样式，修改Dialog内部样式 */
::v-deep .custom-dialog {
  --el-dialog-bg-color:  rgb(22, 71, 97, 0.95); /* 对话框背景色 */
  --el-text-color-primary: #fff; /* 标题文字色 */
}

::v-deep .custom-dialog .el-dialog__body {
  color: #e5e6eb; /* 内容文字色 */
}

/* 自定义按钮样式以适应深色背景 */
::v-deep .custom-dialog .el-button {
  background-color: #303846;
  color: #fff;
  border-color: #4e5969;
}

::v-deep .custom-dialog .el-button--primary {
  background-color: #409eff;
  border-color: #409eff;
}

::v-deep .custom-table {
  background: transparent; // 用 transparent 更规范，替代 rgba(0,0,0,0)
  --el-table-tr-bg-color: transparent;
  --el-table-border-color: rgba(8, 150, 233, 0.2);
}
::v-deep .custom-table .el-table__header th { 
  background-color: rgba(8, 150, 233, 0.4) !important;
  color: #fff;
  font-size: 12px;
}
::v-deep .custom-table .el-table__body td {
  background: transparent;
  color: #fff;
  font-size: 12px;
}
::v-deep .custom-table .el-table__body tr:hover > td {
  background-color: rgba(8, 150, 233, 0.1) !important;
}
::v-deep .custom-table .el-table td.el-table__cell,
::v-deep .custom-table .el-table th.el-table__cell.is-leaf {
  border-bottom: 1px solid rgba(8, 150, 233, 0.2);
}

// 动态表格样式调整，实现自适应宽度
::v-deep .dynamic-table {
  width: 100% !important; // 确保表格占满容器
  table-layout: auto !important; // 自动计算列宽
}

::v-deep .dynamic-table .el-table__header th,
::v-deep .dynamic-table .el-table__body td {
  white-space: nowrap; // 防止内容换行
  // text-overflow: ellipsis; // 内容过长时显示省略号
  // overflow: hidden; // 隐藏溢出内容
}

// 移除固定宽度限制，让列宽根据内容自适应
::v-deep .dynamic-table .el-table__header th {
  width: auto !important;
  min-width: 80px; // 设置最小宽度，防止过窄
}

::v-deep .dynamic-table .el-table__body td {
  width: auto !important;
}

// 第一列（通常是标题列）可以适当宽一些
::v-deep .dynamic-table .el-table__header th:first-child,
::v-deep .dynamic-table .el-table__body td:first-child {
  min-width: 120px;
}

// 去掉表格底部白色长条（隐藏水平滚动条/空隙）
:v-deep .list .el-scrollbar__bar.is-horizontal {
  display: none !important;
}

:v-deep .list .el-scrollbar__wrap {
  overflow-x: hidden !important;
}

:v-deep .list .el-table {
  margin-bottom: 0 !important;
}

.ls-subPage_table .list { overflow-x: hidden; }
.ls-subPage_table .list .zhao { height: auto; }

// 合计行样式，匹配深色主题
::v-deep .list .el-table__footer-wrapper td.el-table__cell,
::v-deep .list .el-table__footer-wrapper th.el-table__cell {
  background-color: rgba(8, 150, 233, 0.4) !important;
  color: #fff !important;
}

::v-deep .list .el-table__footer-wrapper .cell {
  color: #fff !important;
}

::v-deep .custom-table .el-table__footer-wrapper td.el-table__cell,
::v-deep .custom-table .el-table__footer-wrapper th.el-table__cell {
  background-color: rgba(8, 150, 233, 0.4) !important;
  color: #fff !important;
}

::v-deep .custom-table .el-table__footer-wrapper .cell {
  color: #fff !important;
}

</style>
