import { isLoggedIn, req_route } from './getters'
import router from '../router'

const get_error = (e)  => {
  let response = e.response || e
  if (response && response.data) {
    response = response.data
  }
  if (response && response.message) {
    response = response.message
  }
  if (response && response.log) {
    response = response.log
  }
  if (response && response.status) {
    const s = response.status
    response = 'Błąd ' + s
    switch (s) {
      case 401:
        response += ' (brak uprawnień)'
        break
      case 404:
        response += ' (nie znaleziono)'
        break
      case 409:
        response += ' (konflikt)'
        break
    }
  }
  return response
}


const actions = {
  login: ({ commit }) => {
    commit('login')
  },

  logout: ({ commit }) => {
    commit('logout')
    router.push('/login')
  },

  /** Make async request. */
  request: async (_, promise) => {
    let [log, error, data] = ['', '', null]
    try {
      const response = await promise()
      data = (response && response.data) || response
      log = response && response.log
    } catch (e) {
      error = get_error(e)
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

    const res = await state.api.get('/v1/hosts', { params })
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

    const res = await state.api.get('/v1/metrics', { params })
    res.data = res.data.metrics
    res.data.forEach(el => {
      el.name = el['metric-id']
    })
    return res
  },

  /** Pend new compound metric request. */
  postNewMetric: async function ({ state }, options) {
    const body = options

    if (!body.time || body.time <= 0) {
      body.time = undefined
    }

    //TODO: remove when Monitor's bug is fixed
    body['metric-ids'] = body['metric-ids'][0]

    // eslint-disable-next-line
    console.log('body: ' + JSON.stringify(body))
    const res = await state.api.post('/v1/metrics', body)
    // eslint-disable-next-line
    console.log('res: ' + JSON.stringify(res))
    return res
  },

  /** Pend list of metrics' meta. */
  listTypes: async function ({ state }) {
    const params = {
      meta: true
    }
    const res = await state.api.get('/v1/metrics', { params })
    res.data = res.data.meta.types.map(el => ({ name: el }))
    return res
  },

  /** Pend list of records. */
  listRecords: async function ({ state }, { id, n, from, to }) {
    const params = {}

    if (n && n > 0) {
      params.n = n
    }

    const formatDate = (val) => {
      const yyyy = val.getFullYear()
      const mm = ('0' + (val.getMonth() + 1)).slice(-2)
      const dd = ('0' + val.getDate()).slice(-2)
      const date = dd + '/' + mm + '/' + yyyy
      const time = val.toLocaleTimeString('pl-PL')
      return (date + ' ' + time)
    }

    if (from && from !== '') {
      params.from = formatDate(from)
    }
    if (to && to !== '') {
      params.to = formatDate(to)
    }

    const res = await state.api.get(`/v1/metrics/${id}/measurements`,
      { params })
    return res
  },

  /** Pend login request. */
  sendLogin: async function ({ state, commit }, { username, password }) {
    const body = { username, password }
    const res = await state.auth.post('/v1/login', body)

    if (res.data && res.data.access_token) {
      const token = res.data.access_token
      commit('login', { username, password, token })

      if (isLoggedIn) {
        const route = req_route(state) || '/home'
        router.push(route)
      }
    }

    return res
  },

  /** Pend register request. */
  sendRegister: async function ({ state }, { username, password }) {
    const body = { username, password }
    const res = await state.auth.post('/v1/users', body)
    return res
  }
}

export default actions
