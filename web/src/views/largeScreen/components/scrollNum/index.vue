<template>
  <div
    class="scrollNum"
    :style="`justify-content:${!props.unit ? 'center' : 'start'};`"
  >
    <el-statistic
      :value="outputValue"
      :precision="2"
      :value-style="`color:${props.color};font-size:
  ${props.size}px;font-weight: ${props.bold ? 'bold' : ''};`"
    />
    <span v-if="props.unit">{{ props.unit }}</span>
  </div>
</template>

<script setup>
import { useTransition } from "@vueuse/core";
import { ref } from "vue";

const props = defineProps({
  num: {
    type: Number,
    default: 0,
  },
  unit: {
    type: String,
    default: null,
  },
  size: {
    type: String,
    default: "17",
  },
  bold: {
    type: Boolean,
    default: false,
  },
  time: {
    type: Number,
    default: 500,
  },
  color: {
    type: String,
    default: "#23fffc",
  },
});
const number = ref(0);

const outputValue = useTransition(number, {
  duration: props.time,
});
number.value = props.num;
</script>

<style lang="scss" scoped>
.scrollNum {
  display: flex;
  gap: 5px;
  align-items: end;
  // justify-content: center;
  // & > span {
  // }
}
</style>
