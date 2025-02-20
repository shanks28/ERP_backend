# RBAC System in Spring Boot

## Overview

This project is a **Role-Based Access Control (RBAC) System** built using **Spring Boot**, **Java 17**, and **Maven**. The system provides authentication and authorization mechanisms for different roles:

- **ADMIN**
- **CRM**
- **BILLING**

It uses an **in-memory H2 database** for storage and implements **Spring Security** to handle authentication and access control.

---

## Features

✅ **User Authentication:** Secure login with credentials  
✅ **Role-Based Authorization:** Restrict access based on user roles  
✅ **Spring Security Integration:** Uses `@PreAuthorize` for API security  
✅ **In-Memory Database (H2):** Fast prototyping and easy testing  
✅ **RESTful APIs:** Exposes endpoints for user management and access control

---

## Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Security**
- **Maven**
- **H2 Database**

---

## Installation & Setup

### Prerequisites

Ensure you have the following installed:

- **Java 17**
- **Maven**
- **Postman (for testing APIs, optional)**

### Steps to Run

1. Clone the repository:

   ```sh
   git clone https://github.com/yourusername/rbac-spring-boot.git
   cd rbac-spring-boot
