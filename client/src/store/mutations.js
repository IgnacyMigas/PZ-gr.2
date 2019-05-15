const mutations = {
  login (state) {
    state.isLoggedIn = true
  },

  logout (state) {
    state.isLoggedIn = false
  }
}

export default mutations
