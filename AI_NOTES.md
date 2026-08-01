# AI Notes

I used Claude (Anthropic) throughout this assignment — for scaffolding the
API, debugging real environment issues that came up, and drafting
documentation. Details below.

## 1. Which parts of the code were AI-generated vs. written by me

AI-generated (Claude):
- The full project structure and layered design: `Expense` model,
  `ExpenseRepository` (in-memory store), `ExpenseService` (business logic —
  filtering, totals), `ExpenseController` (REST endpoints), and
  `GlobalExceptionHandler` / `ExpenseNotFoundException` for error handling.
- The JUnit test suite (`ExpenseControllerTest.java`).
- The first drafts of `README.md` and this file.

Written / decided by me:
- Chose Java + Spring Boot as the stack and set up the project in Eclipse
  via Spring Initializr myself.
- Ran and debugged the app end-to-end (see below) rather than assuming the
  generated code worked as-is.
- Manually tested every endpoint in Postman before trusting the API.

## 2. What I validated, tested, or changed, and why

- **Startup failure #1**: the app failed to start with `Failed to bind
  properties under 'spring.jackson.serialization'`. Root cause: my project
  generated on Spring Boot 4, which uses a newer JSON library (Jackson 3)
  that renamed/reorganized its internal settings. The property Claude
  originally suggested (`write-dates-as-timestamps`) no longer exists under
  that name. I removed the property entirely after confirming Jackson 3
  already outputs dates in the desired format by default — verified by
  re-running the app and checking a POST response.
- **Startup failure #2**: `@AutoConfigureMockMvc` couldn't be resolved in
  the test file. Root cause: Spring Boot 4 split MockMvc test support into
  its own dependency (`spring-boot-starter-webmvc-test`) and moved the
  annotation to a new package. I added the missing dependency to `pom.xml`
  and corrected the import, then re-ran the tests to confirm they passed.
- **Changed the test file's JSON construction**: the original draft built
  request bodies using a Jackson `ObjectMapper`, which hit the same
  Jackson-2-vs-3 mismatch. I had it rewritten to use plain Java text blocks
  instead, so the tests no longer depend on that library at all.
- **Manual verification in Postman**: tested add (POST), list all (GET),
  filter by category (GET with query param), both totals endpoints (GET),
  and delete (DELETE) against the running server, checking both status
  codes and response bodies against what the code was supposed to do.
- Ran `mvn clean install`, `mvn spring-boot:run`, and `mvn test` myself
  before submitting, to make sure the exact commands in the README work.

## 3. AI suggestions I didn't use, and why

- Claude's first draft of the README included a `spring.jackson.
  serialization.write-dates-as-timestamps=false` property — I removed it
  once we confirmed it was unnecessary (and actually broke startup) on
  Spring Boot 4/Jackson 3.
- I considered using PostgreSQL, since I already had it installed locally,
  but the assignment explicitly allows in-memory storage and doesn't
  require a database. Claude also pointed out that adding a real DB
  dependency creates risk for an automated grading pipeline, since the
  reviewer's environment wouldn't have a matching database configured —
  so I stuck with the simpler in-memory approach.
- I did not implement any of the optional bonus features (search, monthly
  summary, Swagger docs, Docker) in order to keep the core API solid and
  fully tested within the time available.
