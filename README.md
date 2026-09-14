# Azure Bay Hotel

A hotel website built with an Angular front end and a Spring Boot REST API: guests browse rooms,
check availability for their dates, book a stay and cancel it; the front desk sees every reservation
on an admin dashboard.

```
backend/   Spring Boot 4 (Java 21, Spring Web MVC, Spring Data JPA, H2)
frontend/  Angular 22 (standalone components, signals, SCSS)
```

## Requirements

- Java 21+
- Node.js 22+ and npm
- Maven 3.9+ (or the bundled `./mvnw` wrapper)

## Run the API

```bash
cd backend
./mvnw spring-boot:run     # http://localhost:8080
```

Rooms are seeded on first start into an in-memory H2 database (browse it at
`http://localhost:8080/h2-console`, JDBC URL `jdbc:h2:mem:hotel`, user `sa`, no password).
Data resets whenever the API restarts.

## Run the website

```bash
cd frontend
npm install
npm start                  # http://localhost:4200
```

The dev server proxies `/api` to `http://localhost:8080` (see `proxy.conf.json`), so both apps run
side by side without CORS configuration. The API also allows the `http://localhost:4200` origin
directly via `hotel.cors.allowed-origins`.

## Tests

```bash
cd backend  && ./mvnw test
cd frontend && npm test
```

## Pages

| Route | Purpose |
| --- | --- |
| `/` | Landing page with hero, availability search and featured rooms |
| `/rooms` | Availability search results for the selected dates and party size |
| `/book/:id` | Booking form with a live price summary and confirmation reference |
| `/bookings` | Guests look up reservations by email and cancel them |
| `/admin` | Front-desk dashboard: all reservations, occupancy stats, cancellation |

## API

| Method | Path | Description |
| --- | --- | --- |
| `GET` | `/api/rooms?checkIn&checkOut&guests` | Room types with units still available for the stay |
| `GET` | `/api/rooms/{id}` | Single room type |
| `POST` | `/api/bookings` | Create a booking, returns a `HTL-XXXXXX` reference |
| `GET` | `/api/bookings?email=` | All bookings, or only those made with an email address |
| `GET` | `/api/bookings/{reference}` | Look up a single booking |
| `POST` | `/api/bookings/{reference}/cancel` | Cancel a booking and free its unit |

Availability is computed per room type: a room type has `totalUnits`, and a date range is bookable
while confirmed bookings overlapping that range are fewer than `totalUnits`.

The admin dashboard is unauthenticated — add Spring Security before deploying this anywhere public.
