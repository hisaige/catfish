<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
/* 全局 使表格与表头对其 */
body .el-table th.gutter{
display: table-cell!important;
}
</style>
