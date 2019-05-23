<template>
  <page title="Logowanie">
    Tu będzie logowanie.

    <v-layout justify-center>
      <v-card-actions>
        <v-btn @click='login'>Zaloguj się</v-btn>
        <v-btn to='registration'>Zarejestruj się</v-btn>
      </v-card-actions>
    </v-layout>
  </page>
</template>

<script>
import Page from '@/components/templates/Page'
import Vuex from 'vuex'

/**
 * Strona logowania.
 *
 * Używana bezpośrednio przez router.
 *
 * @module components/views/Login
 * @group Strony
 */
export default {
  name: 'login',
  components: {
    'page': Page
  },
  methods: {
    ...Vuex.mapActions(['login'])
  }
}
</script>
