from factory.django import DjangoModelFactory
from .models import Monitor

monitor_url = 'mock://test.com/v1'


class MonitorFactory(DjangoModelFactory):
    class Meta:
        model = Monitor

    id = 'v1'
    endpoint = 'mock://test.com/v1'
