# FFA (Freelancer Finance App)
FFA is a full-stack application managing incomes and expenses built specifically for freelancer delivery drivers. Built with Angular for the frontend, Spring Boot for the backend, and MongoDB as the database, this project helps users track their finances efficiently.Its architecture ensures maintainability, scalability, and extensibility, setting a solid foundation for future enhancements.

The App features:

* ## Modular Design:
 Clear separation of concerns between frontend, backend, and database.
 
* ## Frontend Features

Angular Material Integration

Standalone Components

Real-Time Pagination

Charting Integration

Dynamic Forms

Notifications and Feedback


* ## Backend Features

Spring Boot Framework:
*   Rapid development with built-in features like dependency injection, data binding, and robust error handling.

Layered Architecture:
*   Controllers, services, and DAOs (repositories) are separated for clear responsibilities and maintainability.

*   Code Reusability (DTO pattern)

RESTful APIs

Backend Pagination

Custom Jwt Middleware

Spring Security

JWT-based authentication.

Password Hashing

Data Validation

Swagger/OpenAPI Integration


---

## Prerequisites
To build and deploy the application, ensure the following tools are installed on your system:

### Backend:
Java Development Kit (JDK) 17 or newer

Gradle 8.x (or use the Gradle wrapper)

MongoDB (local or remote instance)

### Frontend:
Node.js 18.x or newer

Angular CLI 15.x or newer

---


### Setup Instructions
Follow these steps to clone, build, and run the project.

1. ## Clone the Repository
Create an empty folder on you hard-drive, right-click on an empty space and open a gitbash window

![Folder Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/folder.PNG?raw=true "Folder Screenshot")

Clone the repository:

```bash
git clone https://github.com/ioanniskemerlis/FFA.git
```

![Clone the repository Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/clone.PNG?raw=true "Clone the repository Screenshot")


### Navigate to project folder

```bash
cd FFA
```

![Navigate to folder Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/navigate.PNG?raw=true "Navigate to folder Screenshot")


2. ## Backend Setup
### Navigate to the backend folder:

```bash
cd backend
```

![Navigate to backend Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/navigate2.PNG?raw=true "Navigate to backend Screenshot")


### Build the backend:

```bash
gradle build
```

![Building the backend Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/gbuild.PNG?raw=true "Building the backend Screenshot")


### Run the backend:

```bash
gradle bootRun
```

![Running the backend Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/grun.PNG?raw=true "Running the backend Screenshot")


If everything is correct, you should see: 

![Running the backend result Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/grun1.PNG?raw=true "Running the backend result Screenshot")

Verify the backend:

The backend runs by default at http://localhost:8080.

Open your browser and navigate to Swagger API documentation: http://localhost:8080/swagger-ui/index.html


3. # Frontend Setup

### Navigate to frontend folder
```bash
cd ../frontend
```

### Install dependencies:

![Install dependencies Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/installdep.PNG?raw=true "Install dependencies Screenshot")
```bash
npm install
```


### Serve the frontend locally:

```bash
ng serve
```

![Serving the frontend Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/serve.PNG?raw=true "Serving the frontend Screenshot")


Verify the frontend:

The Angular application runs by default at http://localhost:4200. Open your browser and navigate to it.


# Testing Instructions

### Navigate to the backend folder:
```bash
cd backend
```
### Run tests:

```bash
gradle test
```
![Tests Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/tests.PNG?raw=true "Tests Screenshot")


The test results should be at build/reports/tests/test/index.html

```bash
gradle jacocotestreport
```
The test results should be at build/reports/jacoco/test/html/index.html

---


## Security Warning ⚠️
The MongoDB URI and the SECRET_KEY for password hashing are both hardcoded in the respective classes. This was done for Ease of Review.
### **Never hardcode sensitive information in a repository.**  use enviromental variables to store them safely.

## User Credentials for Testing
- **Username**: `testuser`
- **Password**: `Testuser1$`

---

## Demo
### Login Page

![Login Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/login.PNG?raw=true "Tests Screenshot")

### Register Page

![Register Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/register.PNG?raw=true "Tests Screenshot")

### Dashboard

![Dashboard Screenshot](https://github.com/ioanniskemerlis/FFA/blob/main/images/dashboard.PNG?raw=true "Tests Screenshot")

---
# Classes analysis

# Controller Layer
The controller layer is responsible for handling HTTP requests from the frontend, invoking services, and returning appropriate responses.

1. ## AuthController

### Purpose:

Handles user authentication and registration.

Issues JWT tokens for authenticated users.

Verifies authentication with test endpoints.

### Key Endpoints:

/api/auth/register: Registers a new user.

/api/auth/login: Authenticates a user and returns a JWT token.

/api/auth/test: Test endpoint to verify JWT authentication.

2. ##  IncomeController

### Purpose:

Manages CRUD operations for user incomes.

Handles pagination and data transformation (DTOs).

### Key Endpoints:

/api/incomes: Create a new income or fetch paginated incomes.

/api/incomes/{id}: Update or delete a specific income.

/api/incomes/all: Fetch all incomes for a user.

3. ## ExpenseController

### Purpose:

Manages CRUD operations for user expenses.

Handles pagination and data transformation (DTOs).

### Key Endpoints:

/api/expenses: Create a new expense or fetch paginated expenses.

/api/expenses/{id}: Update or delete a specific expense.

/api/expenses/all: Fetch all expenses for a user.

# Service Layer

The service layer encapsulates business logic, interacting with repositories and managing application workflows.

4. ## UserService

### Purpose:

Handles business logic related to user management.

Manages user registration with password hashing.

### Key Methods:

registerUser(User user): Hashes the password and saves the user to the database.

5. ## IncomeService

### Purpose:

Provides business logic for managing incomes.

Interacts with IncomeRepository for CRUD operations.

### Key Methods:

addIncome(Income income): Adds a new income record.

getIncomesByUser(String userId, int page, int size): Fetches paginated incomes for a user.

updateIncome(String id, Income updatedIncome): Updates an existing income record.

deleteIncome(String id): Deletes an income record.

6. ## ExpenseService

### Purpose:

Provides business logic for managing expenses.

Interacts with ExpenseRepository for CRUD operations.

### Key Methods:

addExpense(Expense expense): Adds a new expense record.

getExpensesByUser(String userId, int page, int size): Fetches paginated expenses for a user.

updateExpense(String id, Expense updatedExpense): Updates an existing expense record.

deleteExpense(String id): Deletes an expense record.

7. ## CustomUserDetailsService

### Purpose:

Implements Spring Security’s UserDetailsService for authentication.

Loads user details (e.g., username, password, roles) from the database.

### Key Method:

loadUserByUsername(String username): Fetches user details for authentication.

# Repository Layer

The repository layer interacts with the MongoDB database for CRUD operations.


8. ## UserRepository

### Purpose:

Provides methods to query the users collection.

### Key Methods:

findByUsername(String username): Fetches a user by their username.

9. ## IncomeRepository

### Purpose:

Provides methods to query the incomes collection.

### Key Methods:

findByUserId(String userId, Pageable pageable): Fetches paginated incomes for a user.

findAllIncomeByUserId(String userId): Fetches all incomes for a user.

10. ## ExpenseRepository

### Purpose:

Provides methods to query the expenses collection.

### Key Methods:

findByUserId(String userId, Pageable pageable): Fetches paginated expenses for a user.

findAllExpensesByUserId(String userId): Fetches all expenses for a user.

# Model Layer

The model layer defines the data structure used across the application.

11. ## User

### Purpose:

Represents the user entity stored in the users collection.

Includes fields like username, password, email, and role.

Annotations:

@Document(collection = "users"): Maps the class to the MongoDB collection.

12. ## Income

### Purpose:

Represents the income entity stored in the incomes collection.

Includes fields like type, amount, date, and notes.

Annotations:

@Document(collection = "incomes"): Maps the class to the MongoDB collection.

13. ## Expense

### Purpose:

Represents the expense entity stored in the expenses collection.

Includes fields like type, amount, date, and notes.

Annotations:

@Document(collection = "expenses"): Maps the class to the MongoDB collection.

# Utility Layer

The utility layer provides reusable components that support application functionality.


14. ## JwtUtil

### Purpose:

Generates and validates JWT tokens for authentication.

Extracts the username from tokens.

### Key Methods:

generateToken(String username): Generates a JWT token for a username.

extractUsername(String token): Extracts the username from a JWT token.

validateToken(String token, String username): Validates the token for a username.

# Security Layer

The security layer enforces authentication and authorization policies.

15. ## SecurityConfig

### Purpose:

Configures Spring Security settings for the application.

Manages JWT filter integration and endpoint permissions.

### Key Configurations:

CORS: Configures cross-origin requests for the Angular frontend.

JWT Authentication Filter: Validates tokens for protected endpoints.

CSRF Protection: Disabled to allow token-based authentication.

16. ## JwtAuthenticationFilter

### Purpose:

Intercepts HTTP requests to validate JWT tokens.

Populates the SecurityContextHolder with authentication details.

### Key Methods:

doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain): Validates JWT tokens for each request.

# Mapper Layer

The mapper layer transforms data between entities and DTOs.


17. ## IncomeMapper

### Purpose:

Converts between Income entities and IncomeRequestDTO/IncomeResponseDTO.

### Key Methods:

toEntity(IncomeRequestDTO dto, String userId): Converts a DTO to an entity.

toResponseDTO(Income income): Converts an entity to a DTO.

18. ## ExpenseMapper

### Purpose:

Converts between Expense entities and ExpenseRequestDTO/ExpenseResponseDTO.

### Key Methods:

toEntity(ExpenseRequestDTO dto, String userId): Converts a DTO to an entity.

toResponseDTO(Expense expense): Converts an entity to a DTO.


---
### Deployment Instructions

1. ## Backend Deployment

Package the Spring Boot application as a JAR file:

Deploy the JAR file to your hosting provider (e.g., AWS, Azure, Heroku) or a local server.

2. ## Frontend Deployment
Build the production version of the Angular application:

```bash
ng build --prod
```
Deploy the contents of the dist/ folder to a static hosting service or a web server.

---

#Contributions

Feel free to fork the repository and submit pull requests. Contributions are welcome!
This repository is open-source, you have my permission to use for experimenting/learning

---
