# NY Albany Tamil Sangam — Modern Website

A full-stack community web application for the [NY Albany Tamil Sangam](https://nyalbanytamilsangam.org/) built with Angular 17+, Spring Boot 3.x, and MongoDB. Deployed on **$0/month** using free-tier services.

## Tech Stack

| Layer | Technology |
|-------|------------|
| Frontend | Angular 17+ (standalone, signals) |
| Backend | Java 21, Spring Boot 3.2.x |
| Database | MongoDB Atlas M0 (free) |
| Cache | Upstash Redis (free) |
| Files | Cloudinary (25GB free) |
| Email | Resend (3000/month free) |
| Payments | Stripe (no monthly fee) |
| Frontend Host | Vercel (free) |
| Backend Host | Render (free) |
| DNS/CDN | Cloudflare (free) |
| CI/CD | GitHub Actions |

## Quick Start

### Prerequisites
- Java 21+
- Node.js 20+
- Docker & Docker Compose

### Local Development (Docker)

```bash
docker-compose up -d
```

The app will be available at:
- Frontend: http://localhost:4200
- Backend API: http://localhost:8080
- API Docs: http://localhost:8080/swagger-ui.html

### Backend Only

```bash
cd backend
./gradlew bootRun
```

### Frontend Only

```bash
cd frontend
npm install
npm start
```

## Environment Variables

### Backend (`backend/src/main/resources/application.yml` or env vars)

| Variable | Description | Required |
|----------|-------------|----------|
| `SPRING_DATA_MONGODB_URI` | MongoDB connection string | Yes |
| `SPRING_DATA_REDIS_URL` | Redis connection URL | Yes |
| `JWT_SECRET` | JWT signing secret (min 256-bit) | Yes |
| `JWT_ACCESS_EXPIRATION` | Access token TTL (ms, default: 900000) | No |
| `JWT_REFRESH_EXPIRATION` | Refresh token TTL (ms, default: 604800000) | No |
| `CLOUDINARY_URL` | Cloudinary connection URL | Yes |
| `STRIPE_SECRET_KEY` | Stripe secret key | Yes |
| `STRIPE_WEBHOOK_SECRET` | Stripe webhook signing secret | Yes |
| `MAIL_HOST` | SMTP host (Resend: smtp.resend.com) | Yes |
| `MAIL_USERNAME` | SMTP username | Yes |
| `MAIL_PASSWORD` | SMTP password / API key | Yes |

### Frontend (`frontend/src/environments/`)

| Variable | Description |
|----------|-------------|
| `apiUrl` | Backend API base URL |

## Deployment

### Backend → Render

1. Push to `main` branch
2. Connect repo to Render, use `backend/Dockerfile`
3. Set environment variables in Render dashboard
4. Service auto-deploys on push

### Frontend → Vercel

1. Connect repo to Vercel
2. Set root directory to `frontend`
3. Set build command: `npm run build -- --configuration production`
4. Set output directory: `dist/nyats-frontend/browser`

### Domain → Cloudflare

Point your domain DNS to Vercel/Render via Cloudflare. Enable proxied mode for CDN and SSL.

See [docs/deployment-guide.md](docs/deployment-guide.md) for detailed steps.

## API Reference

Base URL: `https://your-render-app.onrender.com/api/v1`

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | /auth/register | Public | Register user |
| POST | /auth/login | Public | Login |
| GET | /events | Public | List events |
| GET | /events/{slug} | Public | Event details |
| POST | /events | Admin | Create event |
| GET | /memberships/plans | Public | List plans |
| POST | /memberships/subscribe | User | Subscribe |
| GET | /gallery | Public | List albums |
| GET | /announcements | Public | List announcements |
| GET | /admin/dashboard | Admin | Dashboard stats |

See [docs/api-reference.md](docs/api-reference.md) for full documentation.

## Project Structure

```
nyats-website/
├── frontend/          # Angular 17+ app
├── backend/           # Spring Boot 3.x API
├── docs/              # Architecture & guides
└── docker-compose.yml # Local dev setup
```

## Contributing

1. Fork and clone the repo
2. Create a feature branch: `git checkout -b feature/my-feature`
3. Make changes and test locally
4. Submit a pull request

## License

MIT License — NY Albany Tamil Sangam
