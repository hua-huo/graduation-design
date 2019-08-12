<template>
  <div id="login" class="invitation-list">
    <div class="tou-class">
      <div style="width:80%">
        <span class="tou-title">校园论坛</span>
        <span class="tou-text">
          <el-button type="primary" @click="login()" plain>登录</el-button>
        </span>
        <a class="tou-text">我已注册，现在就</a>
      </div>
    </div>
    <div class="elfrom-d">
      <div id="elfrom" style="width:400px; ">
        <h3>用户注册</h3>
        <el-form class :model="User" :rules="User" ref="User">
          <el-form-item label="用户名" prop="username" :rules="rules.username">
            <el-input type v-model="User.username" style="width:200px" placeholder="请输用户名，字数限制在16"></el-input>
          </el-form-item>

          <el-form-item
            label-position="right"
            label="密         码"
            prop="password"
            :rules="rules.password"
          >
            <el-input
              type="password"
              v-model="User.password"
              style="width:200px"
              placeholder="请输16位密码，字数限制在16"
            ></el-input>
          </el-form-item>
          <el-form-item label="姓         名" prop="name" :rules="rules.name">
            <el-input type v-model="User.name" style="width:200px" placeholder="请输姓名，字数限制在16"></el-input>
          </el-form-item>
          <el-form-item label="性     别" prop="gender" :rules="rules.gender">
            <el-select v-model="User.gender" style="width:200px;" placeholder="请选择性别">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <el-button type="primary" @click="submit()" style="width:200px;margin-left: 57px;" plain>注册</el-button>
      </div>
      <div class="dialog" style=" text-align: center;width:900px;margin-left:30%;">
        <el-dialog :title="Userd.title" :visible.sync="Userd.dialogFormVisible">
          <el-form class :model="Userd" :rules="Userd" ref="Userd">
            <el-form-item label="用户名" prop="username" :rules="rulesd.username">
              <el-input type v-model="Userd.username" style="width:200px"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password" :rules="rulesd.password">
              <el-input type="password" v-model="Userd.password" style="width:200px"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <!-- <el-button @click="close">取 消</el-button> -->
            <el-button type="primary" @click="loginSub()">登录</el-button>
          </div>
        </el-dialog>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'login',
  data () {
    return {
      Userd: {
        username: '',
        password: '',
        dialogFormVisible: false,
        title: '登录'
      },
      User: {
        username: '',
        password: '',
        name: '',
        gender: ''
      },
      rulesd: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { message: '输入用户名过长', trigger: 'blur', max: 16 },
          { validator: this.checkdName }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { message: '输入密码过长', trigger: 'blur', max: 16 }
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { message: '输入姓名过长', trigger: 'blur', max: 16 }
        ],
        gender: [{ required: true, message: '请选择性别', trigger: 'blur' }]
      },
      options: [{ label: '男', value: '男' }, { label: '女', value: '女' }]
    }
  },
  methods: {
    login () {
      this.Userd.dialogFormVisible = true
    },
    loginSub () {
      this.$refs['Userd'].validate(valid => {
        if (valid) {
          this.$ajax({
            method: 'post',
            url: '/frontend/Login/register',
            data: this.Userd
          })
            .then(result => {
              let data = result.data//后端
              if (data.message === 'SUCCESS') {
                this.$cookieStore.setCookie(//存进cookie
                  'username',
                  data.data.username,
                  1000 * 6 //设置cookie有效时间6分钟
                )
                this.$cookieStore.setCookie('name', data.data.name, 1000 * 6)
                this.$message({
                  message: '登录成功！',
                  type: 'success'
                })
                this.$router.push({
                  name: 'InvitationList',
                  params: {}
                })
              } else {
                alert('用户名或密码错误')
              }
            })
            .catch(result => {
              // alert("系统错误");
            })
        }
      })
    },
    checkdName (rule, value, callback) {
      this.$ajax({
        method: 'post',
        url: '/frontend/Login/checkdname',
        data: this.User
      })
        .then(result => {
          if (result.data.message === 'SUCCESS') {
            callback()
          } else {
            callback(new Error('用户名已存在'))
          }
        })
        .catch(result => {
          // alert("系统错误");
        })
    },
    submit () {
      this.$refs['User'].validate(valid => {
        if (valid) {
          this.$ajax({
            method: 'post',
            url: '/frontend/Login/save',
            data: this.User
          })
            .then(result => {
              if (result.data.message === 'SUCCESS') {
                this.$message({
                  message: '注册成功！请登录',
                  type: 'success'
                })
                this.login()
              } else {
                alert('注册失败')
              }
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
.elfrom-d {
  padding: 10px;
  width: 450px;
  height: 400px;
  border: 1px solid #ccd3e4;
  background-color: #f5faff;
}
.fy-class {
  width: 80%;
  text-align: right;
}
.tou-log {
  width: 80%;
  height: 135px;
  margin: 10px;
}
.span-log {
  line-height: 20px;
  background-color: #f8f5f5;
  height: 130px;
  width: 500px;
  border: 1px solid #ccd3e4;
  float: left;
}
.tou-text {
  margin-right: 20px;
  font-size: 8px;
  color: rgb(255, 255, 255);
  float: right;
}
.tou-title {
  font-size: 25px;
  color: rgb(255, 255, 255);
  float: left;
}
.tou-class {
  padding-top: 10px;
  line-height: 22px;
  width: 100%;
  height: 40px;
  background-color: #3b5998;
}
.invitation-list {
  width: 100%;
  text-align: -webkit-center;
}
.px-class1 {
  height: 30px;
  float: right;
  padding-top: 10px;
}
.px-class {
  height: 20px;
  border: 1px solid #ccd3e4;
  font-size: 8px;
  margin-left: 10px;
  /* float: right; */
}
.dh-class {
  width: 80%;
  text-align: left;
  border-bottom: 1px solid #3b5998;
}
.button-style {
  background-color: #3b5998 !important;
  color: aliceblue !important;
}
.el-button:hover {
  background-color: #3b5998;
  color: aliceblue;
}

.nvitationlist {
  width: 100%;
}
.tablecss {
  width: 100%;
  border: 1px solid#ccd3e4;
  table-layout: fixed;
}
.span_orgid {
  text-align: -webkit-center;
  font-size: 8px;
}
td {
  text-align: left;
  /* white-space: nowrap; */
  overflow: hidden;
  text-overflow: ellipsis;
}
tr {
  border-bottom: 1px solid #ccd3e4;
}
a {
  text-decoration: none;
}
a:hover {
  color: red;
}
table {
  border-collapse: collapse;
}
.count {
  float: left;
}
.el-dialog {
  width: 400px;
}
</style>
