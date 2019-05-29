<template>
  <page title="Wykresy">
    <div id="container">
      <div id="chartsContainer">

        <LinearChart />
        <BrushChart />
        <RealtimeChart />
        <Measurements />

      </div>

    </div>

  </page>
</template>

<script>
    import Page from '@/components/templates/Page'
    import LinearChart from '@/components/charts/LinearChart.vue'
    import BrushChart from '@/components/charts/BrushChart.vue'
    import Measurements from '@/components/charts/GetMeasurements.vue'
    import RealtimeChart from '@/components/charts/RealtimeChart.vue'
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
            LinearChart,
            BrushChart,
            RealtimeChart,
            Measurements
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