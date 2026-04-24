# 🏦 Banking System - Spring Boot Backend

A fully functional **Banking System Backend Application** built using **Spring Boot**, demonstrating real-world backend concepts like transactions, security, exception handling, and RESTful APIs.

---

## 🚀 Features

* 👤 Create and manage bank accounts
* 💰 Deposit money
* 💸 Withdraw money
* 🔁 Transfer funds between accounts
* 📊 View transaction history
* 🔐 Secure APIs using JWT Authentication
* ⚠️ Global exception handling
* 📄 API documentation using Swagger
* 🧪 Unit testing using Mockito

---

## 🛠️ Tech Stack

* **Java 17**
* **Spring Boot**
* **Spring Data JPA (Hibernate)**
* **Spring Security (JWT)**
* **MySQL**
* **Lombok**
* **Swagger (OpenAPI)**
* **JUnit 5 + Mockito**

---

## 📂 Project Structure

```
com.bank.banking
│
├── controller      # REST Controllers
├── service         # Business Logic
├── repository      # Data Access Layer
├── entity          # JPA Entities
├── dto             # Request/Response Models
├── exception       # Global Exception Handling
├── config          # Security & Swagger Config
├── filter          # JWT Filter
└── util            # Utility Classes (JWT)
```

---

## 🔐 Authentication (JWT)

* Login API generates JWT token
* Token must be passed in header:

```
Authorization: Bearer <your_token>
```

---

## 📌 API Endpoints

### 🔑 Authentication

| Method | Endpoint      | Description        |
| ------ | ------------- | ------------------ |
| POST   | `/auth/login` | Generate JWT token |

---

### 👤 Account APIs

| Method | Endpoint         | Description         |
| ------ | ---------------- | ------------------- |
| POST   | `/accounts`      | Create account      |
| GET    | `/accounts/{id}` | Get account details |

---

### 💰 Transactions

| Method | Endpoint                      | Description         |
| ------ | ----------------------------- | ------------------- |
| POST   | `/accounts/{id}/deposit`      | Deposit money       |
| POST   | `/accounts/{id}/withdraw`     | Withdraw money      |
| POST   | `/accounts/transfer`          | Transfer money      |
| GET    | `/accounts/{id}/transactions` | Transaction history |

---

## 📊 Swagger UI

Access API documentation:

```
http://localhost:8080/swagger-ui/index.html
```

Use **Authorize 🔒 button** to enter JWT token.

---

## ⚙️ How to Run

### 1️⃣ Clone the repository

```
git clone https://github.com/nethaji1997/banking-system.git
cd banking-system
```

### 2️⃣ Configure Database

Update `application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/bank_db
spring.datasource.username=root
spring.datasource.password=root
```

### 3️⃣ Run the application

```
mvn spring-boot:run
```

---

## 🧪 Running Tests

```
mvn test
```

---

## 🧠 Key Concepts Implemented

* ✅ Layered Architecture (Controller → Service → Repository)
* ✅ Transaction Management using `@Transactional`
* ✅ JWT-based Authentication & Authorization
* ✅ Global Exception Handling using `@RestControllerAdvice`
* ✅ Logging with SLF4J
* ✅ Unit Testing with Mockito
* ✅ RESTful API Design

---

## ⚠️ Edge Cases Handled

* Invalid amount (≤ 0)
* Insufficient balance
* Same account transfer
* Invalid account ID
* Unauthorized access

---

## 🔮 Future Enhancements

* 🔐 Password encryption using BCrypt
* 👥 Role-based access (Admin/User)
* 📦 Docker support
* ☁️ Deployment (AWS / Azure)
* 📈 Monitoring & Logging tools

---

## 👨‍💻 Author

**Nethaji Gowda**

---

## ⭐ If you like this project

Give it a ⭐ on GitHub and feel free to fork & improve!

---
