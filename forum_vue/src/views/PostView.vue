<template>
  <div>
    <el-button @click="goBack()" class="backButton">返回</el-button>
    <div class="title-article">{{ tableData.title }}</div>
    <el-row :gutter="0" class="poster_message">
      <el-col :span="2" :offset="8">
        <el-tag type="info">作者:{{ posterTable.name }}</el-tag>
      </el-col>
      <el-col :span="2" :offset="0">
        <el-tag>{{ tableData.postDate | dateFormat }}</el-tag>
      </el-col>
      <el-col :span="1" :offset="1">
        <el-tag type="success" size="mini">{{ tableData.label }}</el-tag>
      </el-col>
    </el-row>
    <el-row :gutter="0">
      <el-col :span="22" :offset="1">
        <div class="post_content" v-html="tableData.content"></div>
      </el-col>
    </el-row>
    <el-row :gutter="0">
      <el-col :span="1" :offset="10">
        <el-button
          type="primary"
          icon="el-icon-star-off"
          circle
          :plain="this.likeData.collection"
          size="mini"
          @click="changeCollection()"
        ></el-button>
      </el-col>
      <el-col :span="1" :offset="0">
        <el-button
          type="primary"
          icon="el-icon-magic-stick"
          circle
          :plain="this.likeData.likes"
          size="mini"
          @click="changeLike()"
        ></el-button>
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <el-row :gutter="0">
      <el-col :span="18" :offset="0">
        <el-input
          v-model="comment.main"
          type="textarea"
          placeholder="请输入评论"
          :maxlength="100"
          :show-word-limit="true"
          :autosize="{ minRows: 1, maxRows: 2 }"
        ></el-input>
      </el-col>
      <el-col :span="1" :offset="1">
        <el-button @click="addComment()" size="small">评论</el-button>
      </el-col>
    </el-row>
    <el-divider><div class="comment_line">评论区</div></el-divider>
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
        <el-table-column prop="main" width="870"> </el-table-column>
        <el-table-column prop="commentDate" width="170">
          <template slot-scope="scope">
            {{ scope.row.commentDate | dateFormat }}
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-row>
      <el-col :span="8" :offset="8">
        <el-pagination
          background
          @current-change="page"
          :current-page.sync="currentPage"
          :page-size="10"
          layout="prev, pager, next, jumper"
          :total="this.total"
        >
        </el-pagination>
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
      commentTable: null,
      posterTable: {
        name: null,
      },
      total: null,
      currentPage: 1,
      comment: {
        senderId: null,
        posterId: null,
        postId: null,
        commentDate: null,
        main: null,
        sendName: null,
      },
      likeData: {
        senderId: null,
        posterId: null,
        postId: null,
        likes: true,
        collection: true,
      },
      tableData: {
        postId: null,
        id: null,
        title: null,
        label: null,
        content: null,
        postDate: null,
      },
    };
  },
  computed: {},
  watch: {},
  created() {
    //let requestPram = JSON.parse(this.$route.query.requestPram);
    this.tableData.postId = this.$route.query.postId;
    this.tableData.id = this.$route.query.id;
    this.comment.senderId = sessionStorage.getItem("userId");
    this.comment.postId = this.$route.query.postId;
    this.comment.posterId = this.tableData.id;
    this.comment.sendName = sessionStorage.getItem("userName");
    this.$axios
      .get(
        "/post/getOnePost/" + this.tableData.id + "/" + this.tableData.postId
      )
      .then((resp) => {
        this.tableData = resp.data;
        console.log(this.tableData.content);
      });
    this.$axios
      .get(
        "like/getLike/" +
          this.tableData.postId +
          "/" +
          this.tableData.id +
          "/" +
          this.comment.senderId
      )
      .then((resp) => {
        if (resp.data != "") this.likeData = resp.data;
        else {
          this.likeData.postId = this.tableData.postId;
          this.likeData.posterId = this.tableData.id;
          this.likeData.senderId = this.comment.senderId;
        }
        //console.log(this.likeData);
      });
    this.$axios
      .get(
        "/comment/selectAll/1/10/" +
          this.tableData.postId +
          "/" +
          this.tableData.id
      )
      .then((resp) => {
        if (resp.data === "") this.$message = "数据为空";
        else {
          this.$axios
            .get(
              "/comment/total/" +
                this.tableData.postId +
                "/" +
                this.tableData.id
            )
            .then((resp) => {
              this.total = resp.data;
            });
        }
        this.commentTable = resp.data;
        console.log(resp.data);
      });
    this.$axios
      .get("/user/getMessage/" + sessionStorage.getItem("userId"))
      .then((resp) => {
        this.posterTable = resp.data;
      });
  },

  mounted() {},
  methods: {
    page(currentPage) {
      this.$axios
        .get(
          "/comment/selectAll/" +
            currentPage +
            "/10/" +
            this.tableData.postId +
            "/" +
            this.tableData.id
        )
        .then((resp) => {
          if (resp.data != "") {
            this.commentTable = resp.data;
          }
        });
    },
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
    goBack() {
      this.$router.go(-1);
    },
    changeLike() {
      this.likeData.likes = !this.likeData.likes;
      console.log(this.likeData);
      this.$axios.post("/like/updateLike", this.likeData).then((resp) => {
        if (resp.data == "success") {
          if (this.likeData.likes === false) this.$message("点赞成功");
          else this.$message("取消点赞");
        }
      });
    },
    changeCollection() {
      this.likeData.collection = !this.likeData.collection;
      console.log(this.likeData);
      this.$axios.post("/like/updateLike", this.likeData).then((resp) => {
        if (resp.data == "success") {
          if (this.likeData.collection === false) this.$message("收藏成功");
          else this.$message("取消收藏");
        }
      });
    },
    addComment() {
      this.comment.commentDate = this.formatDateTime(new Date());
      console.log(this.comment);
      if (this.comment.main != null && this.comment.main != "") {
        this.$axios.post("/comment/addComment", this.comment).then((resp) => {
          if (resp.data == "success") {
            this.$message("评论成功");
            window.location.reload();
          } else {
            this.$message("评论失败");
          }
        });
      } else {
        this.$message("未输入评论内容");
      }
    },

    deleteComment(row) {
      console.log(row);
      this.$axios.post("/comment/deleteComment", row).then((resp) => {
        if (resp.data != "") {
          this.$message(resp.data);
          window.location.reload();
        }
      });
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
.commentTable {
  border: 1px solid rgba(0, 0, 0, 0.288);
  width: 1200px;
  box-shadow: 0 2px 12px 0 rgba(113, 198, 234, 0.471);
  border-radius: 4px;
  margin-top: 10px;
}
.backButton {
  position: absolute;
  left: 1px;
  top: 1px;
}
.title-article {
  text-align: center;
  font-size: 35px;
  margin-bottom: 15px;
  color: #020305;
}
.poster_message {
  background-color: rgba(240, 248, 255, 0.548);
  width: 100%;
}
.user-logo {
  width: 22px;
  height: 22px;
  border-radius: 50%;
}
.post_content {
  margin: 50px;
  font-size: 19px;
  /* font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif; */
}
.comment_line {
  background-color: rgba(255, 255, 255, 0.2);
}
</style>
