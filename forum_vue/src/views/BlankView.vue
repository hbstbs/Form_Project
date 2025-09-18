<template>
  <!-- 空白的页面，用于路由跳转过过渡，实现页面的刷新 -->
  <div></div>
</template>
<script>
export default {
  name: "pageBlank",
  data() {
    return {};
  },
  beforeRouteEnter(to, from, next) {
    // 获取商品订单
    next((vm) => {
      let query = vm.$route.query.path;
      vm.$router.push({ path: query });
    });
  },
  methods: {},
};
</script>
