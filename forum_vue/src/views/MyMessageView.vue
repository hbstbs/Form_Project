<template>
  <div>
    <el-dialog title="确认" :visible.sync="dialogVisible" width="30%">
      <span>是否修改个人信息</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm()"> 确 定</el-button>
      </span>
    </el-dialog>
    <el-form
      ref="elForm"
      :model="formData"
      :rules="rules"
      size="medium"
      label-width="100px"
    >
      <el-form-item label="账号" prop="id">
        <el-input
          v-model="formData.id"
          disabled
          show-word-limit
          clearable
          :style="{ width: '100%' }"
        ></el-input>
      </el-form-item>
      <el-form-item label="用户名" prop="name">
        <el-input
          v-model="formData.name"
          placeholder="请输入用户名"
          :maxlength="15"
          show-word-limit
          clearable
          :style="{ width: '100%' }"
        ></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select
          v-model="formData.sex"
          placeholder="请选择性别"
          clearable
          :style="{ width: '100%' }"
        >
          <el-option
            v-for="(item, index) in sexPtions"
            :key="index"
            :label="item.label"
            :value="item.value"
            :disabled="item.disabled"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input
          v-model="formData.email"
          placeholder="请输入邮箱"
          :maxlength="25"
          show-word-limit
          clearable
          prefix-icon="el-icon-s-promotion"
          :style="{ width: '100%' }"
        ></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="telphone">
        <el-input
          v-model="formData.telphone"
          placeholder="请输入手机号"
          :maxlength="11"
          show-word-limit
          clearable
          prefix-icon="el-icon-mobile"
          :style="{ width: '100%' }"
        ></el-input>
      </el-form-item>
      <el-form-item label="爱好" prop="hobby">
        <el-input
          v-model="formData.hobby"
          placeholder="请输入爱好"
          maxlength="5"
          show-word-limit
          clearable
          :style="{ width: '100%' }"
        ></el-input>
      </el-form-item>
      <el-form-item label="个人介绍" prop="introduction">
        <el-input
          v-model="formData.introduction"
          placeholder="请输入个人介绍"
          clearable
          maxlength="100"
          show-word-limit
          :style="{ width: '100%' }"
        >
        </el-input>
      </el-form-item>
      <el-form-item size="large">
        <el-button type="primary" @click="dialogVisible = true">提交</el-button>
        <el-button @click="resetForm()">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  components: {},
  props: [],
  data() {
    return {
      dialogVisible: null,
      emailOld: null,
      formData: {
        id: null,
        name: "",
        sex: undefined,
        email: "",
        telphone: "",
        hobby: undefined,
        introduction: undefined,
      },
      rules: {
        name: [
          {
            required: true,
            message: "请输入用户名",
            trigger: "blur",
          },
        ],
        field106: [
          {
            required: true,
            message: "请选择性别",
            trigger: "change",
          },
        ],
        email: [
          {
            required: true,
            message: "请输入邮箱",
            trigger: "blur",
          },
          {
            pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            message: "邮箱格式错误",
            trigger: "blur",
          },
        ],
        telphone: [
          {
            required: true,
            message: "请输入手机号",
            trigger: "blur",
          },
          {
            pattern: /^1(3|4|5|7|8|9)\d{9}$/,
            message: "手机号格式错误",
            trigger: "blur",
          },
        ],
        hobby: [
          {
            required: true,
            message: "请输入爱好",
            trigger: "blur",
          },
        ],
        introduction: [
          {
            required: true,
            message: "请输入个人介绍",
            trigger: "blur",
          },
        ],
      },
      sexPtions: [
        {
          label: "男",
          value: "男",
        },
        {
          label: "女",
          value: "女",
        },
        {
          label: "保密",
          value: "保密",
        },
      ],
    };
  },
  computed: {},
  watch: {},
  created() {
    this.$axios
      .get("/user/getMessage/" + sessionStorage.getItem("userId"))
      .then((resp) => {
        this.formData = resp.data;
        this.emailOld = this.formData.email;
      });
  },
  mounted() {},
  methods: {
    submitForm() {
      this.$refs["elForm"].validate(async (valid) => {
        if (!valid) {
          this.$message("信息未填写完全或填写错误");
          return;
        }
        if (this.emailOld != this.formData.email) {
          const checkEmail = await this.$axios.post(
            "/user/checkEmail" + this.formData.email
          );
          if (checkEmail === "fail") {
            this.$message("邮箱已存在");
            return;
          }
        }
        console.log(this.formData);
        this.$axios.post("/user/updateMessage", this.formData).then((resp) => {
          this.$message(resp.data);
        });
      });
      this.dialogVisible = false;
    },
    resetForm() {
      this.$refs["elForm"].resetFields();
    },
  },
};
</script>
<style></style>
