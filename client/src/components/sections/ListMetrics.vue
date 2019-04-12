<template>
  <get-table
    ref='table'
    title='Metryki'
    :headers="headers"
    no_data_text='Brak metryk do wyświetlenia'
    :tryGet="reloadList"
    :getOptions="all_options"
    v-slot="items"
  >
    <td align="left">
      {{ items.item.name }}
    </td>
    <td
      v-if="show_type"
      align="right"
      class="caption"
    >
      ( {{ items.item.type }} )
    </td>
    <td v-if="quick_access">
      <bar-button
       icon="list"
       :handler="() => showRecords(items.item)" />
      <bar-button
       icon="add_circle"
       :handler="() => addMetricBy(items.item)" />
      <bar-button
       icon="show_chart"
       :handler="() => addToChart(items.item)" />
    </td>
  </get-table>
</template>

<script>
import GetTable from '@/components/sections/GetTable'
import BarButton from '@/components/elements/BarButton'

/**
 * Sekcja pobierająca dane metryk.
 *
 * @param {String} searched - łańcuch do wyszukania w nazwach metryk
 * @module components/sections/ListMetrics
 */
export default {
  name: 'list-metrics',
  components: {
    'get-table': GetTable,
    'bar-button': BarButton
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
      all_headers: {
        name: {
          text: 'Nazwa',
          value: 'name',
          align: 'left',
          sortable: true
        },
        type: {
          text: 'Typ',
          value: 'type',
          align: 'right',
          sortable: true
        },
        quick_access: {
          text: 'Akcje',
          value: 'actions',
          align: 'center',
          sortable: false
        }
      }
    }
  },
  computed: {
    all_options () {
      let value = {...this.options}
      value.searched = this.searched
      return value
    },

    show_type () {
      return (this.options && this.options.show_type) || false
    },

    quick_access () {
      return (this.options && this.options.quick_access) || false
    },

    headers () {
      let value = []
      value.push(this.all_headers.name)
      if (this.show_type) {
        value.push(this.all_headers.type)
      }
      if (this.quick_access) {
        value.push(this.all_headers.quick_access)
      }
      return value
    }
  },
  methods: {
    /** Pokazuje pomiary danej metryki */
    showRecords (item) {
      item
      //TODO
    },

    /** Dodaje metrykę pochodną */
    addMetricBy (item) {
      item
      //TODO
    },

    /** Dodaje do wykresu */
    addToChart (item) {
      item
      //TODO
    },

    /** Pobiera dane do wylistowania metryk. */
    reloadList: async function (options = {}) {
      // (mock)
      let data = [
        {
          name: 'Temperatura (D10, 205, stanowisko 1)',
          type: 'temperatura'
        },
        {
          name: 'Zużycie pamięci (D10, 205, stanowisko 1)',
          type: 'zużycie pamięci'
        },
        {
          name: 'Temperatura (D10, 205, stanowisko 2)',
          type: 'temperatura'
        },
        {
          name: 'Zużycie pamięci (D10, 205, stanowisko 2)',
          type: 'zużycie pamięci'
        },
        {
          name: 'Zużycie GPU (Cyfronet, 402, stanowisko 4)',
          type: 'zużycie GPU'
        }
      ]

      if (options.searched) {
        data = data.filter(el =>
          el.name.search(options.searched) != -1
        )
      }

      this.data = data
      return data
    }
  }
}
</script>
