<template>
  <div>
    <el-row :gutter="20">
      <el-form
        ref="formData"
        :model="formData"
        :rules="rules"
        size="medium"
        label-width="55px"
      >
        <el-col :span="22" :offset="1">
          <el-form-item label="标题" prop="title">
            <el-input
              v-model="formData.title"
              placeholder="请输入标题（不超过20字）"
              :maxlength="20"
              clearable
              prefix-icon="el-icon-s-order"
              :style="{ width: '100%' }"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="22" :offset="1">
          <el-form-item label="标签" prop="label">
            <el-select
              v-model="formData.label"
              placeholder="请选择标签"
              clearable
              :style="{ width: '100%' }"
            >
              <el-option
                v-for="(item, index) in labelOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
                :disabled="item.disabled"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="22" :offset="1">
          <el-form-item label="内容" prop="content">
            <el-input
              v-model="formData.content"
              type="textarea"
              placeholder="请输入内容"
              :maxlength="2000"
              show-word-limit
              :autosize="{ minRows: 4, maxRows: 15 }"
              :style="{ width: '100%' }"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-form>
      <el-col :span="2" :offset="11">
        <el-button
          type="primary"
          icon="el-icon-upload2"
          size="medium"
          @click="addPost('formData')"
          round
          >提交
        </el-button>
      </el-col>
    </el-row>
  </div>
</template>
<script>
export default {
  components: {},
  props: [],
  data() {
    return {
      formData: {
        postId: null,
        id: null,
        title: undefined,
        label: "游戏",
        content: "",
        postDate: null,
      },
      rules: {
        title: [
          {
            required: true,
            message: "请输入标题",
            trigger: "blur",
          },
        ],
        label: [
          {
            required: true,
            message: "请选择标签",
            trigger: "change",
          },
        ],
        content: [
          {
            required: true,
            message: "请输入内容",
            trigger: "blur",
          },
        ],
      },
      labelOptions: [
        {
          label: "游戏",
          value: "游戏",
        },
        {
          label: "政治",
          value: "政治",
        },
        {
          label: "旅游",
          value: "旅游",
        },
        {
          label: "小说",
          value: "小说",
        },
        {
          label: "动漫",
          value: "动漫",
        },
        {
          label: "娱乐",
          value: "娱乐",
        },
      ],
    };
  },
  computed: {},
  watch: {},
  created() {
    this.formData.id = sessionStorage.getItem("userId");
  },
  mounted() {},
  methods: {
    formatDateTime(date) {
      var y = date.getFullYear();
      var m = date.getMonth() + 1;
      m = m < 10 ? "0" + m : m;
      var d = date.getDate();
      d = d < 10 ? "0" + d : d;
      var h = date.getHours();
      h = h < 10 ? "0" + h : h;
      var minute = date.getMinutes();
      minute = minute < 10 ? "0" + minute : minute;
      var second = date.getSeconds();
      second = second < 10 ? "0" + second : second;
      var dateInt = new Number(y + m + d + h + minute + second);
      //return y + "-" + m + "-" + d + " " + h + ":" + minute + ":" + second;
      return dateInt;
    },

    addPost(formData) {
      console.log(this.formData);
      this.formData.postDate = this.formatDateTime(new Date());
      var ret = this.formData.content;
      this.formData.content = this.formData.content.replace(/\n|\r\n/g, "<br>");
      console.log(this.formData.postDate);
      this.$refs[formData].validate((valid) => {
        if (valid) {
          this.$axios.post("/post/addPost", this.formData).then((resp) => {
            if (resp.data == "success") {
              this.$message("添加成功！");
              this.$router.push("/myposts");
            }
          });
        } else {
          this.$message("添加失败！");
          return false;
        }
      });
      this.formData.content = ret;
    },
  },
};
</script>
<style></style>
