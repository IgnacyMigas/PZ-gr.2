<template>
  <page title="Strona Główna">
    Witamy na stronie głównej.
  </page>
</template>

<script>
import Page from '@/components/templates/Page'

/**
 * Strona główna serwisu.
 *
 * Używana bezpośrednio przez router.
 *
 * @module components/views/Home
 * @group Strony
 */
export default {
  name: 'home',
  components: {
    'page': Page
  }
}
</script>
