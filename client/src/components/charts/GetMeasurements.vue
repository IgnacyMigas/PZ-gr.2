<template>
    <div id="app">
        <h1>Test - retrive data</h1>

        <section v-if="errored">
            <p>We're sorry, we're not able to retrieve this information at the moment, please try back later</p>
        </section>

        <section v-else>
            <div v-if="loading">Loading...</div>

            <div class="currency">
                {{ currency.description }}:
                <span class="lighten">
        <span v-html="currency.symbol"></span>{{ currency.rate_float | currencydecimal }}
      </span>
            </div>
        </section>
    </div>
</template>


<script>
    import axios from 'axios';

    // window.axios.defaults.headers.common = {
    //     'Accept': 'application/json',
    //     'Content-Type': 'application/json'
    // };

    export default {
        name: 'Measurements',
        data: function() {
            return {
                info: null,
                loading: true,
                errored: false
            }
        },
        filters: {
            currencydecimal (value) {
                return value.toFixed(2)
            }
        },
        mounted () {
            axios
                .get('https://api.coindesk.com/v1/bpi/currentprice.json')
                .then(response => {
                    this.info = response.data.bpi
                })
                .catch(error => {
                    //console.log(error)
                    this.errored = true
                })
                .finally(() => this.loading = false)
        }
    }
</script>

<style scoped>

</style>