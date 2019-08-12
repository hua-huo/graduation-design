<template>
  <div class="invitation-list-p">
    <div class="tou-class-p">
      <div style="width:80%">
        <span class="tou-title-p">校园论坛</span>
        <span v-if="name != null">
          <a class="tou-text" href="#" @click="logout()">注销</a>
          <a class="tou-text" href="#" @click="gosystem">[ 欢迎 {{name}} ]</a>
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
            <span>
              <img v-if="user.gender=='女'" src="../../static/girl.png">
              <img v-else src="../../static/man.png">

              <a style="font-size: 24px;">{{name}}</a>
            </span>
            <span>
              <div style="margin-bottom:10px;height: 50px;">
                <a class="compile-title" href="#" @click="aclick(1)">论坛</a> >
                <a class="compile-title">我的主页</a>
              </div>
            </span>

            <div class="dh-class">
              <span v-for="(b,index) in buttons" style="width:12%;" :key="index">
                <el-button :class="b.class" size="small" type @click="redact(b)">{{b.name}}</el-button>
              </span>
              <span>
                <el-button size="small" type @click="redact(1)">修改头像</el-button>
              </span>
              <span>
                <el-button size="small" type @click="redact(1)">隐私设置</el-button>
              </span>
              <span>
                <el-button size="small" type @click="redact(1)">消息设置</el-button>
              </span>
            </div>
            <div id="elfrom" v-if="tab == 1">
              <el-form class :model="user" :rules="rules" ref="user">
                <el-form-item label="用户id：" prop>{{user.orgid}}</el-form-item>
                <el-form-item label="用户名：" prop>{{user.username}}</el-form-item>
                <el-form-item label="昵      称：" prop="name" :rules="rules.name">
                  <el-input type v-model="user.name" style="width:200px"></el-input>
                </el-form-item>
                <el-form-item label="性     别：" prop="gender" :rules="rules.gender">
                  <el-select v-model="user.gender" style="width:200px;" placeholder="请选择性别">
                    <el-option
                      v-for="item in genders"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <!-- <el-button @click="close">取 消</el-button> -->
                <el-button type="primary" @click="submit()">修改</el-button>
              </div>
            </div>
            <div v-if="tab == 2">
              <el-collapse accordion>
                <el-collapse-item title="我发布的帖子" name="1">
                  <table style="width:100%">
                    <tr style="background-color: rgb(134, 158, 210) ">
                      <th style="width:60%">帖子标题</th>
                      <th style="width:20%">回复/点击</th>
                      <th style="width:20%">发表时间</th>
                    </tr>
                  </table>
                  <div style="height: 400px;overflow: auto;">
                    <table style="width:100%">
                      <tr v-for="(item ,index) in publish" :key="index" class="personal-tr">
                        <td style="width:60%">
                          <p>
                            <a href="#" @click="go_junp(item.objid)">{{item.title}}</a>
                          </p>
                          <p>
                            发布在：
                            <a href="#" @click="aclick(item.edition)">{{item.lasttitle}}</a>
                          </p>
                        </td>
                        <td style="text-align: center;width:20%">{{item.replynum}}/{{item.readnum}}</td>
                        <td style="text-align: center;width:20%">{{item.create_at}}</td>
                      </tr>
                    </table>
                  </div>
                </el-collapse-item>
                <el-collapse-item title="我参与的帖子" name="2">
                  <table style="width:100%">
                    <tr style="background-color: rgb(134, 158, 210) ">
                      <th style="width:60%">帖子标题</th>
                      <th style="width:20%">回复/点击</th>
                      <th style="width:20%">发表时间</th>
                    </tr>
                  </table>
                  <div style="height: 400px;overflow: auto;">
                    <table style="width:100%">
                      <tr v-for="(item ,index) in reply" :key="index" class="personal-tr">
                        <td style="width:60%">
                          <p>
                            <a href="#" @click="go_junp(item.objid)">{{item.title}}</a>
                          </p>
                          <p>
                            发布在：
                            <a href="#" @click="aclick(item.edition)">{{item.lasttitle}}</a>
                          </p>
                        </td>
                        <td style="text-align: center;width:20%">{{item.replynum}}/{{item.readnum}}</td>
                        <td style="text-align: center;width:20%">{{item.create_at}}</td>
                      </tr>
                    </table>
                  </div>
                </el-collapse-item>
              </el-collapse>
            </div>
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
export default
  {
    name: 'Invitation',
    data () {
      return {
        Userd: {
          username: '',
          password: '',
          dialogFormVisible: false,
          title: '登录'
        },
        user: {
          orgid: '123',
          username: 'admin',
          name: '',
          gender: ''
        },
        tab: '1',
        genders: [{ label: '男', value: '男' }, { label: '女', value: '女' }],
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
        name: '',
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
        buttons: [
          { name: '个人资料', class: 'button-style', tab: 1 },
          { name: '我的足迹', class: '', tab: 2 }
        ],
        publish: [],
        reply: [],
        rulesd: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' }
          ],
          password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
        },
        params: { edition: '', lasttitle: '' }
      }
    }
    ,
    created: function () {
      this.loadpade()
    }
    ,
    methods: {
      submit () {
        let that = this
        this.$refs['user'].validate(valid => {
          if (valid) {
            this.$ajax({
              method: 'post',
              url: '/frontend/Invitation/modifierUser',
              data: this.user
            })
              .then(result => {
                if (result.data.message == 'SUCCESS') {
                  this.user = result.data.data
                  this.name = result.data.data.name
                  this.$message({
                    message: '修改成功！',
                    type: 'success'
                  })
                  this.$cookieStore.setCookie(
                    'name',
                    result.data.data.name,
                    1000 * 6
                  )
                } else {
                  alert(' 请重新登录后再试！ ')
                }
              }
              )
              .
              catch(result => {
                alert('系统错误')
              }
              )
          }
          else {
          }
        }
        )
      }
      ,
      up () {
        this.result.page--
        this.loadpade()
      }
      ,
      logout () {
        this.$cookieStore.delCookie()
        this.name = this.$cookieStore.getCookie('name')
        this.$message('已退出登录')
        this.$router.push({
          name: 'InvitationList',
          params: {}
        })
      }
      ,
      register () {
        this.$router.push({
          name: 'Login'
        })
      }
      ,
      next () {
        this.result.page++
        this.loadpade()
      }
      ,
      redact (that) {
        if (that == 1) {
          const h = this.$createElement
          this.$notify({
            title: '提示',
            message: h('i', { style: 'color: teal' }, '该功能暂未开放！')
          })
        } else {
          this.buttons.forEach(function (k, v) {
            k.class = ''
          })
          that.class = 'button-style'
        }
        this.tab = that.tab
      }
      ,
      aclick (edition) {
        this.$router.push({
          name: 'InvitationList',
          params: {
            edition: edition
          }
        })

        //   this.$router.push({
        //     name: "InvitationList",
        //     params: {
        //       edition: that.invitation.edition
        //     }
        //   });
      }
      ,
      login () {
        this.Userd.dialogFormVisible = true
      }
      ,
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
                  ),
                    this.$cookieStore.setCookie('name', data.data.name, 1000 * 6)
                  this.$message({
                    message: '登录成功！',
                    type: 'success'
                  })
                  this.Userd.dialogFormVisible = false
                  this.username = this.$cookieStore.getCookie('name')
                }
                else {
                  alert('用户名或密码错误')
                }
              }
              )
              .
              catch(result => {
                // alert("系统错误");
              }
              )
          }
        })
      },
      gosystem () {
        this.$router.push({ name: 'PersonalData' })
      },
      go_junp (id) {
        let that = this
        this.$router.push({
          name: 'InvitationLook',
          params: {
            objid: id
          }
        })
      },
      loadpade () {
        this.name = this.$cookieStore.getCookie('name')
        this.user.name = this.name
        let edition = this.$route.params.edition
        // this.invitation.edition = edition
        this.$ajax({
          method: 'post',
          data: { edition: edition },
          url: '/frontend/Invitation/getUser'
        }).then(result => {
          this.user = result.data.data
        }).catch(result => {
        })
        this.$ajax({
          method: 'post',
          url: '/frontend/Invitation/queryByOrgid'
        }).then(result => {
          this.publish = result.data.data.publish
          this.reply = result.data.data.reply
        }).catch(result => {
        })
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

.button-style {
  background-color: #3b5998 !important;
  color: aliceblue !important;
}

a {
  text-decoration: none;
}
.personal-tr:hover {
  background-color: #ccd3e4;
}
/* a:hover{
        color: red;
    } */
.el-dialog {
  width: 400px;
}
.el-collapse-item__header:hover{
    background-color: #f9fafd;
}
</style>
