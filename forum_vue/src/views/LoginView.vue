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
          <el-input v-model="ruleForm.account" placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="ruleForm.password"
            type="password"
            placeholder="密码"
          ></el-input>
        </el-form-item>
        <el-form-item prop="validate">
          <el-input
            class="validate-code"
            v-model="ruleForm.validate"
            placeholder="验证码"
          ></el-input>
          <div class="code" @click="refreshCode">
            <s-identify :identifyCode="identifyCode"></s-identify>
          </div>
        </el-form-item>
        <template>
          <el-radio v-model="radio" label="1">账号登录</el-radio>
          <el-radio v-model="radio" label="2">邮箱登录</el-radio>
        </template>
        <el-form-item>
          <el-button type="primary" @click="submitForm">登录</el-button>
          <el-button @click="goRegister()">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
/*eslint-disable*/
export default {
  data() {
    var validateV = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入验证码"));
      } else if (value !== this.identifyCode) {
        callback(new Error("验证码输入错误"));
      } else {
        callback();
      }
    };
    return {
      identifyCodes: "1234567890",
      identifyCode: "",
      radio: '2',
      ruleForm: {
        account: "",
        password: "",
        validate: "",
      },
      rules: {
        account: [{ required: true, message: "请输入账号或邮箱", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        validate: [{ validator: validateV, trigger: "blur" }],
      },
      userData: {
        id: null,
        password: null,
      }
    };
  },
  components: {},
  mounted() {
    this.makeCode(this.identifyCodes, 4);
  },
  methods: {
    goRegister() {
      this.$router.push("/register");
    },
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min);
    },
    makeCode(s, l) {
      for (let i = 0; i < l; i++)
        this.identifyCode += s[this.randomNum(0, s.length)];
    },
    refreshCode() {
      this.identifyCode = "";
      this.makeCode(this.identifyCodes, 4);
    },
    resetForm() {
      this.$refs.ruleFrom.resetFields();
    },
    submitForm() {

      this.$refs.ruleForm.validate(async (valid) => {
        if (!valid) {
          return;
        }
        if(this.radio === '1'){
          const ret = await this.$axios.get("/user/login/"+this.ruleForm.account + "/" +this.ruleForm.password);
          if (ret.data != "") {
          this.$message("登录成功");
          console.log(ret.data);
            if (ret.status === 200) {
              console.log(ret.data);
            sessionStorage.setItem("userId", ret.data.id);
            sessionStorage.setItem("userName", ret.data.name);
            sessionStorage.setItem("login", 1);
            this.$router.push("/post");
          }
        } else if (ret.data == "" ) {
          this.$message.error("账号密码错误");
        }
        } else if(this.radio === '2') {
          const ret = await this.$axios.get("/user/loginByEmail/"+this.ruleForm.account + "/" +this.ruleForm.password);
          console.log(ret.data);
          if (ret.data != "") {
          this.$message("登录成功");
            if (ret.status === 200) {
              console.log(ret.data);
            sessionStorage.setItem("userId", ret.data.id);
            sessionStorage.setItem("userName", ret.data.name);
            sessionStorage.setItem("user_sex", ret.data.sex);
            sessionStorage.setItem("login", 1);
            this.$router.push("/post");
          }
        } else if (ret.data == "" ) {
          this.$message.error("邮箱密码错误");
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
  background: rgb(255, 255, 255);
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
