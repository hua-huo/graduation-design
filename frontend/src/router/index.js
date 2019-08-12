import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Demo from '@/components/demo'
import DepartmentAdd from '@/components/DepartmentAdd'
import DepartmentList from '@/components/DepartmentList'
import InvitationList from '@/components/InvitationList'
import InvitationPublish from '@/components/InvitationPublish'
import InvitationLook from '@/components/InvitationLook'
import Login from '@/components/Login'
import PersonalData from '@/components/PersonalData'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/HelloWorld',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/demo',
      name: 'Demo',
      component: Demo
    },
    {
      path: '/department-add',
      name: 'DepartmentAdd',
      component: DepartmentAdd
    },
    {
      path: '/department-list',
      name: 'DepartmentList',
      component: DepartmentList
    },
    {
      path: '/',
      name: 'InvitationList',
      component: InvitationList
    },
    {
      path: '/PersonalData',
      name: 'PersonalData',
      component: PersonalData
    },
    {
      path: '/invitation-publish',
      name: 'InvitationPublish',
      component: InvitationPublish
    },
    {
      path: '/invitation-look',
      name: 'InvitationLook',
      component: InvitationLook
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    }
  ]
})
