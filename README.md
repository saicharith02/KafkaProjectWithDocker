# Kafka Intregation with Python and Java 
This project demonstrates how to use Python as a producer and Java as a consumer in a Kafka setup.
## Steps to Implement the Project
1. **Clone the Project from GitHub:**
Clone the project repository to your local machine.
   ```sh
   git clone https://github.com/saicharith02/KafkaProjectWithDocker.git
   ```
2. **Navigate to the Project Directory:**
After cloning the project, open a terminal and navigate to the project directory.
   ```sh
   cd <project_directory>
   ```
3. **Fetch the en Folder into the Python Folder:**
Ensure the en folder is located inside the producer folder.


4. **Build and Start the Docker Containers:**
Run the following command to build and start the Docker containers. This will take a few minutes for the installation.
  ```sh
  docker compose up --build 
  ```
5. **Access the MySQL Container:**
Once the containers are up, use this command to access the MySQL container.
  ```sh
  docker exec -it mydb bash
  ```
6. **Open the MySQL Server:**
To view all databases, use this command:
  ```sh
  mysql -u root -p
  ```
7. **Select the localspringbootdb Database:**
To view all databases, use this command:
  ```sh
  show databases;
  ```
8. **Use the localspringbootdb Database:**
If the localspringbootdb database is listed, select it with this command:
  ```sh
  use localspringbootdb;
  ```
9.**Query the Records:**
To view the records in the info table, use the following command:
  ```sh
  select * from info;
  ```

