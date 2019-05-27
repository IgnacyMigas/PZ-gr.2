docker_build:
	docker build --tag=monitor-km2 ./Monitor  
	docker build --tag=sensor-km2 ./Sensor
	docker build --tag=gateway-km2 ./APIGateway
	docker build --tag=client-km2 ./client
docker_run:
	docker run -d --network host -it gateway-km2
	docker run -d --network host -it monitor-km2
	docker run -d --network host -it sensor-km2
	docker run -d --network host -it client-km2