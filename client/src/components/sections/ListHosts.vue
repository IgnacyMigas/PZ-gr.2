<template>
  <get-list
    title='Hosty'
    :tryGet="reloadList">

    <v-data-table
      :headers="headers"
      :items="data"
      hide-actions
      class="elevation-1"
      no-data-text='Brak hostów do wyświetlenia'
    >
      <template slot="items" slot-scope="{ item }">
        <td align="left">
          {{ item.name }}
        </td>
        <td>
          <bar-button
           icon="list"
           :handler="() => showMetrics(item)" />
        </td>
      </template>
    </v-data-table>
  </get-list>
</template>

<script>
import GetList from '@/components/sections/GetList'
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
          text: 'Akcje',
          value: 'actions',
          align: 'center',
          sortable: false
        }
      ],
    }
  },
  methods: {
    /** Pokazuje metryki dostępne dla danego hosta */
    showMetrics (item) {
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
          name: 'D10, 205, stanowisko 1'
        },
        {
          name: 'D10, 205, stanowisko 2'
        },
        {
          name: 'Cyfronet, 402, stanowisko 4'
        },
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
