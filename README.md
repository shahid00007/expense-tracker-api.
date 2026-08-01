# Smart Expense Tracker API

A REST API for managing personal expenses — add, view, filter by category,
calculate totals, and delete expenses. Built for the Diligent Software
Engineering Apprenticeship take-home assignment.

## Tech Stack

- Java 17
- Spring Boot 4.x (Spring MVC + MockMvc)
- Maven
- In-memory storage (no database required — data resets when the server restarts)

## Features

- Add an expense (title, amount, category, date)
- View all expenses
- Filter expenses by category
- Get total expenses (overall and grouped by category)
- Delete an expense

## Prerequisites

- JDK 17 or newer — check with `java -version`
- Maven — check with `mvn -version` (or use your IDE's bundled Maven)

## Install

```bash
mvn clean install
```

## Run the server

```bash
mvn spring-boot:run
```

The server starts on **http://localhost:8080**.

## Run tests

```bash
mvn test
```

## API Reference

Base URL: `http://localhost:8080/api/expenses`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/expenses` | Add an expense |
| GET | `/api/expenses` | List all expenses |
| GET | `/api/expenses?category=Food` | Filter by category |
| GET | `/api/expenses/total` | Overall total |
| GET | `/api/expenses/total-by-category` | Totals grouped by category |
| DELETE | `/api/expenses/{id}` | Delete an expense |

### Example: Add an expense

Request:
```bash
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{"title":"Groceries","amount":45.5,"category":"Food","date":"2026-07-30"}'
```

Response (`201 Created`):
```json
{
  "id": "a1b2c3d4-...",
  "title": "Groceries",
  "amount": 45.5,
  "category": "Food",
  "date": "2026-07-30"
}
```

### Example: Delete an expense

```bash
curl -X DELETE http://localhost:8080/api/expenses/a1b2c3d4-...
```

Response: `204 No Content` on success, `404 Not Found` if the id doesn't exist.

## Project Structure

```
src/main/java/com/example/expensetracker/
├── model/          # Expense data model + validation
├── repository/     # In-memory storage
├── service/        # Business logic (totals, filtering)
├── controller/      # REST endpoints
└── exception/       # Centralized error handling
src/test/java/...    # Automated test suite (see also /tests for a pointer)
src/main/resources/application.properties
```

## Notes

- Data is stored in memory and is **not persisted** between server restarts, per the assignment's allowed storage options.
- See `AI_NOTES.md` for details on how AI tools were used while building this.
