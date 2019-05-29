<template>
    <div>
        <apexcharts height=350 align="center" type="line" :options="chartOptions" :series="series"></apexcharts>
    </div>
</template>

<script>

    import VueApexCharts from 'vue-apexcharts'

    // import axios from "axios";
    // const myURL = 'http://localhost:8080/v1/metrics/Battery_testHost/measurements?n=10'

    export default {
        name: 'LinearChart',
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
                        labels: {
                            style: {
                                colors: '#efefef',
                            },
                        },
                    },
                    yaxis: {
                        labels: {
                            style: {
                                color: '#efefef',
                            },
                        },
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
                        align: 'center',
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
        /*
        mounted() {
            axios.get(myURL, {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                },
                proxy: {
                    host: 'http://localhost',
                    port: 8080
                }
            }).then(res => {
                this.ip = res
                //console.log(res)
            }, error => {
                this.ip = error
                //console.error(error);
            });
        }
        */
    }



</script>


           