# API Reference

Base URL: `https://nyats-api.onrender.com/api/v1`

All responses follow the `ApiResponse` wrapper:
```json
{
  "success": true,
  "data": { ... },
  "message": "Optional message",
  "timestamp": "2024-01-01T00:00:00Z"
}
```

Paginated responses use `PagedResponse`:
```json
{
  "content": [...],
  "totalElements": 100,
  "totalPages": 10,
  "number": 0,
  "size": 10,
  "first": true,
  "last": false
}
```

---

## Authentication

### POST /auth/register
```json
// Request
{
  "firstName": "Raj",
  "lastName": "Kumar",
  "email": "raj@example.com",
  "password": "SecurePass123",
  "phone": "+1-518-555-0100"
}

// Response 201
{
  "success": true,
  "data": {
    "accessToken": "eyJhbGc...",
    "refreshToken": "eyJhbGc...",
    "tokenType": "Bearer",
    "userId": "65a1b2c3d4e5f6a7b8c9d0e1",
    "email": "raj@example.com",
    "firstName": "Raj",
    "lastName": "Kumar",
    "role": "USER"
  }
}
```

### POST /auth/login
```json
// Request
{ "email": "raj@example.com", "password": "SecurePass123" }

// Response 200
{ "success": true, "data": { "accessToken": "...", ... } }
```

---

## Events

### GET /events
Query params: `page`, `size`, `type`, `search`

```json
// Response
{
  "success": true,
  "data": {
    "content": [
      {
        "id": "...",
        "title": "Pongal Celebration 2024",
        "slug": "pongal-celebration-2024",
        "type": "CULTURAL",
        "status": "PUBLISHED",
        "startDate": "2024-01-14T18:00:00Z",
        "venue": { "name": "Albany Community Center", "city": "Albany", "state": "NY" },
        "pricing": { "free": true },
        "featured": true
      }
    ],
    "totalElements": 5,
    "totalPages": 1
  }
}
```

### GET /events/{slug}
Returns full event details.

### POST /events (ADMIN)
```json
// Request
{
  "title": "Tamil New Year 2024",
  "type": "CULTURAL",
  "startDate": "2024-04-14T18:00:00Z",
  "venue": { "name": "TBD", "city": "Albany", "state": "NY" },
  "pricing": { "free": true },
  "maxCapacity": 200
}
```

### POST /events/{id}/register (MEMBER)
```json
// Request
{ "attendeeCount": 2, "notes": "Vegetarian preference" }
```

---

## Membership

### GET /memberships/plans (Public)
```json
{
  "success": true,
  "data": [
    { "type": "INDIVIDUAL", "name": "Individual", "price": 50, "duration": 12 },
    { "type": "FAMILY", "name": "Family", "price": 100, "duration": 12 },
    { "type": "STUDENT", "name": "Student", "price": 25, "duration": 12 },
    { "type": "SENIOR", "name": "Senior", "price": 30, "duration": 12 },
    { "type": "LIFETIME", "name": "Lifetime", "price": 500, "duration": 0 }
  ]
}
```

### POST /memberships/subscribe (USER)
```json
{ "type": "FAMILY", "paymentId": "pi_xyz", "familyMembers": [] }
```

---

## Gallery

### GET /gallery?page=0&size=12
### GET /gallery/{slug}
### POST /gallery (ADMIN)
### POST /gallery/{id}/upload (ADMIN, multipart file)

---

## Announcements

### GET /announcements?page=0&size=10
### POST /announcements (ADMIN)
```json
{ "title": "Annual Meeting", "content": "...", "category": "GENERAL", "pinned": true }
```

---

## Payments

### POST /payments/create-intent (USER)
```json
// Request
{ "amount": 100.00, "type": "MEMBERSHIP", "description": "Family Membership 2024" }

// Response
{ "clientSecret": "pi_xxx_secret_yyy", "paymentIntentId": "pi_xxx" }
```

### POST /payments/webhook (Stripe)
### GET /payments/my (USER)

---

## Admin

### GET /admin/dashboard (ADMIN)
```json
{
  "totalUsers": 150,
  "totalEvents": 25,
  "publishedEvents": 5,
  "activeMembers": 80,
  "totalAnnouncements": 12
}
```

### GET /admin/memberships?page=0&size=20&status=ACTIVE (ADMIN)

---

## Error Responses

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Event not found with slug: xyz",
  "path": "/api/v1/events/xyz",
  "timestamp": "2024-01-01T00:00:00Z"
}
```

Common HTTP status codes:
- `400` — Bad Request / Validation Failed
- `401` — Unauthorized (missing/invalid JWT)
- `403` — Forbidden (insufficient role)
- `404` — Resource Not Found
- `429` — Too Many Requests (rate limit)
- `500` — Internal Server Error
