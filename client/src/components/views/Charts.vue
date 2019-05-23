<template>
  <page title="Wykresy">
    Tu będą prezentowane wykresy.
  </page>
</template>

<script>
import Page from '@/components/templates/Page'

/**
 * Strona prezentacji wykresów.
 *
 * Używana bezpośrednio przez router.
 *
 * @module components/views/Charts
 * @group Strony
 */
export default {
  name: 'charts',
  components: {
    'page': Page
  }
}
</script>
