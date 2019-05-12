<template>
  <v-dialog
    v-model="isActive"
  >
    <v-card>
      <v-card-title class="headline">
        Recordy dla: {{ metric_name }}
      </v-card-title>
      <v-card-text>
        <get-list
          ref='list'
          :title='"Recordy dla: {{ metric_name }}"'
          :tryGet="reloadRecords"
          :getOptions="{ id: metric_id }"
          v-slot="props"
        >
          <ul>
            <li
             v-for="prop in props.items"
             :key="prop.ts"
            >
            {{ prop.ts }}: {{ prop.val }} {{ metric_unit }}
            </li>
          </ul>
        </get-list>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
import Vuex from 'vuex'
import GetList from '@/components/sections/GetList'

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
    'get-list': GetList
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
      mymetric: {}
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
    }
  },
  watch: {
    active (value) {
      this.set_mymetric(value)
    }
  }
}
</script>
