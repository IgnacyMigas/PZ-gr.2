import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import router from './router'
import store from './store'
import VueResource from 'vue-resource';
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import DatetimePicker from 'vuetify-datetime-picker'
import 'vuetify-datetime-picker/src/stylus/main.styl'

Vue.use(VueResource);
Vue.config.productionTip = false

Vue.use(Vuetify, {
  theme: {
    primary: '#A63F62'
  }
})
Vue.use(DatetimePicker)

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')


