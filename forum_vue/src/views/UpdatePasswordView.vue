<template>
  <div>
    <el-dialog title="确认" :visible.sync="dialogVisible" width="30%">
      <span>是否修改密码</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm()"> 确 定</el-button>
      </span>
    </el-dialog>
    <div class="ms-login">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="0px"
      >
        <el-form-item prop="passwordOld">
          <el-input
            v-model="ruleForm.passwordOld"
            placeholder="请输入原密码"
            maxlength="10"
            show-word-limit
            type="password"
          >
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="ruleForm.password"
            type="password"
            maxlength="10"
            show-word-limit
            placeholder="新密码"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password1">
          <el-input
            v-model="ruleForm.password1"
            type="password"
            maxlength="10"
            show-word-limit
            placeholder="再次输入新密码"
          ></el-input>
        </el-form-item>
        <el-row>
          <el-col span="3" :offset="8">
            <el-form-item>
              <el-button type="primary" @click="dialogVisible = true">
                确认
              </el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
/*eslint-disable*/
export default {
  data() {
    return {
      dialogVisible: false,
      ruleForm: {
        passwordOld: "",
        password: "",
        password1: "",
      },
      rules: {
        passwordOld: [{ required: true, message: "请输入原密码", trigger: "blur" }],
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
  created() {
    this.$axios
      .get("/user/getMessage/" + sessionStorage.getItem("userId"))
      .then((resp) => {
        this.userData = resp.data;
      });
  },
  methods: {
    resetForm() {
      this.$refs.ruleFrom.resetFields();
    },
    submitForm() {
      this.dialogVisible = false;
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
        if (this.ruleForm.passwordOld != this.userData.password) {
            this.$message("密码错误");
            return ;
        }
        this.userData.password = this.ruleForm.password;
        //console.log(this.userData);
        const ret = await this.$axios.post("/user/updateMessage", this.userData);
          if (ret.data =="success") {
          this.$message("修改密码成功");
        } else {
            this.$message("修改密码失败");
        }

      });
    },
  },
};
</script>

<style scoped>
.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 300px;
  border-radius: 5px;
  height: 240px;
  margin: -150px 0 0 -190px;
  padding: 40px;
}
</style>
