# 🛒 EcomApp

A simple **E-commerce web application** built with **Java Spring Boot** and **MySQL**, designed to demonstrate modern backend development practices.  
This project showcases RESTful API design, database integration, and clean architecture for scalable applications.

---

## 🚀 Features
- User registration & login (Spring Security ready)
- Product management (CRUD APIs)
- Shopping cart functionality
- Order placement & tracking
- MySQL database integration
- REST APIs tested with Postman
- Built using Spring Boot, Hibernate (JPA), and Maven

---

## 🛠️ Tech Stack
- **Backend:** Java, Spring Boot, Spring Data JPA, Hibernate
- **Database:** MySQL
- **Build Tool:** Maven
- **Other Tools:** Git, Postman

---

## 📂 Project Structure
```

src/main/java/com/ecomapp
│── controller   # REST controllers
│── service      # Business logic
│── repository   # JPA repositories
│── model        # Entity classes

````

---

## ⚙️ Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/neeraj17x/EcomApp.git
   cd EcomApp
````

2. **Configure the database**

   * Create a MySQL database:

     ```sql
     CREATE DATABASE ecomapp;
     ```
   * Update `application.properties` with your DB username/password.

3. **Run the application**

   ```bash
   mvn spring-boot:run
   ```

4. **Test APIs**
   Use [Postman](https://www.postman.com/) or any REST client to test endpoints like:

   * `POST /users/login` → Get JWT Token
   * `POST /api/product` → Add a new product
   * `GET /api/products` → Fetch all products
   * `GET /api/products/category/{cat}` → Fetch  products by category
   * `POST /api/cart` → Add to cart
   * `POST /api/orders` → Place an order

---



## 🤝 Contributing

1. Fork the repo
2. Create a new branch (`feature/your-feature`)
3. Commit changes
4. Push the branch and open a Pull Request

---

## 📜 License

This project is licensed under the MIT License - feel free to use and improve.

---

## 👨‍💻 Author

**Neeraj**
Backend Developer | Java (Spring Boot, Hibernate, Microservices) | MySQL

📍 India
🔗 [LinkedIn](https://www.linkedin.com/in/neeraj17x) | [GitHub](https://github.com/neeraj17x)
