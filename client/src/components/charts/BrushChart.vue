<template>
    <div id="app">
        <div id="chart1">
            <apexchart type=line height=230 :options="chartOptionsArea" :series="series" />
        </div>
        <div id="chart2">
            <apexchart type=area height=130 :options="chartOptionsBrush" :series="series" />
        </div>
    </div>
</template>

<script>

    //import Vue from 'vue'
    import VueApexCharts from 'vue-apexcharts'

    //Vue.component('apexchart', VueApexCharts)

    export default {
        name: 'BrushChart',
        components: {
            apexchart: VueApexCharts,
        },
        data: function () {
            return {
                series: [{
                    name: 'value',
                    data: this.generateDayWiseTimeSeries(new Date('11 Feb 2017').getTime(), 185, {
                        min: 30,
                        max: 90
                    })
                }],
                chartOptionsArea: {
                    chart: {
                        id: 'chartArea',
                        toolbar: {
                            autoSelected: 'pan',
                            show: false
                        }
                    },
                    title: {
                        text: 'Dane historyczne - Brush chart',
                        align: 'center',
                        style: {
                            color: '#dedede',
                            fontSize: '12px'
                        },
                    },                    colors: ['#34dE5A'],
                    stroke: {
                        width: 3,
                        curve: 'straight'
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
                    dataLabels: {
                        enabled: false
                    },

                    fill: {
                        opacity: 1,
                    },
                    markers: {
                        //size: 0,
                        colors: ['#149E5A'],
                    },
                    xaxis: {
                        type: 'datetime',
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
                    // crosshairs: {
                    //     show: true,
                    //     width: 1,
                    //     position: 'back',
                    //     opacity: 0.9,
                    //     stroke: {
                    //         color: '#343434',
                    //         width: 0,
                    //         dashArray: 0,
                    //     },
                    //     fill: {
                    //         type: 'solid',
                    //         color: '#343434',
                    //     },
                    //     dropShadow: {
                    //         enabled: false,
                    //         top: 0,
                    //         left: 0,
                    //         blur: 1,
                    //         opacity: 0.4,
                    //     },
                    // },
                    grid: {
                        row: {
                            colors: ['#cdcdcd', 'transparent'], // takes an array which will be repeated on columns
                            opacity: 0.4,
                        },
                        column: {
                            colors: ['#343434'],
                        },
                    },
                },
                chartOptionsBrush: {
                    chart: {
                        id: 'chartBrush',
                        brush: {
                            target: 'chartArea',
                            enabled: true
                        },
                        selection: {
                            enabled: true,
                            xaxis: {
                                min: new Date('19 Jun 2017').getTime(),
                                max: new Date('14 Aug 2017').getTime()
                            }
                        },
                    },
                    grid: {
                        row: {
                            colors: ['#dedede', 'transparent'], // takes an array which will be repeated on columns
                            opacity: 0.2
                        },
                        column: {
                            colors: ['#dedede']
                        }
                    },
                    colors: ['#147E5A'],
                    fill: {
                        type: 'gradient',
                        gradient: {
                            opacityFrom: 0.9,
                            opacityTo: 0.6,
                        }
                    },
                    xaxis: {
                        type: 'datetime',
                        tooltip: {
                            enabled: false
                        },
                        labels: {
                            style: {
                                colors: '#efefef',
                            },
                        },
                    },
                    yaxis: {
                        tickAmount: 2,
                        labels: {
                            style: {
                                color: '#efefef',
                            },
                        },
                    }
                }
            }
        },

        methods: {
            generateDayWiseTimeSeries: function (baseval, count, yrange) {
                var i = 0;
                var series = [];
                while (i < count) {
                    var x = baseval;
                    var y = Math.floor(Math.random() * (yrange.max - yrange.min + 1)) + yrange.min;

                    series.push([x, y]);
                    baseval += 86400000;
                    i++;
                }

                //console.log(series)
                return series;
            }
        }
    }

</script>

<style>

</style>