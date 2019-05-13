<template>
  <v-btn flat :to="to" @click="handler">
    <v-icon left>{{ icon }}</v-icon>
    {{ text }}
  </v-btn>
</template>

<script>
/**
 * Przycisk nawigacji.
 *
 * Używany na pasku nawigacji.
 *
 * @module components/elements/ButtonNav
 */
export default {
  name: 'button-nav',
  props: {
    /** Ikona do wyświetlenia. */
    icon: {
      type: String,
      required: true
    },

    /** Tekst do wyświetlenia. */
    text: {
      type: String,
      required: true
    },

    /** Odnośnik routera do odesłania przy kliknięciu. */
    to: {
      type: String,
      required: false
    },

    /** Funkcja obsługująca kliknięcie */
    handler: {
      type: Function,
      required: false,
      default: () => {}
    }
  }
}
</script>
