FFA (Finance Tracking App)
FFA is a full-stack application for managing incomes, and expenses. Built with Angular for the frontend, Spring Boot for the backend, and MongoDB as the database, this project helps users track their finances efficiently.

Prerequisites
To build and deploy the application, ensure the following tools are installed on your system:

Backend:
Java Development Kit (JDK) 17 or newer
Gradle 8.x (or use the Gradle wrapper)
MongoDB (local or remote instance)
Frontend:
Node.js 18.x or newer
Angular CLI 15.x or newer
Setup Instructions
Follow these steps to clone, build, and run the project.

1. Clone the Repository
Create an empty folder on you hard-drive, right-click on an empty space and open a gitbash window
![Folder Screenshot](https://github.com/ioanniskemerlis/FFA/tree/main/images/folder.png "Folder Screenshot")

Clone the repository:
![Clone the repository Screenshot](images/clone.png "Clone the repository Screenshot")
git clone https://github.com/ioanniskemerlis/FFA.git

Navigate to project folder
![Navigate to folder Screenshot](images/navigate.png "Navigate to folder Screenshot")
cd FFA

2. Backend Setup
Navigate to the backend folder:
![Navigate to backend Screenshot](images/navigate2.png "Navigate to backend Screenshot")
cd backend

Build the backend:
If Gradle is installed:

![Building the backend Screenshot](images/gbuild.png "Building the backend Screenshot")
gradle build
Using the Gradle wrapper:


Run the backend:
![Running the backend Screenshot](images/grun.png "Running the backend Screenshot")
gradle bootRun

If everything is correct, you should see: 
![Running the backend result Screenshot](images/grun1.png "Running the backend result Screenshot")

Verify the backend:
The backend runs by default at http://localhost:8080. Open your browser and navigate to:

Swagger API documentation: http://localhost:8080/swagger-ui/index.html


3. Frontend Setup
Navigate to frontend folder
cd ../frontend

Install dependencies:
![Install dependencies Screenshot](images/installdep.png "Install dependencies Screenshot")
npm install


Serve the frontend locally:
![Serving the frontend Screenshot](images/serve.png "Serving the frontend Screenshot")
ng serve

Verify the frontend:
The Angular application runs by default at http://localhost:4200. Open your browser and navigate to it.


Testing Instructions

Navigate to the backend folder:

cd backend
Run tests:
![Tests Screenshot](images/tests.png "Tests Screenshot")
gradle test

The test results should be at build/reports/tests/test/index.html

gradle jacocotestreport
The test results should be at build/reports/jacoco/test/html/index.html


SECURITY WARNING!!!
The MongoDB URI and the SECRET_KEY for password hashing are both hardcoded in the respective classes. This was done for Ease of Review.
UNDER NO CIRCUMSTANCES you should hardcode these in the repo and should instead use enviromental variables to store them safely.

USER CREDENTIALS POPULATED WITH DATA FOR REVIEW & TESTING:
username: testuser
pwd: Testuser1$


Deployment Instructions
1. Backend Deployment
Package the Spring Boot application as a JAR file:

Deploy the JAR file to your hosting provider (e.g., AWS, Azure, Heroku) or a local server.
2. Frontend Deployment
Build the production version of the Angular application:

ng build --prod
Deploy the contents of the dist/ folder to a static hosting service or a web server.



Contributions
Feel free to fork the repository and submit pull requests. Contributions are welcome!

License
This project is licensed under the MIT License.