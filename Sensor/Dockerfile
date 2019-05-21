FROM python:3.6-slim

WORKDIR /app

COPY . /app

RUN apt-get update && apt-get -y install gcc && rm -rf /var/lib/apt/lists/*

RUN pip install --no-cache-dir --trusted-host pypi.python.org -r requirements.txt

CMD ["python", "source/sensor.py"]