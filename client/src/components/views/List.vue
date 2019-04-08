<template>
  <page title="Lista">
    Tu będzie lista wyszukiwania.

    <v-container fluid fill-height>
      <v-layout justify-center>
        <v-flex xs18 sm12 md6>
          <v-card class="elevation-12">
            <v-card-text>
              <search-bar
                @click:append="toggleMenu"
                :category="listOfSearchFors[0]"
                :categories="listOfSearchFors"
                :onChangeCategory="changeCategory"
                :onSearch="doSearch"
              />
              <span v-if="searched">
                Szukano: ,,{{ searched }}''
              </span>
            </v-card-text>
          </v-card>
          <v-card class="elevation-12">
            <get-list
              v-if="isSearchingForMetrics()"
              title='Metryki'
              :tryGet="tryListMetrics">

              <v-data-table
                :headers="metricsHeaders"
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
            <get-list
              v-if="isSearchingForHosts()"
              title='Hosty'
              :tryGet="tryListHosts">

              <v-data-table
                :headers="hostsHeaders"
                :items="hosts"
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
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </page>
</template>

<script>
import Page from '@/components/templates/Page'
import GetList from '@/components/sections/GetList'
import BarButton from '@/components/elements/BarButton'
import SearchBar from '@/components/elements/SearchBar'

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
    'bar-button': BarButton,
    'search-bar': SearchBar
  },
  data () {
    return {
      searched: '',
      metrics: [],
      metricsHeaders: [
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
      hosts: [],
      hostsHeaders: [
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
      listOfSearchFors: [
        {
          text: 'Metryki',
          value: 'metrics'
        },
        {
          text: 'Hosty',
          value: 'hosts'
        }
      ],
      searchFor: 'metrics'
    }
  },
  methods: {
    /** Jeśli zmieniono kryteria wyszukiwania, aktualizuje listę. */
    doSearch (text) {
      if (text !== this.searched) {
        this.searched = text
        this.reloadList()
      }
    },

    /** Sprawdź, czy szukane są metryki. */
    isSearchingForMetrics () {
      return (this.searchFor === 'metrics')
    },

    /** Sprawdź, czy szukane są hosts. */
    isSearchingForHosts () {
      return (this.searchFor === 'hosts')
    },

    /** Pobiera dane do wylistowania metryk. */
    tryListMetrics: async function () {
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

      if (this.searched) {
        data = data.filter(el =>
          el.name.search(this.searched) != -1
        )
      }

      this.metrics = data
      return data
    },

    /** Pobiera dane do wylistowania hostów. */
    tryListHosts: async function () {
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

      if (this.searched) {
        data = data.filter(el =>
          el.name.search(this.searched) != -1
        )
      }

      this.hosts = data
      return data
    },

    /** Zmienia kategorię i przeładowuje listę. */
    changeCategory(category) {
      this.searchFor = category
      this.reloadList()
    },

    /** Przeładowuje listę. */
    reloadList () {
      if (this.isSearchingForMetrics()) {
        this.tryListMetrics()
      } else {  // hosts
        this.tryListHosts()
      }
    },

    /** Uruchamiana przy autopreładowywaniu */
    reload () {
      this.reloadList()
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
    },

    /** Pokazuje metryki dostępne dla danego hosta */
    showMetrics (item) {
      item
      //TODO
    },

    /** Pokazuje lub ukrywa menu wyszukiwania */
    toggleMenu () {
      //TODO
    }
  }
}
</script>

