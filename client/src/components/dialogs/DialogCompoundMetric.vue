<template>
  <v-dialog
    v-model="isActive"
    width=700
  >
    <v-toolbar dark>
      <v-toolbar-title>
        Stwórz metrykę złożoną dla: {{ metric_name }}
      </v-toolbar-title>

      <v-spacer></v-spacer>

      <v-toolbar-items>
        <!-- Przycisk wyjścia z okna. -->
        <button-bar
         icon = "clear"
         :handler = "() => isActive = false"
        />
      </v-toolbar-items>
    </v-toolbar>
    <v-card>
      <form @submit.prevent='try_new_metric()'>
        <v-layout row wrap>
          <v-flex xs12 sm4 md4>
            <v-card flat>
              <v-card-text>
                <!-- Z jakiego czasu średnia. -->
                <v-text-field
                  type='number'
                  v-model="options.time"
                  label="okno czasowe (s)"
                  hide-details
                />
              </v-card-text>
            </v-card>
          </v-flex>
        </v-layout>

        <v-card-text>
          <p class='log'>{{log}}</p>
          <p class='error'
           v-for='error in errors'
           v-bind:key='error'
          >
            {{error}}
          </p>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn type='submit'>Stwórz</v-btn>
        </v-card-actions>
      </form>
    </v-card>
  </v-dialog>
</template>

<script>
import Vuex from 'vuex'
import ButtonBar from '@/components/elements/ButtonBar'
import ButtonText from '@/components/elements/ButtonText'

/**@group Okienka
 * Dialog recordów metryki.
 *
 * Zawiera listę rekordów oraz przyciski pozwalające modyfikować jej opcje:
 * <ul>
 *   <li> ile --- rekordów do wyświetlenia
 *        (niedodatnie wartości znaczą 'wszystkie') </li>
 *   <li> wszystkie --- resetuje liczbę rekordów do wyświetlenia </li>
 * </ul>
 */
export default {
  name: 'dialog-compound-metric',
  components: {
    'button-bar': ButtonBar,
    'button-text': ButtonText
  },
  props: {
    /** Czy dialog ma być aktywny.
     *
     *  Zalecane użycie opcji v-model.
     */
    active: {
      type: Boolean,
      required: true
    },

    /** Metryka, dla której tworzona będzie metryka pochodna. */
    metric: {
      // {
      //   'metric-id': String,
      //   name: String,
      //   unit: String,
      //   'monitor-id': String
      // }
      type: Object,
      required: true
    },

    /** Funkcja do wywołania przy stworzeniu metryki. */
    handler: {
      // (String) => {}
      type: Function,
      required: false,
      // nop
      default () {
        return (_item) => {}
      }
    }
  },
  /** Aktualizuje flagę aktywności dialogu. */
  model: {
    prop: 'active',
    event: 'activityChanged'
  },
  data () {
    return {
      options: {
        time: null
      },
      errors: [],
      log: ''
    }
  },
  computed: {
    /** Widok metryki resetowany po zamknięciu okna. */
    mymetric () {
      if (!this.active) {  // closing --- reset to no metric
        return {}
      } else {
        return this.metric
      }
    },

    /** Id metryki. */
    metric_id () {
      return this.mymetric['metric-id']
    },

    /** Id monitora. */
    monitor_id () {
      return this.mymetric['monitor-id']
    },

    /** Nazwa metryki. */
    metric_name () {
      return this.mymetric.name || ''
    },

    /** Jednostka metryki. */
    metric_unit () {
      return this.mymetric.unit || ''
    },

    /** Type metryki. */
    metric_type () {
      return this.mymetric.type || ''
    },

    /** Wszystkie opcje zapytania. */
    all_options () {
      return {
        ...this.options,
        'metric-ids': (this.metric_id && [ this.metric_id ]) || [],
        'monitor-id': this.monitor_id,
        type: this.metric_type,
        kind: 'moving-avg'
      }
    },

    /** Czy okno jest aktywne. Nieaktywne okno resetuje swoje dane. */
    isActive: {
      get () {
        return this.active
      },
      set (value) {
        // nextTick pozwala get-table zdążyć zresetować widok
        this.$nextTick(() => {
          // Wyzwalane kiedy okno jest włączane lub wyłączane.
          // @arg Nowy stan aktywności okna (patrz: active).
          this.$emit('activityChanged', value)
        })
      }
    }
  },
  methods: {
    ...Vuex.mapActions(['postNewMetric', 'request']),
    try_new_metric: async function () {
      if (!this.all_options['metric-ids']) {
        return
      }

      const { log, error, data } =
        await this.request(() => this.postNewMetric(this.all_options))

      this.items = data || [];
      this.log = log
      this.errors = error ? error.split('\n') : ''

      if (!this.errors) {
        const id = data['metric-id']
        if (!this.log) {
          this.log = 'Stworzono: ' + id + '\n'
        }
        this.handler(id)
      }
    }
  },
  watch: {
    active (value) {
      this.isActive = value
    }
  }
}
</script>
