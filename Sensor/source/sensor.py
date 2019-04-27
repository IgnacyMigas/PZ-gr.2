import psutil
import timer as ti
import time

def test():
    print("CPU percent: {0}% \nTemp: {1}%".format(psutil.cpu_percent(interval=1.0), psutil.sensors_battery().percent))

def sensor_main():
    timer = ti.Timer(interval=1.0, function=test)
    timer.start()
    time.sleep(5.0)
    timer.stop()
    #time.sleep(10.0)
    #timer.start()
    #time.sleep(5.0)
    #timer.stop()

if __name__ == '__main__':
    sensor_main()
