import requests
from . import config


def authorize(token):
    body = {'access-token': token}
    response = requests.get(config.auth_url+config.method, params=body)
    if response.status_code == 200 and 'logged_in_as' in response.content:
        return True
    return False
