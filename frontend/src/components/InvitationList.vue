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
    <div class="tou-log">
      <span class="span-log">
        <img src="../../static/log.png" style="float:left">
        <h4>河北地质大学</h4>
        <!-- </br> -->
        <a href="http://www.hgu.edu.cn/" style="font-size: 8px;">
          河北地质大学位于河北省石家庄市，是原地质部直属五大院校之一，
          是自然资源部与河北省人民政府共建高校，河北省重点骨干大学
        </a>
      </span>
      <span class="tou-new" ><el-input type placeholder="搜索关键词" style="width:200px; height:20px;"></el-input>
      <el-button style="background-color: #3b5998 !important;color: aliceblue !important;">搜索</el-button></span>
    </div>
    <div class="nvitationlist">
      <div class="dh-class">
        <span v-for=" ( b , index) in buttons "  :key="index" style=" margin-right:10px;width:12%; ">
          <el-button :class="b.class" size="small" type @click="redact(b)">{{b.name}}</el-button>
        </span>
        <span>
          <el-button size="small" type @click="aclick('InvitationPublish')">发新帖</el-button>
        </span>
        <span class="px-class1">
          <span class="px-class">
            排序方式：
            <a href="#" @click="aclic()" style="font-size: 8px;margin-left:10px;">最后回复</a>
            <a href="#" @click="aclic()" style="font-size: 8px;margin-left:10px;">最新发布</a>
          </span>
        </span>
      </div>
      <div class="dh-class">
        <table class="tablecss">
          <tr class="trcss">
            <td style="width:5%"></td>
            <td style="width: 45%;">全部</td>
            <td style="width:15%" class="span_orgid">作者/发布时间</td>
            <td style="width:15%" class="span_orgid">回复/点击</td>
            <td style="width:15%" class="span_orgid">最后回复</td>
            <!-- <td style="width:5%">&nbsp;&nbsp;&nbsp;&nbsp;</td> -->
            <td style="width:5%" class="span_orgid">
              <a v-if="authority">操作</a>
            </td>
          </tr>
          <tr v-for="(stes,index) in Invitations" :key="index" class="trcss">
            <td style="width:5%">
              <span>{{stes.type}}</span>
            </td>
            <td style="width:45%">
              <a href="#" @click="go_junp(stes.objid)" style>{{stes.title}}</a>
            </td>
            <td style="width:15%" class="span_orgid">
              <span class>{{stes.name}}</span>
              <br>
              <span class>{{stes.create_at}}</span>
            </td>
            <td style="width:15%" class="span_orgid">
              <span class>{{stes.replynum}}</span>
              /
              <span style="color:#b1b1b1">{{stes.readnum}}</span>
            </td>
            <td style="width:15%" class="span_orgid">
              <span class>{{stes.replyname}}</span>
              <br>
              <span class>{{stes.replydate}}</span>
            </td>
            <td style="width:5%" class="span_orgid">
              <a v-if="authority" href="javascript:void(0)" @click="delby(stes.objid)">删除</a>
            </td>
            <!-- <td style="width:5%">&nbsp;&nbsp;&nbsp;&nbsp;</td> -->
          </tr>
        </table>
      </div>
      <div class="fy-class">
        <span class="count">共{{result.count}}条</span>
        <a>第{{result.page}}页</a>
        <a href="#" @click="up()" v-if="result.page>1">上一页</a>
        <a style="color:#ccd3e4" v-else>上一页</a>
        <a href="#" @click="next()" v-if="result.pageNum>result.page">下一页</a>
        <a style="color:#ccd3e4" v-else>下一页</a>

        <a>共{{result.pageNum}}页</a>
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
      Invitation: {
        objid: '',
        title: '',
        type: '',
        edition: '',
        content: '',
        orgid: '',
        create_at: '',
        update_at: ''
      },
      Invitations: [],
      buttons: [],
      options: [
        { value: 10, lable: 10 },
        { value: 30, lable: 30 },
        { value: 50, lable: 50 }
      ],
      result: {
        page: 1, // 当前页数
        pageSize: 10, // 每页大小
        pageNum: 0, // 总页数
        count: 0,
        parmap: { edition: 1 }
      },
      rulesd: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    }
  },
  created: function () {
    this.qurey()
    this.loadpade()
  },
  methods: {
    delby (id) {
      this.$confirm('此操作将永久删除该帖子, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$ajax({
            method: 'post',
            url: '/frontend/Invitation/delete',
            data: { id: id }
          })
            .then(result => {
              let data = result.data
              if (data.message === 'SUCCESS') {
                this.$message({
                  type: 'success',
                  message: '删除成功!'
                })
                this.qurey()
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
    up () {
      this.result.page--
      this.qurey()
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
                this.loadpade()
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
    next () {
      this.result.page++
      this.qurey()
    },
    go_junp (id) {
      let that = this
      this.$router.push({
        name: 'InvitationLook',
        params: {
          objid: id,
          edition: that.result.parmap.edition
        }
      })
    },
    redact (that) {
      this.buttons.forEach(function (k, v) {
        k.class = ''
      })
      that.class = 'button-style'
      this.result.parmap.edition = that.value
      this.qurey()
    },
    aclick (path) {
      if (this.$cookieStore.checkLogin()) {
        let that = this
        this.$router.push({
          name: path,
          params: {
            edition: that.result.parmap.edition
          }
        })
      } else {
        this.$message.error('请登录后再进行此操作！')
      }
    },
    qurey () {
      this.$ajax({
        method: 'post',
        data: this.result,
        url: '/frontend/Invitation/query'
      })
        .then(result => {
          this.Invitations = result.data.data//帖子信息
          this.result = result.data
        })
        .catch(result => { })
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
    register () {
      this.$router.push({
        name: 'Login'
      })
    },
    loadpade () {
      this.username = this.$cookieStore.getCookie('name')
      let edition = this.$route.params.edition//获取跳转页面带过来的参数
      if (edition !== undefined) {
        this.result.parmap.edition = edition
      }
      this.$ajax({ //获取类型
        method: 'post',
        data: this.result.parmap,
        url: '/frontend/Invitation/loadpade'
      })
        .then(result => {
          this.buttons = result.data.data
        })
        .catch(result => { })
      this.$ajax({//验证用户权限
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
.span-log {
  line-height: 20px;
  background-color: #f8f5f5;
  height: 130px;
  width: 500px;
  border: 1px solid #ccd3e4;
  float: left;
}
.tou-input {

}
.tou-text {
  margin-right: 20px;
  font-size: 8px;
  color: rgb(255, 255, 255);
  float: right;
}
.tou-new {
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
