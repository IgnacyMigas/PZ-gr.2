import requests
from . import config
import json


def authorize(token):
    body = json.dumps({'access_token': token})
    headers = {'content-type': 'application/json'}
    response = requests.get(config.auth_url+config.method, headers=headers, data=body)
    if response.status_code == 200 and 'logged_in_as' in json.loads(response.content):
        return True
    return False
