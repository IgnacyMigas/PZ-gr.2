<template>
  <v-btn
    flat icon
    @click.stop="handler">
    <v-icon list>{{ icon }}</v-icon>
  </v-btn>
</template>

<script>
/**
 * Przycisk dla pasków i list.
 *
 * @module components/elements/ButtonBar
 * @group Elementy
 */
export default {
  name: 'button-bar',
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
