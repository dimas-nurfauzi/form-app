# Form App — RESTful API

Dynamic form builder API built with Spring Boot for PT. Hare Business Consulting. Users can create forms with various question types, share them via slug, and collect responses from invited users based on email domain restrictions.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.0.6 |
| Security | Spring Security + JWT (jjwt 0.11.5) |
| Database | PostgreSQL |
| ORM | Spring Data JPA + Hibernate |
| Migration | Flyway |
| Documentation | SpringDoc OpenAPI (Swagger UI) 2.8.4 |
| Build Tool | Maven |
| Utility | Lombok |

---

## Requirements

- Java 17+
- PostgreSQL 14+
- Maven 3.8+

---

## Getting Started

### 1. Clone repository

```bash
git clone https://github.com/dimas-nurfauzi/form-app.git
cd form-app
```

### 2. Setup database

```sql
CREATE DATABASE formdb;
```

### 3. Configure environment

Edit `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
      url: ${DB_URL}
      username: ${DB_USERNAME}
      password: ${DB_PASSWORD}

jwt:  
  secret: ${JWT_SECRET}
  expiration: ${JWT_EXPIRATION}
```

### 4. Run application

```bash
mvn spring-boot:run
```

Flyway akan otomatis menjalankan migration dan seed data saat aplikasi pertama kali dijalankan.

---

## Default Users

| Name | Email | Password |
|---|---|---|
| User 1 | user1@webtech.id | password1 |
| User 2 | user2@webtech.id | password2 |
| User 3 | user3@worldskills.org | password3 |

---

## API Documentation

Swagger UI tersedia di:

```
http://localhost:8080/api/v1/swagger-ui/index.html
```

Base URL semua endpoint:

```
http://localhost:8080/api/v1
```

---

## API Endpoints

### Authentication

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| POST | `/auth/login` | Login dan dapatkan JWT token | ❌ |
| POST | `/auth/logout` | Logout dan invalidate token | ✅ |

#### Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "user1@webtech.id",
  "password": "password1"
}
```

Response:
```json
{
  "message": "Login success",
  "user": {
    "name": "User 1",
    "email": "user1@webtech.id",
    "accessToken": "eyJhbGci..."
  }
}
```

#### Logout
```http
POST /api/v1/auth/logout
Authorization: Bearer <accessToken>
```

Response:
```json
{
  "message": "Logout success"
}
```

---

### Form

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| POST | `/forms` | Buat form baru | ✅ |
| GET | `/forms` | Lihat semua form milik user | ✅ |
| GET | `/forms/{slug}` | Lihat detail form by slug | ✅ |

#### Create Form
```http
POST /api/v1/forms
Authorization: Bearer <accessToken>
Content-Type: application/json

{
  "name": "Biodata - Web Tech Members",
  "slug": "biodata",
  "description": "To save web tech members data",
  "limitOneResponse": true,
  "allowedDomains": ["webtech.id"]
}
```

#### Get All Forms
```http
GET /api/v1/forms
Authorization: Bearer <accessToken>
```

#### Get Form Detail
```http
GET /api/v1/forms/{slug}
Authorization: Bearer <accessToken>
```

---

### Question

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| POST | `/forms/{slug}/questions` | Tambah pertanyaan ke form | ✅ |
| DELETE | `/forms/{slug}/questions/{id}` | Hapus pertanyaan dari form | ✅ |

#### Add Question
```http
POST /api/v1/forms/{slug}/questions
Authorization: Bearer <accessToken>
Content-Type: application/json

{
  "name": "Most Favorite JS Framework",
  "choiceType": "multiple choice",
  "choices": ["React JS", "Vue JS", "Angular JS", "Svelte"],
  "isRequired": true
}
```

Supported `choiceType`:
- `short answer`
- `paragraph`
- `date`
- `multiple choice` — wajib isi `choices`
- `dropdown` — wajib isi `choices`
- `checkboxes` — wajib isi `choices`

#### Delete Question
```http
DELETE /api/v1/forms/{slug}/questions/{id}
Authorization: Bearer <accessToken>
```

---

### Response / Submission

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| POST | `/forms/{slug}/responses` | Submit jawaban form | ✅ |
| GET | `/forms/{slug}/responses` | Lihat semua jawaban (creator only) | ✅ |

#### Submit Response
```http
POST /api/v1/forms/{slug}/responses
Authorization: Bearer <accessToken>
Content-Type: application/json

{
  "answers": [
    { "questionId": 1, "value": "Budi Setiawan" },
    { "questionId": 2, "value": "Jakarta" },
    { "questionId": 3, "value": "2000-05-15" },
    { "questionId": 4, "value": "Male" }
  ]
}
```

#### Get All Responses
```http
GET /api/v1/forms/{slug}/responses
Authorization: Bearer <accessToken>
```

---

## Business Rules

- **Allowed Domains** — Form bisa dibatasi hanya untuk email domain tertentu. Jika `allowedDomains` kosong, form dapat diakses oleh siapa saja.
- **Limit One Response** — Jika diaktifkan, user hanya bisa submit satu kali per form.
- **Creator Only** — Hanya creator form yang bisa melihat responses dan mengelola questions.
- **Slug Unique** — Slug form harus unik dan hanya boleh mengandung huruf kecil, angka, dan tanda `-`.

---

## Project Structure

```
src/main/java/com/dims/form/
├── config/          # OpenAPI config
├── controller/      # REST controllers
├── dto/             # Request & Response DTOs
│   ├── auth/
│   ├── form/
│   ├── question/
│   └── submission/
├── entity/          # JPA entities
├── exception/       # Custom exceptions & global handler
├── repository/      # Spring Data JPA repositories
├── security/        # JWT, filters, Spring Security config
└── service/         # Business logic (interface + impl)
    └── impl/
```

---

## Database Schema

```
users
forms          (FK: creator_id → users)
allowed_domains (FK: form_id → forms)
questions      (FK: form_id → forms)
question_choices (FK: question_id → questions)
submissions    (FK: form_id → forms, user_id → users)
answers        (FK: submission_id → submissions, question_id → questions)
```

---

## Error Responses

| Status | Description |
|---|---|
| 401 | Unauthenticated — token tidak ada atau tidak valid |
| 403 | Forbidden — tidak punya akses ke resource |
| 404 | Not Found — resource tidak ditemukan |
| 422 | Unprocessable Entity — validasi gagal atau sudah submit |