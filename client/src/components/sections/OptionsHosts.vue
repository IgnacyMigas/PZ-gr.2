<template>
  <v-container fluid>
    <v-layout row wrap>
      <v-flex xs12 sm4 md4>
        <v-card flat>
          <v-card-text>
            Ogólne
            <v-text-field
              type='number'
              v-model="options.top"
              label="n najlepszych"
              hide-details
            />
          </v-card-text>
        </v-card>
      </v-flex>
      <v-flex xs12 sm4 md4>
        <v-card flat>
          <v-card-text>
            Tylko typy
            <v-checkbox
              v-for="type in types"
              v-model="options.metric_types"
              :value="type.name"
              :key="type.name"
              :label="type.name"
              hide-details
            />
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
    options: `{{ JSON.stringify(options) }}`
  </v-container>
</template>

<script>
// import BarButton from '@/components/elements/BarButton'

/**
 * Sekcja opcji wyszukiwania hostów.
 *
 * @param {Object} options - obiekt opcji do przekazania do listy hostów
 * @module components/sections/OptionsHosts
 */
export default {
  name: 'options-hosts',
  components: {
    // 'bar-button': BarButton
  },
  props: {
    /** Obiekt opcji do przekazania do listy hostów. */
    options: {
      type: Object,
      required: true,
      default () {
        return {
          metric_types: [],
          top: 0
        }
      }
    },

    /** Dostępne typy metryk. */
    types: {
      type: Array,
      required: false,
      default () {
        return []
      }
    }
  },
  /** Aktualizuje wyjście do rodzica przy każdym wywołaniu wydarzenia. */
  model: {
    prop: 'options',
    event: 'change'
  }
}
</script>
