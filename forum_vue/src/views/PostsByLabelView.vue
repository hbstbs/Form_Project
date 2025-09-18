<template>
  <div style="width: 100%">
    <el-row>
      <el-col :span="16" :offset="1">
        <el-input
          v-model="searchText"
          placeholder="请输入搜索内容搜索"
          clearable
          prefix-icon="el-icon-search"
          :style="{ width: '100%' }"
        ></el-input>
      </el-col>
      <el-col :span="4" :offset="2">
        <el-button
          type="primary"
          icon="el-icon-upload2"
          size="medium"
          @click="searchPost()"
          round
          >搜索
        </el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="4" :offset="0">
        <el-button type="text" size="mini" @click="orderDESC()" round
          >时间倒序
        </el-button>
        <el-button type="text" size="mini" @click="orderASC()" round
          >时间正序
        </el-button>
      </el-col>
    </el-row>
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
        </template>
      </el-table-column>
    </el-table>
    <el-row>
      <el-col :span="8" :offset="7">
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
  methods: {
    page(currentPage) {
      if (this.search === "" || this.search == null) {
        this.$axios
          .get(
            "/post/selectAllByLabel/" +
              this.Label +
              "/" +
              currentPage +
              "/10/" +
              this.DESC
          )
          .then((resp) => {
            if (resp.data != "") {
              this.tableData = resp.data;
            }
          });
      } else {
        console.log(this.search);
        this.$axios
          .get(
            "/post/selectAllByLabel/" +
              this.search +
              "/" +
              this.Label +
              "/" +
              currentPage +
              "/10/" +
              this.DESC
          )
          .then((resp) => {
            if (resp.data != "") {
              this.tableData = resp.data;
            }
          });
      }
    },
    orderDESC() {
      this.DESC = true;
      this.page(1);
    },
    orderASC() {
      this.DESC = false;
      this.page(1);
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
          if (resp.data != "") {
            this.$message(resp.data);
            window.location.reload();
          }
        });
    },

    updatePost(row) {
      this.$router.push({
        path: "/updatepost",
        query: {
          parm: JSON.stringify(row),
        },
      });
    },

    searchPost() {
      console.log(this.search);
      this.search = this.searchText;
      if (this.search != "" && this.search != null) {
        this.$axios
          .get(
            "/post/selectAllByLabel/" +
              this.search +
              "/" +
              this.Label +
              "/1/10/" +
              this.DESC
          )
          .then((resp) => {
            if (resp.data != "") {
              this.tableData = resp.data;
              console.log(this.tableData);
            } else {
              this.$message("搜索结果为空");
              console.log(resp.data);
            }
          });
        this.$axios
          .get("/post/totalByLabel/" + this.search + "/" + this.Label)
          .then((resp) => {
            this.total = resp.data;
            console.log("ss " + resp.data);
          });
      } else {
        this.$axios.get("/post/selectAll/1/10/" + this.DESC).then((resp) => {
          this.tableData = resp.data;
          console.log(resp.data);
        });
      }
    },
  },
  created() {
    this.Label = this.$route.query.Label;
    console.log(this.$route.path);
    this.$axios
      .get("/post/selectAllByLabel/" + this.Label + "/1/10/" + this.DESC)
      .then((resp) => {
        if (resp.data == "") this.$message = "数据为空";
        this.tableData = resp.data;
        console.log(resp.data);
      });
    this.$axios.get("/post/totalByLabel/" + this.Label).then((resp) => {
      this.total = resp.data;
      console.log(this.total);
    });
  },

  data() {
    return {
      tableData: null,
      Label: null,
      search: "",
      searchText: null,
      DESC: true,
      total: 10,
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
<style>
.table {
  margin-bottom: 10px;
  margin-top: 10px;
}
</style>
