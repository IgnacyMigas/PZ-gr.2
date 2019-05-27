<script>
    import { Line } from 'vue-chartjs'
    import axios from "axios";

    const myURL = 'http://localhost:8080/v1/metrics/Battery_testHost/measurements?n=5'

    var allVal = []
    var json = []
    export default {
        extends: Line,
        name: "LinearChart",
        props: ["data", "options"],
        mounted() {
            axios.get(myURL, {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                },
                proxy: {
                    host: 'http://localhost',
                    port: 8080
                }
            }).then(res => {
                json= res
                for(var i = 0; i < json.length; i++) {
                    allVal = json[i];
                }
                //console.log(res)
            }
            //, error => {
            //    json = null
            //    console.error(error);
            //}
            ),

            // Overwriting base render method with actual data.
            this.renderChart({
                labels: ['26/05/2019 01:05:35', '26/05/2019 01:05:36', '26/05/2019 01:05:37', '26/05/2019 01:05:38', '26/05/2019 01:05:39', '26/05/2019 01:05:40', '26/05/2019 01:05:41', '26/05/2019 01:05:42', '26/05/2019 01:05:43', '26/05/2019 01:05:44'],
                datasets: [
                    {
                        label: 'Value',
                        backgroundColor: '#f87979',
                        data: allVal
                    }

                ]
            })
        }
    }
</script>

<style scoped>

</style>