<template>
  <v-container fluid>
    <v-layout row wrap>
      <v-checkbox
        v-model="options.show_type"
        @change="set_field('show_type', $event)"
        label="wyświetlaj typ"
      />
      <v-checkbox
        v-model="options.quick_access"
        @change="set_field('quick_access', $event)"
        label="szybki dostęp"
      />
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
      required: true
    }
  },
  /** Aktualizuje wyjście do rodzica przy każdym wywołaniu wydarzenia. */
  model: {
    prop: 'options',
    event: 'change'
  },
  methods: {
    set_field (name, value) {
      const field = {[name]: value};
      const copy = {...this.options, ...field};
      this.$emit('change', copy)
    }
  }
}
</script>
