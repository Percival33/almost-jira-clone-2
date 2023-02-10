# Almost Jira
This is a Jira clone application with a Spring API backend and a Vue.js frontend using MongoDB as a database with [pico] styles.
The application is designed to run in separate containers, allowing for easy scaling and deployment.

## About

It has started as university project at  for Application Programming course. 
See mode details at 

And now is further developed by myself.

### Requirements
- Docker and Docker Compose
- Java 17
- Node.js and npm

### Running the Application
To run the application, simply clone the repository and run the following command in the root directory:

```
docker-compose up
```
This will start the application and both the frontend and backend will be accessible through the following URLs:

Frontend: http://localhost:3000
Backend: http://localhost:8080

Also swagger documentation of [api] is available.

### Configuration
The Jira clone application requires a few configuration parameters to run. 

The application can be configured through the following files:
- `backend/src/main/resources/application.yml`
- `frontend/src/config.js`

Additionally parameters are stored in a .env file in the root directory of the project.
The file should contain the following fields:
```
DB_USER=<your-database-user>
DB_PASSWORD=<your-database-password>
```
Replace `<your-database-user>` and `<your-database-password>` with your actual database credentials. 
These credentials will be used by both the frontend and backend to connect to the database.

The .env file is excluded from version control, so your credentials will remain private. 
If you are deploying the application to a production environment, 
make sure to set the DB_USER and DB_PASSWORD environment variables in the environment where the containers are running.

### Deployment
To deploy the application, simply run the following command in the root directory:

```bash
docker-compose up -d
```
This will start the containers in detached mode and the application will be running in the background.

### Contributing
Contributions are very welcome! If you would like to help, take a look at the list of open issues and see if there is 
anything you would like to work on. 
If you have an idea for a new feature or improvement, feel free to open a new issue to start a discussion. 
When you are ready to start coding, fork the repository and create a new branch for your changes. 
When you are done, create a pull request to merge your changes back into the main codebase. We appreciate your contributions!

### License
This project is licensed under the MIT license.

[commit]: https://github.com/Percival33/almost-jira-clone/tree/a7f51f4d3b9d6806ed4db78ff5efd46765f63dc1
[api]: http://localhost:8080/swagger-ui/index.html
[pico]: https://picocss.com/