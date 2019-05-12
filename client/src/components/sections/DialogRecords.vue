<template>
  <v-dialog
    v-model="isActive"
  >
    <v-card>
      <v-card-title class="headline">
        Recordy dla: {{ metric_name }}
      </v-card-title>
      <v-card-text>
        <ul>
          <li
            v-for="measurement in measurements"
            :key="measurement.ts"
          >
            {{ measurement.ts }}: {{ measurement.value }}
          </li>
        </ul>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
/**
 * Dialog recordów metryki.
 *
 * @param {Boolean} active - czy dialog ma być aktywny (zalecane v-model)
 * @param {Object} metric - obiekt metryki
 * @module components/sections/DialogRecords
 */
export default {
  name: 'dialog-records',
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
      measurements: [
        {
          ts: 1,
          value: 1
        },
        {
          ts: 2,
          value: 4
        },
        {
          ts: 3,
          value: 9
        }
      ]
    }
  },
  computed: {
    metric_id () {
      return this.metric['metric-id']
    },

    metric_name () {
      return this.metric.name || ''
    },

    isActive: {
      get () {
        return this.active
      },
      set (value) {
        this.$emit('activityChanged', value)
      }
    }
  }
}
</script>
