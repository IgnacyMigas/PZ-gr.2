from django.http import HttpResponse
from django.shortcuts import render


# Create your views here.

def metrics(request, metrics_id=None):
    # if request.GET
    if metrics_id is None:
        output = '''{
   "metrics":[
                {
                   "metric-id": "id_of_metric",
                   "monitor-id": "unique_name_of_monitor"
                }
             ],
   "meta":{
      "types":[
        {
          name: 'temperatura',
          unit: '°C'
        },
        {
          name: 'zużycie pamięci',
          unit: 'MB'
        },
        {
          name: 'zużycie pamięci GPU',
          unit: 'MB'
        },
        {
          name: 'zużycie GPU',
          unit: 'flops'
        }
      ]
   }
}'''
    else:
        output = '''{
   "type":"type_of_metric",
   "id":"id",
   "unit":"units_of_measurement",
   "host-id":"host-id",
   "user-id":"user-id",
   "monitor-id":"unique_name_of_monitor"
}'''
    return HttpResponse(output)


def metrics_measurements(request, metrics_id):
    output = '''[
   {
      "val":"measurement_value",
      "ts":"timestamp"
   }
]'''
    return HttpResponse(output)


def hosts(request, hosts_id=None):
    if hosts_id is None:
        output = '''[
   {
      "host-id":"host id",
      "monitor-id": "unique name of monitor"
   }
]'''
    else:
        output = '''[
   {
      "host-id":"host id",
      "metrics": [
                    {
                        "type":"type of metric",
                        "metric-id":"metric-id",
                        "unit":"units of measurement",
                        "host-id":"host-id",
                        "user-id":"user-id",
                        "monitor-id":"unique name of monitor"
                    }
                 ]
   }
]'''
    return HttpResponse(output)


def monitors(request, monitors_id=None):
    if monitors_id is None:
        output = '''{  
   "monitor-id":"unique_name_of_the_monitor",
   "api-endpoint":"address_of_monitors_API_endpoint"
}'''
    else:
        output = '''{  
   "monitor-id":"unique_name_of_the_monitor"
}'''
    return HttpResponse(output)
