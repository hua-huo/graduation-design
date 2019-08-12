<template>
  <div id="add">
    <div id="elfrom" style="width:400px;margin-left:36%">
      <h3>新增部门</h3>
      <el-form class :model="Department" :rules="Department" ref="Department">
        <el-form-item label="部门名称：" prop="name" :rules="rules.name">
          <el-input type v-model="Department.name"></el-input>
        </el-form-item>
        <el-form-item label="部门描述：" prop="description" :rules="rules.description">
          <el-input type v-model="Department.description"></el-input>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="submit" plain>保存</el-button>
    </div>
  </div>
</template>
<script>
export default {
  name: 'departmen-add',
  created: function () {
    this.$ajax({
      method: 'post',
      url: '/frontend/demo'
    })
      .then(result => {
        this.User = result.data
      })
      .catch(result => { })
  },
  data () {
    return {
      Department: {
        name: '',
        description: ''
      },
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
  methods: {
    // 验证部门名称是否存在
    checkdName (rule, value, callback) {
      this.$ajax({
        method: 'post',
        url: '/frontend/checkdName',
        data: this.Department
      })
        .then(result => {
          if (result.data.data === null) {
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
      this.$refs['Department'].validate(valid => {
        if (valid) {
          this.$ajax({
            method: 'post',
            url: '/frontend/insert',
            data: this.Department
          })
            .then(result => {
              alert('操作成功')
            })
            .catch(result => {
              alert('系统错误')
            })
        } else {
        }
      })
    }
  }
}
</script>
<style>
.elfrom {
}
</style>
