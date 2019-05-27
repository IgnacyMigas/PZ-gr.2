<template>
  <page title="Wykresy">
    Tu będą prezentowane wykresy.

    <div id="container">
      <div></div>
      <div></div>
      <div id="chartsContainer">

        <LineChart class="charts" align="center" />
        <div class="empty"></div>
        Dane historyczne
      <linear-chart class="charts" align="center" width=900 height=300 > </linear-chart>

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
    import LinearChart from '@/components/charts/LinearChart.vue'
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
  .charts {
    text-align: center;
    align-content: center;
    margin: 5px;
  }

  .empty {
    margin-top: 20px;
  }

  #container {
    min-width: 310px;
    max-width: 400px;
    max-height: 100px;
    margin: 0 auto
  }

  #chartsContainer {
    padding: 10px;
  }
</Style>
