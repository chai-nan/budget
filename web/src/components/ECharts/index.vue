<template>
  <div
    ref="chartRef"
    id="myChart"
    :style="{ width: width, height: height }"
  ></div>
</template>

<script setup>
import { onMounted, ref, watch, defineProps } from "vue";
import * as echarts from "echarts";

const props = defineProps({
  options: Object,
  width: {
    type: String,
    default: "100%",
  },
  height: {
    type: String,
    default: "400px",
  },
});

const chartRef = ref(null);
let chartInstance = null;

onMounted(async () => {
  // chartInstance = echarts.init(chartRef.value);
  // updateChart();
  // 为了保证dom渲染完成，建议使用Vue中的这个nextTice
  await nextTick();
  chartInstance = echarts.init(chartRef.value);
  updateChart();
  resizeObserver.observe(chartRef.value);
});

// 创建 ResizeObserver 实例
const resizeObserver = new ResizeObserver((entries) => {
  entries.forEach((entry) => {
    chartInstance.resize();
  });
});

watch(
  () => props.options,
  (newOptions) => {
    updateChart();
  },
  { deep: true }
);

const updateChart = () => {
  if (chartInstance && props.options) {
    chartInstance.setOption(props.options);
  }
};
</script>
