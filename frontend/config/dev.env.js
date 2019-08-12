'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')
const index =require('./index')
module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  API_ROOT: index.dev.proxyTable["/frontend"].target  //接口路径
})
