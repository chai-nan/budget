<template>
  <!-- 封装公共盒子组件 -->
  <div class="ls-box">
    <div class="ls-box_title">
      <span class="iconLeft"></span>
      <span class="title">{{ props.title }}</span>
      <span class="iconRight"></span>
    </div>
    <div class="ls-box_content">
      <slot name="content"></slot>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  title: {
    type: String,
    default: "123",
    required: true,
  },
});
// console.log(props.title, "tttttttttttt");
</script>

<style lang="scss" scoped></style>
