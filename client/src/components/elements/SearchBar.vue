<template>
  <v-layout align-center class="search-bar">
    <v-flex shrink>
      <v-select
        class="search-bar-category"
        :value="category"
        :items="categories"
        item-text="text"
        item-value="value"
        prepend-icon="search"
        color="gray"
        @change="onChangeCategory($event)"
      />
    </v-flex>
    <v-flex grow>
      <v-text-field
        class="search-bar-text"
        placeholder="Szukaj..."
        :clearable=true
        append-icon="menu"
        @change="onSearch($event)">
      </v-text-field>
    </v-flex>
  </v-layout>
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
    onSearch: {
      type: Function,
      required: true
    },

    /** Funkcja wywoływana przy zmianie kategorii. */
    onChangeCategory: {
      type: Function,
      required: false,
      default: () => {}
    },

    /** Domyślna kategoria szukanego elementu.
     *
     *  Obiekt o postaci: { text: ..., value: ... }
     */
    category: {
      required: false
    },

    /** Dostępne kategorie wyszukiwania. */
    categories: {
      type: Array,
      required: true
    }
  }
}
</script>

<style scoped>
.search-bar-category >>> .v-select__selection {
  color: gray
}
.search-bar-category >>> .v-select__selections > input {
  width: 5px !important
}
.search-bar-category >>> .v-select__slot > .v-input__append-inner .v-icon {
  color: gray
}

.search-bar-text {
  margin-left: -10px !important;
}
.search-bar-text >>> .v-input__slot {
  padding-left: 20px !important;
}
</style>
