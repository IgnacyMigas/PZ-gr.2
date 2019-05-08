import requests 

API_REGISTER_ENDPOINT     = "http://localhost:8080/v1/hosts"
API_MEASUREMENTS_ENDPOINT = "http://localhost:8080/v1/metrics"

token = "xxxxxxxx"
register_header = { "access-token":token }

def register_sensor(hostID, os, type, unit, name):
    data            = {}
    data["host-id"] = hostID
    data["os"]      = os
    
    metrics              = {}
    metrics["type"]      = type
    metrics["unit"]      = unit
    metrics["metric-id"] = name
    
    data["metrics"] = [metrics]
    
    print(data)
    r = requests.post(url = API_REGISTER_ENDPOINT, json = data)#, params = register_header) 

def send_data(hostID, data):
    sdata            = {}
    sdata["host-id"] = hostID
    sdata["data"]    = data
    
    print(sdata)
    r = requests.post(url = API_MEASUREMENTS_ENDPOINT, json = sdata)#, params = register_header)

