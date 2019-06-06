<template>
  <v-dialog
    v-model="isActive"
    width=350
  >
    <v-card>
      <v-card-text>
        <p class='error'
         v-for='error in errors'
         v-bind:key='error'
        >
          {{ error }}
        </p>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>

/**@group Okienka
 * Dialog niepowodzenia operacji. Zgłasza błędy.
 */
export default {
  name: 'dialog-errors',
  props: {
    /** Czy dialog ma być aktywny.
     *
     *  Zalecane użycie opcji v-model.
     */
    active: {
      type: Boolean,
      required: true
    },

    /** Błędy do wyświetlenia. */
    errors: {
      // [String...]
      type: Array,
      required: true
    }
  },
  /** Aktualizuje flagę aktywności dialogu. */
  model: {
    prop: 'active',
    event: 'activityChanged'
  },
  computed: {
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
  watch: {
    active (value) {
      this.isActive = value
    }
  }
}
</script>
