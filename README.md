
How the project works:
first will insert new records for drone in Drone table using this API /drone/save-drone/ status of the drone will by default Idle
the dynamically will change by every periodic time. The battery of the drone will get decreased every periodic time if it's loaded with a medication and will get full charge 
when the status changed from Returning to IDLE.
then we can load the medication by using this API drone/save-medication/ this API will insert new record into MEDICATION table and then will load it on any free drones.
there is a scheduling is applied in the project that will print the status of the drone and battery level and will change the status of each drone whose status not equal Idle and will decrease the battery capacity.
The is a file called appLog.log which is storing all the logs. 
Installation:
execute the attached sql file 'SQL.sql'
import the data.sql into your database
import the drone project into your IDE
Run it as spring boot application in the IDE.
Update the dependencies given in the pom.xml using Maven.

APIS
first drone API to insert new record
API: http://localhost:8080/drone/save-drone/

Request
{
  "batteryCapacity": 90,
  "model": "LIGHTWEIGHT",
  "serialNumber": "sert510",
  "weightLimit": 200
}	

response 
{
    "status": "SUCCESS "
}
then use this API to insert medication and load it on a drone

http://localhost:8083/drone/save-medication/

request
file:
medname:
weight:	
code:

response 
{
    "status": "SUCCESS "
}

pass the required file in 'file' field and pass 'medname', 'code', 'weight'
for an Example, please review image with name 'saveMedication.png' 
once the medication got registered if there is no drone free to lead it will wait till one of the drones get free
changing the status for the loaded drones will be automatically changing every periodic time.

you can use swagger for testing by this link http://localhost:8080/swagger-ui.html#!/

please find the commit history for this project with file name =commitHistory.png
