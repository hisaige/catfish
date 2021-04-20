<template>
  <el-container
    v-loading="loading"
    class="area-manage page-container"
  >
    <el-aside
      v-show="isShowTree"
      width="250px"
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
      :title="'编辑'"
      :visible.sync="dialogFormVisible"
    >
      <el-form
        ref="treeDataForm"
        :rules="rules"
        :model="temp"
        label-position="left"
        label-width="80px"
        style="width: 400px; margin-left:120px;"
      >
        <el-form-item
          label="组织名称"
          prop="orgName"
        >
          <el-input v-model="temp.username" />
        </el-form-item>
        <el-form-item
          label="组织编码"
          prop="nickName"
        >
          <el-input v-model="temp.nickName" />
        </el-form-item>

        <el-form-item
          label="排序"
          prop="sort"
        >
          <el-input-number
            v-model="temp.sort"
            :min="1"
            :max="10"
            label="排序"
            @change="handleSortChange"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            :disabled="updateBtnDisabled"
            type="primary"
            :loading="loading"
            @click="handleUpdate('dataForm')"
          >更新</el-button>
          <router-link
            :to="'/user/index'"
            style="margin-left: 16px;"
          >
            <el-button @click="dialogFormVisible=false">返回</el-button>
          </router-link>
        </el-form-item>
      </el-form>
    </el-dialog>

    <div
      class="filter-container"
      style="width:100%; margin-top:16px; margin-left:6px;"
    >
      <div>
        <el-input
          v-model="listQuery.orgName"
          placeholder="组织名"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="handleFilter"
        />
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
          class="filter-item"
          style="margin-left: 10px;"
          type="primary"
          icon="el-icon-edit"
          @click="handleDelete"
        >
          删除
        </el-button>
      </div>
      <el-table
        :key="tableKey"
        v-loading="loading"
        :data="tableList"
        border
        fit
        highlight-current-row
        style="width: 100%;"
        @sort-change="sortChange"
      >

        <el-table-column
          label="组织名称"
          min-width="80px"
          align="center"
        >
          <template slot-scope="{row}">
            <span class="link-type">{{ row.orgName }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="组织编码"
          min-width="80px"
          align="center"
        >
          <template slot-scope="{row}">
            <span class="link-type">{{ row.orgCode }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="排序"
          width="60"
          align="center"
        >
          <template slot-scope="{row}">
            <span class="link-type">{{ row.sort }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="修改时间"
          align="center"
          width="95"
        >
          <template slot-scope="{row}">
            <span>{{ row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="操作"
          align="center"
          width="230"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="{row,$index}">
            <el-button
              type="primary"
              size="mini"
              @click="handleUpdate(row)"
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
              @click="handleDelete(row, $index)"
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
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // 分页插件

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
      // 是否展示组织树
      isShowTree: true,
      // 右键是否显示标志
      tmDisplay: false,
      tmNodeId: '',
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
      // 组织树弹窗编辑属性
      temp: {
        orgName: '',
        orgCode: '',
        sort: 0
      },
      // 组织树
      organization: [
        {
          id: '1',
          orgName: '一级 1',
          children: [
            {
              id: '3',
              orgName: '二级 1-1',
              children: [
                {
                  id: '4',
                  orgName: '三级 1-1-1'
                }
              ]
            },
            {
              id: '2',
              orgName: '二级 1-2',
              children: [
                {
                  id: '5',
                  orgName: '三级 1-2-1'
                },
                {
                  id: '3',
                  orgName: '三级 1-2-2'
                }
              ]
            }
          ]
        }
      ],
      // 属性校验规则
      rules: undefined
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
  },
  methods: {
    // 右键组织树
    rightClick(e, data, node, comp) {
      console.log('e:', e, 'data', data)
      this.rightMenu = { top: e.pageY + 'px', left: e.pageX + 'px' }
      this.tmDisplay = true
      this.tmNodeId = data.id
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
      this.dialogFormVisible = true
    },
    // 右键组织树-编辑按钮
    treeEditDialog() {
      console.log('点击编辑按钮' + this.tmNodeId)
    },
    // 右键组织树-删除
    removeTreeNode() {
      console.log('点击移除按钮' + this.tmNodeId)
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
    // 新增组织
    addOrg() {
      console.log(this.$refs.tree.getCheckedKeys())
    },
    removeOrg() {},
    // 定义方法，获取高度减去头尾
    getHeight() {
      this.defaultHeight.height = window.innerHeight - 90 + 'px'
    },
    handleNodeClick(data) {
      console.log(data)
    },
    handleCreate() {
      console.log('create...')
    },
    handleUpdate(formName) {
      console.log(row)
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
  padding: 0 16px 0 8px;
  font-size: 14px;
  line-height: 30px;
  cursor: pointer;
}
ul li:hover {
  background-color: #ebeef5;
}
</style>
