docker_build:
	docker build --tag=pzgr2/pz-gr2:auth-km3 AuthService/ 
	docker build --tag=pzgr2/pz-gr2:monitor-km3 ./Monitor  
	docker build --tag=pzgr2/pz-gr2:sensor-km3 ./Sensor
	docker build --tag=pzgr2/pz-gr2:gateway-km3 ./APIGateway
	docker build --tag=pzgr2/pz-gr2:client-km3 ./client
	docker build --tag=pzgr2/pz-gr2:autoclient-km3 AutomaticClient/

docker_run:
	docker run -d --network host -it pzgr2/pz-gr2:auth-km3
	docker run -d --network host -it pzgr2/pz-gr2:gateway-km3
	docker run -d --network host -it pzgr2/pz-gr2:monitor-km3
	docker run --network host -it pzgr2/pz-gr2:sensor-km3
	
docker_run_no_d:
	docker run --network host -it pzgr2/pz-gr2:auth-km3
	docker run --network host -it pzgr2/pz-gr2:gateway-km3
	docker run --network host -it pzgr2/pz-gr2:monitor-km3
	docker run --network host -it pzgr2/pz-gr2:sensor-km3

docker_push:
		docker login -u pzgr2 
		docker push pzgr2/pz-gr2:auth-km3
		docker push pzgr2/pz-gr2:monitor-km3
		docker push pzgr2/pz-gr2:gateway-km3
		docker push pzgr2/pz-gr2:sensor-km3
		docker push pzgr2/pz-gr2:client-km3
		docker push pzgr2/pz-gr2:autoclient-km3