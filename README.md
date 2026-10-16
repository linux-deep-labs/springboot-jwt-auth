# Spring Boot Authentication & Authorization API

ระบบ RESTful API สำหรับ Authentication และ Authorization พัฒนาด้วย Spring Boot, Spring Security, JWT และ PostgreSQL โดยออกแบบเป็น Monolithic Architecture และแยกความรับผิดชอบของแต่ละ Layer อย่างชัดเจน เพื่อให้ง่ายต่อการพัฒนา ทดสอบ และบำรุงรักษา

## Table of Contents

- [Spring Boot Authentication \& Authorization API](#spring-boot-authentication--authorization-api)
  - [Table of Contents](#table-of-contents)
  - [Overview](#overview)
    - [Architecture](#architecture)
  - [Tech Stack](#tech-stack)
  - [Architecture](#architecture-1)
    - [Layer Responsibilities](#layer-responsibilities)
  - [Project Structure](#project-structure)
  - [Features](#features)
    - [Authentication](#authentication)
    - [Authorization](#authorization)
    - [Logging and Session Management](#logging-and-session-management)
  - [Database Design](#database-design)
    - [Audit Fields](#audit-fields)
  - [Prerequisites](#prerequisites)
  - [Configuration](#configuration)
  - [Getting Started](#getting-started)
    - [1. Clone Repository](#1-clone-repository)
    - [2. Create Database](#2-create-database)
    - [3. Configure Environment Variables](#3-configure-environment-variables)
    - [4. Build the Application](#4-build-the-application)
    - [5. Run the Application](#5-run-the-application)
  - [Authentication Flow](#authentication-flow)
  - [API Endpoints](#api-endpoints)
    - [Example: Login Request](#example-login-request)
    - [Example: Authenticated Request](#example-authenticated-request)
  - [Security](#security)
  - [Logging](#logging)
    - [Application Log](#application-log)
    - [Login Audit Log](#login-audit-log)
  - [Testing](#testing)
  - [Development Guidelines](#development-guidelines)
  - [License](#license)
  - [Author](#author)

## Overview

โปรเจกต์นี้มีวัตถุประสงค์เพื่อสร้างระบบ Authentication และ Authorization สำหรับ RESTful API โดยใช้ JWT สำหรับยืนยันตัวตน และ Spring Security สำหรับควบคุมการเข้าถึงทรัพยากรตามสิทธิ์ของผู้ใช้

ระบบจัดเก็บข้อมูลผู้ใช้ บทบาท สิทธิ์การใช้งาน Session และประวัติการเข้าสู่ระบบใน PostgreSQL

### Architecture

- **Architecture:** Monolithic
- **Application Type:** RESTful API
- **Authentication:** JWT
- **Authorization:** Role-Based Access Control (RBAC)
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA

## Tech Stack

| Technology | Purpose |
|---|---|
| Java | Programming Language |
| Spring Boot | Application Framework |
| Spring Web | REST API Development |
| Spring Security | Authentication and Authorization |
| JWT | Token-Based Authentication |
| Spring Data JPA | Database Access |
| PostgreSQL | Relational Database |
| Lombok | Reduce Boilerplate Code |
| Bean Validation | Request Validation |
| Maven | Dependency Management |
| JUnit 5 | Unit Testing |
| Mockito | Mocking Dependencies |

> เวอร์ชันของ Java, Spring Boot และ JWT Library ให้ยึดตามเวอร์ชันที่กำหนดไว้ในโปรเจกต์จริง

## Architecture

ระบบใช้ Layered Architecture เพื่อแยกความรับผิดชอบของแต่ละส่วน

```text
Client
  |
  v
Controller Layer
  |
  v
Security Layer
  |
  v
Service Layer
  |
  v
Repository Layer
  |
  v
PostgreSQL
```

### Layer Responsibilities

| Layer | Responsibility |
|---|---|
| Controller | รับ HTTP Request และส่ง HTTP Response |
| Security | ตรวจสอบ JWT และควบคุมสิทธิ์การเข้าถึง |
| Service | ประมวลผล Business Logic |
| Repository | ติดต่อฐานข้อมูลผ่าน Spring Data JPA |
| Entity | Mapping ระหว่าง Java Object และ Database Table |
| DTO | กำหนดโครงสร้าง Request และ Response |
| Exception | จัดการข้อผิดพลาดของระบบ |
| Common | จัดเก็บองค์ประกอบที่ใช้ร่วมกัน |

## Project Structure

```text
src/
└── main/
    ├── java/com/example/auth/
    │   ├── AuthApplication.java
    │   │
    │   ├── config/
    │   │   ├── SecurityConfig.java
    │   │   ├── JpaConfig.java
    │   │   └── OpenApiConfig.java
    │   │
    │   ├── security/
    │   │   ├── JwtAuthenticationFilter.java
    │   │   ├── JwtAuthenticationEntryPoint.java
    │   │   ├── CustomUserDetailsService.java
    │   │   └── CustomUserDetails.java
    │   │
    │   ├── controller/
    │   │   ├── AuthController.java
    │   │   └── UserController.java
    │   │
    │   ├── service/
    │   │   ├── AuthService.java
    │   │   ├── JwtService.java
    │   │   ├── SessionService.java
    │   │   └── LoginLogService.java
    │   │
    │   ├── repository/
    │   │   ├── UserRepository.java
    │   │   ├── RoleRepository.java
    │   │   ├── SessionRepository.java
    │   │   └── LoginLogRepository.java
    │   │
    │   ├── entity/
    │   │   ├── User.java
    │   │   ├── Role.java
    │   │   ├── UserRole.java
    │   │   ├── UserSession.java
    │   │   └── LoginLog.java
    │   │
    │   ├── dto/
    │   │   ├── request/
    │   │   │   ├── LoginRequest.java
    │   │   │   └── RefreshTokenRequest.java
    │   │   └── response/
    │   │       ├── LoginResponse.java
    │   │       └── UserProfileResponse.java
    │   │
    │   ├── exception/
    │   │   ├── GlobalExceptionHandler.java
    │   │   ├── InvalidCredentialsException.java
    │   │   └── ResourceNotFoundException.java
    │   │
    │   └── common/
    │       ├── constant/
    │       ├── enums/
    │       └── util/
    │
    └── resources/
        ├── application.yml
        └── application-dev.yml

src/test/
└── java/com/example/auth/
```

> โครงสร้างนี้เป็นโครงสร้างเป้าหมายสำหรับการพัฒนา ไม่ได้หมายความว่าทุกคลาสถูกสร้างและ Implement แล้ว

## Features

### Authentication

- Login ด้วย Username และ Password
- ตรวจสอบ Password ด้วย Password Encoder
- สร้างและตรวจสอบ JWT
- โหลดข้อมูลผู้ใช้ผ่าน Spring Security
- รองรับการกำหนดอายุ Token
- รองรับ Refresh Token เมื่อพัฒนากลไกดังกล่าว
- รองรับ Logout และ Session Revocation เมื่อมีการ Implement

### Authorization

- กำหนดสิทธิ์ด้วย Role-Based Access Control (RBAC)
- จำกัดการเข้าถึง Endpoint ตาม Role
- รองรับการเพิ่ม Role และ Permission ในอนาคต

### Logging and Session Management

- บันทึกประวัติ Login สำเร็จและล้มเหลว
- จัดเก็บวันและเวลาของเหตุการณ์
- จัดเก็บ IP Address และ User-Agent ตามความเหมาะสม
- ติดตาม Session และเวลาหมดอายุ
- รองรับการตรวจสอบประวัติการเข้าสู่ระบบ

## Database Design

ฐานข้อมูลหลักของระบบคือ `auth_db` บน PostgreSQL

ตารางที่ระบบอาจใช้ประกอบด้วย:

| Table | Purpose |
|---|---|
| `users` | จัดเก็บข้อมูลผู้ใช้ |
| `roles` | จัดเก็บบทบาทของผู้ใช้ |
| `user_roles` | เชื่อมความสัมพันธ์ระหว่างผู้ใช้และบทบาท หากออกแบบเป็น Many-to-Many |
| `sessions` | จัดเก็บข้อมูล Session หรือ Token Lifecycle |
| `login_logs` | จัดเก็บประวัติการเข้าสู่ระบบ |

> ชื่อตารางข้างต้นเป็นชื่ออ้างอิง ต้องปรับให้ตรงกับ SQL Schema ที่สร้างไว้จริง

### Audit Fields

ตารางที่ต้องการตรวจสอบการเปลี่ยนแปลงควรมีฟิลด์วันที่ตาม Schema ที่กำหนด เช่น

- `created_date` — วันที่สร้างข้อมูล
- `updated_date` — วันที่แก้ไขข้อมูลล่าสุด

สามารถใช้ Spring Data JPA Auditing เพื่อจัดการวันที่อัตโนมัติ โดยต้องกำหนด Annotation และเปิดใช้งาน Auditing ให้ถูกต้อง

## Prerequisites

ก่อนเริ่มใช้งาน ควรติดตั้ง:

- JDK เวอร์ชันที่รองรับกับโปรเจกต์
- Maven หรือ Maven Wrapper
- PostgreSQL
- Git
- IDE เช่น IntelliJ IDEA หรือ Visual Studio Code

## Configuration

กำหนดค่า Database และ JWT ผ่าน Environment Variables แทนการเก็บ Secret ไว้ใน Source Code

ตัวอย่าง `application.yml`:

```yaml
spring:
  application:
    name: auth-api

  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: validate
    open-in-view: false
    properties:
      hibernate:
        format_sql: true

security:
  jwt:
    secret: ${JWT_SECRET}
    access-token-expiration: ${JWT_ACCESS_EXPIRATION_MS:900000}
```

ตัวอย่าง Environment Variables สำหรับเครื่องพัฒนา:

```bash
export DB_URL="jdbc:postgresql://localhost:5432/auth_db"
export DB_USERNAME="your_db_username"
export DB_PASSWORD="your_db_password"
export JWT_SECRET="your_base64_encoded_secret"
export JWT_ACCESS_EXPIRATION_MS="900000"
```

**ข้อควรระวัง**

- `JWT_SECRET` ต้องมีความแข็งแรงและมีขนาดเหมาะสมกับอัลกอริทึมที่เลือก
- ห้าม Commit Password และ JWT Secret ลง Git
- `900000` มิลลิวินาที เท่ากับ 15 นาที
- `ddl-auto: validate` ใช้ตรวจสอบ Schema ที่มีอยู่ ไม่ได้สร้างหรือแก้ไขตารางให้โดยอัตโนมัติ
- ต้องตรวจสอบว่า JWT Library อ่านค่า Secret ในรูปแบบเดียวกับที่กำหนด

## Getting Started

### 1. Clone Repository

```bash
git clone <YOUR_REPOSITORY_URL>
cd <YOUR_PROJECT_DIRECTORY>
```

### 2. Create Database

สร้าง Database ใน PostgreSQL หากยังไม่มี:

```sql
CREATE DATABASE auth_db;
```

จากนั้นรัน SQL Schema ที่ออกแบบไว้เพื่อสร้างตารางที่จำเป็น

หากมีตารางอยู่แล้ว ให้ตรวจสอบชื่อ Column, Primary Key, Foreign Key และชนิดข้อมูลให้ตรงกับ Entity

### 3. Configure Environment Variables

กำหนด Environment Variables ตามหัวข้อ Configuration ให้ครบ

### 4. Build the Application

```bash
./mvnw clean package
```

หากใช้ Maven ที่ติดตั้งในเครื่อง:

```bash
mvn clean package
```

### 5. Run the Application

```bash
./mvnw spring-boot:run
```

หรือรันไฟล์ JAR:

```bash
java -jar target/<APPLICATION_JAR_NAME>.jar
```

ตรวจสอบว่า Application เริ่มทำงานสำเร็จ และเชื่อมต่อ PostgreSQL ได้

## Authentication Flow

```text
Client
  |
  | POST /api/auth/login
  v
AuthController
  |
  v
AuthService
  |
  +--> UserRepository
  |      Find User
  |
  +--> PasswordEncoder
  |      Verify Password
  |
  +--> LoginLogService
  |      Record Login Result
  |
  +--> JwtService
  |      Generate JWT
  |
  +--> SessionService
         Record Session if enabled
  |
  v
Return Access Token
  |
  v
Client sends Authorization: Bearer <token>
  |
  v
JwtAuthenticationFilter
  |
  +--> Validate Token
  |
  +--> Set SecurityContext
  |
  v
Protected Controller
```

## API Endpoints

Endpoint ที่แนะนำสำหรับระบบ Authentication มีดังนี้

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/login` | เข้าสู่ระบบ |
| POST | `/api/auth/refresh` | ขอ Access Token ใหม่ หากเปิดใช้ Refresh Token |
| POST | `/api/auth/logout` | ออกจากระบบและเพิกถอน Session ตามที่ออกแบบ |
| GET | `/api/auth/me` | ดูข้อมูลผู้ใช้ที่เข้าสู่ระบบ |
| GET | `/api/users` | ดูรายชื่อผู้ใช้ตามสิทธิ์ |
| GET | `/api/admin/logs` | ตรวจสอบ Login Logs ตามสิทธิ์ที่กำหนด |

รายการนี้เป็น API ที่แนะนำ ไม่ใช่การยืนยันว่า Endpoint ทั้งหมดมีอยู่แล้วในโปรเจกต์

### Example: Login Request

```http
POST /api/auth/login
Content-Type: application/json
```

```json
{
  "username": "example_user",
  "password": "your_password"
}
```

### Example: Authenticated Request

```http
GET /api/auth/me
Authorization: Bearer <ACCESS_TOKEN>
```

โครงสร้าง JSON Response จริงควรอ้างอิง DTO ที่ Implement ไว้ในโปรเจกต์

## Security

แนวทางด้านความปลอดภัยที่ควรปฏิบัติ:

1. ใช้ `PasswordEncoder` เช่น BCrypt สำหรับ Hash Password
2. ตรวจสอบ Signature, Expiration และ Claims ที่จำเป็นของ JWT
3. ใช้ HTTPS ใน Production
4. กำหนด Endpoint แบบ Public เฉพาะที่จำเป็น
5. ตรวจสอบ Role และ Permission ที่ฝั่ง Server เสมอ
6. ไม่เก็บ Password หรือ JWT แบบเต็มใน Log
7. จำกัดสิทธิ์การเข้าถึง Login Logs
8. ใช้ Secret ผ่าน Environment Variables หรือ Secret Manager
9. กำหนด Rate Limit หรือกลไกป้องกัน Brute-Force สำหรับ Login
10. ออกแบบการเพิกถอน Token และ Session ให้ชัดเจน

สำหรับระบบ JWT แบบ Stateless การ Logout ฝั่ง Client เพียงอย่างเดียวไม่ได้ทำให้ Token ที่ยังไม่หมดอายุใช้ไม่ได้ทันที หากต้องการเพิกถอน Token ต้องมีกลไกฝั่ง Server รองรับ

## Logging

แยก Application Log ออกจาก Login Audit Log ให้ชัดเจน

### Application Log

ใช้ติดตามการทำงานและข้อผิดพลาดของ Application เช่น:

- Application Startup
- Database Connection Error
- Unexpected Exception
- Integration Error

### Login Audit Log

ใช้บันทึกเหตุการณ์ที่เกี่ยวข้องกับการเข้าสู่ระบบ เช่น:

- Login Success
- Login Failure
- Logout
- Token หรือ Session Revocation

ควรบันทึกเฉพาะข้อมูลที่จำเป็นต่อการตรวจสอบ และหลีกเลี่ยงการเก็บข้อมูลลับหรือข้อมูลส่วนบุคคลเกินความจำเป็น

## Testing

ควรมี Unit Tests และ Integration Tests ครอบคลุม:

- Login ด้วยข้อมูลถูกต้อง
- Login ด้วย Password ไม่ถูกต้อง
- Login ด้วย User ที่ไม่มีอยู่
- JWT หมดอายุหรือ Signature ไม่ถูกต้อง
- Access Endpoint โดยไม่มี Token
- Access Endpoint ด้วย Role ที่ไม่มีสิทธิ์
- บันทึก Login Log สำเร็จและล้มเหลว
- Logout และ Session Revocation หากรองรับ
- การเชื่อมต่อและ Mapping กับ PostgreSQL

รัน Test ด้วยคำสั่ง:

```bash
./mvnw test
```

## Development Guidelines

- ใช้ DTO แทนการส่ง Entity ออก API โดยตรง
- ให้ Business Logic อยู่ใน Service Layer
- ให้ Repository รับผิดชอบการเข้าถึงข้อมูล
- ใช้ Constructor Injection สำหรับ Dependency Injection
- ใช้ Transaction ให้เหมาะสมกับขอบเขตของ Business Operation
- หลีกเลี่ยงการสร้าง Utility Class ที่รวมหน้าที่หลายอย่างไว้ด้วยกัน
- ใช้ Migration Tool เช่น Flyway หรือ Liquibase หากต้องการควบคุมการเปลี่ยนแปลง Schema อย่างเป็นระบบ
- เขียน Tests สำหรับ Authentication และ Authorization ทุกกรณีสำคัญ

---

## License

NNUXX@linux-deep-labs

## Author

Linuxxxxx@gmail.com
