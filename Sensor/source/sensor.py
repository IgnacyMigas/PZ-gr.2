import timer as ti
import time
from tools import SensorTools
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('--reg',                 '-r',  help="registration flag. Options:yes or no. Default: yes",             type=str,   default="yes")
parser.add_argument('--name',                '-n',  help="sensor name",                                                    type=str,   default="pokojXXX")
parser.add_argument('--interval',            '-i',  help="measurements interval. Min: 1.0. Max: 30.0",                     type=float, default=1.0)
parser.add_argument('--measurements_amount', '-ma', help="number of measurements sended in one package. Min: 1. Max: 100", type=int,   default=10)
parser.add_argument('--metrics',             '-m',  help="metrics flag. Options: CPU, Battery or Both, Default: Both",     type=str,   default="Both")

def check_starting_arguments(args):
    args.reg                 = check_reg_argument(args.reg)
    args.interval            = check_interval_argument(args.interval)
    args.measurements_amount = check_measurements_amount_argument(args.measurements_amount)
    args.metrics             = check_metrics_argument(args.metrics)
    return args

def check_reg_argument(reg):
    if (reg != "yes") and (reg != "no"):
        print("Invalid reg option. Sensor will be run with default value: yes")
        reg = "yes"
    return reg

def check_interval_argument(interval):
    if (interval < 1.0):
        print("Interval can not be lower than 1.0. Sensor will be run with interval min value: 1.0")
        interval = 1.0
    elif (interval > 30.0):
        print("Interval can not be higher than 30.0. Sensor will be run with interval max value: 30.0")
        interval = 30.0
    return interval

def check_measurements_amount_argument(measurements_amount):
    if (measurements_amount < 1):
        print("Measurements amount can not be lower than 1. Sensor will be run with measurements amount min value: 1")
        measurements_amount = 1
    elif (measurements_amount > 100):
        print("Measurements amount can not be higher than 100. Sensor will be run with measurements amount max value: 100")
        measurements_amount = 100
    return measurements_amount

def check_metrics_argument(metrics):
    if (metrics != "CPU") and (metrics != "Battery") and (metrics != "Both"):
        print("Invalid metrics option. Sensor will be run with default value: Both")
        metrics = "Both"
    return metrics
        
def sensor_main(args):
    tool = SensorTools(args.metrics, args.name)
    if args.reg == "yes":
        tool.register_sensor()
    
    timer = ti.Timer(interval=args.interval, function=tool.colect_data)
    
    timer.start()
    while True:
        time.sleep(args.measurements_amount*args.interval)
        #timer.stop()
        tool.send_data()
    
if __name__ == '__main__':
    args = parser.parse_args()
    args = check_starting_arguments(args)
    sensor_main(args)