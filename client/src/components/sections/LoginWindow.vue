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
                type='email'
                label='E-mail'
                placeholder='e-mail'
                v-model='creds.email'
                required />
              <v-text-field
                prepend-icon="lock"
                type='password'
                label='Hasło'
                placeholder='hasło'
                v-model='creds.password'
                required />
            </v-card-text>
            <v-card-actions>
              <v-btn to='registration'>Zarejestruj się</v-btn>
              <v-spacer></v-spacer>
              <v-btn type='submit'>Zaloguj</v-btn>
            </v-card-actions>
          </form>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import Vuex from 'vuex'
import Page from '@/components/templates/Page'

/**@group Sekcje
 * Sekcja logowania.
 */
export default {
  name: 'login',
  components: {
    'page': Page
  },
  data () {
    return {
      creds: {
        email: '',
        password: ''
      },
      error: '',
      log: ''
    }
  },
  methods: {
    ...Vuex.mapActions(['login']),
    ...Vuex.mapMutations(['loginSuccess', 'loginWrongCredentials', 'loginError']),
    trylogin: async function () {
      this.log = ''
      this.error = ''
      try {
        const response = await this.login(this.creds)
        this.log = response.data

        const user = response.data.user
        user.email = this.creds.email
        this.loginSuccess({
          token: response.data.token,
          email: this.creds.email,
          user: response.data.user
        })
        this.$router.push('/home')
      } catch (error) {
        if (error.response) {
          const response = error.response
          if (response.status === 401 || response.status === 403) {
            this.error = response.data.message // 'Niepoprawny login lub hasło'
            this.loginWrongCredentials()
          } else {
            this.error = response.data
            this.loginError()
          }
        } else {
          this.error = 'Brak połączenia z serwerem'
        }
      } finally {
        this.finPending()
      }
    }
  }
}
</script>
