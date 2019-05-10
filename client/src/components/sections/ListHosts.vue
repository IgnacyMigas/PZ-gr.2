<template>
  <get-table
    ref='table'
    title='Hosty'
    id='hosts'
    :headers="headers"
    :actions="actions"
    no_data_text='Brak hostów do wyświetlenia'
    :tryGet="reloadList"
    :getOptions="all_options"
  >
    <template v-slot="props">
      <td align="left">
        {{ props.item.name }}
      </td>
    </template>
    <template v-slot:text="props">
      <v-list two-line>
        <v-list-tile
         v-for="metric in props.item.metrics"
         :key="metric.name"
        >
          <v-list-tile-content>
            <v-list-tile-title>
              {{ metric.name }}
            </v-list-tile-title>
            <v-list-tile-sub-title>
              {{ metric.type }} [{{ metric.unit }}]
            </v-list-tile-sub-title>
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
    </template>
  </get-table>
</template>

<script>
import Vuex from 'vuex'
import GetTable from '@/components/sections/GetTable'

/**
 * Sekcja pobierająca dane hostów.
 *
 * @param {String} searched - łańcuch do wyszukania w nazwach hostów
 * @module components/sections/ListHosts
 */
export default {
  name: 'list-hosts',
  components: {
    'get-table': GetTable
  },
  props: {
    /** Funkcja pobierająca dane do wylistowania. */
    searched: {
      type: String,
      required: false
    },

    /** Dodatkowe opcje listowania. */
    options: {
      type: Object,
      required: false
    }
  },
  data () {
    return {
      data: [],
      headers: [
        {
          text: 'Nazwa',
          value: 'name',
          align: 'left',
          sortable: true
        }
      ],
      actions: []
    }
  },
  computed: {
    all_options () {
      let value = {...this.options}
      value.searched = this.searched
      return value
    }
  },
  methods: {
    ...Vuex.mapActions(['listHosts']),

    /** Pokazuje metryki dostępne dla danego hosta */
    showMetrics (item) {
      item
      //TODO
    },

    /** Pobiera dane do wylistowania metryk. */
    reloadList: async function (options = {}) {
      let data = await this.listHosts(options)

      if (options.searched) {
        data = data.filter(el => el.name.search(options.searched) != -1)
      }
      if (options.metric_types && options.metric_types.length > 0) {
        const filter_fun = (el) => {
          let types = el.metrics.map(m => m.type)
          return options.metric_types.every(mt => types.includes(mt))
        }
        data = data.filter(filter_fun)
      }

      this.data = data
      return data
    }
  }
}
</script>
