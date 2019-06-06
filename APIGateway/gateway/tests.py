from django.test import TestCase, RequestFactory
import requests_mock
from mock import patch
from . import factories, views, config
import json


def mocked_monitor(**kargs):
    monitor = factories.MonitorFactory.build()
    return [monitor]


class MetricsTestCase(TestCase):
    monitor_response_text = {'metrics': [
        {'metric-id': 'id of metric 1', 'monitor-id': 'unique name of monitor 1'},
        {'metric-id': 'id of metric 2', 'monitor-id': 'unique name of monitor 2'}
    ],
        "meta": {"types": ['123', '234', '345']}
    }

    access_headers = {'access-token': '1234'}
    no_access_headers = {}

    @patch('gateway.models.Monitor.objects.all', side_effect=mocked_monitor)
    @patch('gateway.authentication.authorize', side_effect=[True])
    def test_successful_get_data_with_no_params(self, *arg):
        expected_response = self.monitor_response_text.copy()
        del expected_response['meta']

        with requests_mock.Mocker() as mock:
            mock.get(factories.monitor_url + '/metrics', text=json.dumps(self.monitor_response_text))
            request = RequestFactory().get('/v1/metrics')
            request.headers = self.access_headers
            response = views.metrics(request)
            self.assertEquals(response.status_code, 200)
            self.assertEquals(json.loads(response.content), expected_response)

    @patch('gateway.models.Monitor.objects.all', side_effect=mocked_monitor)
    @patch('gateway.authentication.authorize', side_effect=[True])
    def test_successful_get_data_with_meta_only(self, *arg):
        expected_response = self.monitor_response_text.copy()
        del expected_response['metrics']

        with requests_mock.Mocker() as mock:
            mock.get(factories.monitor_url + '/metrics', text=json.dumps(self.monitor_response_text))
            request = RequestFactory().get('/v1/metrics', {'meta': 'only'})
            request.headers = self.access_headers
            response = views.metrics(request)
            self.assertEquals(response.status_code, 200)
            expected_response['meta']['types'].sort()
            actual_response = json.loads(response.content)
            actual_response['meta']['types'].sort()
            self.assertEquals(actual_response, expected_response)

    @patch('gateway.models.Monitor.objects.all', side_effect=mocked_monitor)
    def test_unauthorized_with_no_access_header(self, *arg):
        request = RequestFactory().get('/v1/metrics')
        request.headers = self.no_access_headers
        response = views.metrics(request)
        self.assertEquals(response.status_code, 401)

    @patch('gateway.models.Monitor.objects.all', side_effect=mocked_monitor)
    def test_unauthorized_when_auth_service_deny(self, *arg):
        with requests_mock.Mocker() as mock:
            mock.get(config.auth_url+config.method, status_code=401)
            request = RequestFactory().get('/v1/metrics')
            request.headers = self.access_headers
            response = views.metrics(request)
            self.assertEquals(response.status_code, 401)
