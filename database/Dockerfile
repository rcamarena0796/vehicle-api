# Use the official MySQL image from Docker Hub
FROM mysql:latest

# Set environment variables for MySQL
ENV MYSQL_ROOT_PASSWORD=admin
ENV MYSQL_DATABASE=unow_challenge

# When the container starts, it will execute any *.sql or *.sh scripts found in /docker-entrypoint-initdb.d
#COPY ./database_vehicles.sql /docker-entrypoint-initdb.d/