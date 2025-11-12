<template>
  <div class="line">
    <div ref="line" style="width:100%;height:100%"></div>
    <!-- 组件内容占位 -->
  </div>
</template>
  
  <script setup name='Line'>
import { ref, onMounted, onUnmounted, nextTick } from "vue";
// 引入ECharts库
import * as echarts from 'echarts';

// 定义一个ref引用，用于后续获取DOM元素并初始化ECharts实例
const line = ref(null);

// 使用echarts初始化一个图表实例
let myChart


// 定义图表的配置选项
let option;

// 设置图表的配置
const setChart = function () {
   myChart = echarts.init(line.value);
  // 配置图表的高度、宽度以及X轴和Y轴的属性
  option = {
    // width: 400,
    // height: 200,
    xAxis: {
      type: "category",
      boundaryGap: false,
      show: false,
      // X轴的数据点
      // data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    },
    yAxis: {
      show: false,
      type: "value",
    },
    series: [
      {
        data: [820, 932, 901, 934, 1290, 1330, 1320],
        type: "line",
        lineStyle: {
          width: 6,
          type: "solid",
          color: "#4985fe",
        },
        // 不显示数据点
        symbol: "none",
        areaStyle: {
          color: {
            type: "linear",
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [
              {
                offset: 1,
                color: "rgba(255, 255, 255, 0.3)",
              },
              {
                offset: 0,
                color: "#4985fe",
              },
            ],
          },
        },
      },
    ],
  };

  // 设置图表的选项
  option && myChart.setOption(option);
};

// 在组件挂载时，初始化图表
onMounted(() => {
    console.log(line.value)
    nextTick(() => {
      setChart();
    })
 
});

// 在组件卸载时，释放ECharts实例
onUnmounted(() => {
  myChart.dispose();
});
</script>
<style lang="scss" scoped>
 .line{
    width:100%;
    height:100%;
 }
</style>