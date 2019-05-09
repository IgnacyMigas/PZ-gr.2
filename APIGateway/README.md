# APIGateway

[![Build Status](https://travis-ci.org/IgnacyMigas/PZ-gr.2.svg?branch=api-gateway)](https://travis-ci.org/IgnacyMigas/PZ-gr.2)

## Project setup
```
python install
pip install -r requirements.txt
```

### Run server
```
python manage.py runserver
```
To set ip address and port
```
python manage.py runserver 127.0.0.1:8000
```
or to set only port
```
python manage.py runserver 8000
```

On default Django server start at

```
http://127.0.0.1:8000
```

### Run your tests
```
python tests.py
```

## Technologies
* Python 3.7
* Django 2.2
* requests 2.21
* django-cors-headers 2.5.3
