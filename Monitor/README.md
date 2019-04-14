############################### ######## Monitor README ######## ###############################

######## Technologies:

    Java

######## Libraries:

    Spring Boot

######## Import Maven Project in Eclipse:

    File > Import > Maven > Existing Maven Project
	
Make sure that there is path to JDK set. (Window > Preferences > Java > Installed JREs)

######## Working with project:

To set monitorId:

	Modify variable MONITORID in file application.properties (src/main/resources)

To run project:

    Run As > Maven build
	Set goals: spring-boot:run

To clean project:

    Run As > Maven clean

URL for monitor with "v1" as MONITORID :
	
	http://localhost:8080/v1/
	
Available endpoints:

	http://localhost:8080/v1/hosts (GET/POST)
	http://localhost:8080/v1/hosts/{id} (GET)
	http://localhost:8080/v1/metrics (GET)
	http://localhost:8080/v1/metrics/{id} (GET/DELETE)
	http://localhost:8080/v1/metrics/{id}/measurements (GET/POST)

Date format:

	"dd/MM/yyyy HH:mm:ss"
	
Parameters and status codes handling not yet implemented!


	
	
