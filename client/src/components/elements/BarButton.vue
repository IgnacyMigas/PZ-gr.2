<template>
  <v-btn
    flat icon
    @click="handler">
    <v-icon list>{{ icon }}</v-icon>
  </v-btn>
</template>

<script>
/**
 * Przycisk dla pasków i list.
 *
 * @module components/elements/BarButton
 */
export default {
  name: 'bar-button',
  props: {
    /** Ikona do wyświetlenia. */
    icon: {
      type: String,
      required: true
    },

    /** Funkcja obsługująca kliknięcie */
    handler: {
      type: Function,
      required: true
    }
  }
}
</script>
