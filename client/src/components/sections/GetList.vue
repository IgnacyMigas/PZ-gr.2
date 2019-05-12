<template>
  <div>
    <v-card-text v-if="log || error">
      <p class='log'>{{ log }}</p>
      <p class='error' v-for="err in error" :key="err">
        {{ err }}
      </p>
    </v-card-text>
  
    <slot :items="items"></slot>

    <slot name="dialogs"></slot>
  </div>
</template>

<script>
import Vuex from 'vuex'

/**
 * Szablon strony pobierającej dane.
 *
 * @example
 *  <get-list
 *    title='Hellos'
 *    :tryGet="tryListHellos">
 *   <div
 *     slot-scope="{ names }"
 *     v-for="name in names"
 *     :key="name">
 *     Hello, {{ name }}
 *   </div>
 * </get-list>
 *
 * @param {String} title - (required) tytuł
 * @param {Function} tryGet - (required) Funkcja pobierająca dane
 * @param {Object} getOptions - Parametry dla funkcji pobierającej
 * @module components/sections/GetList
 */
export default {
  name: 'get-list',
  data () {
    return {
      items: [],
      log: '',
      error: ''
    }
  },
  props: {
    /** Tytuł listy. */
    title: {
      type: String,
      required: true
    },

    /** Funkcja pobierająca dane do wylistowania. */
    tryGet: {
      type: Function,
      required: true
    },

    /** Opcje do przekazania funkcji przeładowującej. */
    getOptions: {
      type: Object,
      required: false,
      default: undefined
    }
  },
  methods: {
    ...Vuex.mapActions(['request']),

    /** Przeładuj listę. */
    reload: async function (options = this.getOptions) {
      const { log, error, data } = await this.request(() => this.tryGet(options))
      this.items = data || [];
      this.log = log
      this.error = error ? error.split('\n') : ''
    }
  },
  mounted () {
    this.reload()
  },
  watch: {
    /** Kiedy opcje się zmieniają, przeładuj listę. */
    getOptions (newVal) {  // newVal, oldVal
      this.getOptions = newVal
      this.reload(newVal)
    }
  }
}
</script>
