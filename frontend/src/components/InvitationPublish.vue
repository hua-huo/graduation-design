<template>
  <div class="invitation-list-p">
    <div class="tou-class-p">
      <div style="width:80%">
        <span class="tou-title-p">校园论坛</span>
        <span v-if="username != null">
          <a class="tou-text" href="#" @click="logout()">注销</a>
          <a class="tou-text" href="#" @click="gosystem">[ 欢迎 {{username}} ]</a>
        </span>
        <span v-else>
          <a class="tou-text" href="#" @click="register()">注册</a>
          <a class="tou-text" href="#" @click="login()">登录</a>
          <a class="tou-text">[ 欢迎 游客 ]</a>
        </span>
      </div>
    </div>
    <div class="compile-p">
      <div class="compile-from-p">
        <div id="elfrom-p" style="width:700px;">
          <div style="margin-bottom:10px">
            <a class="compile-title-p" href="#" @click="aclick()">{{lasttitle}}</a> >
            <a class="compile-title-p">发表新帖</a>
          </div>
          <el-form :model="invitation" :rules="invitation" ref="invitation">
            <el-form-item label prop="title" style="width:700px" :rules="rules.title">
              <el-input type v-model="invitation.title" placeholder="请输入标题，字数限制在30字"></el-input>
            </el-form-item>
            <el-form-item label prop="type" style="width:700px" :rules="rules.type">
              <el-select v-model="invitation.type" style="width:300px;" placeholder="请选择发表类型">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label prop="content" :rules="rules.content">
              <el-input
                type="textarea"
                placeholder="请输入正文内容，字数限制在5000字"
                rows="8"
                style="width:700px;"
                v-model="invitation.content"
              ></el-input>
            </el-form-item>
          </el-form>
          <span style="margin-left: 90%;">
            <el-button type="primary" @click="submit()" plain>发表</el-button>
          </span>
        </div>
      </div>
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
</template>

<script>
export default {
  name: 'Invitation',
  data () {
    return {
      Userd: {
        username: '',
        password: '',
        dialogFormVisible: false,
        title: '登录'
      },
      username: '',
      lasttitle: '',
      invitation: {
        objid: '',
        title: '',
        type: '',
        edition: '',
        content: '',
        orgid: '',
        create_at: '',
        update_at: ''
      },
      options: [],
      rules: {
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' },
          { message: '标题输入过长', trigger: 'blur', max: 30 },
          { message: '标题内容过短', trigger: 'blur', mix: 6 },
          { validator: this.checkdName }
        ],
        content: [
          { required: true, message: '请输入内容', trigger: 'blur' },
          { message: '内容输入过长', trigger: 'blur', max: 50000 },
          { validator: this.checkdName }
        ],
        type: [
          { required: true, message: '请选择发表类型', trigger: 'blur' },
          { validator: this.checkdName }
        ]
      },
      Invitations: [],
      rulesd: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    }
  },
  created: function () {
    this.loadpade()
  },
  methods: {
    submit () {
      let that = this
      this.$refs['invitation'].validate(valid => {
        if (valid) {
          this.$ajax({
            method: 'post',
            url: '/frontend/Invitation/save',
            data: this.invitation
          })
            .then(result => {
              if (result.data.message == 'SUCCESS') {
                let objid = result.data.objid
                this.$router.push({
                  name: 'InvitationLook',
                  params: {
                    objid: objid,
                    edition: that.invitation.edition
                  }
                })
              } else {
                alert('发表失败')
              }
            })
            .catch(result => {
              alert('系统错误')
            })
        } else {
        }
      })
    },
    up () {
      this.result.page--
      this.loadpade()
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
              let data = result.data
              if (data.message === 'SUCCESS') {
                this.$cookieStore.setCookie(
                  'username',
                  data.data.username,
                  1000 * 6
                )
                this.$cookieStore.setCookie('name', data.data.name, 1000 * 6)
                this.$message({
                  message: '登录成功！',
                  type: 'success'
                })
                this.Userd.dialogFormVisible = false
                this.username = this.$cookieStore.getCookie('name')
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
    gosystem () {
      this.$router.push({ name: 'PersonalData' })
    },
    logout () {
      this.$cookieStore.delCookie()
      this.username = this.$cookieStore.getCookie('name')
      this.$message('已退出登录')
      this.$router.push({
        name: 'InvitationList',
        params: {}
      })
    },
    register () {
      this.$router.push({
        name: 'Login'
      })
    },
    next () {
      this.result.page++
      this.loadpade()
    },
    redact (that) {
      this.buttons.forEach(function (k, v) {
        k.class = ''
      })
      that.class = 'button-style-p'
    },
    aclick () {
      let that = this
      this.$router.push({
        name: 'InvitationList',
        params: {
          edition: that.invitation.edition
        }
      })
    },
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
              let data = result.data
              if (data.message === 'SUCCESS') {
                this.$cookieStore.setCookie(
                  'username',
                  data.data.username,
                  1000 * 6
                )
                this.$cookieStore.setCookie('name', data.data.name, 1000 * 6)
                this.$message({
                  message: '登录成功！',
                  type: 'success'
                })
                this.Userd.dialogFormVisible = false
                this.username = this.$cookieStore.getCookie('name')
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
    loadpade () {
      this.username = this.$cookieStore.getCookie('name')
      let edition = this.$route.params.edition
      this.invitation.edition = edition
      this.$ajax({
        method: 'post',
        data: { edition: edition },
        url: '/frontend/Invitation/queryenummap'
      })
        .then(result => {
          // console.log(result)
          this.options = result.data.data.list
          this.lasttitle = result.data.data.title
        })
        .catch(result => { })
    }
  }
}
</script>
<style>
.fy-class-p {
  width: 80%;
  text-align: right;
}
.tou-log-p {
  width: 80%;
  height: 135px;
  margin: 10px;
}
.span-log-p {
  line-height: 20px;
  background-color: #f8f5f5;
  height: 130px;
  width: 500px;
  border: 1px solid #ccd3e4;
  float: left;
}
.tou-text-p {
  margin-right: 20px;
  font-size: 8px;
  color: rgb(255, 255, 255);
  float: right;
}
.tou-title-p {
  font-size: 25px;
  color: rgb(255, 255, 255);
  float: left;
}
.tou-class-p {
  padding-top: 10px;
  line-height: 22px;
  width: 100%;
  height: 40px;
  background-color: #3b5998;
}
.invitation-list-p {
  width: 100%;
  height: 100%;
  text-align: -webkit-center;
}
.compile-p {
  text-align: -webkit-center;
  width: 100%;
  height: 100%;
  /* background-color: #eff1f5; */
}
.compile-from-p {
  padding: 20px;
  text-align: -webkit-left;
  width: 60%;
  background-color: #eff1f5;
  border: 1px solid #ccd3e4;
}
.compile-title-p {
  font-size: 14px;
}
a {
  text-decoration: none;
}
/* a:hover{
    color: red;
} */
.el-dialog {
  width: 400px;
}
</style>
