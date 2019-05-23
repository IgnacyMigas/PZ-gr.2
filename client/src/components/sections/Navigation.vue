<template>
  <v-toolbar class="ma-3" style="width: auto">
    <v-toolbar-title>
      PZ-gr.2
      <span class="caption" v-if="isLoggedIn">
        ({{ user.username }})
      </span>
    </v-toolbar-title>

    <v-spacer></v-spacer>

    <v-toolbar-items>
      <button-nav
        icon='home'
        text='Dom'
        to='/home'
      />
      <button-nav
        icon='show_chart'
        text='Wykresy'
        to='/charts'
      />
      <button-nav
        icon='list'
        text='Lista'
        to='/list'
      />
      <button-nav
        v-if="!isLoggedIn"
        icon='exit_to_app'
        text='Wyloguj'
        to='/login'
      />
      <button-nav
        v-if="isLoggedIn"
        icon='power_settings_new'
        text='Wyloguj'
        @click.native="logout"
      />
    </v-toolbar-items>
  </v-toolbar>
</template>

<script>
import ButtonNav from '@/components/elements/ButtonNav'
import Vuex from 'vuex'

/**
 * Pasek nawigacji aplikacji. Łączy kilka funkcjonalności:
 * <lu>
 *   <li> nagłówek całego systemu</li>
 *   <li> menu główne programu, uwzględniające
 *        stan autentyfikacji i autoryzacji </li>
 * </lu>
 *
 * @param {string} title - (required)
 * @module components/sections/Navigation
 * @group Sekcje
 */
export default {
  name: 'navigation',
  props: {
    /** Tytuł strony, wyświetlany na pasku. */
    title: String
  },
  components: {
    'button-nav': ButtonNav
  },
  computed: {
    ...Vuex.mapGetters(['isLoggedIn', 'user'])
  },
  methods: {
    ...Vuex.mapActions(['logout'])
  }
}
</script>
