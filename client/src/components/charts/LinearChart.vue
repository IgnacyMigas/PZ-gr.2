<template>
    <div>
        <h1><a @click="created">Show data</a></h1>
        <apexcharts ref="updateChart" height=350 align="left" type="line" :options="chartOptions" :series="series"></apexcharts>
        <!--<h2>{{ time }}</h2>-->
        <!--<h2>{{ value }}</h2>-->
        <!--<h2>{{ blob_samples }}</h2>-->
    </div>
</template>

<script>

    import VueApexCharts from 'vue-apexcharts'

    var host = 'http://localhost:8080'
    var metricsName = 'Battery_testHost'
    var version = 'v1'
    var n = 15
    var url = host + '/' + version + '/metrics/' + metricsName + '/measurements?n=' + n

     var time= []
     var value = []

    export default {
        name: 'LinearChart',
        components: {
            apexcharts: VueApexCharts,
        },
        methods: {
            created: function() {
                this.$http.get(url, {useCredentails: true}).then(function (data) {
                    this.blob_samples = data.body.slice(0, 10);

                    var index;
                    for (index = 0; index < this.blob_samples.length; ++index) {
                        this.value[index] = parseFloat(this.blob_samples[this.blob_samples.length - index - 1].val),
                            this.time[index] = this.blob_samples[this.blob_samples.length - index - 1].ts
                    }

                    this.$refs.updateChart.updateOptions({
                        xaxis: {
                            categories: this.time,
                        },
                        series: [{
                            data: this.value,
                        }],
                    })
                });
            },
        },
            data() {
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
                            colors: ['#F44336'],
                            size: 2
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
                            categories: time.slice(0,10),
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
                            align: 'left',
                            style: {
                                color: '#dedede',
                                fontSize: '16px'
                            },
                        },
                        dataLabels: {
                            enabled: false,
                            colors: ['#1Ca710']
                        },
                        stroke: {
                            curve: 'straight'//smooth
                        },
                    },
                    series: [{
                        name: 'value',
                        data: value.slice(0,10)
                    }],
                    time: [],
                    value: [],
                    blob_samples: [],

                    mounted: function () {
                        this.created()
                    },
                }
            }
        }

</script>


           