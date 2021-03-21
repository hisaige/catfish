<template>
  <org-tree @handleNodeClick="nodeClick">
    <template v-slot:container>
      <div>
        <div class="filter-container">
          <el-input
            v-model="listQuery.username"
            placeholder="用户名"
            style="width: 200px;"
            class="filter-item"
            @keyup.enter.native="handleFilter"
          />
          <el-input
            v-model="listQuery.phone"
            placeholder="手机号"
            style="width: 200px;"
            class="filter-item"
            @keyup.enter.native="handleFilter"
          />
          <el-input
            v-model="listQuery.email"
            placeholder="邮箱"
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
          <el-table
            :key="tableKey"
            v-loading="listLoading"
            :data="list"
            border
            fit
            highlight-current-row
            style="width: 100%;"
            @sort-change="sortChange"
          >

            <el-table-column
              label="用户名"
              width="90"
              align="center"
            >
              <template slot-scope="{row}">
                <span class="link-type">{{ row.username }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="头像"
              width="90px"
              align="center"
            >
              <template slot-scope="{row}">
                <img
                  :src="row.avatar"
                  style="width: 60px; height: 60px;"
                  class="user-avatar"
                >
              </template>
            </el-table-column>
            <el-table-column
              label="邮箱"
              width="150"
              align="center"
            >
              <template slot-scope="{row}">
                <span class="link-type">{{ row.email }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="手机号"
              align="center"
              width="95"
            >
              <template slot-scope="{row}">
                <span class="link-type">{{ row.phone }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="别名"
              align="center"
              width="95"
            >
              <template slot-scope="{row}">
                <span class="link-type">{{ row.nickName }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="真实姓名"
              align="center"
              width="110"
            >
              <template slot-scope="{row}">
                <span class="link-type">{{ row.realName }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="证件号码"
              align="center"
              width="100"
            >
              <template slot-scope="{row}">
                <span class="link-type">{{ row.idcard }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="备注信息"
              align="center"
              min-width="110px"
            >
              <template slot-scope="{row}">
                <span class="link-type">{{ row.introduction }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="创建时间"
              align="center"
              width="95"
            >
              <template slot-scope="{row}">
                <span>{{ row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
              </template>
            </el-table-column>

            <el-table-column
              label="状态"
              align="center"
              width="50"
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

        <el-dialog
          v-loading="dialogLoading"
          :title="'编辑'"
          :visible.sync="dialogFormVisible"
        >
          <el-form
            ref="dataForm"
            :rules="rules"
            :model="temp"
            label-position="left"
            label-width="80px"
            style="width: 400px; margin-left:120px;"
          >
            <el-form-item
              label="用户名"
              prop="username"
            >
              <el-input v-model="temp.username" />
            </el-form-item>
            <el-form-item
              label="别名"
              prop="nickName"
            >
              <el-input v-model="temp.nickName" />
            </el-form-item>
            <el-form-item
              label="真实姓名"
              prop="realName"
            >
              <el-input v-model="temp.realName" />
            </el-form-item>

            <el-form-item label="密码">
              <el-input
                v-model="temp.password"
                type="password"
                maxlength="18"
                minlength="5"
                :show-password="true"
                placeholder="******"
                auto-complete="off"
              />
            </el-form-item>
            <el-form-item
              label="手机号"
              prop="phone"
            >
              <el-input v-model="temp.phone" />
            </el-form-item>
            <el-form-item
              label="邮箱"
              prop="email"
            >
              <el-input v-model="temp.email" />
            </el-form-item>
            <el-form-item
              label="证件号码"
              prop="idcard"
            >
              <el-input v-model="temp.idcard" />
            </el-form-item>
            <el-form-item
              label="到期时间"
              prop="datetime"
            >
              <el-date-picker
                v-model="temp.validDate"
                type="datetime"
                value-format="timestamp"
                placeholder="选择一个日期"
              />
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
            <el-form-item
              label="排序"
              prop="sort"
            >
              <el-input-number v-model="temp.sort" size="mini" />
            </el-form-item>
            <el-form-item label="备注">
              <el-input
                v-model="temp.introduction"
                :autosize="{ minRows: 2, maxRows: 4}"
                type="textarea"
                placeholder="备注信息"
              />
            </el-form-item>
            <!-- 用户头像：TODO -->
            <el-form-item label="头像">

              <!-- 头衔缩略图 -->
              <pan-thumb :image="temp.avatar" />
              <!-- 文件上传按钮 -->
              <el-button
                type="primary"
                icon="el-icon-upload"
                @click="imagecropperShow=true"
              >更换头像
              </el-button>

              <!--
      v-show：是否显示上传组件
      :key：类似于id，如果一个页面多个图片上传控件，可以做区分
      :url：后台上传的url地址
      @close：关闭上传组件
      @crop-upload-success：上传成功后的回调
        <input type="file" name="file"/>
      -->
              <image-cropper
                v-show="imagecropperShow"
                :key="imagecropperKey"
                :width="300"
                :height="300"
                :url="'/FILE/aliyun/oss/upload'"
                field="file"
                @close="close"
                @crop-upload-success="cropSuccess"
                @crop-upload-fail="cropUploadFail"
              />
            </el-form-item>
            <el-form-item>
              <el-button
                :disabled="updateBtnDisabled"
                type="primary"
                :loading="editLoading"
                @click="handleUpdate('dataForm')"
              >更新</el-button>
              <router-link
                :to="'/user/index'"
                style="margin-left: 16px;"
              >
                <el-button @click="dialogFormVisible = false">返回</el-button>
              </router-link>
            </el-form-item>
          </el-form>
        </el-dialog>
      </div>
    </template>
  </org-tree>
</template>

<script>
import { fetchPage, addUser, editUser, deleteUser } from '@/api/ums/user'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { getUserValidProp, validEmail } from '@/utils/validate'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import ImageCropper from '@/components/ImageCropper' // 头像组件
import PanThumb from '@/components/PanThumb' // 头像组件
import OrgTree from '@/components/Hisaige/OrgTree' // 组织组件

const calendarTypeOptions = [
  { key: 'CN', display_name: 'China' },
  { key: 'US', display_name: 'USA' },
  { key: 'JP', display_name: 'Japan' },
  { key: 'EU', display_name: 'Eurozone' }
]

// arr to obj, such as { CN : "China", US : "USA" }
const calendarTypeKeyValue = calendarTypeOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export default {
  name: 'UserIndex',
  components: { Pagination, ImageCropper, PanThumb, OrgTree },
  // div里加上 v-waves使用水波效果
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
      dialogLoading: 0,
      updateBtnDisabled: false,
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      editLoading: false,
      listQuery: {
        pageNum: 1,
        pageSize: 20,
        username: undefined,
        phone: undefined,
        email: undefined,
        status: undefined,
        orgId: undefined
      },
      importanceOptions: [1, 2, 3],
      calendarTypeOptions,
      sortOptions: [
        { label: 'ID Ascending', key: '+id' },
        { label: 'ID Descending', key: '-id' }
      ],
      statusOptions: [
        { enable: 1, label: '启用' },
        { enable: 0, label: '禁用' }
      ],
      // 上传弹框组件是否显示
      imagecropperShow: false,
      imagecropperKey: 0, // 上传组件key值
      temp: {
        id: undefined,
        username: '',
        nickName: '',
        realName: '',
        password: undefined,
        phone: '',
        idcard: '',
        enable: undefined,
        introduction: undefined,
        validDate: undefined,
        avatar: '',
        status: 1
      },
      orgName: undefined,
      dialogFormVisible: false,
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: undefined,
      downloadLoading: false
    }
  },
  created() {
    this.rules = getUserValidProp()
    this.getList()
  },
  mounted() {
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchPage(this.listQuery).then((response) => {
        this.list = response.msg.list
        this.total = response.msg.total
        this.listLoading = false
      })
    },
    addUser(userDTO) {
      addUser(userDTO).then((response) => {
        this.$message({
          message: response.desc,
          type: 'success'
        })
      })
    },
    handleFilter() {
      this.listQuery.pageNum = 1
      this.getList()
    },
    close() {
      // 关闭上传弹框的方法
      this.imagecropperShow = false
      // 上传组件初始化
      this.imagecropperKey = this.imagecropperKey + 1
    },
    // 上传成功方法/
    cropSuccess(res) {
      console.log(res)
      this.imagecropperShow = false
      // 上传之后接口返回图片地址
      this.temp.avatar = res.msg
      this.imagecropperKey = this.imagecropperKey + 1
    },
    cropUploadFail(error) {
      console.log('upload avatar failed...' + error)
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作成功',
        type: 'success'
      })
      row.status = status
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        status: '启用',
        type: ''
      }
    },
    handleCreate() {
      const orgId = this.listQuery.orgId
      const orgName = this.orgName
      if (undefined == orgId) {
        this.$message({
          message: '请选择组织',
          type: 'error'
        })
      } else {
        this.$router.push({ path: '/user/save', query: { orgId: orgId, orgName: orgName }}) // 带参跳转
      }
    },
    handleEdit(row) {
      // 点击编辑按钮
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
      // this.$refs[formName].validate((valid) => {

      // })
    },
    handleUpdate(formName) {
      if (this.temp === 'undefined') {
        this.$message({
          message: '当前数据不存在',
          type: 'error'
        })
      }
      // 点击更新按钮
      this.$refs[formName].validate((valid) => {
        this.editLoading = true
        this.updateBtnDisabled = true
        const that = this
        editUser(that.temp)
          .then((response) => {
            // 刷新列表数据
            that.getList()
            // Just to simulate the time of the request
            this.$notify({
              title: 'Success',
              message: '更新成功',
              type: 'success',
              duration: 2000
            })
          })
          .finally(() => {
            that.editLoading = false
            that.updateBtnDisabled = false
            that.dialogFormVisible = false
          })
      })
    },
    handleDelete(row, index) {
      this.listLoading = true
      deleteUser(row.id).then((response) => {
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
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then((excel) => {
        const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
        const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
        const data = this.formatJson(filterVal)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal) {
      return this.list.map((v) =>
        filterVal.map((j) => {
          if (j === 'timestamp') {
            return parseTime(v[j])
          } else {
            return v[j]
          }
        })
      )
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    },
    nodeClick: function(data) {
      // 单击组织事件
      this.listQuery.orgId = data.id
      this.orgName = data.orgName
      this.getList()
    }
  }
}
</script>
<style scoped>
.scale {
  transform: scale(0.7,0.7);
  -ms-transform: scale(0.7,0.7); /* IE 9 */
  -webkit-transform: scale(0.7,0.7); /* Safari and Chrome */
}
</style>
