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
    icon: String,

    /** Funkcja obsługująca kliknięcie */
    handler: Function
  }
}
</script>
