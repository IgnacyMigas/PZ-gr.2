<template>
  <get-list
    title='Metryki'
e   :tryGet="reloadList">

    <v-data-table
      :headers="headers"
      :items="data"
      hide-actions
      class="elevation-1"
      no-data-text='Brak metryk do wyświetlenia'
    >
      <template slot="items" slot-scope="{ item }">
        <td align="left">
          {{ item.name }}
        </td>
        <td align="right" class="caption">
          ( {{ item.type }} )
        </td>
        <td>
          <bar-button
           icon="list"
           :handler="() => showRecords(item)" />
          <bar-button
           icon="add_circle"
           :handler="() => addMetricBy(item)" />
          <bar-button
           icon="show_chart"
           :handler="() => addToChart(item)" />
        </td>
      </template>
    </v-data-table>
  </get-list>
</template>

<script>
import GetList from '@/components/sections/GetList'
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
    'get-list': GetList,
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
          text: 'Typ',
          value: 'type',
          align: 'right',
          sortable: true
        },
        {
          text: 'Akcje',
          value: 'actions',
          align: 'center',
          sortable: false
        }
      ]
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
    reloadList: async function (searched) {
      if (searched === undefined) {
        searched = this.searched
      }

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

      if (searched) {
        data = data.filter(el =>
          el.name.search(searched) != -1
        )
      }

      this.data = data
      return data
    }
  },
  watch: {
    searched (newVal) {  // newVal, oldVal
      this.reloadList(newVal)
    }
  }
}
</script>
