# Architecture Overview

## System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Cloudflare CDN/DNS/SSL                    │
└──────────────────────┬──────────────────────────────────────┘
                       │
         ┌─────────────┴─────────────┐
         │                           │
    ┌────▼────┐               ┌──────▼──────┐
    │  Vercel │               │   Render    │
    │ Angular │               │ Spring Boot │
    │  App    │               │    API      │
    └────┬────┘               └──────┬──────┘
         │                           │
         │         REST API          │
         └─────────────────────────→ │
                                     │
              ┌──────────────────────┼─────────────────┐
              │                      │                  │
        ┌─────▼──────┐   ┌──────────▼───┐   ┌─────────▼──────┐
        │  MongoDB   │   │    Upstash   │   │  Cloudinary    │
        │  Atlas M0  │   │    Redis     │   │  File Storage  │
        └────────────┘   └──────────────┘   └────────────────┘
```

## Backend Architecture (Spring Boot 3.x)

```
api/
├── config/          # Security, CORS, Cache, Rate Limit
├── security/        # JWT filter, UserPrincipal, UserDetails
├── common/          # Shared DTOs, exceptions, utilities
├── auth/            # Authentication endpoints
├── user/            # User profiles
├── event/           # Event CRUD + registration
├── membership/      # Membership plans + subscriptions
├── payment/         # Stripe payment intents
├── gallery/         # Photo albums + Cloudinary
├── announcement/    # Community announcements
├── notification/    # Email via Resend
└── admin/           # Admin dashboard
```

### Patterns Used
- **Repository pattern** via Spring Data MongoDB
- **Service layer** for business logic
- **DTO pattern** for API contracts
- **Global exception handler** for consistent error responses
- **JWT stateless auth** with refresh tokens
- **Redis caching** with TTL: Events (1h), Announcements (2h), Gallery (6h), Plans (24h)
- **Async email** sending with Spring @Async

## Frontend Architecture (Angular 17+)

```
src/app/
├── core/            # Singleton services, guards, interceptors
│   ├── auth/        # AuthService (signals), guards
│   ├── services/    # ApiService, NotificationService, LoadingService
│   └── interceptors/ # JWT, Error, Loading
├── shared/          # Reusable standalone components
├── layouts/         # PublicLayout, AdminLayout
└── features/        # Lazy-loaded feature components
    ├── home/
    ├── events/
    ├── membership/
    ├── gallery/
    ├── announcements/
    ├── contact/
    ├── auth/
    ├── profile/
    └── admin/
```

### Patterns Used
- **Standalone components** — no NgModules
- **Angular Signals** — reactive state management
- **Functional guards** — `authGuard`, `adminGuard`
- **Functional interceptors** — JWT token injection, error handling, loading indicator
- **Lazy-loaded routes** — code splitting per feature
- **Input binding** — `@Input()` with `withComponentInputBinding()`

## Security Architecture

```
Request → RateLimitFilter → JwtAuthFilter → Spring Security → Controller
                               │
                         Extract JWT
                         Validate signature
                         Load UserPrincipal
                         Set SecurityContext
```

### Roles
- `USER` — registered users, view public content
- `MEMBER` — active members, event registration
- `ADMIN` — full access, content management

## Data Flow

```
Angular Component
    → ApiService (HTTP)
    → JWT Interceptor (add token)
    → Backend REST API
    → Security Filter Chain
    → Controller → Service → Repository
    → MongoDB Atlas
    → Redis Cache (read-through)
```
