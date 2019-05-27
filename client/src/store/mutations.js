import newAPI from './index'

const mutations = {
  login (state, username, password, token) {
    state.isLoggedIn = true
    state.user = {
      username,
      password
    }
    state.access_token = token
    state.api = newAPI(token)
  },

  logout (state) {
    state.isLoggedIn = false
    state.user = {
      username: null,
      password: null
    }
    state.access_token = null
    state.api = null
  }
}

export default mutations
