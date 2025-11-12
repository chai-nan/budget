<template>
  <div style="width:100%;">
    <el-table v-loading="loading" id="Table" :data="tableData" style="width: 100%"  :max-height="maxHeight" :header-cell-style="{'text-align':'center'}">
      <el-table-column  type="index" fixed="left" width="50"></el-table-column>
        <template v-for='(item,index) in tableHeadData' :key='index'>

            <el-table-column :label="item.name" v-if='item.children && item.children.length > 0'  >
             
            <template v-for='(ite,idx) in item.children' :key='idx'>
                <el-table-column :label="ite.name" :prop="ite.key"  min-width="150px" align='center'  >
                </el-table-column>
            </template>
            </el-table-column>
            <el-table-column :label="item.name" v-else :prop="item.key" :fixed="item.key=='fymc'||item.key=='fylx' ?'left':false"  min-width="150px" align='center' >
            </el-table-column>

        </template>
    </el-table>
  </div>
</template>

<script setup name='TabList'>
import { ref, reactive, toRefs, onMounted, watch } from "vue";
 const props = defineProps({
  tableData: {
    type: Array,
    default: () => [],
  },
  tableHeadData: {
    type: Array,
    default: () => [],
  },
  maxHeight:{
    type:String,
    default:"83.5vh"
  }
});
const loading=ref(false)
watch(()=>props.tableHeadData,(newValue,oldValue)=>{
  loading.value=true
  if(  newValue.length>0) return loading.value=false


})

</script>
