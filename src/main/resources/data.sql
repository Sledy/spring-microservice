DROP TABLE IF EXISTS docker_instances;
CREATE TABLE docker_instances (
id INT AUTO_INCREMENT PRIMARY KEY,
container_name VARCHAR (200),
container_id VARCHAR(200),
image_name VARCHAR (200),
status VARCHAR(200) ,
last_status_update TIMESTAMP NOT NULL
);
