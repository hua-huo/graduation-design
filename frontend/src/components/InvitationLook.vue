<template>
  <div class="invitation-list">
    <div class="tou-class">
      <div style="width:80%">
        <span class="tou-title">校园论坛</span>
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
    <div class="compile">
      <div class>
        <div style="margin-bottom:10px;height: 50px;">
          <a class="compile-title" href="#" @click="aclick()">{{params.lasttitle}}</a> >
          <a class="compile-title">{{title}}</a>
        </div>
        <div class="details">
          <table style="width:100%;border-collapse: collapse;">
            <tr style="width:100%" v-for=" (item , index) in Invitations " :key="index">
              <td class="user-message">
                <img v-if="item.author" style="float:right" src="../../static/forum-icon.gif">
                <img v-if="item.gender=='女'" src="../../static/girl.png">
                <img v-else src="../../static/man.png">
                <br>
                <a style="font-size: 12px;color:blue">{{item.name}}</a>
              </td>
              <td>
                <div class="content">
                  <span>{{item.content}}</span>
                </div>
                <div class="content-message">
                  <a
                    v-if="authority && index>0"
                    href="javascript:void(0)"
                    @click="delby(item.objid)"
                  >删除</a>
                  <span>
                    <a style="margin-right: 5px;">{{index+1}}楼</a>
                    <a>发表于 {{item.create_at}}</a>
                  </span>
                </div>
              </td>
            </tr>
          </table>
        </div>
      </div>
      <div>
        <div style="margin-left:40px">
          <div>
            <a style="font-size:14px;font-weight: 700;">发表回复</a>
            <a
              href="#"
              style="font-size:14px;float:right;margin-right:20px"
              @click="aclick()"
            >《《 回到论坛</a>
          </div>
          <div>
            <el-form class :model="Replyinfo" :rules="Replyinfo" ref="Replyinfo">
              <el-form-item label prop="content" :rules="rules.content">
                <el-input
                  type="textarea"
                  placeholder="请输入回复内容，字数限制在1000字"
                  rows="8"
                  style="width:700px;"
                  v-model="Replyinfo.content"
                ></el-input>
              </el-form-item>
            </el-form>
            <span style="margin-left: 80%;">
              <el-button type="primary" @click="submit()" plain>发表</el-button>
            </span>
          </div>
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
      authority: false,
      Userd: {
        username: '',
        password: '',
        dialogFormVisible: false,
        title: '登录'
      },
      username: '',
      Replyinfo: {
        content: '',
        articleid: ''
      },
      options: [],
      rules: {
        content: [
          { required: true, message: '请输入内容', trigger: 'blur' },
          { message: '内容输入过长', trigger: 'blur', max: 50000 },
          { validator: this.checkdName }
        ]
      },
      Invitations: [],
      title: '',
      params: { edition: '', lasttitle: '' },
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
    delby (id) {
      this.$confirm('此操作将永久删除该回复, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$ajax({
            method: 'post',
            url: '/frontend/Replyinfo/delete',
            data: { id: id }
          })
            .then(result => {
              let data = result.data
              if (data.message === 'SUCCESS') {
                this.$message({
                  type: 'success',
                  message: '删除成功!'
                })
                this.loadpade()
              } else {
                this.$message({
                  type: 'error',
                  message: '删除失败!'
                })
              }
            })
            .catch(result => {
              this.$message({
                type: 'error',
                message: '系统错误!'
              })
            })
        })
        .catch(() => { })
    },
    submit () {
      this.$refs['Replyinfo'].validate(valid => {
        if (valid) {
          if (this.$cookieStore.checkLogin()) {
            this.$ajax({
              method: 'post',
              url: '/frontend/Replyinfo/save',
              data: this.Replyinfo
            })
              .then(result => {
                this.Replyinfo.content = ''
                this.loadpade()
              })
              .catch(result => {
                alert('系统错误')
              })
          } else {
            this.$message.error('请登录后再进行此操作！')
          }
        } else {
        }
      })
    },
    register () {
      this.$router.push({
        name: 'Login'
      })
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
                this.loadpade()
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
    logout () {
      this.$cookieStore.delCookie()
      this.username = this.$cookieStore.getCookie('name')
      this.$message('已退出登录')
      this.loadpade()
    },
    gosystem () {
      this.$router.push({ name: 'PersonalData' })
    },
    loadpade () {
      this.username = this.$cookieStore.getCookie('name')
      let objid = this.$route.params.objid
      this.Replyinfo.articleid = objid
      this.$ajax({
        method: 'post',
        data: { objid: objid},
        url: '/frontend/content/query'
      })
        .then(result => {
          this.Invitations = result.data.list
          this.title = result.data.title
          console.log(result.data)
          this.params = result.data.param
        })
        .catch(result => { })
      this.$ajax({
        method: 'post',
        url: '/frontend/Invitation/userlimits',
        data: this.Userd
      })
        .then(result => {
          this.authority = result.data.data
        })
        .catch(result => {
          // alert("系统错误");
        })
    },
    login () {
      this.Userd.dialogFormVisible = true
    },
    aclick () {
      let that = this
      this.$router.push({
        name: 'InvitationList',
        params: {
          edition: that.params.edition
        }
      })
    }
  }
}
</script>
<style>
.fy-class {
  width: 80%;
  text-align: right;
}
.tou-log {
  width: 80%;
  height: 135px;
  margin: 10px;
}
.details {
  line-height: 20px;
  /* background-color:  #f8f5f5; */
  width: 99.5%;
  /* border: 1px solid #ccd3e4; */
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
  height: 100%;
  text-align: -webkit-center;
}
.compile {
  border: 1px solid #ccd3e4;
  line-height: 65px;
  text-align: left;
  width: 60%;
  height: 100%;
  background-color: #eff1f5;
}
.user-message {
  width: 15%;
  background-color: #eff1f5;
  text-align: center;
}
.compile-title {
  font-size: 18px;
}
.content {
  margin: 20px;
  margin-bottom: 100px;
}
.el-dialog {
  width: 400px;
}
.content-message {
  float: right;
  font-size: 10px;
  color: #9e9e9e;
}
a {
  text-decoration: none;
}
/* a:hover{
    color: red;
} */
td {
  background-color: #ffffff;
}
tr {
  border: 1px solid #ccd3e4;
}
</style>
