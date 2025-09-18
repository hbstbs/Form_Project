<template>
  <div style="width: 100%">
    <el-dialog title="确认" :visible.sync="dialogVisible" width="30%">
      <span>是否删除帖子</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="deletePost(currectPost)">
          确 定
        </el-button>
      </span>
    </el-dialog>
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
          <el-button @click="updatePost(scope.row)" type="text" size="small"
            >修改</el-button
          >
          <el-button
            @click="checkDeletePost(scope.row)"
            type="text"
            size="small"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  methods: {
    checkDeletePost(row) {
      this.currectPost = row;
      this.dialogVisible = true;
    },
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

    updatePost(row) {
      console.log(row);
      this.$router.push({
        path: "/updatepost",
        query: {
          id: row.id,
          postId: row.postId,
        },
      });
    },
  },
  created() {
    this.userId = sessionStorage.getItem("userId");
    this.$axios.get("/post/selectByPosterId/" + this.userId).then((resp) => {
      if (resp.data == null) this.$message = "数据为空";
      this.tableData = resp.data;
      console.log(resp.data);
    });
  },

  data() {
    return {
      tableData: null,
      search: null,
      currectPost: null,
      dialogVisible: false,
      total: 10,
      currentPage: 1,
      userId: null,
      currentPostKey: {
        postId: null,
        id: null,
      },
    };
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
