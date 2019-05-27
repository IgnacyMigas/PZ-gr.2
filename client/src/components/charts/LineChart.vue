<template>
    <div>
        <apexcharts width="1100" height="350" type="line" :options="chartOptions" :series="series"></apexcharts>
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
                    labels: {
                        style: {
                            colors: ['#dede12']
                        }
                    },
                    markers: {
                        colors: ['#F44336']
                    },
                    colors: ['#ee8FFB'],
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
                        categories: ['26/05/2019 01:05:35', '26/05/2019 01:05:36', '26/05/2019 01:05:37', '26/05/2019 01:05:38', '26/05/2019 01:05:39', '26/05/2019 01:05:40', '26/05/2019 01:05:41', '26/05/2019 01:05:42', '26/05/2019 01:05:43', '26/05/2019 01:05:44'],
                        colors: ['#dede12']
                    },
                    axisBorder: {
                        show: true,
                        color: '#dede12'
                    },
                    grid: {
                        row: {
                            colors: ['#676767', 'transparent'], // takes an array which will be repeated on columns
                            opacity: 0.4
                        },
                        column: {
                            colors: ['#343434']
                        }
                    },
                    title: {
                        text: 'Dane historyczne - wykres liniowy',
                        align: 'left',
                        style: {
                            color: '#dedede',
                            fontSize: '12px'
                        },
                    },
                    dataLabels: {
                        enabled: false,
                        colors: ['#1Ca710']
                    },
                    stroke: {
                        curve: 'straight'
                    }
                },
                series: [{
                    name: 'value',
                    data: [0.81, 0.81, 0.81, 0.81, 0.84, 0.99, 0.99, 0.97, 0.95, 0.96]
                }]
            }
        },
    }
</script>


           