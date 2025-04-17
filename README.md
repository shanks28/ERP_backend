# ERP System

A modular Enterprise Resource Planning (ERP) system built with Java, Spring Boot, and JPA. This project is designed to manage users, jobs, field options, and email notifications, providing a robust backend for business operations.

## Features

- **User Management:** Register, authenticate, and manage users with different roles (Admin, CRM, Billing, Operations).
- **Job Management:** Create, update, and track jobs with role-based access.
- **Field Options:** Dynamically manage field options for various entities.
- **Email Notifications:** Send emails for password resets and notifications using SMTP.
- **OTP Support:** Generate and validate OTPs for secure operations.
- **Redis Integration:** Caching support for improved performance.
- **Environment Configuration:** Securely manage environment variables using Dotenv.

## Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- Spring Web
- Spring Mail
- Redis (via Lettuce)
- Dotenv for environment variable management
- Lombok for boilerplate code reduction
- Maven for build automation

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Redis server (for caching)
- SMTP server credentials (for email notifications)

### Installation

1. **Clone the repository:**
    ```bash
    git clone https://github.com/yourusername/erp.git
    cd erp
    ```

2. **Configure Environment Variables:**

    Create a `.env` file in the project root with the following variables:

    ```
    MAIL_HOST=smtp.example.com
    MAIL_PORT=587
    MAIL_USERNAME=your_email@example.com
    MAIL_PASSWORD=your_email_password
    ```

    Also, configure `application.properties` for database and Redis:

    ```
    spring.datasource.url=jdbc:mysql://localhost:3306/erp
    spring.datasource.username=root
    spring.datasource.password=yourpassword

    spring.redis.host=localhost
    spring.redis.port=6379
    ```

3. **Build the project:**
    ```bash
    mvnw clean install
    ```

4. **Run the application:**
    ```bash
    mvnw spring-boot:run
    ```

    The application will start on `http://localhost:8080`.

## API Endpoints

### Authentication & User

- `POST /auth/login` — User login
- `GET /admin/reset` — Admin password reset (example)

### Job Management

- `POST /crm/create-job` — Create a new job (CRM role)
- `PATCH /job/update-job` — Update job details
- `GET /job/get-all-jobs` — List all jobs

### Field Options

- `GET /field-options/{fieldName}` — Get options for a field
- `POST /field-options/add` — Add a new field option

### Email

- `POST /email/send` — Send email (for password reset, etc.)


## Contributing

Contributions are welcome! Please open issues or submit pull requests for improvements and bug fixes.

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

## Contact

For questions or support, please contact [gaunterodim68@gmail.com].
---

**How to use:**

- Replace placeholder values (like SMTP credentials, database info, and contact email) with your actual project details.
- Add or adjust API endpoints as your project evolves.
- Place this file at `d:\ERP\README.md` in your repository root.