import timer as ti
import time
from tools import SensorTools

def sensor_main(tool):
    tool.register_sensor()
    # TODO: check if registration OK. If not try to register again !!!
    
    timer = ti.Timer(interval=1.0, function=tool.colect_data)
    
    while True:
        timer.start()
        time.sleep(10.0)
        timer.stop()
        tool.send_data()
    
if __name__ == '__main__':
    tool = SensorTools("Both")
    sensor_main(tool)
