<template>
  <v-container fluid>
    <v-layout row wrap>
      <v-flex xs12 sm4 md4>
        <v-card flat>
          <v-card-text>
            Ogólne
            <v-checkbox
              v-model="options.quick_access"
              label="szybki dostęp"
              hide-details
            />
            <v-checkbox
              v-model="options.show_type"
              label="wyświetlaj typ"
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
              v-model="options.types"
              :value="type.name"
              :key="type.name"
              :label="type.name"
              hide-details
            />
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
// import BarButton from '@/components/elements/BarButton'

/**
 * Sekcja opcji wyszukiwania metryk.
 *
 * @param {Object} options - obiekt opcji do przekazania do listy metryk
 * @module components/sections/OptionsMetrics
 */
export default {
  name: 'options-metrics',
  components: {
    // 'bar-button': BarButton
  },
  props: {
    /** Obiekt opcji do przekazania do listy metryk. */
    options: {
      type: Object,
      required: true,
      default () {
        return {
          quick_access: true,
          show_type: true,
          types: []
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
