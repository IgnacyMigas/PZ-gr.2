<template>
    <div>
        <h1><a @click="handleButtonClick" >Pokaż dane dla: {{ metricsName_2 }}</a></h1>
        <h2>{{metrics}}</h2>
        <h2>{{metrics_keys}}</h2>
        <h2>{{monitor_keys }}</h2>

        <span>Selected:  </span>

        <select class="t2e-select" v-model="selected">
            <option disabled value=""> Please select one </option>
            <option v-for="(metric) in metrics_keys"  v-bind:value="metric">
                metric-id: {{ metric }} , monitoid: {{monitor_keys[0]}}
            </option>
        </select>


        <apexcharts ref="updateChart" height=350 align="left" type="line" :options="chartOptions" :series="series"></apexcharts>
        <!--<h2>{{ time }}</h2>-->
        <!--<h2>{{ value }}</h2>-->
        <!--<h2>{{ blob_samples }}</h2>-->
    </div>
</template>

<script>

    import VueApexCharts from 'vue-apexcharts'

    var host = 'http://localhost:8080'
    // var metricsName_1 = 'CPU_testHost'
    var metricsName_2 = 'Battery_testHost'
    //var metricsName_3 = 'CPU_Host'
    var version = 'v1'
    var n = 15


    var url_metrics = host + '/' + version + '/metrics'
    var url_2 = host + '/' + version + '/metrics/' + metricsName_2 + '/measurements?n=' + n

     var time= []
     var value = []

    export default {
        name: 'LinearChart',
        components: {
            apexcharts: VueApexCharts,
        },
        mounted:function(){
            this.getMetrics() //method1 will execute at pageload
        },
        methods: {
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
                this.$http.get(url_2, {useCredentails: true}).then(function (data) {
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
            handleButtonClick: function() {
                /* call two methods. */
                this.getMetrics();
                this.created();
            }
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
                    metricsName_2: metricsName_2,
                    metrics: [],
                    metrics_keys: [],
                    monitor_keys : [],
                    selected: '',
                    mounted: function () {
                        this.getMetrics()
                        this.created()
                    },
                }
            }
        }

</script>

<style>
    .t2e-select{
        background-color: #898989;

        padding: 15px;
        font-size: 14px;
    }
</style>

           