<template>
  <div>
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
            <img class="user-logo" src="@/assets/img/user3.jpeg" />
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
              @click="deleteComment(scope.row)"
              type="text"
              size="small"
              >删除</el-button
            >
            <el-button @click="read(scope.row)" type="text" size="small"
              >详情</el-button
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
    };
  },
  computed: {},
  watch: {},
  created() {
    this.$axios
      .get("/comment/getMyComment/" + sessionStorage.getItem("userId"))
      .then((resp) => {
        if (resp.data == "") this.$message = "数据为空";
        this.commentTable = resp.data;
        //console.log(resp.data);
      });
  },

  mounted() {},
  methods: {
    deleteComment(row) {
      console.log(row);
      this.$axios.post("/comment/deleteComment", row).then((resp) => {
        if (resp.data != "") {
          this.$message(resp.data);
          window.location.reload();
        }
      });
    },
    read(row) {
      console.log(row.posterId);
      console.log(row.postId);
      this.$router.push({
        path: "/detailpost",
        query: {
          //requestPram: JSON.stringify({ id: row.id, postId: row.postId }),
          id: row.posterId,
          postId: row.postId,
        },
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
.commentTable {
  border: 1px solid rgba(0, 0, 0, 0.288);
  width: 1200px;
  box-shadow: 0 2px 12px 0 rgba(113, 198, 234, 0.471);
  border-radius: 4px;
  margin-top: 10px;
}
</style>
