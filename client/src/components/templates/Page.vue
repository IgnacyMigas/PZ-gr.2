<template>
  <div>
    <navigation :title="title" fixed app m=a-5px></navigation>
    <v-content app>
      <h1 v-if="title !== ''">{{ title }}</h1>

      <slot></slot>
    </v-content>
  </div>
</template>

<script>
import Navigation from '@/components/sections/Navigation'

/**
 * Podstawowy szablon strony.
 *
 * Dodaje [pasek nawigacji]{@link components/Navigation}, nagłówek itd.
 *
 * @example
 * <page title="Hello">
 *   Hello, world!
 * </page>
 *
 * @param {string} title - (required)
 * @module components/templates/Page
 */
export default {
  name: 'page',
  props: {
    title: {
      type: String,
      required: true
    }
  },
  components: {
    'navigation': Navigation
  }
}
</script>
