import requests
from . import config


def authorize(token):
    body = {'access-token': token}
    headers = {'Content-Type': 'application/json'}
    response = requests.get(config.auth_url+config.method, headers=headers, params=body)
    if response.status_code == 200 and 'logged_in_as' in response.content:
        return True
    return False
