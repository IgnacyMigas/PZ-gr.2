<template>
    <div>
        <h2>{{ time }}</h2>
        <h2>{{ value }}</h2>
        <h2>{{ blob_samples }}</h2>



        <apexcharts height=350 align="center" type="line" :options="chartOptions" :series="series"></apexcharts>
    </div>
</template>

<script>

    import VueApexCharts from 'vue-apexcharts'

    var host = 'http://localhost:8080'
    var metricsName = 'Battery_testHost'
    var version = 'v1'
    var n = 15
    var url = host + '/' + version + '/metrics/' + metricsName + '/measurements?n=' + n

    var ts = ["yolo"]
    var val = [96]

    export default {
        name: 'LinearChart',
        components: {
            apexcharts: VueApexCharts,
        },
        created() {
            this.$http.get(url, { useCredentails: true }).then(function(data) {
                this.blob_samples = data.body.slice(0, 10);

                var index;
                for (index = 0; index < this.blob_samples.length; ++index) {
                    this.value[index] = parseFloat(this.blob_samples[this.blob_samples.length - index - 1].val),
                        this.time[index] = this.blob_samples[this.blob_samples.length - index - 1].ts,
                        this.$data.val = this.value[index],
                        this.$data.ts = this.time[index]
                }
                //     var vm = this;
                //
                //     setTimeout(function() {
                //         // This works, since wm refers to your view model.
                //         vm.val = this.value
                //         vm.ts = this.time
                //     }, 1000);
                // });
            });
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
                        categories: ts,
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
                    data: val
                }],
                time: [],
                value: [],
                blob_samples: [],

            }
        }
    }



</script>


           