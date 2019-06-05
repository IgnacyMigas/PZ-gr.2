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
      <button-nav class="t2e-log"
        v-if="!isLoggedIn"
        icon='exit_to_app'
        text='Zaloguj'
        to='/login'
      />
      <button-nav class="t2e-log"
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

/**@group Sekcje
 * Pasek nawigacji aplikacji. Łączy kilka funkcjonalności:
 * <lu>
 *   <li> nagłówek całego systemu</li>
 *   <li> menu główne programu, uwzględniające
 *        stan autentyfikacji i autoryzacji </li>
 * </lu>
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

<style>
  .t2e-log{
    color: #232323 !important;
    background-color: rgb(221, 221, 221);
    border-bottom-color: rgb(221, 221, 221);
    border-bottom-left-radius: 0px;
    border-bottom-right-radius: 0px;
    border-bottom-style: outset;
    border-bottom-width: 2px;
    border-left-color: rgb(221, 221, 221);
    border-left-style: outset;
    border-left-width: 2px;
    border-right-color: rgb(221, 221, 221);
    border-right-style: outset;
    border-right-width: 2px;
    border-top-color: rgb(221, 221, 221);
    border-top-left-radius: 0px;
    border-top-right-radius: 0px;
    border-top-style: outset;
    border-top-width: 2px;
    box-sizing: border-box;
    cursor: pointer;
    font-size: 13px;
    font-weight: 400;
    height: 58px;
    letter-spacing: normal;
    line-height: 30px;
    margin-bottom: 0px;
    margin-left: 5px;
    margin-right: 0px;
    margin-top: 0px;
    padding-bottom: 1px;
    padding-left: 1px;
    padding-right: 1px;
    padding-top: 1px;
    text-align: left;
    text-indent: 0px;
    text-rendering: auto;
    text-shadow: none;
    text-size-adjust: 100%;
    text-transform: none;
    width: 145px;
  }
</style>
