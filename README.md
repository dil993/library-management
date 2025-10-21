---

## 🚀 Steps to Run the Application

### **1️⃣ Prerequisites**

Make sure the following are installed on your system:

* **Java 17+**
* **Maven 3.8+**
* (Optional) **Postman / cURL** for testing APIs
* (Optional) **IntelliJ IDEA / Eclipse** for IDE-based execution

---

### **2️⃣ Clone or Download the Project**

```bash
git clone https://github.com/<your-username>/library-management.git
cd library-management
```

Or download the ZIP file and extract it to your workspace.

---

### **3️⃣ Build the Application**

Run the Maven build to download dependencies and compile the code:

```bash
mvn clean install
```

---

### **4️⃣ Run the Application**

Start the Spring Boot app using:

```bash
mvn spring-boot:run
```

or run the `LibraryApplication.java` class directly from your IDE.

✅ **Default Port:** `8080`
✅ **Base URL:** `http://localhost:8080/api/library`

---

### **5️⃣ Access the H2 Database Console**

Open your browser and go to:
👉 **[http://localhost:8080/h2-console](http://localhost:8080/h2-console)**

**JDBC URL:** `jdbc:h2:mem:librarydb`
**Username:** `sa`
**Password:** *(leave blank)*

You can view the `BOOKS` and `USERS` tables and see how data changes after checkouts or returns.

---

### **6️⃣ Test the REST APIs**

#### ✅ **Checkout a Book**

**Request**

```
POST http://localhost:8080/api/library/checkoutBook?userId=1&bookId=2
```

**Response**

```
Book checked out successfully!
```

#### ✅ **Return a Book**

**Request**

```
POST http://localhost:8080/api/library/returnBook?userId=1&bookId=2
```

**Response**

```
Book returned successfully!
```

#### ❌ **Book Already Checked Out (Error Example)**

**Response**

```json
{
  "status": 409,
  "error": "Conflict",
  "message": "Book 'Clean Code' is already checked out.",
  "path": "/api/library/checkoutBook"
}
```

---

### **7️⃣ Run Unit Tests (Optional)**

To execute the service-level tests:

```bash
mvn test
```

This will run all test cases in `LibraryServiceImplTest.java` and print the results in the console.

---

### **8️⃣ Stop the Application**

To stop the Spring Boot application:

* Press `Ctrl + C` in the terminal, or
* Stop the process in your IDE.

---

### ✅ Summary

| Step | Description                                        |
| ---- | -------------------------------------------------- |
| 1️⃣  | Install Java + Maven                               |
| 2️⃣  | Clone or extract project                           |
| 3️⃣  | Build using `mvn clean install`                    |
| 4️⃣  | Run using `mvn spring-boot:run`                    |
| 5️⃣  | Access APIs on `http://localhost:8080/api/library` |
| 6️⃣  | Test via Postman / browser                         |
| 7️⃣  | Check database in H2 console                       |
| 8️⃣  | Run tests using `mvn test`                         |

---

Would you like me to also add a **visual diagram** (flowchart of “User → Controller → Service → DB”) in the README for explaining to interviewers how each layer interacts? It helps make your project presentation even more professional.
