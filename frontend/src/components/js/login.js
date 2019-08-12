
export function checkLogin (that) {
  let username = that.$cookieStore.getCookie('username')
  // eslint-disable-next-line eqeqeq
  if (username == undefined || username == null) {
    that.$message.error('请登录后再进行此操作！');
    return false
  } else {
    return true
  }
}
