import requests 
import psutil
import datetime
import time
import platform

class SensorTools:
    API_REGISTER_ENDPOINT             = "http://localhost:8080/v1/hosts"
    API_CPU_MEASUREMENTS_ENDPOINT     = "http://localhost:8080/v1/metrics/CPU_Host/measurements"
    API_BATTERY_MEASUREMENTS_ENDPOINT = "http://localhost:8080/v1/metrics/Battery_Host/measurements"

    token = "xxxxxxxx"
    register_header = { "access-token":token }

    def __init__(self, metric, sensor_name):
        self.metric = metric
        self.set_variables(sensor_name)
    
    def set_variables(self, sensor_name):
        self.hostID = sensor_name
        self.os     = platform.system()
        if self.metric == "CPU":
            self.type           = "CPU"
            self.unit           = "%"
            self.metric_id      = "CPU_Host"
            self.collected_data = []
            self.timestamp      = []
        elif self.metric == "Battery":
            self.type           = "Battery"
            self.unit           = "%"
            self.metric_id      = "Battery_Host"
            self.collected_data = []
            self.timestamp      = []
        else:
            self.type1           = "CPU"
            self.unit1           = "%"
            self.metric_id1      = "CPU_Host"
            self.type2           = "Battery"
            self.unit2           = "%"
            self.metric_id2      = "Battery_Host"
            self.collected_data1 = []
            self.collected_data2 = []
            self.timestamp       = []
    
    def register_sensor(self):
        if self.metric == "Both":
            data = self.register_json_data_for_two_metrics()
        else:
            data = self.register_json_data_for_one_metric()
        
        while True:
            print(data)
            r = requests.post(url = self.API_REGISTER_ENDPOINT, json = data)#, params = register_header) 
            #print(r)
            print(r.status_code, r.reason)
            if r.status_code == 201:
                print("Sensor registration complete")
                break
            elif r.status_code == 409:
                print('Looks like, sensor with name "{0}" already exist. Skipping registration...'.format(self.hostID))
                break
            else:
                print("Something went wrong during registration. HTTP code: {0}. After a few seconds, we will try again...".format(r.status_code))
                time.sleep(5)
    
    def register_json_data_for_one_metric(self):
        data            = {}
        data["host-id"] = self.hostID
        data["os"]      = self.os
    
        metrics              = {}
        metrics["type"]      = self.type
        metrics["unit"]      = self.unit
        metrics["metric-id"] = self.metric_id
    
        data["metrics"] = [metrics]
        
        return data
        
    def register_json_data_for_two_metrics(self):
        data            = {}
        data["host-id"] = self.hostID
        data["os"]      = self.os
    
        metrics1              = {}
        metrics1["type"]      = self.type1
        metrics1["unit"]      = self.unit1
        metrics1["metric-id"] = self.metric_id1
        
        metrics2              = {}
        metrics2["type"]      = self.type2
        metrics2["unit"]      = self.unit2
        metrics2["metric-id"] = self.metric_id2
        
        data["metrics"] = [metrics1, metrics2]
        
        return data

    def send_data(self):
        if self.metric == "Both":
            data = self.json_data_for_two_metrics()
            r = requests.post(url = self.API_CPU_MEASUREMENTS_ENDPOINT, json = data[0])#, params = register_header)
            print(data[0])
            print(r.status_code, r.reason)
            print("#################")
            r = requests.post(url = self.API_BATTERY_MEASUREMENTS_ENDPOINT, json = data[1])#, params = register_header)
            print(data[1])
            print(r.status_code, r.reason)
            self.collected_data1 = []
            self.collected_data2 = []
        elif self.metric == "CPU":
            data = self.json_data_for_one_metric()
            r = requests.post(url = self.API_CPU_MEASUREMENTS_ENDPOINT, json = data)#, params = register_header)
            print(data)
            print(r.status_code, r.reason)
            self.collected_data = []
        else:
            data = self.json_data_for_one_metric()
            r = requests.post(url = self.API_BATTERY_MEASUREMENTS_ENDPOINT, json = data)#, params = register_header)
            print(data)
            print(r.status_code, r.reason)
            self.collected_data = []
        self.timestamp = []

    def json_data_for_one_metric(self):
        data = []
        for i in range(len(self.collected_data)):
            temp        = {}
            temp["val"] = str(self.collected_data[i])
            temp["ts"]  = self.timestamp[i]
            data.append(temp)
        
        return data
    
    def json_data_for_two_metrics(self):
        data1 = []
        data2 = []
        for i in range(len(self.collected_data1)):
            temp1        = {}
            temp1["val"] = str(self.collected_data1[i])
            temp1["ts"]  = self.timestamp[i]
            
            temp2        = {}
            temp2["val"] = str(self.collected_data2[i])
            temp2["ts"]  = self.timestamp[i]
            
            data1.append(temp1)
            data2.append(temp2)
        
        return [data1, data2]    
        
    def colect_data(self):  
        if self.metric == "CPU":
            self.collected_data.append(psutil.cpu_percent(interval=1.0))
        elif self.metric == "Battery":
            self.collected_data.append(psutil.sensors_battery().percent)
        else:
            self.collected_data1.append(psutil.cpu_percent(interval=1.0))
            self.collected_data2.append(psutil.sensors_battery().percent)
        self.timestamp.append(datetime.datetime.fromtimestamp(time.time()).strftime('%d/%m/%Y %H:%M:%S'))
        #print(self.collected_data)
        #print(self.timestamp)
        #print("CPU percent: {0}% \nBattery: {1}%".format(psutil.cpu_percent(interval=1.0), psutil.sensors_battery().percent))
