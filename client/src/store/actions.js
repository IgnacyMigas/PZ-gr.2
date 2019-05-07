const actions = {
  login: ({ commit }) => {
    commit('login')
  },

  logout: ({ commit }) => {
    commit('logout')
  },

  /** Make async request. */
  request: async (_, promise) => {
    let [log, error, data] = ['', '', null]
    try {
      const response = await promise()
      if (response !== undefined) {
        if (response.data !== undefined) {
          if (response.log !== undefined) log = response.log
          data = response.data
        } else {
          data = response
        }
      }
    } catch (e) {
      if (e.response) { // there is some response
        const data = e.response.data
        if (data.message !== undefined && data.message !== '') {
          error = data.message
        } else {
          error = data
        }
      } else {
        error = 'Błąd ' + e.status
      }
    }
    return { log, error, data }
  },

  /** Pend list of hosts. */
  listHosts: ({ state }) => {
    return state.axios.get('/hosts')
  },

  /** Pend list of metrics. */
  listMetrics: ({ state }) => {
    return state.axios.get('/metrics')
  }

}

export default actions
