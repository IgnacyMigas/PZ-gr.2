from django.db import IntegrityError
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from . import models
import json
import requests


@csrf_exempt
def metrics(request, metrics_id=None):
    if not request.headers['access-token']:
        return HttpResponse(status=401)

    metrics_dict = {"metrics": [], "meta": {"types": []}}
    if metrics_id is None:
        if request.method == 'GET':
            for m in models.Monitor.objects.all():
                monitor_response = requests.get(str(m.endpoint) + '/metrics', headers=request.headers)
                if monitor_response.status_code == 200:
                    body = monitor_response.content
                    if isinstance(body, bytes):
                        body = json.loads(body)
                    metrics_dict['metrics'].extend(body['metrics'])
                    if body['meta'] is not None:
                        metrics_dict['meta']['types'].extend(body['meta']['types'])
            return HttpResponse(json.dumps(metrics_dict))
        elif request.method == 'POST':
            status = 201
            for m in models.Monitor.objects.all():
                monitor_response = requests.post(str(m.endpoint) + '/metrics', data=request.body, headers=request.headers)
                if monitor_response.status_code != 201:
                    status = monitor_response.status_code
            return HttpResponse(status=status)
    else:
        if request.method == 'GET':
            return HttpResponse(status=501)
        elif request.method == 'DELETE':
            for m in models.Monitor.objects.all():
                requests.delete(str(m.endpoint) + '/metrics/' + metrics_id, data=request.body, headers=request.headers)
            return HttpResponse(status=200)
    return HttpResponse(status=404)


def metrics_measurements(request, metrics_id):
    if not request.headers['access-token']:
        return HttpResponse(status=401)

    if request.method == 'GET':
        measurements_list = []
        for m in models.Monitor.objects.all():
            monitor_response = requests.get(str(m.endpoint) + '/metrics/' + metrics_id + '/measurements', headers=request.headers)
            if monitor_response.status_code == 200:
                body = monitor_response.content
                if isinstance(body, bytes):
                    body = json.loads(body)
                measurements_list.extend(body)
        return HttpResponse(json.dumps(measurements_list))
    return HttpResponse(status=404)


def hosts(request, hosts_id=None):
    if not request.headers['access-token']:
        return HttpResponse(status=401)

    if request.method == 'GET':
        monitors_list = []
        if hosts_id is None:
            for m in models.Monitor.objects.all():
                monitor_response = requests.get(str(m.endpoint) + '/hosts', headers=request.headers)
                if monitor_response.status_code == 200:
                    body = monitor_response.content
                    if isinstance(body, bytes):
                        body = json.loads(body)
                    monitors_list.extend(body)
        else:
            for m in models.Monitor.objects.all():
                monitor_response = requests.get(str(m.endpoint) + '/hosts/' + hosts_id, headers=request.headers)
                if monitor_response.status_code == 200:
                    body = monitor_response.content
                    if isinstance(body, bytes):
                        body = json.loads(body)
                    monitors_list.append(body)
        return HttpResponse(json.dumps(monitors_list))
    return HttpResponse(status=404)


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
            for m in models.Monitor.objects.all():
                print(m.id)
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
