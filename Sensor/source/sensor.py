import timer as ti
import time
from tools import SensorTools
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('--reg',                 '-r',  help="registration flag. Options:yes or no. Default: yes",         type=str,   default="yes")
parser.add_argument('--name',                '-n',  help="sensor name",                                                type=str,   default="pokojXXX")
parser.add_argument('--interval',            '-i',  help="measurements interval",                                      type=float, default=1.0)
parser.add_argument('--measurements_amount', '-ma', help="number of measurements sended in one package",               type=int,   default=10)
parser.add_argument('--metrics',             '-m',  help="metrics flag. Options: CPU, Battery or Both, Default: Both", type=str,   default="Both")

def check_starting_arguments(reg):
    if reg != "yes":
        print("elo")

def sensor_main(args):
    tool = SensorTools(args.metrics)
    tool.register_sensor()
    # TODO: check if registration OK. If not try to register again !!!
    
    timer = ti.Timer(interval=args.interval, function=tool.colect_data)
    
    while True:
        timer.start()
        time.sleep(args.measurements_amount*args.interval)
        timer.stop()
        tool.send_data()
    
if __name__ == '__main__':
    args = parser.parse_args()
    sensor_main(args)
