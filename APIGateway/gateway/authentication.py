import requests
from . import config
import json


def authorize(token):
    return_value = False
    body = json.dumps({'access_token': token})
    headers = {'content-type': 'application/json'}
    try:
        response = requests.get(config.auth_url+config.method, headers=headers, data=body)
        if response.status_code == 200 and 'logged_in_as' in response.json():
            return_value = True
    except Exception as e:
        print(e)
    return return_value
