<template>
  <div id="list" class>
    <ul>
      <li></li>
    </ul>
    <h5>部门列表：</h5>
    <div class="operation">
      <el-button size="small" type @click="addShow('新增')">新增</el-button>
      <el-button size="small" type @click="handleDelete(scope.$index, scope.row)">删除</el-button>
    </div>
    <div class="departmenlist">
      <el-table
        ref="Departments"
        :data="Departments.slice((currentPage-1)*6,currentPage*6)"
        stripe
        style="width: 100%"
      >
        <el-table-column type="selection" prop="checkbox" width="55"></el-table-column>
        <el-table-column prop="name" label="部门名称" width="200"></el-table-column>
        <el-table-column prop="description" label="部门描述" width="200"></el-table-column>
        <el-table-column prop="create_at" label="创建日期" width="250"></el-table-column>
        <el-table-column prop="update_at" label="更新日期" width="250"></el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button size="small" type @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            <el-button size="small" type @click="redact(scope.row)">修改</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog :title="title" :visible.sync="dialogFormVisible">
      <el-form class :model="Department" :rules="Department" ref="Department">
        <el-form-item label="部门名称：" prop="name" :rules="rules.name">
          <el-input type v-model="Department.name"></el-input>
        </el-form-item>
        <el-form-item label="部门描述：" prop="description" :rules="rules.description">
          <el-input type v-model="Department.description"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="submit">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'department-add',
  data () {
    return {
      title: '新增',
      dialogFormVisible: false,
      pagesize: 9,
      currentPage: 1,
      Department: {
        department_id: '',
        name: '',
        description: '',
        create_at: '',
        update_at: ''
      },
      Departments: [],
      rules: {
        name: [
          { required: true, message: '请输入部门名称', trigger: 'blur' },
          { message: '输入部门名称过长', trigger: 'blur', max: 50 },
          { validator: this.checkdName }
        ],
        description: [{ message: '输入部门说明过长', trigger: 'blur', max: 50 }]
      }
    }
  },
  created: function () {
    this.loadpade()
  },
  methods: {
    loadpade () {
      this.$ajax({
        method: 'post',
        url: '/frontend/query'
      })
        .then(result => {
          this.Departments = result.data.data
          console.log(this.Departments)
        })
        .catch(result => { })
    },
    addShow (title) {
      // 弹出新增窗口
      this.title = title
      this.dialogFormVisible = true
      this.initDepartment()
    },
    close () {
      // 取消关闭新增窗口
      this.dialogFormVisible = false
    },
    redact (data) {
      // 编辑数据
      let dement = data
      this.Department = dement
      this.title = '修改'
      this.dialogFormVisible = true
    },

    checkdName (rule, value, callback) {
      // 验证部门名称是否存在
      this.$ajax({
        method: 'post',
        url: '/frontend/checkdName',
        data: this.Department
      })
        .then(result => {
          console.log(result.data.data)

          if (result.data.data) {
            callback()
          } else {
            callback(new Error('该部门名称已存在'))
          }
        })
        .catch(result => {
          console.log('系统错误')
        })
    },

    submit () {
      // 提交保存
      this.$refs['Department'].validate(valid => {
        if (valid) {
          this.$ajax({
            method: 'post',
            url: '/frontend/insert',
            data: this.Department
          })
            .then(result => {
              this.dialogFormVisible = false
              this.initDepartment()
              this.loadpade()
            })
            .catch(result => {
              alert('系统错误')
            })
        } else {
        }
      })
    },
    initDepartment () {
      // 清空 Department 对象
      this.Department.name = ''
      this.Department.description = ''
      this.Department.create_at = ''
      this.Department.update_at = ''
      this.Department.department_id = ''
    },
    handleDelete (index, row) {
      console.log(index, row.department_id)
      this.$ajax({
        method: 'post',
        url: '/frontend/delete',
        data: { department_id: row.department_id }
      })
        .then(result => {
          this.loadpade()
        })
        .catch(result => {
          console.log('系统错误')
        })
    }
  }
}
</script>
<style>
.departmenlist {
  text-align: center;
}
.operation {
  text-align: left;
}
</style>
