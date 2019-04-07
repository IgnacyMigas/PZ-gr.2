<template>
  <v-container fluid fill-height>
    <v-layout justify-center>
      <v-flex xs18 sm12 md6>
        <v-card class="elevation-12">
          <v-card-text v-if="log || error">
            <p class='log'>{{ log }}</p>
            <p class='error' v-for="err in error" :key="err">
              {{ err }}
            </p>
          </v-card-text>

          <slot :items="items"></slot>

        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
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
 * @param {String} title - tytuł
 * @param {Function} tryGet - (required) Funkcja pobierająca dane
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
    title: String,

    /** Funkcja pobierająca dane do wylistowania. */
    tryGet: Function
  },
  methods: {
    reload: async function () {
      const data = await this.tryGet()
      return data
    }
  },
  mounted () {
    this.reload()
  }
}
</script>
