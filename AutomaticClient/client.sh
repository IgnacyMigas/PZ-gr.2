#!/bin/bash

url="http://www.example.com"
FAILED=1
SUCCESS=0

# h - help, l - list of metrics, m - metric type, none - help
usage() {
	echo "Usage: $0 [-m <metric_type>] [-l] [-h]"
	echo
	echo "-m metric_type  Set metric type to print top 10"
	echo "-l              Print list of metrics types"
	echo "-h              Print usage"
	exit $SUCCESS;
}

while getopts ":m:lh" o
do
	case $o in
	m) metric=$OPTARG
	   ;;
	l) curl "$url/metrics";
	   exit $SUCCESS;
	   ;;
	h|*) usage
	   ;;
	esac
done

echo $metric;
if [ -z "$metric" ]; then
	usage
fi

#auth
#curl "$url/token?access_token=1234567890abcdefghijklmnopqrstuvwxyzABCD"

#quick check
if curl --output /dev/null --silent --head --fail "$url"; then
  printf '%s\n' "$url exist"
else
  printf '%s\n' "$url does not exist"
  exit $FAILED;
fi

#print top 10, refresh 1s
watch -n1 -t curl "$url/hosts?top=10&metric_type=$metric"

exit $SUCCESS;
