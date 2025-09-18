<template>
  <div class="container">
    <el-menu
      :default-active="$route.path"
      class="el-menu-vertical-demo"
      background-color="#e7f2fec2"
      text-color="#000"
      active-text-color="#ff00ff"
      router
    >
      <div v-for="(item, key) in items" :key="key">
        <el-menu-item v-if="item.title == '首页'" :index="item.index"
          ><i :class="item.icon"></i>
          {{ item.title }}
        </el-menu-item>
        <el-menu-item
          v-else
          :index="item.index"
          @click.native="goPostsByLabel(item)"
          ><i class="el-icon-share"></i>
          {{ item.title }}
        </el-menu-item>
      </div>
    </el-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          icon: "el-icon-setting",
          index: "post",
          title: "首页",
        },
        {
          icon: "el-icon-setting",
          index: "postsByLabelGame",
          title: "游戏",
        },
        {
          icon: "el-icon-setting",
          index: "postsByLabelPolitics",
          title: "政治",
        },
        {
          icon: "el-icon-setting",
          index: "postsByLabelEntertainment",
          title: "娱乐",
        },
        {
          icon: "el-icon-setting",
          index: "postsByLabelNovel",
          title: "小说",
        },
        {
          icon: "el-icon-setting",
          index: "postsByLabelTrval",
          title: "旅游",
        },
        {
          icon: "el-icon-setting",
          index: "postsByLabelComic",
          title: "动漫",
        },
      ],
    };
  },
  components: {},
  methods: {
    goPostsByLabel(item) {
      this.$router.push({
        path: "/index",
        query: {
          path: item.index + "?Label=" + item.title,
        },
      });
      //this.$router.go(0);
      //window.location.reload();
    },
  },
};
</script>

<style scoped>
.sidebar {
  display: block;
  position: absolute;
  width: 200px;
  left: 0;
  top: 50px;
  bottom: 0;
  background-color: #fee6e0;
}
.sidebar > ul {
  height: 100%;
}
</style>
