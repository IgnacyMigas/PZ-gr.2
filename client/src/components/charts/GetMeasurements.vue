<template>
    <div id="show-blogs">
        <h1>All Blog Articles</h1>
        <h2>{{ blogs }}</h2>
        <!--<div v-for="blog in blogs" class="single-blog">-->
            <!--<h2>{{ blog.val }}</h2>-->
            <!--<article>{{ blog.ts }}</article>-->
        <!--</div>-->
    </div>
</template>



<script>
    export default {
        data () {
            return {
                blogs: []
            }
        },
        methods: {
        },
        created() {

            //
             this.$http.get('http://localhost:8080/v1/metrics/Battery_testHost/measurements', { useCredentails: true }).then(function(data){
            //     this.$http.get('http://jsonplaceholder.typicode.com/posts').then(function(data){
                  this.blogs = data.body.slice(0,10);
            });
        }
    }
</script>

<style>
    #show-blogs{
        max-width: 800px;
        margin: 0px auto;
    }
    .single-blog{
        padding: 20px;
        margin: 20px 0;
        box-sizing: border-box;
        background: #dedede;
        color: #343434;
    }
</style>