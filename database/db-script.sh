docker build -t mysql_db .

docker run -d -p 3306:3306 --name mysql_db_container mysql_db