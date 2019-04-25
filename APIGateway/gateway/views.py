from django.http import HttpResponse
from django.shortcuts import render
from . import models


# Create your views here.

def metrics(request, metrics_id=None):
    # if request.GET
    if metrics_id is None:
        # get metrict from all monitors (loop over monitors
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


def monitors(request):
    if not request.headers['access-token']:
        return HttpResponse(status=401)
    if request.method == 'POST':
        try:
            monitor = models.Monitor.objects.create(id=request.POST['monitor-id'], endpoint=request.POST['api-endpoint'])
            monitor.save()
        except:
            return HttpResponse(status=400)
        return HttpResponse(status=201)
    elif request.method == 'DELETE':
        monitor = models.Monitor.objects.get(id=request.POST['monitor-id'])
        monitor.save()
        output = '''{  
   "monitor-id":"unique_name_of_the_monitor"
}'''
    return HttpResponse(output)
