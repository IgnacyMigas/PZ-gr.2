<template>
  <page title="Logowanie">
    Tu będzie logowanie.

    <v-layout justify-center>
      <v-card-actions>
        <v-btn to='registration'>Zarejestruj się</v-btn>
      </v-card-actions>
    </v-layout>
  </page>
</template>

<script>
import Page from '@/components/templates/Page'

/**
 * Strona logowania.
 *
 * Używana bezpośrednio przez router.
 *
 * @module components/views/Login
 */
export default {
  name: 'login',
  components: {
    'page': Page
  }
}
</script>
