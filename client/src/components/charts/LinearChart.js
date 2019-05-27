// LinearChart.js
import { Line } from 'vue-chartjs'

export default {
    extends: Line,
    props: ["data", "options"],
    mounted () {
        // Overwriting base render method with actual data.
        this.renderChart({
            labels: ['26/05/2019 01:05:35', '26/05/2019 01:05:36', '26/05/2019 01:05:37', '26/05/2019 01:05:38', '26/05/2019 01:05:39', '26/05/2019 01:05:40', '26/05/2019 01:05:41', '26/05/2019 01:05:42', '26/05/2019 01:05:43', '26/05/2019 01:05:44'],
            datasets: [
                {
                    label: 'Value',
                    backgroundColor: '#f87979',
                    data: [0.81, 0.81, 0.81, 0.81, 0.84, 0.99, 0.99, 0.97, 0.95, 0.96]
                }
            ]
        })
    }
}