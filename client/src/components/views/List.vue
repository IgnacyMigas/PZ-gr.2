<template>
  <page title="Lista">
    Tu będzie lista wyszukiwania.
  </page>
</template>

<script>
import Page from '@/components/templates/Page'

/**
 * Strona listy zasobów.
 *
 * Używana bezpośrednio przez router.
 *
 * @module components/views/List
 */
export default {
  name: 'list',
  components: {
    'page': Page
  }
}
</script>
