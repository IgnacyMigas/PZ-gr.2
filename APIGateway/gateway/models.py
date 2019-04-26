from django.db import models


# Create your models here.
class Monitor(models.Model):
    id = models.TextField(primary_key=True, unique=True)
    endpoint = models.TextField()

    def __str__(self):
        return str(self.id) + ": " + str(self.endpoint)

