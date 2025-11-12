<template>
  <div
    class="ls"
    :class="zoomStore.zoomState ? 'big' : 'little'"
    :key="zoomStore.zoomState"
  >
    <div class="ls-view" v-if="!flag" :key="flag">
      <div class="ls-view_header">
        <div>
          <p class="headTime">{{ formattedDate }} {{ currentDayOfWeek }}</p>
        </div>
        <div>
          <p class="headText">费用分析看板</p>
        </div>
        <div class="headBtn">
          <el-select
            popper-class="large-select"
            v-model="firmValue"
            class="m-2"
            placeholder="选择公司"
            size="large"
            style="width: 240px"
            @change="areaChange"
          >
            <el-option
              v-for="item in firmOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
          <el-select
            popper-class="large-select"
            v-model="taskValue"
            class="m-2"
            placeholder="选择任务"
            size="large"
            style="width: 240px"
            @change="areaChange"
          >
            <el-option
              v-for="item in taskOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
          <!-- <el-button round @click="zoomClick">{{
            zoomStore.zoomState ? "缩小" : "放大"
          }}</el-button> -->
          <svg-icon
            :icon-class="zoomStore.zoomState ? 'exit-fullscreen' : 'fullscreen'"
            @click="zoomClick"
          />
        </div>
      </div>
      <div class="ls-view_content">
        <div class="left side">
          <div class="top">
            <LsBox title="整体概况">
              <template #content>
                <div class="ls-survey contentBox">
                  <div>
                    <p class="titTop" v-if="predictNum !== 0" style="font-size: 20px">
                      {{ `支出进度：${costRatio}%` }}
                    </p>
                    <div class="ls-survey_data">
                      <div>
                        <div class="pedestal">
                          <span class="plus"></span>
                        </div>
                        <div class="text">
                          <p>预算金额</p>
                          <p style="font-size: large">
                            <ScrollNum
                              size="20"
                              bold
                              :num="Number(predictNum.toFixed(2))"
                              unit="万元"
                              :key="predictNum"
                            />
                          </p>
                        </div>
                      </div>
                      <div>
                        <div class="pedestal">
                          <span class="home"></span>
                        </div>
                        <div class="text">
                          <p>实际费用</p>
                          <p>
                            <ScrollNum
                            size="20"
                              bold
                              :num="realityNum"
                              unit="万元"
                              :key="realityNum"
                            />
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div>
                    <LsBox title="近三年预算费用/实际费用">
                      <template #content>
                        <div class="ls-cost contentBox">
                          <ECharts
                            :options="threeYearChartData"
                            width="100%"
                            height="100%"
                          ></ECharts>
                        </div>
                      </template>
                    </LsBox>
                  </div>
                </div>
              </template>
            </LsBox>
          </div>
          <div class="bottom">
            <!-- <LsBox title="预算分析">
              <template #content>
                <div class="ls-analyse contentBox">
                  <ECharts
                    :options="fxChartData"
                    width="100%"
                    height="100%"
                  ></ECharts>
                </div>
              </template>
            </LsBox> -->
            <LsBox title="预算费用/实际费用">
                <template #content>
                  <div class="ls-cost contentBox">
                    <ECharts
                      :options="fyChartData"
                      width="100%"
                      height="100%"
                    ></ECharts>
                  </div>
                </template>
              </LsBox>
          </div>
        </div>
        <div class="right">
          <div class="top">
            <div>
              <div v-for="item in centreData" :key="item">
                <div class="main">
                  <div
                    v-for="ite in item.data"
                    :key="ite"
                    @click="skipPages(ite)"
                  >
                    <p>
                      <ScrollNum
                        :num="Number(ite.value)"
                        size="20"
                        bold
                        :key="ite.value"
                      />
                    </p>
                    <p>{{ ite.name }}</p>
                    <p>{{ ite.unit }}</p>
                    <span class="cost"></span>
                  </div>
                </div>
                <div class="back">
                  <img
                    src="../../assets/images//lsImg/backTop.svg"
                    alt="back"
                  />
                </div>
              </div>
            </div>
            <!-- <div>
              <LsBox title="预算费用/实际费用">
                <template #content>
                  <div class="ls-cost contentBox">
                    <ECharts
                      :options="fyChartData"
                      width="100%"
                      height="100%"
                    ></ECharts>
                  </div>
                </template>
              </LsBox>
            </div> -->
          </div>
          <div class="bottom">
            <LsBox title="预算统计（金额，企业，部门）">
              <template #content>
                <div class="ls-statistics contentBox">
                  <ECharts
                    :options="tjChartData1"
                    width="100%"
                    height="100%"
                  ></ECharts>
                </div>
              </template>
            </LsBox>
          </div>
        </div>
      </div>
    </div>

    <SubPage
      v-else
      :params="paramsData"
      @child-event="handleChildEvent"
    ></SubPage>
  </div>
</template>
<script setup name="largeScreenIndex">
import LsBox from "@/views/largeScreen/components/boxTitle";
import SubPage from "@/views/largeScreen/subPage/index.vue";
import ECharts from "@/components/ECharts/index.vue";
import ScrollNum from "@/views/largeScreen/components/scrollNum/index.vue";
import {
  analyseChart,
  statisticsChart,
  statisticsChart1,
  threeYearChart,
  costChart,
} from "@/views/largeScreen/chartConfig.js";
import getPie3D from "@/views/largeScreen/threedPieChart.js";
import { onMounted, ref, onUnmounted } from "vue";
import dayjs from "dayjs";
import { getSelectList, getLargeScreenData } from "@/api/largeScreen/large.js";
import { useZoomState } from "@/store/modules/zoom.js";
import { useRouter } from "vue-router";
const router = useRouter();
const zoomStore = useZoomState();

//子页面开关
const flag = ref(false);
const paramsData = ref({});
// 定义处理子组件事件的方法
const handleChildEvent = (data) => {
  flag.value = data.flag;
};

//缩放
const zoomClick = () => {
  zoomStore.updateZoomState(!zoomStore.zoomState);
};

//时间戳
const formattedDate = ref(dayjs().format("YYYY-MM-DD HH:mm:ss"));
const currentDayOfWeek = ref(""); // 当前星期几
const ratioData = ref([]);
//公司下拉数据
const firmValue = ref("");
const firmOptions = ref([]);
//任务下拉数据
const taskValue = ref("");
const taskOptions = ref([]);

//整体概况数据
const predictNum = ref(0); //预算
const realityNum = ref(0); //实际
const costRatio = ref(0);


//南丁格尔玫瑰图
const fxChartData = ref();

//屏幕中间靠上的数据
const centreData = ref([]);

//预算统计渐变柱图
const tjChartData = ref();

const tjChartData1 = ref();

//费用堆叠柱图
const fyChartData = ref();

const threeYearChartData = ref();

//获取公司&任务数据
const getFirmList = async () => {
  try {
    const res = await getSelectList();
    // console.log(res, "res");
    if (res.code === 200) {
      firmOptions.value = res.data.companys;
      firmValue.value = res.data.companys[0].id; // 默认选中第一个公司
      taskOptions.value = res.data.tasks;
      taskValue.value = res.data.tasks[0].id; // 默认选中第一个任务

      getLargeData();
    }
  } catch (error) {
    console.error("获取企业列表失败:", error);
  }
};

// 选择框切换
const areaChange = () => {
  fxChartData.value = null;
  tjChartData.value = null;
  fyChartData.value = null;
  threeYearChartData.value = null;
  tjChartData1.value = null;

  getLargeData();
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

//获取大屏总数据
const getLargeData = async () => {
  const params = {
    companyId: firmValue.value,
    taskId: taskValue.value,
  };
  try {
    const res = await getLargeScreenData(params);
    if (res.code === 200) {
      // 数据解构
      const { ztgk, yszc, ysfx, zdp, ystj, yssjfy } = res.data;

      //整体概况赋值
      predictNum.value = Number(ztgk.ysje); // 预算金额
      realityNum.value = Number(ztgk.sjfy); // 实际费用

      //计算实际费用占预算的百分比
      costRatio.value = ((realityNum.value / predictNum.value) * 100).toFixed(
        2
      );

      //预算分析玫瑰图
      const colorList = [
        "#488CF7",
        "#38CAFB",
        "#4CAFF9",
        "#49DDC9",
        "#28A0E6",
        "#036DEA",
        "#183F86",
      ];
      let fxData = ysfx.map((item, index) => ({
        name: item.name,
        value: item.value,
        label: {
          show: true,
          color: colorList[index],
        },
        itemStyle: {
          color: colorList[index],
        },
      }));
      analyseChart.series[0].data = fxData;
      fxChartData.value = analyseChart;

      // console.log(fxChartData.value, "看一下数据有没有更新");

      //tab跳转数据
      const groupedData = Array.from(
        { length: Math.ceil(zdp.length / 3) },
        (_, i) => zdp.slice(i * 3, (i + 1) * 3)
      );

      // 定义输出格式
      const result = groupedData.map((group, index) => ({
        data: group.map((item) => ({
          id: item.id,
          value: item.value.toString(),
          name: item.name,
          unit: "（万元）", // 添加单位
        })),
      }));

      centreData.value = result;

      //预算统计柱图
      // 遍历areaNames列表，然后从每个字符串中去除"有限公司"和"华润燃气"这两个子字符串
      ystj.area = ystj.area.map(item => item.replace("有限公司", "").replace("华润燃气", ""));
      // console.log(ystj.area, "ystj.area");

      // statisticsChart.xAxis[0].data = ystj.area;
      // const ysfy = ystj.ysfy.map(value => value === 0 ? null  : value);
      // statisticsChart.series[0].data = ysfy;
      // const sjfy = ystj.sjfy.map(value => value === 0 ? null  : value);
      // statisticsChart.series[1].data = sjfy;
      // tjChartData.value = statisticsChart;


      //费用堆叠柱图

      ystj.sjfy = ystj.sjfy.map(value => value + 1);
      ystj.ysfy = ystj.ysfy.map(value => value + 1);
      // 将yssjfy.currentYearSjfy和yssjfy.lastYearSjfy 合并为一个数组 偶数位去年 奇数位今年
      
      statisticsChart1.xAxis.data = ystj.area;
      statisticsChart1.series[1].data = ystj.sjfy;
      statisticsChart1.series[1].name = "实际费用";
      statisticsChart1.series[2].data = ystj.sjfy;
      statisticsChart1.series[3].data = ystj.ysfy;
      statisticsChart1.series[3].name = "预算费用";
      statisticsChart1.series[4].data = ystj.sjfy;
      statisticsChart1.series[5].data = ystj.ysfy;
      tjChartData1.value = statisticsChart1;

      // //类型占比信息
      // ratioData.value = [
      //   { title: "员工成本", value: lxzb.ygcb || 0 },
      //   { title: "资产投资", value: lxzb.zctz || 0 },
      //   { title: "安全生产", value: lxzb.aqsc || 0 },
      //   { title: "业务拓展", value: lxzb.ywtz || 0 },
      //   { title: "日常生产", value: lxzb.rcsc || 0 },
      //   { title: "其它可控", value: lxzb.qt || 0 },
      // ];


      // 三年 预算金额与实际金额 
      
      // 提取yszc中的预算和实际费用数据
      let budgetData = [
        yszc.find(item => item.name === "今年预算总额")?.value + 1 || 0,
        yszc.find(item => item.name === "去年预算总额")?.value + 1 || 0,
        yszc.find(item => item.name === "前年预算总额")?.value + 1 || 0
      ];
      
      let actualData = [
        yszc.find(item => item.name === "今年实际总额")?.value + 1 || 0,
        yszc.find(item => item.name === "去年实际总额")?.value + 1 || 0,
        yszc.find(item => item.name === "前年实际总额")?.value + 1 || 0
      ];


      threeYearChart.yAxis.data = yszc[0].years;
      threeYearChart.series[1].data = actualData;
      threeYearChart.series[1].name = "实际费用";
      threeYearChart.series[2].data = actualData;
      threeYearChart.series[3].data = budgetData;
      threeYearChart.series[3].name = "预算费用";
      threeYearChart.series[4].data = actualData;
      threeYearChart.series[5].data = budgetData;
      threeYearChartData.value = threeYearChart;

      //费用堆叠柱图
      yssjfy.currentYearSjfy = yssjfy.currentYearSjfy.map(value => value + 1);
      yssjfy.currentYearYsfy = yssjfy.currentYearYsfy.map(value => value + 1);
      yssjfy.lastYearSjfy = yssjfy.lastYearSjfy.map(value => value + 1);
      yssjfy.lastYearYsfy = yssjfy.lastYearYsfy.map(value => value + 1);

      // 将yssjfy.currentYearSjfy和yssjfy.lastYearSjfy 合并为一个数组 偶数位去年 奇数位今年
      let sjfy = [];
      for (let i = 0; i < yssjfy.currentYearSjfy.length; i++) {
        sjfy.push(yssjfy.lastYearSjfy[i]);
        sjfy.push(yssjfy.currentYearSjfy[i]);
      }

      // 将yssjfy.currentYearYsfy和yssjfy.lastYearYsfy 合并为一个数组 偶数位去年 奇数位今年
      let ysfy = [];
      for (let i = 0; i < yssjfy.currentYearYsfy.length; i++) {
        ysfy.push(yssjfy.lastYearYsfy[i]);
        ysfy.push(yssjfy.currentYearYsfy[i]);
      }

      costChart.series[1].data = sjfy;
      costChart.series[1].name = "实际费用";
      costChart.series[2].data = sjfy;
      costChart.series[3].data = ysfy;
      costChart.series[3].name = "预算费用";  
      costChart.series[4].data = sjfy;
      costChart.series[5].data = ysfy;

      fyChartData.value = costChart;
    }
  } catch (error) {
    console.error("获取大屏数据失败:", error);
  }
};

// 获取星期几的名称
const getDayOfWeek = (date) => {
  const daysOfWeek = [
    "星期日",
    "星期一",
    "星期二",
    "星期三",
    "星期四",
    "星期五",
    "星期六",
  ];
  const dayIndex = date.day(); // 获取当前日期是星期几（0 表示周日，1 表示周一，依此类推）
  return daysOfWeek[dayIndex];
};

//点击跳转子页面
const skipPages = (info) => {
  // console.log(info, "跳转");
  // router.push({
  //   path: "/subPage",
  //   query: {
  //     firmId: firmValue.value,
  //     taskId: taskValue.value,
  //     name: info.name,
  //     id: info.id,
  //   },
  // });
  paramsData.value = {
    firmId: firmValue.value,
    taskId: taskValue.value,
    name: info.name,
    id: info.id,
  };
  flag.value = true;
};

let timerId;
onMounted(() => {
  //每秒更新时间
  timerId = setInterval(() => {
    formattedDate.value = dayjs().format("YYYY年MM月DD日 HH:mm:ss"); // 更新日期和时间
  }, 1000);
  currentDayOfWeek.value = getDayOfWeek(dayjs()); // 设置当前星期几
});

onUnmounted(() => {
  clearInterval(timerId); // 组件卸载时清除定时器
});

getFirmList();
</script>
<style lang="scss" scoped></style>
