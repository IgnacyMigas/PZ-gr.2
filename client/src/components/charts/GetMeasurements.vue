<template>
    <div id="show-blob">
        <h1>Blob</h1>
        <h2>{{ blob_samples }}</h2>
        <div v-for="blob_sample in blob_samples" class="single-sample">
            <h2>{{ blob_sample.val }}</h2>
            <article>{{ blob_sample.ts }}</article>
        </div>
    </div>
</template>



<script>

    var host = 'http://localhost:8080'
    var metricsName = 'Battery_testHost'
    var version = 'v1'
    var n = 15
    var url = host + '/' + version + '/metrics/' + metricsName + '/measurements?n=' + n

    export default {
        data () {
            return {
                blob_samples: []
            }
        },
        methods: {
        },
        created() {
             this.$http.get(url, { useCredentails: true }).then(function(data){
                  this.blob_samples = data.body.slice(0,10);
            });
        }
    }
</script>

<style>
    #show-blob{
        max-width: 800px;
        margin: 0px auto;
    }
    .single-sample{
        padding: 20px;
        margin: 20px 0;
        box-sizing: border-box;
        background: #dedede;
        color: #343434;
    }
</style>