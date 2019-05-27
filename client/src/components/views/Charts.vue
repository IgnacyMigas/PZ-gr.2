<template>
  <page title="Wykresy">
    Tu będą prezentowane wykresy.

    <div id="container">
      <div id="chartsContainer">
        Dane historyczne
        <LineChart class="myChart" />

        Dane historyczne
        <linear-chart :width="1100" :height="300" class="myChart" align="center"></linear-chart>
        Zoom chart
        <!--
         <ZoomableChart />
         -->
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
  #container {
    min-width: 310px;
    max-width: 400px;
    min-height: 100px;
    margin: 0 auto
  }

  #chartsContainer {
    padding: 10px;
  }

  .myChart {
    margin-top: 30px;
    padding: 40px;
  }

</Style>