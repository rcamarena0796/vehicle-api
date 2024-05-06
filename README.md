# vehicle-api

Instructions to run locally:

- Install the latest Docker version: https://www.docker.com/products/docker-desktop/
- Make sure that local ports 8080 and 3306 are not in use.
- Run the start-compose.sh script in a terminal.
- Brief script explanation: The script creates a MySql DB container, then compiles and run the Springboot application in another container.
- Use the " interface.html " file to interact with the application.

To access the API swagger: 
- Go to: http://localhost:8080/swagger-ui/index.html

To create or update a vehicle:

- All the fields are mandatory.
- License plate must be unique across all of the registered vehicles.
- The format of the year is four numeric digits.
- All the validations mentioned above are handled in the backend and shown in the interface. 
