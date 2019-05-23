<template>
  <v-dialog
    v-model="isActive"
    width=500
  >
    <v-toolbar dark>
      <v-toolbar-title>
        Recordy dla: {{ metric_name }}
      </v-toolbar-title>

      <v-spacer></v-spacer>

      <v-toolbar-items>
        <button-bar
         icon = "show_chart"
         :handler = "() => addToStaticChart()"
        />
        <button-bar
         icon = "clear"
         :handler = "() => isActive = false"
        />
      </v-toolbar-items>
    </v-toolbar>
    <v-card>
      <v-layout row wrap>
        <v-flex xs12 sm4 md4>
          <v-card flat>
            <v-card-text>
              <v-text-field
                type='number'
                v-model="options.n"
                label="ile"
                hide-details
              />
              <button-text
                text = 'wszystkie'
                :handler = '() => options.n = 0'
              />
            </v-card-text>
          </v-card>
        </v-flex>
      </v-layout>

      <get-list
        ref='list'
        :title='"Recordy dla: {{ metric_name }}"'
        :tryGet="reloadRecords"
        :getOptions="all_options"
        v-slot="props"
      >
        <v-container>
          <v-layout
            row
            justify-space-between
            v-for="item in props.items"
            :key="item.ts"
          >
            <v-flex xs4 class="text-xs-right">
              {{ item.val }} {{ metric_unit }}
            </v-flex>
            <v-flex xs4 class="text-xs-left">
              {{ item.ts }}
            </v-flex>
          </v-layout>
        </v-container>
      </get-list>
    </v-card>
  </v-dialog>
</template>

<script>
import Vuex from 'vuex'
import GetList from '@/components/sections/GetList'
import ButtonBar from '@/components/elements/ButtonBar'
import ButtonText from '@/components/elements/ButtonText'

/**
 * Dialog recordów metryki.
 *
 * @param {Boolean} active - czy dialog ma być aktywny (zalecane v-model)
 * @param {Object} metric - obiekt metryki
 * @module components/sections/DialogRecords
 */
export default {
  name: 'dialog-records',
  components: {
    'get-list': GetList,
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

    /** Metryka, dla której wyświetlane są rekordy. */
    metric: {
      type: Object,
      required: true
    }
  },
  /** Aktualizuje flagę aktywności dialogu. */
  model: {
    prop: 'active',
    event: 'activityChanged'
  },
  data () {
    return {
      mymetric: {},
      options: {
        n: 0
      }
    }
  },
  computed: {
    metric_id () {
      return this.mymetric['metric-id']
    },

    metric_name () {
      return this.mymetric.name || ''
    },

    metric_unit () {
      return this.mymetric.unit || ''
    },

    all_options () {
      return { ...this.options, id: this.metric_id }
    },

    isActive: {
      get () {
        return this.active
      },
      set (value) {
        this.set_mymetric(value)

        // nextTick pozwala get-table zdążyć zresetować widok
        this.$nextTick(() => this.$emit('activityChanged', value))
      }
    }
  },
  methods: {
    ...Vuex.mapActions(['listRecords']),

    /** Pobiera dane do wylistowania pomiarów.
     *
     *  Uwaga: jeśli options nie zawiera id,
     *  zapytanie nie zostanie wykonane.
     */
    reloadRecords: async function (options) {
      if (options.id) {
        return await this.listRecords(options)
      } else {
        return []
      }
    },

    /** Resetuje metrykę przy zamknieciu okna lub ustawia po otwarciu. */
    set_mymetric (value) {
      if (!value) {  // closing --- reset to no metric
        this.mymetric = {}
      } else {
        this.mymetric = this.metric
      }
    },
    
    /** Dodaje pomiary (nie metrykę) do statycznego wykresu. */
    addToStaticChart () {
      const records = this.get_records()
      // eslint-disable-next-line
      console.log("Records: " + JSON.stringify(records))
      //TODO
    },

    get_records () {
      return this.$refs.list.get_items
    }
  },
  watch: {
    active (value) {
      this.set_mymetric(value)
    }
  }
}
</script>
