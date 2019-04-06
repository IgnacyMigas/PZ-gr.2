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
      <nav-button
        icon='home'
        to='/home'>
        Dom
      </nav-button>
      <nav-button
        icon='show_chart'
        to='/charts'>
        Wykresy
      </nav-button>
      <nav-button
        icon='list'
        to='/list'>
        Lista
      </nav-button>
      <nav-button
        v-if="!isLoggedIn"
        icon='exit_to_app'
        to='/login'>
        Zaloguj
      </nav-button>
      <nav-button
        v-if="isLoggedIn"
        icon='power_settings_new'
        @click.native="logout">
        Wyloguj
      </nav-button>
    </v-toolbar-items>
  </v-toolbar>
</template>

<script>
import NavButton from '@/components/elements/NavButton'

/**
 * Pasek nawigacji aplikacji. Łączy kilka funkcjonalności:
 * <lu>
 *   <li> nagłówek całego systemu</li>
 *   <li> menu główne programu, uwzględniające
 *        stan autentyfikacji i autoryzacji </li>
 * </lu>
 *
 * @example
 * <navbar title="Hello"/>
 *
 * @param {string} title - (required)
 * @module components/sections/Navigation
 */
export default {
  name: 'navigation',
  props: {
    /** Tytuł strony, wyświetlany na pasku. */
    title: String
  },
  components: {
    'nav-button': NavButton
  },
  data () {
    return {
      isLoggedIn: true,
      user: {
        username: 'someuser',
        password: 'somepass'
      }
    }
  },
  methods: {
    logout() {
      this.isLoggedIn = false
    }
  }
}
</script>
