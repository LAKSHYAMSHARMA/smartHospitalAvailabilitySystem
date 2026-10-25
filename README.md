# Smart Hospital Availability System

Smart Hospital Availability System is a Spring Boot REST API for managing hospital resources and bookings from a single local application. It keeps hospital availability information together with doctors, ambulances, and patient bookings so clients can quickly find and use current resource data.

## Features

- Create, update, list, and delete hospitals.
- Track ICU, general, and ventilator bed availability.
- Track blood-group inventory and the number of available doctors.
- Add doctors and ambulances to hospitals.
- Update doctor and ambulance availability.
- Register users and retrieve user records.
- Create, view, confirm, and cancel bookings.
- Cache frequently requested hospital, doctor, and ambulance data in Redis.
- Use MySQL for persistent application data.

Authentication is intentionally not included. This project is configured for local development and all API endpoints are accessible without credentials.

## Technology stack

- Java 21
- Spring Boot 3.5.6
- Spring Web
- Spring Data JPA and Hibernate
- MySQL
- Redis
- Maven
- Lombok and ModelMapper

## Prerequisites

Install the following before starting the application:

1. Java Development Kit 21.
2. MySQL Server 8 or a compatible MySQL installation.
3. Redis Server 6 or newer.
4. Git, if cloning the repository.

Verify the tools:

```text
java -version
mysql --version
redis-cli ping
```

The Redis check should return `PONG`.

## Database setup

Create the local database:

```sql
CREATE DATABASE smart_hospital;
```

The default local connection uses:

| Setting | Default |
| --- | --- |
| Host | `localhost` |
| Port | `3306` |
| Database | `smart_hospital` |
| Username | `root` |
| Password | `root` |

The application creates and updates tables automatically with `spring.jpa.hibernate.ddl-auto=update`. For a shared or production database, replace this with a migration-based schema process.

## Redis setup

Start Redis using the service manager for your operating system, or run it directly:

```text
redis-server
```

The application connects to Redis at `localhost:6379` by default. Cached entries expire after ten minutes. Hospital, doctor, and ambulance writes evict the relevant caches so subsequent reads use current database values.

## Configuration

The default configuration is in `src/main/resources/application.properties`. Database and Redis values can be overridden with environment variables:

| Variable | Default | Purpose |
| --- | --- | --- |
| `DB_URL` | `jdbc:mysql://localhost:3306/smart_hospital?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC` | MySQL JDBC URL |
| `DB_USERNAME` | `root` | MySQL username |
| `DB_PASSWORD` | `root` | MySQL password |
| `REDIS_HOST` | `localhost` | Redis host |
| `REDIS_PORT` | `6379` | Redis port |
| `REDIS_DATABASE` | `0` | Redis logical database |

For example, in PowerShell:

```powershell
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "your-mysql-password"
$env:REDIS_HOST = "localhost"
$env:REDIS_PORT = "6379"
```

## Run locally

Clone the repository and enter the project directory:

```text
git clone <repository-url>
cd smartHospitalAvailabilitySystem
```

Start MySQL and Redis, create the database, then run the application with the Maven wrapper:

On Windows:

```text
.\mvnw.cmd spring-boot:run
```

On macOS or Linux:

```text
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080`.

To build and run the tests:

On Windows:

```text
.\mvnw.cmd clean test
```

On macOS or Linux:

```text
./mvnw clean test
```

## REST API

### Hospitals

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/hospitals` | Create a hospital |
| `GET` | `/hospitals` | List hospitals |
| `GET` | `/hospitals/{id}` | Get a hospital |
| `PUT` | `/hospitals/{id}` | Update a hospital |
| `DELETE` | `/hospitals/{id}` | Delete a hospital |

### Doctors

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/hospitals/{hospitalId}/doctors` | Add a doctor to a hospital |
| `GET` | `/hospitals/{hospitalId}/doctors` | List doctors for a hospital |
| `POST` | `/doctors` | Add a doctor using a hospital ID in the request body |
| `GET` | `/doctors/hospital/{hospitalId}` | List doctors for a hospital |
| `PUT` | `/doctors/{doctorId}/availability` | Update doctor availability |
| `DELETE` | `/doctors/{doctorId}` | Delete a doctor |

### Ambulances

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/hospitals/{hospitalId}/ambulances` | Add an ambulance to a hospital |
| `GET` | `/hospitals/{hospitalId}/ambulances` | List ambulances for a hospital |
| `PUT` | `/ambulances/{ambulanceId}/availability` | Update ambulance availability |
| `DELETE` | `/ambulances/{ambulanceId}` | Delete an ambulance |

### Users and bookings

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/users/register` | Register a user |
| `GET` | `/users` | List users |
| `GET` | `/users/{id}` | Get a user |
| `POST` | `/api/v1/bookings` | Create a booking |
| `GET` | `/api/v1/bookings/{id}` | Get a booking |
| `GET` | `/api/v1/bookings/user/{userId}` | List a user's bookings |
| `GET` | `/api/v1/bookings/hospital/{hospitalId}` | List a hospital's bookings |
| `PUT` | `/api/v1/bookings/{id}/confirm` | Confirm a booking |
| `PUT` | `/api/v1/bookings/{id}/cancel` | Cancel a booking |

## Caching

Caching is enabled by `@EnableCaching` on the application and `RedisConfig`. The service implementations use these cache regions:

- `hospitals` caches the hospital list.
- `hospital` caches individual hospitals by ID.
- `doctors` caches doctor lists by hospital ID.
- `ambulances` caches ambulance lists by hospital ID.

Create, update, availability, and delete operations use `@CacheEvict` to remove stale values. Redis stores cached DTOs as JSON and applies a ten-minute time-to-live.

## Project structure

```text
src/main/java/com/narcissisticengineer/smartHospitalAvailabilitySystem
├── controller
├── dto
├── entity
├── repository
├── service
│   └── implementation
└── tools
    ├── config
    └── exceptions
```

## Troubleshooting

- If the application cannot connect to MySQL, confirm the server is running and check `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD`.
- If Redis connection errors appear, start Redis and confirm `redis-cli ping` returns `PONG`.
- If port `8080` is occupied, add `server.port=8081` to `application.properties`.
- If tables are not created, confirm that the MySQL user can create tables in `smart_hospital`.
