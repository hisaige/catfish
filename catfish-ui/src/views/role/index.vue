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
          align="center"
          label="排序"
          width="60"
        >
          <template slot-scope="{row}">
            <span>{{ row.sort }}</span>
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

      <!-- 编辑弹窗 -->
      <el-dialog
        v-loading="dialogLoading"
        :title="'编辑'"
        :visible.sync="dialogFormVisible"
      >
        <el-form
          ref="dataForm"
          :model="temp"
          label-position="left"
          label-width="80px"
          style="width: 400px; margin-left:120px;"
        >
          <el-form-item
            label="角色名称"
            prop="name"
          >
            <el-input v-model="temp.name" />
          </el-form-item>

          <el-form-item
            label="角色编码"
            prop="roleCode"
          >
            <el-input v-model="temp.roleCode" />
          </el-form-item>

          <el-form-item
            label="角色描述"
            prop="description"
          >
            <el-input v-model="temp.description" />
          </el-form-item>

          <el-form-item
            label="排序"
            prop="sort"
          >
            <el-input-number v-model="temp.sort" :step="1" size="mini" />
          </el-form-item>

          <el-form-item label="启用状态">
            <el-select
              v-model="temp.status"
              class="filter-item"
              placeholder="请选择"
            >
              <el-option
                v-for="item in statusOptions"
                :key="item.enable"
                :label="item.label"
                :value="item.enable"
              />
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-button
              type="primary"
              :loading="editLoading"
              @click="addOrUpdate('dataForm')"
            >{{ addOrUpdateBtn }}</el-button>
            <el-button @click="dialogFormVisible = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { fetchPage, addRole, editRole, deleteRole } from '@/api/ums/role'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // 分页插件
import { updateRole } from '@/api/role'

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
      dialogFormVisible: false, // 编辑弹窗
      dialogLoading: 0,
      listLoading: false,
      editLoading: false,
      tableKey: 0,
      list: null,
      total: 0,
      addOrUpdateBtn: '新增', // 新增|更新 按钮文字
      listQuery: {
        pageNum: 1,
        pageSize: 20,
        username: undefined,
        phone: undefined,
        email: undefined,
        status: undefined,
        orgId: undefined
      },
      tempSort: 0,
      temp: {
        name: undefined,
        roleCode: undefined,
        description: undefined,
        sort: 0,
        status: 1
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
      this.addOrUpdateBtn = '新增'

      this.clearTemp()

      this.dialogFormVisible = true
    },
    clearTemp() {
      // 清空 对象熟悉回到默认值
      this.name = undefined
      this.roleCode = undefined
      this.temp.status = 1
      this.temp.sort = 0
    },
    handleEdit(row) {
      // 点击编辑按钮
      this.temp = Object.assign({}, row) // copy obj
      // this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.addOrUpdateBtn = '编辑'
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
    },
    addOrUpdate(formName) {
      // 处理新增或更新
      const that = this
      this.editLoading = true
      if (this.addOrUpdateBtn === '新增') {
        addRole(this.temp)
          .then((response) => {
            this.$message({
              message: response.desc,
              type: 'success'
            })
            that.getList()
          })
          .finally(() => {
            that.editLoading = false
            that.dialogFormVisible = false
          })
      } else {
        editRole(this.temp)
          .then((response) => {
            this.$message({
              message: response.desc,
              type: 'success'
            })
            that.getList()
          })
          .finally(() => {
            that.editLoading = false
            that.dialogFormVisible = false
          })
      }
    },
    handleDelete(row, index) {
      this.listLoading = true
      deleteRole(row.id).then((response) => {
        // Just to simulate the time of the request
        this.$notify({
          title: 'Success',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
        this.list.splice(index, 1)
        this.listLoading = false
      })
    }
  }
}
</script>

<style>
</style>
