<template>
  <div>
    <table>
      <tr>
        <td>姓名：</td>
        <td>{{User.name}}</td>
      </tr>
      <tr>
        <td>年龄：</td>
        <td>{{User.age}}</td>
      </tr>
      <tr>
        <td>性别：</td>
        <td>{{User.geeder}}</td>
      </tr>
      <tr>
        <td>工资：</td>
        <td>{{User.salary}}</td>
      </tr>
    </table>
  </div>
</template>
<script>
export default {
  name: 'demo',
  created: function () {
    this.$ajax({
      method: 'post',
      url: '/frontend/demo'
    })
      .then(result => {
        this.User = result.data
      })
      .catch(result => { })
    // this.$ajax.post("/frontend/demo").then((result)=>{
    //     alert(result)
    // }).catch((error)=>{

    // })
  },
  data () {
    return {
      User: {
        name: '',
        age: '',
        geeder: '',
        salary: ''
      }
    }
  }
}
</script>
