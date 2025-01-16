<span style="font-size:24px; color:blue; font-weight:bold;">FFA (Finance Tracking App)
<span style="font-size:12px;">FFA is a full-stack application for managing incomes, and expenses. Built with Angular for the frontend, Spring Boot for the backend, and MongoDB as the database, this project helps users track their finances efficiently.

<span style="font-weight:bold;">Prerequisites
To build and deploy the application, ensure the following tools are installed on your system:

<span style="font-weight:bold;">Backend:
Java Development Kit (JDK) 17 or newer
Gradle 8.x (or use the Gradle wrapper)
MongoDB (local or remote instance)
<span style="font-weight:bold;">Frontend:
Node.js 18.x or newer
Angular CLI 15.x or newer


<span style="font-weight:bold;">Setup Instructions
Follow these steps to clone, build, and run the project.

1. Clone the Repository
Create an empty folder on you hard-drive, right-click on an empty space and open a gitbash window

![Folder Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/folder.PNG?raw=true "Folder Screenshot")

Clone the repository:

![Clone the repository Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/clone.PNG?raw=true "Clone the repository Screenshot")
```bash
git clone https://github.com/ioanniskemerlis/FFA.git
```

Navigate to project folder

![Navigate to folder Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/navigate.PNG?raw=true "Navigate to folder Screenshot")
```bash
cd FFA
```

2. Backend Setup
Navigate to the backend folder:

![Navigate to backend Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/navigate2.PNG?raw=true "Navigate to backend Screenshot")
```bash
cd backend
```

Build the backend:
If Gradle is installed:

![Building the backend Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/gbuild.PNG?raw=true "Building the backend Screenshot")
```bash
gradle build
```
Using the Gradle wrapper:


Run the backend:

![Running the backend Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/grun.PNG?raw=true "Running the backend Screenshot")
```bash
gradle bootRun
```

If everything is correct, you should see: 

![Running the backend result Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/grun1.PNG?raw=true "Running the backend result Screenshot")

Verify the backend:
The backend runs by default at http://localhost:8080. Open your browser and navigate to:

Swagger API documentation: http://localhost:8080/swagger-ui/index.html


3. Frontend Setup
Navigate to frontend folder
```bash
cd ../frontend
```

Install dependencies:

![Install dependencies Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/installdep.PNG?raw=true "Install dependencies Screenshot")
```bash
npm install
```


Serve the frontend locally:

![Serving the frontend Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/serve.PNG?raw=true "Serving the frontend Screenshot")
```bash
ng serve
```

Verify the frontend:
The Angular application runs by default at http://localhost:4200. Open your browser and navigate to it.


Testing Instructions

Navigate to the backend folder:
```bash
cd backend
```
Run tests:

![Tests Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/tests.PNG?raw=true "Tests Screenshot")
```bash
gradle test
```

The test results should be at build/reports/tests/test/index.html

```bash
gradle jacocotestreport
```
The test results should be at build/reports/jacoco/test/html/index.html


<span style="font-size:24px; color:red; font-weight:bold;">SECURITY WARNING!!!
<span style="font-size:18px; color:black;">The MongoDB URI and the SECRET_KEY for password hashing are both hardcoded in the respective classes. This was done for Ease of Review.
<span style="font-size:18px; color:black;">UNDER NO CIRCUMSTANCES you should hardcode these in the repo and should instead use enviromental variables to store them safely.

<span style="font-size:24px; font-weight:bold;">USER CREDENTIALS POPULATED WITH DATA FOR REVIEW & TESTING:
<span style="font-size:14px; color:green;">username: testuser
<span style="font-size:14px; color:green;">pwd: Testuser1$


<span style="font-weight:bold;">Demo

Login page

![Login Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/login.PNG?raw=true "Tests Screenshot")

Register page

![Register Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/register.PNG?raw=true "Tests Screenshot")

Dashboard

![Dashboard Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/dashboard.PNG?raw=true "Tests Screenshot")


Deployment Instructions
1. Backend Deployment
Package the Spring Boot application as a JAR file:

Deploy the JAR file to your hosting provider (e.g., AWS, Azure, Heroku) or a local server.
2. Frontend Deployment
Build the production version of the Angular application:

```bash
ng build --prod
```
Deploy the contents of the dist/ folder to a static hosting service or a web server.



Contributions
Feel free to fork the repository and submit pull requests. Contributions are welcome!

License
This project is licensed under the MIT License.