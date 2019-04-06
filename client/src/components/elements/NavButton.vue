<template>
  <v-btn flat :to="to">
    <v-icon left>{{ icon }}</v-icon>
    <slot></slot>
  </v-btn>
</template>

<script>
/**
 * Navigation button.
 * @module components/elements/NavButton
 */
export default {
  name: 'nav-button',
  props: {
    icon: String,
    to: String
  }
}
</script>
