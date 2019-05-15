import requests 
import psutil
import datetime
import time

class SensorTools:
    API_REGISTER_ENDPOINT             = "http://localhost:8080/v1/hosts"
    API_CPU_MEASUREMENTS_ENDPOINT     = "http://localhost:8080/v1/metrics/CPU_testHost/measurements"
    API_BATTERY_MEASUREMENTS_ENDPOINT = "http://localhost:8080/v1/metrics/Battery_testHost/measurements"

    token = "xxxxxxxx"
    register_header = { "access-token":token }

    def __init__(self, metric):
        self.metric = metric
        self.set_variables()
    
    def set_variables(self):
        self.hostID = "pokoj 222"
        self.os     = "Windows"
        if self.metric == "CPU":
            self.type           = "CPU"
            self.unit           = "%"
            self.metric_id      = "CPU_testHost"
            self.collected_data = []
            self.timestamp      = []
        elif self.metric == "Battery":
            self.type           = "Battery"
            self.unit           = "%"
            self.metric_id      = "Battery_testHost"
            self.collected_data = []
            self.timestamp      = []
        else:
            self.type1           = "CPU"
            self.unit1           = "%"
            self.metric_id1      = "CPU_testHost"
            self.type2           = "Battery"
            self.unit2           = "%"
            self.metric_id2      = "Battery_testHost"
            self.collected_data1 = []
            self.collected_data2 = []
            self.timestamp       = []
    
    def register_sensor(self):
        if self.metric == "Both":
            data = self.register_json_data_for_two_metrics()
        else:
            data = self.register_json_data_for_one_metric()
    
        print(data)
        #r = requests.post(url = API_REGISTER_ENDPOINT, json = data)#, params = register_header) 
    
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
            #r = requests.post(url = API_CPU_MEASUREMENTS_ENDPOINT, json = data[0])#, params = register_header)
            #r = requests.post(url = API_BATTERY_MEASUREMENTS_ENDPOINT, json = data[1])#, params = register_header)
            print(data[0])
            print("#################")
            print(data[1])
            self.collected_data1 = []
            self.collected_data2 = []
        elif self.metric == "CPU":
            data = self.json_data_for_one_metric()
            #r = requests.post(url = API_CPU_MEASUREMENTS_ENDPOINT, json = sdata)#, params = register_header)
            print(data)
            self.collected_data = []
        else:
            data = self.json_data_for_one_metric()
            #r = requests.post(url = API_BATTERY_MEASUREMENTS_ENDPOINT, json = sdata)#, params = register_header)
            print(data)
            self.collected_data = []
        self.timestamp = []

    def json_data_for_one_metric(self):
        data = []
        for i in range(len(self.collected_data)):
            temp        = {}
            temp["val"] = self.collected_data[i]
            temp["ts"]  = self.timestamp[i]
            data.append(temp)
        
        return data
    
    def json_data_for_two_metrics(self):
        data1 = []
        data2 = []
        for i in range(len(self.collected_data1)):
            temp1        = {}
            temp1["val"] = self.collected_data1[i]
            temp1["ts"]  = self.timestamp[i]
            
            temp2        = {}
            temp2["val"] = self.collected_data2[i]
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
        self.timestamp.append(datetime.datetime.fromtimestamp(time.time()).strftime('%Y-%m-%d %H:%M:%S'))
        #print(self.collected_data)
        #print(self.timestamp)
        #print("CPU percent: {0}% \nBattery: {1}%".format(psutil.cpu_percent(interval=1.0), psutil.sensors_battery().percent))
