<template>
    <div>
        <h2>Wybierz metrykę:  </h2>

        <select  @change="onChange($event)" class="t2e-select-metric" v-model="selected" >
            <option disabled value=""> Przoszę wybrać metrykę</option>
            <option v-for="(metric, index) in metrics_keys"  v-bind:value="metric" >
                Metryka: '{{ metric }}' , Monitor: '{{monitor_keys[index]}}'
            </option>
        </select>
        v

        <div>
            <div class="actions">
                <button @click="setActiveTab" class="btn btn-xs t2e-btn-select-time-15m" :class="{'btn-primary': range == '15m', 'btn-white': range != '15m'}" type="button"  v-on:click="timeRange('15m')"> 15 min </button>
                <button @click="setActiveTab" class="btn btn-xs t2e-btn-select-time-30m" :class="{'btn-primary': range == '30m', 'btn-white': range != '30m'}" type="button" v-on:click="timeRange('30m')"> 30 min </button>
                <button @click="setActiveTab" class="btn btn-xs t2e-btn-select-time-1h"  :class="{'btn-primary': range == '1h', 'btn-white': range != '1h'}" type="button" v-on:click="timeRange('1h')"> 1 h </button>
                <button @click="setActiveTab" class="btn btn-xs t2e-btn-select-time-24h" :class="{'btn-primary': range == '24h', 'btn-white': range != '24h'}" type="button" v-on:click="timeRange('24h')"> 24 h </button>
                <button @click="setActiveTab" class="btn btn-xs t2e-btn-select-time-48h" :class="{'btn-primary': range == '48h', 'btn-white': range != '48h'}" type="button" v-on:click="timeRange('48h')"> 48 h </button>
                <button @click="setActiveTab" class="btn btn-xs t2e-btn-select-time-range" :class="{'btn-primary': range == 'selectRange', 'btn-white': range != 'selectRange'}" type="button" v-on:click="timeRange('selectRange')"> Zakres </button>

                <v-range-selector  @click="yolo()" v-if="!isHiddenCalendar"
                                  :start-date.sync="dataRange.start"
                        :end-date.sync="dataRange.end"
                />
                <!--v-on:click="yolo('dataRange.start', 'dataRange.end')"-->
            </div>
            <h1>{{dataRange.start}}</h1>
            <h1>{{dataRange.end}}</h1>

            <span class="metricsAlerts">{{noDataInfo}}</span>
            <apexcharts ref="updateChart" height=350 align="left" type="line" :options="chartOptions" :series="series"></apexcharts>
        </div>

        <h5>{{metrics}}</h5>

    </div>
</template>

<script>
    import VueApexCharts from 'vue-apexcharts'
    import VRangeSelector from 'vuelendar/components/vl-range-selector';


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
            VRangeSelector,
        },
        mounted:function(){
            this.getMetrics() //method will execute at pageload
        },
        methods: {
            setActiveTab(){
                let _this = this;
                _this.activeTab = 1;
            },
            yolo(){
                 var url = host + '/' + version + '/metrics/' + this.selectedMetric + '/measurements?n=30&from='
                // this.$http.get(url + start + '&to='+ end, {useCredentails: true}).then(function (data) {
                // });
               //if(this.dataRange.start != undefined && this.dataRange.end != undefined){
                    this.draw(url + this.dataRange.start + '&to='+ this.dataRange.end);
               // }

            },
            timeRange: function(range) {
                if(!(this.selectedMetric == '' || this.selectedMetric == null || this.selectedMetric == undefined)) {
                    this.isHiddenCalendar = true;
                    var url = host + '/' + version + '/metrics/' + this.selectedMetric + '/measurements?n=30&from='
                    var dateFormat = require('dateformat');
                    var now = new Date();
                    dateFormat(now, "DD/mm/YYYY HH:MM:SS");

                    // default Data(): "Fri May 31 2019 15:19:41 GMT+0200 (Central European Summer Time)"
                    // expected: "31/05/2019 15:34:41"
                    var hour
                    if (range == '15m') {
                        var _15min = new Date(Date.now() - 1000 * 60 * 15);
                        hour = _15min.getHours();
                        _15min = dateFormat(_15min, "dd/mm/yyyy "+ hour +":MM:ss");
                        this.draw(url + _15min);
                        this.range = '15m';
                    }
                    else if (range == '30m') {
                        var _30min = new Date(Date.now() - 1000 * 60 * 30);
                        hour = _30min.getHours();
                        _30min = dateFormat(_30min, "dd/mm/yyyy "+ hour +":MM:ss");
                        this.draw(url + _30min);
                        this.range = '30m';
                    }
                    else if (range == '1h') {
                        var _1h = new Date(Date.now() - 1000 * 60 * 60 * 1);
                        hour = _1h.getHours();
                        _1h = dateFormat(_1h, "dd/mm/yyyy "+ hour +":MM:ss");
                        this.draw(url + _1h);
                        this.range = '1h';

                    }
                    else if (range == '24h') {
                        var _24h = new Date(Date.now() - 1000 * 60 * 60 * 24);
                        hour = _24h.getHours();
                        _24h = dateFormat(_24h, "dd/mm/yyyy "+ hour +":MM:ss");
                        this.draw(url + _24h);
                        this.range = '24h';
                    }
                    else if (range == '48h') {
                        var _48h = new Date(Date.now() - 1000 * 60 * 60 * 48);
                        hour = _48h.getHours();
                        _48h = dateFormat(_48h, "dd/mm/yyyy "+ hour +":MM:ss");
                        this.draw(url + _48h);
                        this.range = '48h';
                    }
                    else if (range == 'selectRange') {

                        this.isHiddenCalendar = false;
                        // while(this.dataRange.start != undefined && this.dataRange.end != undefined){
                        //     this.range = 'selectRange';
                        // }
                        // this.draw(url + this.dataRange.start + '&to='+ this.dataRange.end);
                        this.range = 'selectRange';
                        //this.draw(url + this.dataRange.start + '&to='+ this.dataRange.end);
                        //alert('TODO select range!');
                    }
                    else {
                        this.noDataInfo = 'Niepoprawny zakres czasu'
                    }
            }
                else{
                    this.noDataInfo = 'Proszę wybrać metrykę, aby zobaczyć jej pomiary'
                }
            },
            draw(url){
                if(!(this.selectedMetric == '' || this.selectedMetric == null || this.selectedMetric == undefined)) {
                    this.range = '15m';//default
                    this.isHiddenCalendar = true;
                    this.$http.get(url, {useCredentails: true}).then(function (data) {
                        this.blob_samples = data.body;//.slice(0, 10);

                        if (data.body == '' || data.body == '[]' || data.body == null || data.body == []) {
                            this.value = [];
                            this.time = [];
                            this.noDataInfo = 'Brak danych'
                        } else {
                            var index;
                            for (index = 0; index < this.blob_samples.length; ++index) {
                                this.value[index] = parseFloat(this.blob_samples[this.blob_samples.length - index - 1].val),
                                    this.time[index] = this.blob_samples[this.blob_samples.length - index - 1].ts
                            }
                            this.noDataInfo = ''
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
            onChange(event) {
                this.selectedMetric = event.target.value
                this.getMetrics();
                this.created();
            },
            getMetrics: function(){
                this.value = [];
                this.time = [];
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
                var url = host + '/' + version + '/metrics/' + this.selectedMetric + '/measurements?n=' + n
                this.getMetrics();
                this.draw(url);
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
                        markers: {
                            colors: ['#F44336'],
                            size: 2
                        },
                        colors: ['#faaf40'],
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
                            categories: time,//.slice(0,10),
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
                        grid: {
                            row: {
                                colors: ['#676767', 'transparent'], // takes an array which will be repeated on columns
                                opacity: 0.4
                            },
                            column: {
                                colors: ['#343434']
                            }
                        },
                        // title: {
                        //     text: 'Dane historyczne - wykres liniowy',
                        //     align: 'left',
                        //     style: {
                        //         color: '#dedede',
                        //         fontSize: '16px'
                        //     },
                        // },
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
                        data: value,//.slice(0,10)
                    }],
                    time: [],
                    value: [],
                    blob_samples: [],
                    metrics: [],
                    metrics_keys: [],
                    monitor_keys : [],
                    selected: '',
                    selectedMetric: [],
                    d: [],
                    noDataInfo: '',
                    activeTab: 0,
                    range: '',//default
                    dataRange: {},
                    date: null,
                    isHiddenCalendar: true,
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
    .metricsAlerts{
        text-align: center;
        margin-top: 25px;
        color: #ee7d00;
        padding: 30px;
        position: relative;
    }

    select{
        cursor:pointer;
    }

    .t2e-select-metric{
        background-color: #898989;
        padding: 15px;
        font-size: 14px;
    }

    .actions{
        text-align: left;
        box-sizing: border-box;
        color: rgb(103, 106, 108);
        display: block;
        font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
        font-size: 13px;
        height: 27px;
        line-height: 19px;
        text-size-adjust: 100%;
        width: 800px;
        margin-left: 40px;
        margin-top: 30px;
    }

    .btn-white {
        color: inherit;
        background: #fff;
        border: 1px solid #e7eaec;
    }
    .btn-primary{
        background-color: #faaf40;
        border-color: #faaf40;
        color: #fff;
        margin-bottom: 15px;
    }

    .btn{
        margin-bottom: 15px;
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

    .btn-xs{
        padding: 1px 5px;
        font-size: 12px;
        line-height: 1.5;
        margin-bottom: 15px;
    }

    button {
        align-items: flex-start;
        background-color: rgb(221, 221, 221);
        border-bottom-color: rgb(221, 221, 221);
        border-bottom-left-radius: 0px;
        border-bottom-right-radius: 0px;
        border-bottom-style: outset;
        border-bottom-width: 2px;
        border-image-outset: 0px;
        border-image-repeat: stretch;
        border-image-slice: 100%;
        border-image-source: none;
        border-image-width: 1;
        border-left-color: rgb(221, 221, 221);
        border-left-style: outset;
        border-left-width: 2px;
        border-right-color: rgb(221, 221, 221);
        border-right-style: outset;
        border-right-width: 2px;
        border-top-color: rgb(221, 221, 221);
        border-top-left-radius: 0px;
        border-top-right-radius: 0px;
        border-top-style: outset;
        border-top-width: 2px;
        box-sizing: border-box;
        color: rgb(103, 106, 108);
        cursor: pointer;
        display: inline-block;
        font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
        font-size: 13px;
        font-stretch: 100%;
        font-style: normal;
        font-variant-caps: normal;
        font-variant-east-asian: normal;
        font-variant-ligatures: normal;
        font-variant-numeric: normal;
        font-weight: 400;
        height: 24px;
        letter-spacing: normal;
        line-height: 18.5714px;
        margin-bottom: 0px;
        margin-left: 0px;
        margin-right: 10px;
        margin-top: 0px;
        overflow-x: visible;
        overflow-y: visible;
        padding-bottom: 1px;
        padding-left: 6px;
        padding-right: 6px;
        padding-top: 1px;
        text-align: center;
        text-indent: 0px;
        text-rendering: auto;
        text-shadow: none;
        text-size-adjust: 100%;
        text-transform: none;
        width: 55.0156px;
        word-spacing: 0px;
        writing-mode: horizontal-tb;
    }
    div {
        display: block;
    }
</style>

           