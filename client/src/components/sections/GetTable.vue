<template>
  <get-list
    ref='list'
    :title="title"
    :tryGet="tryGet"
    :getOptions="getOptions"
    v-slot="prop"
  >
    <v-data-table
      :headers="headers"
      :items="prop.items"
      hide-actions
      class="elevation-1"
      :no-data-text="no_data_text"
      v-slot:items="props"
    >
      <slot :item="props.item" :index="props.index">
        <!-- Default is for arrays -->
        <td v-for="value in item" :key="value">
          {{ value }}
        </td>
      </slot>
    </v-data-table>
  </get-list>
</template>

<script>
import GetList from '@/components/sections/GetList'

/**
 * Samopobierająca tabela danych.
 *
 * @example
 *  <get-table
 *    title='Hellos'
 *    :headers="[{ text: 'greeting' }]"
 *    :tryGet="tryListHellos">
 *    <template v-slot:default="item">
 *      <td>
 *        Hello, {{ item.name }}
 *      </td>
 *    </template>
 * </get-table>
 *
 * @param {String} title - (required) tytuł
 * @param {Array} headers - (required) nagłówki tabeli
 * @param {Function} tryGet - (required) Funkcja pobierająca dane
 * @param {Object} getOptions - Parametry dla funkcji pobierającej
 * @module components/sections/GetTable
 */
export default {
  name: 'get-table',
  components: {
    'get-list': GetList
  },
  props: {
    /** Tytuł listy. */
    title: {
      type: String,
      required: true
    },

    /** Funkcja pobierająca dane do wylistowania. */
    tryGet: {
      type: Function,
      required: true
    },

    /** Opcje do przekazania funkcji przeładowującej. */
    getOptions: {
      type: Object,
      required: false,
      default: undefined
    },

    /** Nagłówki tabeli. */
    headers: {
      type: Array,
      required: true
    },

    /** Tekst braku danych. */
    no_data_text: {
      type: String,
      required: false,
      default: 'Brak danych do wyświetlenia'
    }
  }
}
</script>
