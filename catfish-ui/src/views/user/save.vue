<template>
  <div class="app-container">
    <el-form
      ref="dataForm"
      :rules="rules"
      :model="record"
      label-position="left"
      label-width="80px"
      style="width: 400px; margin-left:120px;"
    >
      <el-form-item
        label="用户名"
        prop="username"
      >
        <el-input v-model="record.username" />
      </el-form-item>
      <el-form-item
        label="别名"
        prop="nickName"
      >
        <el-input v-model="record.nickName" />
      </el-form-item>
      <el-form-item
        label="真实姓名"
        prop="realName"
      >
        <el-input v-model="record.realName" />
      </el-form-item>

      <el-form-item
        label="密码"
        prop="password"
      >
        <el-input
          v-model="record.password"
          type="password"
          maxlength="18"
          minlength="5"
          :show-password="true"
          placeholder="******"
          auto-complete="off"
        />
      </el-form-item>
      <el-form-item
        label="所属组织"
        prop="orgName"
      >
        <el-input v-model="record.orgName" readonly="readonly" />
      </el-form-item>
      <el-form-item
        label="手机号"
        prop="phone"
      >
        <el-input v-model="record.phone" />
      </el-form-item>
      <el-form-item
        label="邮箱"
        prop="email"
      >
        <el-input v-model="record.email" />
      </el-form-item>
      <el-form-item
        label="证件号码"
        prop="idcard"
      >
        <el-input v-model="record.idcard" />
      </el-form-item>
      <el-form-item
        label="到期时间"
        prop="validDate"
      >
        <el-date-picker
          v-model="record.validDate"
          type="datetime"
          value-format="timestamp"
          placeholder="选择一个日期"
        />
      </el-form-item>
      <el-form-item label="启用状态">
        <el-select
          v-model="record.status"
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
        <el-input-number v-model="record.sort" size="mini" />
      </el-form-item>
      <el-form-item label="备注">
        <el-input
          v-model="record.introduction"
          :autosize="{ minRows: 2, maxRows: 4}"
          type="textarea"
          placeholder="备注信息"
        />
      </el-form-item>
      <!-- 用户头像：TODO -->
      <el-form-item label="头像">

        <!-- 头衔缩略图 -->
        <pan-thumb :image="record.avatar" />
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
          :disabled="saveBtnDisabled"
          type="primary"
          @click="save('dataForm')"
        >保存</el-button>
        <router-link
          :to="'/user/index'"
          style="margin-left: 16px;"
        >
          <el-button>返回</el-button>
        </router-link>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { addUser } from '@/api/ums/user'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

import { getUserValidProp } from '@/utils/validate'

export default {
  components: { addUser, ImageCropper, PanThumb },
  data() {
    return {
      record: {
        id: undefined,
        username: '',
        nickName: '',
        realName: '',
        password: undefined,
        orgId: undefined,
        orgName: undefined,
        phone: '',
        idcard: '',
        enable: undefined,
        sort: 999,
        introduction: undefined,
        validDate: undefined,
        avatar: '',
        status: 1
      },
      rules: undefined,
      statusOptions: [
        { enable: 1, label: '启用' },
        { enable: 0, label: '禁用' }
      ],
      saveBtnDisabled: false, // 保存按钮是否禁用
      // 上传弹框组件是否显示
      imagecropperShow: false,
      imagecropperKey: 0, // 上传组件key值
      BASE_API: process.env.BASE_API // 获取dev.env.js里面地址
    }
  },
  created() {
    this.rules = getUserValidProp()
  },
  mounted() {
    debugger
    this.record.orgId = this.$route.query.orgId
    this.record.orgName = this.$route.query.orgName
  },
  methods: {
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 表单校验成功才会继续
          this.saveBtnDisabled = true
          const path = this.$route.path
          addUser(this.record)
            .then((res) => {
              this.$message({
                message: '添加成功',
                type: 'success'
              })
              this.saveBtnDisabled = false
              this.$router.push({ path: '/user/index' })
            })
            .catch((error) => {
              this.saveBtnDisabled = false
            })
        }
      })
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
      this.record.avatar = res.msg
      this.imagecropperKey = this.imagecropperKey + 1
    },
    cropUploadFail(error) {
      console.log('upload avatar failed...' + error)
    }
  }
}
</script>
