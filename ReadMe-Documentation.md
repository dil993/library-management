---

## 📚 Library Management System – Spring Boot

This project implements a **simple Book Checkout & Return feature** for a library system.
It’s designed using clean, layered architecture with proper exception handling and unit tests.

---

## 🏗️ Project Architecture

```
com.library
 ├── controller        → REST endpoints for clients
 ├── service           → Business logic (checkout, return)
 ├── repository        → Spring Data JPA repositories
 ├── model             → Entity classes (User, Book)
 ├── exception/config  → Custom exceptions + global error handler
 └── LibraryApplication.java
```

---

## ⚙️ Functional Overview

### 1️⃣ **LibraryController**

Located at: `com.library.controller.LibraryController`

**Purpose:** Exposes REST APIs for book checkout and return.

**Endpoints:**

| HTTP Method | URL                                                 | Description                  |
| ----------- | --------------------------------------------------- | ---------------------------- |
| `POST`      | `/api/library/checkoutBook?userId={id}&bookId={id}` | Checks out a book for a user |
| `POST`      | `/api/library/returnBook?userId={id}&bookId={id}`   | Returns a book               |

**Code Summary:**

```java
@PostMapping("/checkoutBook")
public ResponseEntity<String> checkoutBook(@RequestParam Long userId, @RequestParam Long bookId) {
    return ResponseEntity.ok(service.checkoutBook(userId, bookId));
}
```

✅ Uses `LibraryServiceImpl` for business logic.
✅ Returns user-friendly message strings wrapped in `ResponseEntity`.

---

### 2️⃣ **LibraryServiceImpl**

Located at: `com.library.service.LibraryServiceImpl`

**Purpose:** Core business logic for checking out and returning books.
Implements `LibraryService` interface.

**Dependencies:**

* `BookRepository`
* `UserRepository`

**Key Functions:**

#### 🟢 `checkoutBook(Long userId, Long bookId)`

**Logic:**

1. Fetch user and book from repositories.
2. If either is missing → throw respective custom exception.
3. If the book is already checked out → throw `AlreadyCheckedOutException`.
4. Otherwise:

    * Mark book as unavailable (`available = false`)
    * Assign user as `checkedOutBy`
    * Save book.

**Returns:** `"Book checked out successfully!"`

#### 🟢 `returnBook(Long userId, Long bookId)`

**Logic:**

1. Verify that the book exists.
2. Check if the user returning matches `book.getCheckedOutBy()`.
3. If not → throw `InvalidReturnException`.
4. Reset `checkedOutBy = null`, mark book available again, save.

**Returns:** `"Book returned successfully!"`

#### 🟢 `saveUser(User user)` / `saveBook(Book book)`

Convenience methods for adding new records to the database.

---

### 3️⃣ **Custom Exceptions**

Located at: `com.library.exception`

| Exception                    | Description                   | HTTP Status       |
| ---------------------------- | ----------------------------- | ----------------- |
| `BookNotFoundException`      | Thrown when book ID not found | `404 NOT_FOUND`   |
| `UserNotFoundException`      | Thrown when user ID not found | `404 NOT_FOUND`   |
| `AlreadyCheckedOutException` | Book already checked out      | `409 CONFLICT`    |
| `InvalidReturnException`     | Wrong user returning the book | `400 BAD_REQUEST` |

**Example:**

```java
if (!book.isAvailable()) {
    throw new AlreadyCheckedOutException("Book '" + book.getTitle() + "' is already checked out.");
}
```

---

### 4️⃣ **LibraryControllerAdvice**

Located at: `com.library.config.LibraryControllerAdvice`

**Purpose:** Centralized exception handling for the whole application.
Maps Java exceptions → clean JSON error responses.

**Structure:**

```java
@RestControllerAdvice
public class LibraryControllerAdvice {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFound(BookNotFoundException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(AlreadyCheckedOutException.class)
    public ResponseEntity<Object> handleAlreadyCheckedOut(AlreadyCheckedOutException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT, request);
    }
    ...
}
```

**Output Example:**

```json
{
  "timestamp": "2025-10-19T22:43:11.187",
  "status": 409,
  "error": "Conflict",
  "message": "Book 'Clean Code' is already checked out.",
  "path": "/api/library/checkoutBook"
}
```

---

### 5️⃣ **Entity Design**

#### **User.java**

```java
@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "checkedOutBy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Book> checkedOutBooks = new HashSet<>();
}
```

#### **Book.java**

```java
@Entity
@Table(name = "books")
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "is_available")
    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "checked_out_by", referencedColumnName = "user_id")
    private User checkedOutBy;
}
```

---

### 6️⃣ **Unit Tests**

Located at: `src/test/java/com/library/service/LibraryServiceImplTest.java`

**Tests cover:**

* ✅ Successful checkout
* ❌ Book not found
* ❌ User not found
* ❌ Book already checked out
* ✅ Successful return
* ❌ Wrong user returning

**Example:**

```java
@Test
void testReturnBook_InvalidReturn() {
    book.setCheckedOutBy(anotherUser);
    when(bookRepository.findById(10L)).thenReturn(Optional.of(book));

    Exception ex = assertThrows(InvalidReturnException.class, () ->
        libraryService.returnBook(1L, 10L));

    assertTrue(ex.getMessage().contains("not authorized"));
}
```

---

## 🧪 API Testing

You can test via **Postman** or **curl**:

### Checkout a Book

```
POST http://localhost:8080/api/library/checkoutBook?userId=1&bookId=2
```

### Return a Book

```
POST http://localhost:8080/api/library/returnBook?userId=1&bookId=2
```

### Expected Responses

✅ `Book checked out successfully!`
❌ `Book not found`
❌ `Book 'Clean Code' is already checked out.`
❌ `This user is not authorized to return this book.`

---

## 💡 Design Highlights

* **Layered architecture** (Controller → Service → Repository → Entity)
* **Custom exceptions** with mapped HTTP responses
* **Centralized error handling** (`@RestControllerAdvice`)
* **Unit-tested** service layer
* **In-memory H2** database (auto-reset each startup)

---