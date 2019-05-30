<template>
    <div>
        <!--<h1><a @click="handleButtonClick" >Pokaż dane</a></h1>-->
        <!--<h2>{{metrics_keys}}</h2>-->
        <!--<h2>{{monitor_keys }}</h2>-->

        <h2>Wybierz metrykę:  </h2>

        <select  @click="handleButtonClick" @change="onChange($event)" class="t2e-select" v-model="selected" >
            <option disabled value=""> Please select one </option>
            <option v-for="(metric) in metrics_keys"  v-bind:value="metric" >
                metric-id: {{ metric }} , monitor-id: {{monitor_keys[0]}}
            </option>
        </select>

        <!--<h2>{{selectedMetric}}</h2>-->

        <apexcharts ref="updateChart" height=350 align="left" type="line" :options="chartOptions" :series="series"></apexcharts>
        <h5>{{metrics}}</h5>
        <!--<h2>{{ time }}</h2>-->
        <!--<h2>{{ value }}</h2>-->
        <!--<h2>{{ blob_samples }}</h2>-->
    </div>
</template>

<script>

    import VueApexCharts from 'vue-apexcharts'

    var host = 'http://localhost:8080'
    var version = 'v1'
    var n = 15
    var url_metrics = host + '/' + version + '/metrics'

     var time= []
     var value = []

    export default {
        name: 'LinearChart',
        props: ['metric'],
        components: {
            apexcharts: VueApexCharts,
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

           