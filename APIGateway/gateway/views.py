from django.db import IntegrityError
from django.http import HttpResponse
from . import models
import json
from django.views.decorators.csrf import csrf_exempt


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


@csrf_exempt
def monitors(request):

    if not request.headers['access-token']:
        return HttpResponse(status=401)

    # if not request.body:
    #     return HttpResponse(status=406)

    if request.method == 'POST':
        try:
            body = json.loads(request.body)
            monitor = models.Monitor.objects.create(id=body["monitor-id"], endpoint=body['api-endpoint'])
            monitor.save()
        except IntegrityError:
            return HttpResponse(status=409)
        except Exception as e:
            print("exeption")
            print(e)
            return HttpResponse(status=400)
        return HttpResponse(status=201)

    elif request.method == 'DELETE':
        try:
            body = json.loads(request.body)
            monitor = models.Monitor.objects.get(id=body['monitor-id'])
            monitor.delete()
        except IntegrityError:
            return HttpResponse(status=409)
        except Exception as e:
            print("exeption")
            print(e)
            return HttpResponse(status=400)
        return HttpResponse(status=200)

    return HttpResponse(status=404)
