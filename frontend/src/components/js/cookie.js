/* eslint-disable eqeqeq */
/* eslint-disable space-before-function-paren */
/* 用export把方法暴露出来 */
/* 设置cookie */
export function setCookie (key, value, expire) {
  var date = new Date()
  date.setSeconds(date.getSeconds() + expire)
  document.cookie = key + '=' + (value) + '; expires=' + date.toGMTString()
}

/* 获取cookie */
export function getCookie (key) {
  let result
  if (document.cookie.length > 0) {
    let cookies = document.cookie.split(';')
    cookies.forEach(cookie => {
      cookie = cookie.trim()
      // eslint-disable-next-line camelcase
      let c_start = cookie.indexOf(key + '=')
      // eslint-disable-next-line camelcase
      if (c_start == 0) {
        result = (cookie.split('=')[1])
        return result
      }
    })
  }
  return result
}

/* 删除cookie */
export function delCookie () {
  setCookie('username', '', -1)
  setCookie('name', '', -1)
}

export function checkLogin () {
  let username = getCookie('username')
  if (username == undefined || username == null) {
    return false
  } else {
    return true
  }
}
