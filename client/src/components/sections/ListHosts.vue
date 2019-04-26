<template>
  <get-table
    ref='table'
    title='Hosty'
    id='hosts'
    :headers="headers"
    no_data_text='Brak hostów do wyświetlenia'
    :tryGet="reloadList"
    :getOptions="all_options"
    v-slot="items"
  >
    <td align="left">
      {{ items.item.name }}
    </td>
    <td>
      <bar-button
       icon="list"
       :handler="() => showMetrics(item)" />
    </td>
  </get-table>
</template>

<script>
import GetTable from '@/components/sections/GetTable'
import BarButton from '@/components/elements/BarButton'

/**
 * Sekcja pobierająca dane hostów.
 *
 * @param {String} searched - łańcuch do wyszukania w nazwach hostów
 * @module components/sections/ListHosts
 */
export default {
  name: 'list-hosts',
  components: {
    'get-table': GetTable,
    'bar-button': BarButton
  },
  props: {
    /** Funkcja pobierająca dane do wylistowania. */
    searched: {
      type: String,
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
        },
        {
          text: 'Akcje',
          value: 'actions',
          align: 'center',
          sortable: false
        }
      ],
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
    /** Pokazuje metryki dostępne dla danego hosta */
    showMetrics (item) {
      item
      //TODO
    },

    /** Pobiera dane do wylistowania metryk. */
    reloadList: async function (options = {}) {
      // (mock)
      let data = [
        {
          name: 'D10, 205, stanowisko 1',
          metric_types: [ 'temperatura', 'zużycie pamięci' ]
        },
        {
          name: 'D10, 205, stanowisko 2',
          metric_types: [ 'temperatura', 'zużycie pamięci' ]
        },
        {
          name: 'Cyfronet, 402, stanowisko 4',
          metric_types: [ 'zużycie GPU' ]
        },
      ]

      if (options.searched ||
           (options.types && options.metric_types.length > 0)) {
        data = data.filter(el =>
          el.name.search(options.searched) != -1 &&
          options.metric_types.all(t => el.metric_types.includes(t))
        )
      }

      this.data = data
      return data
    }
  }
}
</script>
