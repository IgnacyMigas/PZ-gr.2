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
                :onChangeShowOptions="changeShowOptions"
                :onChangeCategory="changeCategory"
                :onSearch="doSearch"
              />

              <options-metrics
                v-if="showOptions && isSearchingForMetrics"
                v-model="options.metrics"
                :types="types"
              />

              <options-hosts
                v-if="showOptions && isSearchingForHosts"
                v-model="options.hosts"
                :types="types"
              />

            </v-card-text>
          </v-card>
          <v-card class="elevation-12">
            <list-metrics
              v-if="isSearchingForMetrics"
              :options="options.metrics"
              :searched="searched"
            />
            <list-hosts
              v-if="isSearchingForHosts"
              :options="options.hosts"
              :searched="searched"
            />
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </page>
</template>

<script>
import Page from '@/components/templates/Page'
import OptionsMetrics from '@/components/sections/OptionsMetrics'
import OptionsHosts from '@/components/sections/OptionsHosts'
import ListMetrics from '@/components/sections/ListMetrics'
import ListHosts from '@/components/sections/ListHosts'
// import BarButton from '@/components/elements/BarButton'
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
    'options-metrics': OptionsMetrics,
    'options-hosts': OptionsHosts,
    'list-metrics': ListMetrics,
    'list-hosts': ListHosts,
    'search-bar': SearchBar
  },
  data () {
    return {
      searched: '',
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
      searchFor: 'metrics',
      showOptions: false,
      options: {
        metrics: {
          quick_access: true,
          show_type: true,
          types: []
        },
        hosts: {
          quick_access: true,
          metric_types: [],
          top: 0
        }
      },
      //TODO: unmock
      types: [
        {
          name: 'temperatura',
          unit: '°C'
        },
        {
          name: 'zużycie pamięci',
          unit: 'MB'
        },
        {
          name: 'zużycie pamięci GPU',
          unit: 'MB'
        },
        {
          name: 'zużycie GPU',
          unit: 'flops'
        }
      ]
    }
  },
  computed: {
    /** Sprawdź, czy szukane są metryki. */
    isSearchingForMetrics () {
      return (this.searchFor === 'metrics')
    },

    /** Sprawdź, czy szukane są hosts. */
    isSearchingForHosts () {
      return (this.searchFor === 'hosts')
    }
  },
  methods: {
    /** Jeśli zmieniono kryteria wyszukiwania, aktualizuje listę. */
    doSearch (text) {
      if (text !== this.searched) {
        this.searched = text
      }
    },

    /** Zmienia opcje. */
    changeShowOptions(value) {
      if (value !== this.showOptions) {
        this.showOptions = value
      }
    },

    /** Zmienia kategorię i przeładowuje listę. */
    changeCategory(category) {
      if (category !== this.searchFor) {
        this.searchFor = category
      }
    },

    /** Pokazuje lub ukrywa menu wyszukiwania */
    toggleMenu () {
      //TODO
    }
  }
}
</script>

