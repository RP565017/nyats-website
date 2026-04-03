# Database Schema (MongoDB)

## Collections Overview

| Collection | Description |
|------------|-------------|
| `users` | Registered community members |
| `events` | Community events |
| `event_registrations` | Event attendance records |
| `memberships` | Membership subscriptions |
| `payments` | Payment transactions |
| `gallery` | Photo albums |
| `announcements` | Community announcements |

---

## users

```json
{
  "_id": "ObjectId",
  "firstName": "Raj",
  "lastName": "Kumar",
  "email": "raj@example.com",
  "password": "$2a$12$...",
  "phone": "+1-518-555-0100",
  "address": "Albany, NY",
  "role": "MEMBER",
  "emailVerified": true,
  "profileImageUrl": "https://res.cloudinary.com/...",
  "preferredLanguage": "en",
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```
Indexes: `email` (unique)

---

## events

```json
{
  "_id": "ObjectId",
  "title": "Pongal Celebration 2024",
  "slug": "pongal-celebration-2024",
  "description": "Join us for...",
  "shortDescription": "Annual Pongal festival",
  "type": "CULTURAL",
  "status": "PUBLISHED",
  "startDate": "ISODate",
  "endDate": "ISODate",
  "venue": {
    "name": "Albany Community Center",
    "address": "100 Main St",
    "city": "Albany",
    "state": "NY",
    "zipCode": "12207"
  },
  "pricing": {
    "free": true,
    "memberPrice": 0,
    "nonMemberPrice": 10,
    "currency": "USD"
  },
  "maxCapacity": 200,
  "registeredCount": 45,
  "coverImageUrl": "https://res.cloudinary.com/...",
  "tags": ["cultural", "festival", "pongal"],
  "featured": true,
  "createdBy": "ObjectId",
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```
Indexes: `slug` (unique), `status`, `startDate`

---

## memberships

```json
{
  "_id": "ObjectId",
  "userId": "ObjectId",
  "type": "FAMILY",
  "status": "ACTIVE",
  "startDate": "ISODate",
  "expiryDate": "ISODate",
  "amount": 100.00,
  "paymentId": "pi_stripe_id",
  "familyMembers": [
    { "firstName": "Priya", "lastName": "Kumar", "relationship": "spouse" }
  ],
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

---

## gallery

```json
{
  "_id": "ObjectId",
  "title": "Pongal 2024 Celebration",
  "slug": "pongal-2024-celebration",
  "description": "Photos from our annual...",
  "coverImageUrl": "https://res.cloudinary.com/...",
  "items": [
    {
      "publicId": "nyats/gallery/...",
      "url": "https://res.cloudinary.com/...",
      "thumbnailUrl": "https://res.cloudinary.com/.../w_400,h_300,c_fill/...",
      "caption": "Traditional Pongal kolam",
      "sizeBytes": 524288
    }
  ],
  "published": true,
  "eventId": "ObjectId",
  "createdBy": "ObjectId",
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

---

## MongoDB Atlas M0 Limits (Free Tier)

- **Storage**: 512 MB
- **RAM**: 512 MB shared
- **Connections**: 500 max
- **No backups** (manual export only)
- **Oplog**: 1 hour
