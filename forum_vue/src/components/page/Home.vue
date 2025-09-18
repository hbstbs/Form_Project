<template>
  <div class="container">
    <v-head />
    <v-sidebar class="sidebar" />
    <div class="content">
      <router-view :key="$route.fullpath"></router-view>
    </div>
  </div>
</template>

<script>
import VHead from "./Header.vue";
import VSidebar from "./Sidebar.vue";
export default {
  date() {
    return {};
  },
  components: { VHead, VSidebar },
};
</script>

<style scoped land="less"></style>
