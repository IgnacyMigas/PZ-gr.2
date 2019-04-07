<template>
  <v-btn flat :to="to">
    <v-icon left>{{ icon }}</v-icon>
    <slot></slot>
  </v-btn>
</template>

<script>
/**
 * Przycisk nawigacji.
 *
 * @module components/elements/NavButton
 */
export default {
  name: 'nav-button',
  props: {
    /** Ikona do wyświetlenia. */
    icon: {
      type: String,
      required: true
    },

    /** Odnośnik routera do odesłania przy kliknięciu. */
    to: {
      type: String,
      required: false
    }
  }
}
</script>
