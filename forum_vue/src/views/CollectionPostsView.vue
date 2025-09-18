<template>
  <div style="width: 100%">
    <el-table
      :data="tableData"
      border
      style="width: 100%"
      :key="Math.random()"
      class="table"
    >
      <el-table-column prop="title" label="标题" width="450"> </el-table-column>
      <el-table-column prop="postDate" label="时间" width="350">
        <template slot-scope="scope">
          {{ scope.row.postDate | dateFormat }}
        </template>
      </el-table-column>
      <el-table-column prop="label" label="标签" width="150"> </el-table-column>
      <el-table-column fixed="right" label="操作" width="200">
        <template slot-scope="scope">
          <el-button @click="read(scope.row)" type="text" size="small"
            >查看</el-button
          >
          <el-button
            @click="changeCollection(scope.row)"
            type="text"
            size="small"
            >移除收藏夹</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  methods: {
    read(row) {
      this.$router.push({
        path: "/detailpost",
        query: {
          //requestPram: JSON.stringify({ id: row.id, postId: row.postId }),
          id: row.id,
          postId: row.postId,
        },
      });
    },

    deletePost(row) {
      this.$axios
        .get("/post/deletePost/" + row.id + "/" + row.postId)
        .then((resp) => {
          if (resp.data != null) {
            this.$message(resp.data);
            window.location.reload();
          }
        });
    },
    changeCollection(row) {
      this.$axios
        .get(
          "like/getLike/" +
            row.postId +
            "/" +
            row.id +
            "/" +
            sessionStorage.getItem("userId")
        )
        .then((resp) => {
          this.currentLikeData = resp.data;
        });
      this.currentLikeData.collection = !this.currentLikeData.collection;
      this.currentLikeData.senderId = sessionStorage.getItem("userId");
      this.currentLikeData.posterId = row.id;
      this.currentLikeData.postId = row.postId;
      console.log(this.currentLikeData);
      this.$axios
        .post("/like/updateLike", this.currentLikeData)
        .then((resp) => {
          if (resp.data == "success") {
            this.$message("取消收藏成功");
            window.location.reload();
          }
        });
    },
  },
  created() {
    this.userId = sessionStorage.getItem("userId");
    this.$axios.get("post/selectByCollection/" + this.userId).then((resp) => {
      if (resp.data == null) this.$message = "数据为空";
      this.tableData = resp.data;
      console.log(resp.data);
    });
  },

  data() {
    return {
      tableData: null,
      search: null,
      userId: null,
      total: 10,
      currentLikeData: {
        senderId: null,
        posterId: null,
        postId: null,
        likes: false,
        collection: false,
      },
      currentPage: 1,
      currentPostKey: {
        postId: null,
        id: null,
      },
    };
  },
  filters: {
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
