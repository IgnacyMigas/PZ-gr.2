<template>
    <div>
        <apexcharts width="1400" height="600" type="line" :options="chartOptions" :series="series"></apexcharts>
    </div>
</template>

<script>
    import VueApexCharts from 'vue-apexcharts'

    export default {
        name: 'LineChart',
        components: {
            apexcharts: VueApexCharts,
        },
        data: function() {
            return {
                chartOptions: {
                    chart: {
                        id: 'basic-bar'
                    },
                    colors: ['#008FFB'],
                    tooltip: {
                        followCursor: false,
                        theme: 'dark',
                        x: {
                            show: false
                        },
                        marker: {
                            show: false
                        }
                    },
                    xaxis: {
                        categories: ['26/05/2019 01:05:35', '26/05/2019 01:05:36', '26/05/2019 01:05:37', '26/05/2019 01:05:38', '26/05/2019 01:05:39', '26/05/2019 01:05:40', '26/05/2019 01:05:41', '26/05/2019 01:05:42', '26/05/2019 01:05:43', '26/05/2019 01:05:44']
                    },
                    grid: {
                        row: {
                            colors: ['#676767', 'transparent'], // takes an array which will be repeated on columns
                            opacity: 0.4
                        },
                    },
                    title: {
                        text: 'Dane historyczne - wykres liniowy',
                        align: 'left'
                    },
                    dataLabels: {
                        enabled: false
                    },
                    stroke: {
                        curve: 'straight'
                    },
                },
                series: [{
                    name: 'value',
                    colors: '#121212',
                    data: [0.81, 0.81, 0.81, 0.81, 0.84, 0.99, 0.99, 0.97, 0.95, 0.96]
                }]
            }
        },
    }
</script>


           