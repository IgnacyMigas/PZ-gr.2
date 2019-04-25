from django.db import models


# Create your models here.
class Monitor(models.Model):
    id = models.CharField(primary_key=True)
    endpoint = models.CharField()

