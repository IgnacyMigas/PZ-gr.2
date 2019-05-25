import Vue from 'vue'
import Vuex from 'vuex'
import actions from './actions'
import * as getters from './getters'
import mutations from './mutations'
import axios from 'axios'

Vue.use(Vuex)

const newAxios = (basepath, access_token) => {
  const a = axios.create({
    baseURL: basepath
  })
  a.defaults.headers.common['Content-Type'] = 'application/json; charset=UTF-8'
  if (access_token !== undefined) {
    a.defaults.headers.common['access-token'] = access_token
  }
  return a
}

const newAuth = () => {
  return newAxios(process.env.VUE_APP_AUTH_BASE_URL)
}

const newAPI = (access_token) => {
  return newAxios(process.env.VUE_APP_API_BASE_URL, access_token)
}

const access_token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NTg4MTkzNjgsIm5iZiI6MTU1ODgxOTM2OCwianRpIjoiZDcxNGU0Y2EtYzg4Ni00YWNhLWE1ZmYtMzRjZmVkMGYwM2NkIiwiZXhwIjoxNTU4ODIwMjY4LCJpZGVudGl0eSI6InRlc3QiLCJmcmVzaCI6ZmFsc2UsInR5cGUiOiJhY2Nlc3MifQ.NF30kxJVq7W0ygwUeZUkdjtjzpItrcCRUez6HviAk70'

const state = {
  /** If the user is authentified */
  isLoggedIn: false,

  /** User's data. Null if not logged in. */
  user: {
    username: null,
    password: null
  },

  /** Access token of the user. */
  access_token: access_token,

  /** API Gateway's axios instance. */
  api: newAPI(access_token),

  /** Authentication service's axios instance. */
  auth: newAuth()
}

const options = {
  state,
  mutations,
  actions,
  getters
}

const store = new Vuex.Store(options)

export default store
