<template>
  <div>
    <el-dialog title="确认" :visible.sync="dialogVisible" width="30%">
      <span>是否修改帖子</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updatePost()"> 确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="确认" :visible.sync="dialogVisible1" width="30%">
      <span>是否删除评论</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="deleteComment(currectComment)">
          确 定
        </el-button>
      </span>
    </el-dialog>
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
      <el-col :span="4" :offset="10">
        <el-button
          type="primary"
          icon="el-icon-upload2"
          size="medium"
          @click="checkUpdatePost()"
          round
          >修改
        </el-button>
        <el-button @click="goBack()">返回</el-button>
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <div class="commentTable">
      <el-table
        :data="commentTable"
        :show-header="false"
        :key="Math.random()"
        :row-style="{ height: '1px' }"
        :cell-style="rowStyle"
        width="600px"
      >
        <el-table-column width="40">
          <template>
            <img class="user-logo" src="@/assets/img/user1.jpeg" />
          </template>
        </el-table-column>
        <el-table-column prop="sendName" width="120">
          <template slot-scope="scope">
            {{ scope.row.sendName | nameFormat }}
          </template>
        </el-table-column>
        <el-table-column prop="main" width="780"> </el-table-column>
        <el-table-column prop="commentDate" width="170">
          <template slot-scope="scope">
            {{ scope.row.commentDate | dateFormat }}
          </template>
        </el-table-column>
        <el-table-column width="90">
          <template slot-scope="scope">
            <el-button
              @click="checkDeleteComment(scope.row)"
              type="text"
              size="small"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
export default {
  components: {},
  props: [],
  data() {
    return {
      commentTable: null,
      dialogVisible: false,
      dialogVisible1: false,
      currectComment: null,
      formData: {
        postId: null,
        id: null,
        title: null,
        label: null,
        content: null,
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
    this.formData.postId = this.$route.query.postId;
    this.formData.id = this.$route.query.id;
    this.$axios
      .get(
        "/post/getOnePost/" +
          this.$route.query.id +
          "/" +
          this.$route.query.postId
      )
      .then((resp) => {
        this.formData = resp.data;
        var reg = new RegExp("<br>", "g");
        this.formData.content = this.formData.content.replace(reg, "\n");
      });

    this.$axios
      .get(
        "/comment/selectAll/1/10/" +
          this.formData.postId +
          "/" +
          this.formData.id
      )
      .then((resp) => {
        if (resp.data == "" || resp.data == null) {
          this.$message = "数据为空";
        } else {
          this.commentTable = resp.data;
        }
        console.log(resp.data);
      });
  },
  mounted() {},
  methods: {
    checkDeleteComment(row) {
      this.currectComment = row;
      this.dialogVisible1 = true;
    },
    checkUpdatePost() {
      this.dialogVisible = true;
    },
    updatePost() {
      this.dialogVisible = false;
      this.$refs.formData.validate((valid) => {
        if (valid) {
          console.log(this.formData.id);
          var i = true;
          var ret = this.formData.content;
          var result;
          this.formData.content = this.formData.content.replace(
            /\n|\r\n/g,
            "<br>"
          );
          this.$axios.post("/post/updatePost", this.formData).then((resp) => {
            if (resp.data == "success") {
              this.formData.content = ret;
              console.log(ret);
              this.$message("修改成功");
            } else {
              this.$message("修改失败");
            }
          });
        } else {
          this.$message("信息未填写完全");
        }
      });
    },
    deleteComment(row) {
      this.dialogVisible1 = false;
      console.log(row);
      this.$axios.post("/comment/deleteComment", row).then((resp) => {
        if (resp.data != "") {
          this.$message(resp.data);
          window.location.reload();
        } else {
          this.$message(resp.data);
        }
      });
    },
    goBack() {
      this.$router.go(-1);
    },
  },
  filters: {
    nameFormat(name) {
      return name;
    },
    dateFormat(date) {
      date = "" + date;
      let result;
      result = date.split(""); //输出为["1","2","3","4","5","6"]
      result.splice(4, 0, "/");
      result.splice(7, 0, "/");
      result.splice(10, 0, "/");
      result.splice(13, 0, ":");
      result.splice(16, 0, ":");
      var dt = result.join("");
      return dt;
    },
  },
};
</script>
<style>
.commentTable {
  border: 1px solid rgba(0, 0, 0, 0.288);
  width: 1200px;
  box-shadow: 0 2px 12px 0 rgba(113, 198, 234, 0.471);
  border-radius: 4px;
  margin-top: 10px;
}
.title-article {
  font-size: 35px;
  margin-bottom: 15px;
  color: #020305;
}
</style>
