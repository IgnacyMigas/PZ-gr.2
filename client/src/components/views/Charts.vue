<template>
  <page title="Wykresy">
    Tu będą prezentowane wykresy.

    <div id="container">
      <div></div>
      <div></div>
      <div id="chartsContainer">
      Dane historyczne
      <linear-chart :width="300" :height="300"> </linear-chart>
        Zoom chart
        <LineChart />
        Online
        <!--
        <RealtimeChart />
        -->
      </div>
    </div>
  </page>
</template>

<script>
    import Page from '@/components/templates/Page'
    import LinearChart from '@/components/charts/LinearChart'
    import LineChart from '@/components/charts/LineChart.vue'

    /*
    import RealtimeChart from '@/components/charts/RealtimeChart.vue'
    */

  /**@group Strony
   * @vuese
   * Strona prezentacji wykresów.
   *
   * Używana bezpośrednio przez router.
   */


export default {
    name: 'Charts',
    components: {
        'page': Page,
        LineChart,
        LinearChart
        /*
        TODO: RealtimeChart
        ,
        RealtimeChart
         */
    }
}

</script>

<Style>
  #container {
    min-width: 310px;
    max-width: 400px;
    max-height: 100px;
    margin: 0 auto
  }
  LinearChart {
    width: 400px;
    height: 100px;
  }
  #chartsContainer {
    padding: 10px;
  }
</Style>
