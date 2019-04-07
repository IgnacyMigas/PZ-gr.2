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
    icon: String,

    /** Odnośnik routera do odesłania przy kliknięciu. */
    to: String
  }
}
</script>
