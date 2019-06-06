<!--
https://apexcharts.com/vue-chart-demos/line-charts/realtime/
-->
<template>
    <div id="chart">
        <apexchart ref="realtimeChart" type=line height=350 :options="chartOptions" :series="series" />
    </div>
</template>

<script>
    import VueApexCharts from 'vue-apexcharts'

    var lastDate = 0;
    var data = []

    function getDayWiseTimeSeries(baseval, count, yrange) {
        var i = 0;
        while (i < count) {
            var x = baseval;
            var y = Math.floor(Math.random() * (yrange.max - yrange.min + 1)) + yrange.min;

            data.push({
                x,
                y
            });
            lastDate = baseval
            baseval += 86400000;
            i++;
        }
    }

    getDayWiseTimeSeries(new Date('11 Feb 2017 GMT').getTime(), 10, {
        min: 10,
        max: 90
    })

    function getNewSeries(baseval, yrange) {
        var newDate = baseval + 86400000;
        lastDate = newDate
        data.push({
            x: newDate,
            y: Math.floor(Math.random() * (yrange.max - yrange.min + 1)) + yrange.min
        })
    }

    function resetData() {
        data = data.slice(data.length - 10, data.length);
    }

    export default {
        name: 'realtimeChart',
        components: {
            apexchart: VueApexCharts,
        },
        data: function () {
            return {
                series: [{
                    name: 'value',
                    data: data.slice()
                }],
                chartOptions: {
                    chart: {
                        animations: {
                            enabled: true,
                            easing: 'linear',
                            dynamicAnimation: {
                                speed: 1000
                            }
                        },
                        toolbar: {
                            show: true
                        },
                        zoom: {
                            enabled: false
                        }
                    },
                    dataLabels: {
                        enabled: false
                    },
                    stroke: {
                        curve: 'smooth'
                    },

                    title: {
                        text: 'Dynamic Updating Chart',
                        align: 'center',
                        style: {
                            color: '#dedede',
                            fontSize: '12px'
                        },
                    },
                    grid: {
                        row: {
                            colors: ['#dedede', 'transparent'], // takes an array which will be repeated on columns
                            opacity: 0.1
                        },
                        column: {
                            colors: ['#dedede']
                        }
                    },

                    xaxis: {
                        type: 'datetime',
                        range: 777600000,
                        labels: {
                            style: {
                                colors: '#efefef',
                            },
                        },
                    },
                    yaxis: {
                        max: 100,
                        labels: {
                            style: {
                                color: '#efefef',
                            },
                        },
                    },
                    tooltip: {
                        followCursor: false,
                        theme: 'dark',
                        x: {
                            show: true
                        },
                        marker: {
                            show: true
                        }
                    },
                    legend: {
                        show: true
                    }
                },
                mounted: function () {
                    this.intervals()
                },
                methods: {
                    intervals: function () {
                        var me = this
                        window.setInterval(function () {
                            getNewSeries(lastDate, {
                                min: 10,
                                max: 90
                            })

                            me.$refs.realtimeChart.updateSeries([{
                                data: data
                            }])
                        }, 1000)

                        // every 1 seconds, we reset the data to prevent memory leaks
                        window.setInterval(function () {
                            resetData()
                            me.$refs.realtimeChart.updateSeries([{
                                data
                            }], false, true)
                        }, 1000)
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>