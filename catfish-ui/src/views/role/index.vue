<template>
  <div class="filter-container">
    <el-input
      v-model="listQuery.name"
      placeholder="角色名称"
      style="width: 200px;"
      class="filter-item"
      @keyup.enter.native="handleFilter"
    />
    <el-input
      v-model="listQuery.description"
      placeholder="角色编码"
      style="width: 200px;"
      class="filter-item"
      @keyup.enter.native="handleFilter"
    />
    <el-select
      v-model="listQuery.status"
      placeholder="启用状态"
      clearable
      style="width: 110px; margin-right: 20px;"
      class="filter-item"
    >
      <el-option
        v-for="item in statusOptions"
        :key="item.enable"
        :label="item.label"
        :value="item.enable"
      />
    </el-select>
    <el-button
      v-waves
      class="filter-item"
      type="primary"
      icon="el-icon-search"
      @click="handleFilter"
    >
      查询
    </el-button>
    <el-button
      class="filter-item"
      style="margin-left: 10px;"
      type="primary"
      icon="el-icon-edit"
      @click="handleCreate"
    >
      新增
    </el-button>
    <el-button
      v-waves
      :loading="downloadLoading"
      class="filter-item"
      type="primary"
      icon="el-icon-download"
      @click="handleDownload"
    >
      导出
    </el-button>

    <div>
      <el-table
        v-loading="listLoading"
        :data="list"
        border
        fit
        highlight-current-row
        style="width: 100%"
      >
        <el-table-column
          align="center"
          label="角色名称"
          width="120"
        >
          <template slot-scope="{row}">
            <span>{{ row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column
          align="center"
          label="角色编码"
          width="150"
        >
          <template slot-scope="{row}">
            <span>{{ row.roleCode }}</span>
          </template>
        </el-table-column>

        <el-table-column
          align="center"
          label="角色描述"
        >
          <template slot-scope="{row}">
            <span>{{ row.description }}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="状态"
          align="center"
          width="80"
        >
          <template slot-scope="{row}">
            <span
              v-if="row.status == 0"
              class="link-type"
            >禁用</span>
            <span
              v-if="row.status == 1"
              class="link-type"
            >启用</span>
          </template>
        </el-table-column>

        <el-table-column
          label="操作"
          align="center"
          width="180"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="{row,$index}">
            <el-button
              type="primary"
              size="mini"
              @click="handleEdit(row)"
            >
              编辑
            </el-button>
            <!-- <router-link :to="'/user/edit/' + row.id">
              <el-button size="mini" type="primary">编辑</el-button>
          </router-link> -->
            <el-button
              v-if="row.status!='deleted'"
              size="mini"
              type="danger"
              @click="handleDelete(row,$index)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="listQuery.page"
        :limit.sync="listQuery.limit"
        @pagination="getList"
      />
    </div>
  </div>
</template>

<script>
import { fetchPage, addRole, editRole, deleteRole } from '@/api/ums/role'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // 分页插件

export default {
  name: 'UserIndex',
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    },
    typeFilter(type) {
      return calendarTypeKeyValue[type]
    }
  },
  data() {
    return {
      downloadLoading: false,
      dialogLoading: 0,
      listLoading: false,
      editLoading: false,
      tableKey: 0,
      list: null,
      total: 0,
      listQuery: {
        pageNum: 1,
        pageSize: 20,
        username: undefined,
        phone: undefined,
        email: undefined,
        status: undefined,
        orgId: undefined
      },
      statusOptions: [
        { enable: 1, label: '启用' },
        { enable: 0, label: '禁用' }
      ]
    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleFilter() {
      this.listQuery.pageNum = 1
      this.getList()
    },
    handleCreate() {

    },
    handleEdit(row) {
      // 点击编辑按钮
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
    },
    handleDownload() {},
    getList() {
      this.listLoading = true
      fetchPage(this.listQuery).then((response) => {
        this.list = response.msg.list
        this.total = response.msg.total
        this.listLoading = false
      })
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    }
  }
}
</script>

<style>
</style>
