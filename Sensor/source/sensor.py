import psutil
import timer as ti
import time
import tools

collected_data = []

def colect_data():
    global collected_data   
    collected_data.append(psutil.cpu_percent(interval=1.0))
    #print("CPU percent: {0}% \nBattery: {1}%".format(psutil.cpu_percent(interval=1.0), psutil.sensors_battery().percent))

def sensor_main():
    tools.register_sensor(hostID="1", os="windows", type="measurement data", unit="%", name="cpu usage")
    # TODO: check if registration OK. If not try to register again !!!
    
    global collected_data
    timer = ti.Timer(interval=1.0, function=colect_data)
    
    while True:
        timer.start()
        time.sleep(10.0)
        timer.stop()
        print(collected_data)
        tools.send_data(hostID="1", data=collected_data)
        collected_data = []
    
if __name__ == '__main__':
    sensor_main()
