#Delete containers, images and network

docker stop vehicle_app_container
docker stop mysql_db_container

docker rm vehicle_app_container
docker rm mysql_db_container
docker rmi mysql_db vehicle_app

docker network disconnect my-network vehicle_app_container
docker network disconnect my-network mysql_db_container
docker network rm my-network

#START
docker network create my-network

cd database

docker build -t mysql_db .

docker run -d --network my-network -p 3306:3306 --name mysql_db_container mysql_db

cd ..

docker build --no-cache -t vehicle_app .

docker run --name vehicle_app_container --network my-network -p 8080:8080 vehicle_app