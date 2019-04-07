<template>
  <v-text-field
    class="ml-2"
    label="Szukaj..."
    prepend-icon="search"
    :clearable=true
    append-icon="menu"
    @change="handler($event)">
  </v-text-field>
</template>

<script>
/**
 * Pasek wyszukiwania.
 *
 * @module components/elements/SearchBar
 */
export default {
  name: 'search-bar',
  props: {
    /** Funkcja wywoływana przy zmianie wartości. */
    handler: {
      type: Function,
      required: true
    }
  }
}
</script>
