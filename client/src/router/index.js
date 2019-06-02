import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/views/Home'
import Login from '@/components/views/Login'
import Registration from '@/components/views/Registration'
import Charts from '@/components/views/Charts'
import List from '@/components/views/List'
import { isLoggedIn } from '../store/getters'

/**
 * Check if logged in.
 */
const checkLoggedIn = (to, from, next) => {
  // if logged in, go!
  if (isLoggedIn) {
    next()
    return true
  }
  store.commit('tryFromLocalStorage')
  if (user.isLoggedIn) {
    next()
    return true
  }
  return false
}

/**
 * Check login status, if not logged in, route to home.
 */
const loginOrHome = (to, from, next) => {
  const login = checkLoggedIn()
  if (!login) {
    router.push('/home')
  }
}


Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      alias: '/home',
      name: 'Home',
      component: Home,
      beforeEnter: checkLoggedIn
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
      beforeEnter: checkLoggedIn
    },
    {
      path: '/registration',
      name: 'Registration',
      component: Registration,
      beforeEnter: checkLoggedIn
    },
    {
      path: '/charts',
      name: 'Charts',
      component: Charts,
      beforeEnter: loginOrHome
    },
    {
      path: '/list',
      name: 'List',
      component: List,
      beforeEnter: loginOrHome
    }
  ]
})

export default router
