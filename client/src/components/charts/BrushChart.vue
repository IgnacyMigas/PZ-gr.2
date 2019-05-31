<template>
    <div>
    <h2>Wybierz metrykę:  </h2>

    <select  @click="handleButtonClick" @change="onChange($event)" class="t2e-select" v-model="selected" >
        <option disabled value=""> Please select one </option>
        <option v-for="(metric) in metrics_keys"  v-bind:value="metric" >
            metric-id: {{ metric }}
        </option>
    </select>


    <div class="actions">
            <button class="btn btn-primary" @click="handleButtonClick" v-on:click="timeRange">15min</button>
            <button class="btn btn-primary" v-on:click="timeRange">30min</button>
            <button class="btn btn-primary" v-on:click="timeRange">1h</button>
            <button class="btn btn-primary" v-on:click="timeRange">24h</button>
            <button class="btn btn-primary" v-on:click="timeRange">48h</button>
    </div>
        <div id="chart1">
            <apexchart ref="updateChart" type=line height=230 :options="chartOptionsArea" :series="series" />
        </div>
        <div id="chart2">
            <apexchart type=area height=130 :options="chartOptionsBrush" :series="series" />
        </div>


    <h5>{{metrics}}</h5>
    </div>
</template>

<script>

    //import Vue from 'vue'
    import VueApexCharts from 'vue-apexcharts'

    var host = 'http://localhost:8080'
    var version = 'v1'
    var n = 15
    var url_metrics = host + '/' + version + '/metrics'

    var time= []
    var value = []

    export default {
        name: 'BrushChart',
        props: ['metric'],
        components: {
            apexchart: VueApexCharts,
        },
        mounted:function(){
            this.getMetrics() //method1 will execute at pageload
        },
        methods: {
            onChange(event) {
                this.selectedMetric = event.target.value
            },
            getMetrics: function(){
                this.$http.get(url_metrics , {useCredentails: true}).then(function (data) {
                    this.metrics= data.body.metrics;
                    var index;
                    for (index = 0; index < this.metrics.length; ++index) {
                        this.metrics_keys[index]  = this.metrics[index]["metric-id"];
                        this.monitor_keys[index]  = this.metrics[index]["monitor-id"];
                    }
                });
            },
            created: function() {
                this.getMetrics()
                if(!(this.selectedMetric == '' || this.selectedMetric == null || this.selectedMetric == undefined)) {
                    var url_2 = host + '/' + version + '/metrics/' + this.selectedMetric + '/measurements?n=' + n

                    this.$http.get(url_2, {useCredentails: true}).then(function (data) {
                        this.blob_samples = data.body.slice(0, 10);

                        if (data.body == '' || data.body == '[]' || data.body == null || data.body == []) {
                            this.value = [];
                            this.time = [];
                        } else {
                            var index;
                            for (index = 0; index < this.blob_samples.length; ++index) {
                                this.value[index] = parseFloat(this.blob_samples[this.blob_samples.length - index - 1].val),
                                    this.time[index] = this.blob_samples[this.blob_samples.length - index - 1].ts
                            }
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
                }
            },
            handleButtonClick: function() {
                /* call two methods. */
                this.getMetrics();
                this.created();
            }
        },
        data: function () {
            return {
                time: [],
                value: [],
                blob_samples: [],
                metrics: [],
                metrics_keys: [],
                monitor_keys : [],
                selected: '',
                selectedMetric: [],
                mounted: function () {
                    this.onChange(event)
                    this.getMetrics()
                    this.created()
                },
                series: [{
                    name: 'value',
                    data: value.slice(0,10),
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
                    },
                    colors: ['#34dE5A'],
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
                                // min: new Date('19 Jun 2017').getTime(),
                                // max: new Date('14 Aug 2017').getTime()
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
                        categories: time.slice(0,10),
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
                    },
                    series: [{
                        name: 'value',
                        data: value.slice(0,10),
                    }],
                }
            }
        },
    }

</script>

<style>
    .t2e-select{
        background-color: #898989;

        padding: 15px;
        font-size: 14px;
    }

    .actions{
        margin-right: 10px;
    }

    .btn-primary{
        background-color: #faaf40;
        border-color: #faaf40;
        color: #fff;
    }

    .btn{
        border-radius: 3px;
        display: inline-block;
        font-weight: 400;
        text-align: center;
        vertical-align: middle;
        touch-action: manipulation;
        cursor: pointer;
        background-image: none;
        border: 1px solid transparent;
        white-space: nowrap;
        user-select: none;
    }
</style>