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
  // eslint-disable-next-line
  listHosts: async function ({ state }, options) {
    const params = {
      recursive: true
    }

    if (options.searched) {
      params.name_like = options.searched
    }
    if (options.metric_types && options.metric_types.length > 0) {
      params.metric_types = options.metric_types
    }
    if (options.top && options.top > 0) {
      params.top = options.top
    }

    const res = await state.api.get('/hosts', { params })
    res.data.forEach(el => {
      el.name = el['host-id']
    })
    return res
  },

  /** Pend list of metrics. */
  listMetrics: async function ({ state }, options) {
    const params = {
      recursive: true
    }

    if (options.searched) {
      params.name_like = options.searched
    }
    if (options.types && options.types.length > 0) {
      params.type = options.types
    }

    const res = await state.api.get('/metrics', { params })
    res.data = res.data.metrics
    res.data.forEach(el => {
      el.name = el['metric-id']
    })
    return res
  },

  /** Pend list of metrics' meta. */
  listTypes: async function ({ state }) {
    const params = {
      meta: true
    }
    const res = await state.api.get('/metrics', { params })
    res.data = res.data.meta.types.map(el => ({ name: el }))
    return res
  },

  /** Pend list of records. */
  listRecords: async function ({ state }, { id, n, from, to }) {
    const params = {}

    if (n && n > 0) {
      params.n = n
    }
    if (from && from !== '') {
      params.from = from
    }
    if (to && to !== '') {
      params.to = to
    }

    const res = await state.api.get(`/metrics/${id}/measurements`,
      { params })
    return res
  },

  /** Pend login request. */
  sendLogin: async function ({ state, commit }, { username, password }) {
    const body = {
      username,
      password
    }
    const res = await state.auth.post('/login', body)
    //ERROR: Same Origin Policy!

    if (res.access_token) {
      commit('login', username, password, res.access_token)
    }

    return res
  }
}

export default actions
