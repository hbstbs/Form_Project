<template>
  <div class="login-wrap">
    <div class="ms-title">PJM论坛</div>
    <div class="ms-login">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="0px"
      >
        <el-form-item prop="account">
          <el-input v-model="ruleForm.account" placeholder="请输入邮箱">
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="ruleForm.password"
            type="password"
            maxlength="10"
            show-word-limit
            placeholder="密码"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password1">
          <el-input
            v-model="ruleForm.password1"
            type="password"
            maxlength="10"
            show-word-limit
            placeholder="再次输入密码"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">注册</el-button>
          <el-button @click="goBack()">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
/*eslint-disable*/
export default {
  data() {
    return {

      ruleForm: {
        account: "",
        password: "",
        password1: "",
      },
      rules: {
        account: [{ required: true, message: "请输入邮箱", trigger: "blur" },          
        {
            pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            message: "邮箱格式错误",
            trigger: "blur",
          },
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        password1: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
      userData: {
        email: null,
        password: null,
        sex: null,
        id: null,
        telphone: null,
        hobby: null,
        introduction: null,
        name: null,
      }
    };
  },
  components: {},
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    resetForm() {["elForm"]
      this.$refs.ruleFrom.resetFields();
    },
    submitForm() {

      this.$refs.ruleForm.validate(async (valid) => {
        if (!valid) {
          this.$message("信息未填写完全");
          return;
        }
        //前后端交互
        console.log(this.ruleForm);
        if (this.ruleForm.password != this.ruleForm.password1) {
            this.$message("密码不一致");
            return ;
        }
        const checkEmail = await this.$axios.post("/user/checkEmail/" + this.ruleForm.account);
        if(checkEmail === "fail"){
          this.$message("邮箱已存在");
          return;
        }
        this.userData.email = this.ruleForm.account;
        this.userData.password = this.ruleForm.password;
        console.log(this.userData);
        const ret = await this.$axios.post("/user/register/", this.userData);
        console.log(ret.data);
        console.log(ret.status);
          if (ret.data =="success") {
          this.$message("注册成功");
            if (ret.status === 200) {
              console.log(ret.data);
            this.$router.push("/login");
          }else{
            this.$message("注册失败");
          }
        }

      });
    },
  },
};
</script>

<style scoped>
.login-wrap {
  background: #fff url("../assets/img/background2.png") no-repeat;
  position: relative;
  width: 100%;
  height: 100%;
}
.ms-title {
  position: absolute;
  top: 50%;
  width: 100%;
  margin-top: -230px;
  text-align: center;
  font-size: 30px;
  color: #fff;
}
.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 300px;
  border-radius: 5px;
  height: 240px;
  margin: -150px 0 0 -190px;
  padding: 40px;
  background: #fff;
}
.code {
  width: 112px;
  height: 35px;
  border: 1px solid #ccc;
  float: right;
  border-radius: 2px;
}
.validate-code {
  width: 136px;
  float: left;
}
</style>
