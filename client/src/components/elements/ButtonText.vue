<template>
  <v-btn round :to="to" @click="handler">
    <v-icon left v-if="icon">{{ icon }}</v-icon>
    {{ text }}
  </v-btn>
</template>

<script>
/**
 * Przycisk dla kart i okienek.
 *
 * @module components/elements/ButtonText
 * @group Elementy
 */
export default {
  name: 'button-text',
  props: {
    /** Ikona do wyświetlenia. */
    icon: {
      type: String,
      required: false
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
