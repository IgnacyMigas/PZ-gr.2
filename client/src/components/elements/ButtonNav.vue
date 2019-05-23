<template>
  <v-btn flat :to="to" @click="handler">
    <v-icon left>{{ icon }}</v-icon>
    {{ text }}
  </v-btn>
</template>

<script>
/**
 * Przycisk paska nawigacji.
 *
 * @module components/elements/ButtonNav
 * @group Elementy
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
      // brak obsługi
      default: () => {}
    }
  }
}
</script>
