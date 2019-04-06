import Vue from 'vue'
import Vuex from 'vuex'
import actions from './actions'
import * as getters from './getters'
import mutations from './mutations'

Vue.use(Vuex)

const state = {
  /** If the user is authentified */
  isLoggedIn: true,

  /** User's data. Null if not logged in. */
  user: {
    username: 'someuser',
    password: 'somepass'
  }
}

const options = {
  state,
  mutations,
  actions,
  getters
}

const store = new Vuex.Store(options)

export default store
