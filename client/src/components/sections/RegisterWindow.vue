<template>
  <v-container fluid fill-height>
    <v-layout justify-center>
      <v-flex xs12 sm8 md4>
        <v-card class="elevation-12">
          <form @submit.prevent='trylogin()'>
            <v-card-text>
              <p class='log'>{{log}}</p>
              <p class='error'>{{error}}</p>
              <v-text-field
                prepend-icon="person"
                label='Nazwa'
                placeholder='Nazwa'
                v-model='user.username'
                required />
              <v-text-field
                prepend-icon="lock"
                type='password'
                label='Hasło'
                placeholder='hasło'
                v-model='user.password'
                required />
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn type='submit'>Zarejestruj</v-btn>
            </v-card-actions>
          </form>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import Vuex from 'vuex'

/**@group Sekcje
 * @vuese
 * Sekcja rejestracji.
 */
export default {
  name: 'register',
  data () {
    return {
      user: {
        username: '',
        password: ''
      },
      error: '',
      log: ''
    }
  },
  methods: {
    ...Vuex.mapActions(['sendRegister', 'request']),
    trylogin: async function () {
      const { log, error, data } =
        await this.request(() => this.sendRegister(this.user))

      this.items = data || [];
      this.log = log
      this.error = error ? error.split('\n') : ''

      if (!this.error) {
        this.log = 'Zarejestrowano pomyślnie!\n'
      }
    }
  }
}
</script>
