<template>
  <page title="Lista">
    Tu będzie lista wyszukiwania.
    <get-list
      title='Metryki'
      :tryGet="tryListMetrics">

      <p class='log'>{{ log }}</p>
      <p class='error' v-for="err in error" :key="err">
        {{ err }}
      </p>

      <v-data-table
        :headers="headers"
        :items="metrics"
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
             handler="showRecords(item)" />
            <bar-button
             icon="add_circle"
             handler="addMetricBy(item)" />
            <bar-button
             icon="show_chart"
             handler="addToChart(item)" />
          </td>
        </template>
      </v-data-table>
    </get-list>
  </page>
</template>

<script>
import Page from '@/components/templates/Page'
import GetList from '@/components/sections/GetList'
import BarButton from '@/components/elements/BarButton'

/**
 * Strona listy zasobów.
 *
 * Używana bezpośrednio przez router.
 *
 * @module components/views/List
 */
export default {
  name: 'list',
  components: {
    'page': Page,
    'get-list': GetList,
    'bar-button': BarButton
  },
  data () {
    return {
      metrics: [],
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
      ],
      error: '',
      log: ''
    }
  },
  methods: {
    /** Pobiera dane do wylistowania metryk. */
    tryListMetrics: async function () {
      const data = [
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
      this.metrics = data
      return data
    },

    /** Uruchamiana przy autopreładowywaniu */
    reload () {
      this.tryListMetrics()
    },

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
    }
  }
}
</script>
