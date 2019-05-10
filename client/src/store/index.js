import Vue from 'vue'
import Vuex from 'vuex'
import actions from './actions'
import * as getters from './getters'
import mutations from './mutations'
import axios from 'axios'

Vue.use(Vuex)

const newAxios = (access_token) => {
    const a = axios.create({
      baseURL: process.env.VUE_APP_API_BASE_URL,
    })
    a.defaults.headers.common['Content-Type'] = 'application/json; charset=UTF-8'
    a.defaults.headers.common['access-token'] = access_token
    return a
}

const state = {
  /** If the user is authentified */
  isLoggedIn: true,

  /** User's data. Null if not logged in. */
  user: {
    username: 'someuser',
    password: 'somepass'
  },

  /** Access token of the user. */
  access_token: '<access-token>',

  /** Axios instance. */
  axios: newAxios('<access-token>')
}

const options = {
  state,
  mutations,
  actions,
  getters
}

const store = new Vuex.Store(options)

export default store
