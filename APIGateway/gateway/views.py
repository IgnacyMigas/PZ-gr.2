from django.db import IntegrityError
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from . import models
from . import authentication
import json
import requests


@csrf_exempt
def metrics(request, metrics_id=None):
    if 'access-token' not in request.headers or not authentication.authorize(request.headers['access-token']):
        return HttpResponse(status=401)

    headers = {'access-token': request.headers['access-token']}

    metrics_dict = {"metrics": [], "meta": {"types": set()}}
    if metrics_id is None:
        if request.method == 'GET':
            meta = 'false'
            if 'meta' in request.GET:
                meta = request.GET['meta']
            for m in models.Monitor.objects.all():
                try:
                    monitor_response = requests.get(m.endpoint + '/metrics', headers=headers, params=request.GET)
                    if monitor_response.status_code == 200:
                        body = monitor_response.content
                        if isinstance(body, bytes):
                            body = json.loads(body)
                        if meta != 'only':
                            metrics_dict['metrics'].extend(body['metrics'])
                        if meta != 'false' and body['meta'] is not None:
                            metrics_dict['meta']['types'].update(body['meta']['types'])
                except requests.ConnectionError:
                    print("Delete monitor %s" % m.id)
                    m.delete()
                except requests.exceptions.InvalidSchema:
                    print("Delete monitor %s" % m.id)
                    m.delete()
            metrics_dict['meta']['types'] = list(metrics_dict['meta']['types'])
            if meta == 'only':
                metrics_dict.pop('metrics', None)
            elif meta == 'false':
                metrics_dict.pop('meta', None)
            return HttpResponse(json.dumps(metrics_dict))
        elif request.method == 'POST':
            body = json.loads(request.body)
            if 'monitor-id' in body:
                for m in models.Monitor.objects.all():
                    if body['monitor-id'] == m.id:
                        try:
                            response = requests.post(m.endpoint + '/metrics', data=request.body, headers=headers)
                            return HttpResponse(response.content, status=response.status_code)
                        except requests.ConnectionError:
                            print("Delete monitor %s" % m.id)
                            m.delete()
                        except requests.exceptions.InvalidSchema:
                            print("Delete monitor %s" % m.id)
                            m.delete()
            return HttpResponse(status=400)
    else:
        if request.method == 'GET':
            for m in models.Monitor.objects.all():
                if 'monitor-id' in request.GET and request.GET['monitor-id'] != m.id:
                    continue
                try:
                    monitor_response = requests.get(m.endpoint + '/metrics/' + metrics_id, headers=headers)
                    if monitor_response.status_code == 200:
                        return HttpResponse(monitor_response.content, status=200)
                except requests.ConnectionError:
                    print("Delete monitor %s" % m.id)
                    m.delete()
                except requests.exceptions.InvalidSchema:
                    print("Delete monitor %s" % m.id)
                    m.delete()
        elif request.method == 'DELETE':
            for m in models.Monitor.objects.all():
                try:
                    response = requests.delete(m.endpoint + '/metrics/' + metrics_id, headers=headers)
                    if response.status_code == 200:
                        return HttpResponse(status=200)
                except requests.ConnectionError:
                    print("Delete monitor %s" % m.id)
                    m.delete()
                except requests.exceptions.InvalidSchema:
                    print("Delete monitor %s" % m.id)
                    m.delete()
            return HttpResponse(status=400)
    return HttpResponse(status=404)


def metrics_measurements(request, metrics_id):
    if 'access-token' not in request.headers or not authentication.authorize(request.headers['access-token']):
        return HttpResponse(status=401)

    headers = {'access-token': request.headers['access-token']}

    if request.method == 'GET':
        measurements_list = []
        params = request.GET.copy()
        params.pop('monitor-id', None)
        for m in models.Monitor.objects.all():
            if 'monitor-id' in request.GET and request.GET['monitor-id'] != m.id:
                continue
            try:
                monitor_response = requests.get(m.endpoint + '/metrics/' + metrics_id + '/measurements',
                                                headers=headers, params=params)
                if monitor_response.status_code == 200:
                    body = monitor_response.content
                    if isinstance(body, bytes):
                        body = json.loads(body)
                    measurements_list.extend(body)
            except requests.ConnectionError:
                print("Delete monitor %s" % m.id)
                m.delete()
            except requests.exceptions.InvalidSchema:
                print("Delete monitor %s" % m.id)
                m.delete()
        return HttpResponse(json.dumps(measurements_list))
    return HttpResponse(status=404)


def hosts(request, hosts_id=None):
    if 'access-token' not in request.headers or not authentication.authorize(request.headers['access-token']):
        return HttpResponse(status=401)

    headers = {'access-token': request.headers['access-token']}

    if request.method == 'GET':
        monitors_list = []
        if hosts_id is None:
            for m in models.Monitor.objects.all():
                try:
                    monitor_response = requests.get(m.endpoint + '/hosts', headers=headers, params=request.GET)
                    if monitor_response.status_code == 200:
                        body = monitor_response.content
                        if isinstance(body, bytes):
                            body = json.loads(body)
                        monitors_list.extend(body)
                except requests.ConnectionError:
                    print("Delete monitor %s" % m.id)
                    m.delete()
                except requests.exceptions.InvalidSchema:
                    print("Delete monitor %s" % m.id)
                    m.delete()
        else:
            for m in models.Monitor.objects.all():
                if 'monitor-id' in request.GET and request.GET['monitor-id'] != m.id:
                    continue
                try:
                    monitor_response = requests.get(m.endpoint + '/hosts/' + hosts_id, headers=headers)
                    if monitor_response.status_code == 200:
                        body = monitor_response.content
                        if isinstance(body, bytes):
                            body = json.loads(body)
                        monitors_list.append(body)
                except requests.ConnectionError:
                    print("Delete monitor %s" % m.id)
                    m.delete()
                except requests.exceptions.InvalidSchema:
                    print("Delete monitor %s" % m.id)
                    m.delete()
        return HttpResponse(json.dumps(monitors_list))
    return HttpResponse(status=404)


@csrf_exempt
def monitors(request):
    if 'access-token' not in request.headers or not authentication.authorize(request.headers['access-token']):
        return HttpResponse(status=401)

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
        except models.Monitor.DoesNotExist:
            return HttpResponse(status=400)
        except Exception as e:
            print("exeption")
            print(e)
            return HttpResponse(status=400)
        return HttpResponse(status=200)

    return HttpResponse(status=404)
