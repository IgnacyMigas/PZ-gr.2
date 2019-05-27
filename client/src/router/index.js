import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/views/Home'
import Login from '@/components/views/Login'
import Registration from '@/components/views/Registration'
import Charts from '@/components/views/Charts'
import List from '@/components/views/List'

/**
 * Check login token, if present, route to home.
 */
const tokenOrHome = (to, from, next) => {
  next()
}

/**
 * Check login token.
 */
const hasToken = (to, from, next) => {
  next()
}

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      alias: '/home',
      name: 'Home',
      component: Home,
      beforeEnter: hasToken
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
      beforeEnter: tokenOrHome
    },
    {
      path: '/registration',
      name: 'Registration',
      component: Registration,
      beforeEnter: tokenOrHome
    },
    {
      path: '/charts',
      name: 'Charts',
      component: Charts,
      beforeEnter: hasToken
    },
    {
      path: '/list',
      name: 'List',
      component: List,
      beforeEnter: hasToken
    }
  ]
})

export default router
