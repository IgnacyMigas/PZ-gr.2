<template>
  <page title='Rejestracja'>
    Tu będzie rejestracja.
  </page>
</template>

<script>
import Page from '@/components/templates/Page'

/**@group Strony
 * @vuese
 * Strona formularza rejestracji.
 *
 * Używana bezpośrednio przez router.
 */
export default {
  name: 'registration',
  components: {
    'page': Page
  }
}
</script>
