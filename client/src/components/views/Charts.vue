<template>
  <page title="Wykresy">
    Tu będą prezentowane wykresy.
  </page>
</template>

<script>
import Page from '@/components/templates/Page'

/**@group Strony
 * @vuese
 * Strona prezentacji wykresów.
 *
 * Używana bezpośrednio przez router.
 */
export default {
  name: 'charts',
  components: {
    'page': Page
  }
}
</script>
