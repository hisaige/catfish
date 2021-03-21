<template>
  <el-container
    v-loading="loading"
    class="area-manage page-container"
  >
    <el-aside
      v-show="isShowTree"
      width="220px"
    >

      <el-input
        v-model="orgFilterText"
        style="margin-bottom:6px;"
        placeholder="输入组织名称"
        maxlength="12"
        show-word-limit
        suffix-icon="el-icon-search"
      />
      <!-- <el-checkbox v-model="strictlyTree">父子联动</el-checkbox>
      <div style="float:right">
        <i class="el-icon-circle-plus icon-btn"
           @click="addOrg" />
        <i class="el-icon-remove icon-btn"
           @click="removeOrg" />
      </div> -->
      <el-tree
        ref="tree"
        class="filter-tree"
        :style="defaultHeight"
        :data="organization"
        :props="defaultProps"
        node-key="id"
        :filter-node-method="filterNode"
        :expand-on-click-node="false"
        @node-contextmenu="rightClick"
        @node-click="handleNodeClick"
      />
    </el-aside>

    <!-- 右键菜单 -->
    <div
      v-if="tmDisplay"
      id="perTreeMenu"
      class="tree_menu"
      :style="{...rightMenu}"
    >
      <ul>
        <li @click="treeAddDialog"><i class="el-icon-plus" /> 新增</li>
        <li @click="treeEditDialog"><i class="el-icon-edit" /> 编辑</li>
        <li @click="removeTreeNode"><i class="el-icon-minus" /> 删除</li>
      </ul>
    </div>

    <!-- 右键弹窗 -->
    <el-dialog
      v-loading="loading"
      :title="dialogActionBtn"
      width="500px"
      :visible.sync="dialogFormVisible"
    >
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="left"
        label-width="80px"
        style="width: 300px; margin-left:60px;"
      >
        <el-form-item
          label="组织名称"
          prop="orgName"
        >
          <el-input
            v-model="temp.orgName"
            maxlength="30"
          />
        </el-form-item>
        <el-form-item
          label="组织编码"
          prop="orgCode"
        >
          <el-input
            v-model="temp.orgCode"
            maxlength="30"
          />
        </el-form-item>

        <!-- <el-form-item
          label="排序"
          prop="sort"
        >
          <el-input-number v-model="temp.sort" :min="1" :max="10" label="排序" @change="handleSortChange" />
        </el-form-item> -->
        <el-form-item>
          <el-button
            :disabled="updateBtnDisabled"
            type="primary"
            :loading="loading"
            @click="handleAddOrUpdate(dialogActionBtn)"
          >{{ dialogActionBtn }}</el-button>
          <el-button @click="dialogFormVisible=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <div ref="wrapper" class="wrapper">
      <slot name="container" />
    </div>
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // 分页插件
import { getRoot, addNode, editNode, delNode } from '@/api/ums/orgTree'

export default {
  name: 'OrgTree',
  components: { Pagination },
  directives: { waves },
  data() {
    return {
      // 按钮显示
      updateBtnDisabled: false,
      // 组织树弹窗可见
      dialogFormVisible: false,
      // 组织树弹窗时按钮展示 新增|编辑
      dialogActionBtn: '新增',
      // 是否展示组织树
      isShowTree: true,
      // 右键是否显示标志
      tmDisplay: false,
      // 右键选中的组织id
      tmNodeId: '',
      // 右键选中的组织节点，添加/修改时更新它即可
      tmpNode: undefined,
      // 组织树组织联动
      strictlyTree: true,
      watchStrictlyTree: false,
      // 组织树过滤检索
      orgFilterText: undefined,
      tableKey: 0,
      tableList: [],
      defaultProps: {
        children: 'children',
        label: 'orgName'
      },
      defaultHeight: {
        height: ''
      },
      loading: false,
      listQuery: {
        pageNum: 1,
        pageSize: 20,
        orgName: undefined
      },
      selectItems: [],
      total: 0,
      // 组织树弹窗编辑属性,右键组织时给其赋值
      temp: {
        orgPid: '',
        orgName: '',
        orgCode: ''
        // sort: 0
      },
      // 组织树
      organization: [],
      // 属性校验规则
      rules: {
        orgName: [
          { required: true, message: '组织名称不能为空', trigger: 'blur' }
        ],
        orgCode: [
          { required: true, message: '组织编码不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    orgFilterText(val) {
      this.$refs.tree.filter(val)
    }
    // strictlyTree(val) {
    //   this.watchStrictlyTree = !val
    // }
  },
  created() {
    // 页面创建时执行一次getHeight进行赋值，顺道绑定resize事件
    window.addEventListener('resize', this.getHeight)
    this.getHeight()
    // 获取根节点
    this.getRoot()
  },
  mounted() {

  },
  methods: {
    // 右键组织树
    rightClick(e, data, node, comp) {
      console.log('e:', e, 'data:', data, 'node:', node)
      this.temp = Object.assign({}, data) // copy obj
      // this.temp.orgName = data.orgName
      // this.temp.orgCode = data.orgCode

      // 因为不知道是新增还是编辑，新增不能有id，因此先将id清掉，然后保留到 tmNodeId 编辑时需要带上
      this.tmNodeId = data.id
      this.temp.id = null
      // 传递数据时，子节点不需要传过去
      this.temp.children = null

      this.rightMenu = { top: e.pageY + 'px', left: e.pageX + 'px' }
      this.tmDisplay = true

      const self = this
      document.onclick = function(ev) {
        if (ev.target !== document.getElementById('perTreeMenu')) {
          self.tmDisplay = false
        }
      }
    },
    // 右键组织树-添加按钮
    treeAddDialog() {
      console.log('点击添加按钮' + this.tmNodeId)

      // 父组织id
      this.temp.orgPid = this.tmNodeId
      // 点击添加时清空输入框
      this.temp.orgName = ''
      this.temp.orgCode = ''

      this.dialogActionBtn = '新增'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        // 清空校验
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 右键组织树-编辑按钮
    treeEditDialog() {
      console.log('点击编辑按钮' + this.tmNodeId)
      this.dialogActionBtn = '编辑'
      this.dialogFormVisible = true
      // 编辑时需要带上id
      this.temp.id = this.tmNodeId
      this.$nextTick(() => {
        // 清空校验
        this.$refs['dataForm'].clearValidate()
      })
      this.updateOrg
    },
    // 右键组织树-删除
    removeTreeNode() {
      console.log('点击移除按钮' + this.tmNodeId)
      // 打转转
      this.loading = true
      delNode(this.tmNodeId)
        .then((response) => {
          // 刷新前端组织树
          this.getRoot()
        })
        .catch((err) => {
          this.loading = false
        })
    },
    // 排序变更
    handleSortChange(value) {
      console.log(value)
    },
    // 加载组织树 data 即 organization
    filterNode(value, data) {
      if (!value) return true
      return data.orgName.indexOf(value) !== -1
    },
    getRoot() {
      // 打转转
      this.loading = true
      // 递归查询所有组织
      const recursion = { recursion: true }
      getRoot(recursion)
        .then((response) => {
          // 清空数组
          this.organization.length = 0
          // 将根节点放入数组中
          this.organization.push(response.msg)
        })
        .finally(() => {
          this.loading = false
        })
    },
    // 新增组织
    addOrg() {
      const that = this
      var failed = false
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 打转转
          that.loading = true
          addNode(that.temp)
            .then((response) => {
              // 刷新前端组织树
              that.getRoot()
            })
            .catch((err) => {
              that.loading = false
              failed = true
            })
            .finally((response) => {
              if (!failed) {
                // 出错时弹窗保留
                that.dialogFormVisible = false
              }
            })
        }
      })
    },
    updateOrg() {
      const that = this
      var failed = false
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 打转转
          that.loading = true
          editNode(that.temp)
            .then((response) => {
              // 刷新前端组织树
              that.getRoot()
            })
            .catch((err) => {
              that.loading = false
              failed = true
            })
            .finally((response) => {
              if (!failed) {
                // 出错时弹窗保留
                that.dialogFormVisible = false
              }
            })
        }
      })
    },
    // 定义方法，获取高度减去头尾
    getHeight() {
      // this.defaultHeight.height = window.innerHeight - 90 + 'px'
      this.defaultHeight.height = document.body.scrollHeight - 90 + 'px'
    },
    handleNodeClick(data) {
      // 向外投递事件
      this.$emit('handleNodeClick', data)
    },
    handleAddOrUpdate(btn) {
      if (btn === '新增') {
        this.addOrg()
      } else {
        this.updateOrg()
      }
    },
    handleDelete() {
      console.log(selectItems)
    },
    handleDelete(row, index) {
      console.log(row)
    },
    getList() {},
    handleFilter() {
      this.listQuery.pageNum = 1
      this.getList()
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
    }
  }
}
</script>

<style scoped>
</style>
<style>
/*asaide标签边框*/
aside {
  padding: 8px 16px;
}

/*添加滚动条的解决办法*/
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 6px;
}
.el-tree {
  width: 100%;
  overflow-x: scroll;
}
.el-tree > .el-tree-node {
  min-width: 100%;
  display: inline-block !important;
}
/* .icon-btn {
  padding: 0 8px;
  cursor: pointer;
}
.el-icon-circle-plus {
  color: #1890ff;
}
.el-icon-remove {
  color: red;
} */

/* 组织树右键菜单样式 */
.tree_menu {
  position: fixed;
  display: block;
  z-index: 20000;
  background-color: #fff;
  padding: 5px 0;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

ul {
  margin: 0;
  padding: 0;
}
ul li {
  list-style: none;
  margin: 0;
  padding: 0 16px 0 4px;
  font-size: 14px;
  line-height: 30px;
  cursor: pointer;
}
ul li:hover {
  background-color: #ebeef5;
}
.wrapper {
  overflow: hidden;
  width: 90%;
}
</style>
