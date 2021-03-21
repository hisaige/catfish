<template>
  <div class="app-container">
    <el-table v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%">
      <el-table-column align="center" label="用户名称" width="80">
        <template slot-scope="{row}">
          <span>{{ row.username }}</span>
        </template>
      </el-table-column>

      <el-table-column width="180px" align="center" label="IP">
        <template slot-scope="{row}">
          <span>{{ row.ipAddr }}</span>
        </template>
      </el-table-column>

      <el-table-column width="150px" align="center" label="登录地点">
        <template slot-scope="{row}">
          <span>{{ row.ipLocation }}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="120px" align="center" label="客户端">
        <template slot-scope="{row}">
          <span>{{ row.browser }}</span>
        </template>
      </el-table-column>

      <el-table-column width="180px" align="center" label="登录时间">
        <template slot-scope="{row}">
          <span>{{ row.loginTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>

      <el-table-column class-name="status-col" align="center" label="最近访问时间" width="180">
        <template slot-scope="{row}">
          <span>{{ row.lastAccessTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="120">
        <template slot-scope="{row}">
          <el-button
            type="danger"
            size="small"
            @click="doForceLogout(row)"
          >强退</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.pageNum" :limit.sync="listQuery.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { onlineUsers, forceLogout } from '@/api/ums/onlineUser'
import Pagination from '@/components/Pagination'

export default {
  name: 'OnlineUserTable',
  components: { Pagination },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        pageNum: 1,
        pageSize: 20,
        username: undefined,
        ipAddr: undefined
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      onlineUsers(this.listQuery).then(response => {
        this.list = response.msg.list
        this.total = response.msg.total

        // Just to simulate the time of the request
        this.listLoading = false
      })
    },
    doForceLogout(row) {
      const tokenPrefix = row.tokenPrefix
      const username = row.username
      const logOutObj = { tokenPrefix, username }
      this.$confirm('是否强退用户?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用http请求登出方法
        forceLogout(logOutObj).then(response => {
          if (response.code === 0) {
            this.$message({
              message: '强退用户成功',
              type: 'success'
            })
            this.getList()
          } else {
            this.$message({
              message: response.desc,
              type: 'error'
            })
          }
        })
      }).catch(() => {})
    },
    confirmEdit(row) {
      row.edit = false
      row.originalTitle = row.title
      this.$message({
        message: 'The title has been edited',
        type: 'success'
      })
    }
  }
}
</script>

<style scoped>
.edit-input {
  padding-right: 100px;
}
.cancel-btn {
  position: absolute;
  right: 15px;
  top: 10px;
}
</style>
