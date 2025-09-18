<template>
  <div class="header">
    <div class="logo">PJM论坛</div>
    <div class="user-info">
      <el-dropdown trigger="click" @command="handleCommand">
        <span>
          <img class="user-logo" src="@/assets/img/user3.jpeg" />
        </span>
        <el-dropdown-menu slot="dropdown" class="popper-dropdown">
          <el-dropdown-item command="myMessage">我的信息</el-dropdown-item>
          <el-dropdown-item command="myCollection">我的收藏</el-dropdown-item>
          <el-dropdown-item command="myPosts">我的帖子</el-dropdown-item>
          <el-dropdown-item command="addPost">发帖</el-dropdown-item>
          <el-dropdown-item command="myComment">我的评论</el-dropdown-item>
          <el-dropdown-item command="updatePassword">修改密码</el-dropdown-item>
          <el-dropdown-item command="logout">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>
<script>
// eslint-disable-next-line
export default {
  data() {
    return {
      usersex: "woman",
      username: "xiaoming",
    };
  },
  mounted() {
    this.usersex = sessionStorage.getItem("user_sex");
    this.username = sessionStorage.getItem("ms_username");
  },
  components: {},
  methods: {
    handleCommand(command) {
      if (command === "myMessage") {
        this.$router.push("/message");
      } else if (command === "logout") {
        sessionStorage.clear();
        this.$router.push("/login");
      } else if (command === "myPosts") {
        this.$router.push("/myposts");
      } else if (command === "addPost") {
        this.$router.push("/add");
      } else if (command === "updatePassword") {
        this.$router.push("/updatepwd");
      } else if (command === "myCollection") {
        this.$router.push("/collection");
      } else if (command === "myComment") {
        this.$router.push("/mycomment");
      }
    },
  },
};
</script>

<style>
.header {
  background: #fff url("../../assets/img/head.png") no-repeat;
  background-size: cover;
  position: relative;
  box-sizing: border-box;
  width: 100%;
  height: 50px;
  font-size: 22px;
  line-height: 50px;
  color: #fff;
}
.header .logo {
  float: left;
  width: 250px;
  text-align: center;
}
.user-info {
  float: right;
  padding-right: 45px;
  font-size: 16px;
  color: #fff;
}
.user-info .el-dropdown-link {
  display: inline-block;
  padding-top: 0px;
  padding-right: 50px;
  color: #fff;
  cursor: pointer;
  vertical-align: middle;
}
.user-info .user-logo {
  position: fixed;
  top: 5px;
  left: 1460px;
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.el-dropdown-menu__item {
  text-align: center;
}
/* .user-info .popper-dropdown {
  min-width: 120px;
  position: absolute !important;
  top: 35px !important;
  left: -45px !important;
  color: #000;
} */
</style>
