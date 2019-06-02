import { newAPI } from './connections'

const set_on = (state, data) => {
  const token = data.token
  const username = data.username
  const password = data.password

  if (!token || !username) { return }

  state.isLoggedIn = true
  state.user = { username, password }
  state.access_token = token
  state.api = newAPI(token)

  localStorage.setItem('JWT', token)
  localStorage.setItem('user', JSON.stringify(username))
}

const set_off = (state) => {
  state.isLoggedIn = false
  stateuser = {
    username: null,
    password: null
  }
  state.access_token = null
  state.api = null
}

const mutations = {
  tryFromLocalStorage (state) {
    const token = localStorage.getItem('JWT')
    state.access_token = token
    if (token) {
      const userStr = localStorage.getItem('user')
      const username = JSON.parse(userStr)
      set_on(state, { username, password: null, token })
    }
  },
  login (state, username, password, token) {
    set_on(state, { username, password, token })
  },

  logout (state) {
    set_off(state)
  }
}

export default mutations
