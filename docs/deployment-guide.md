# Deployment Guide

## Prerequisites

1. GitHub account with repository access
2. Free accounts: MongoDB Atlas, Upstash, Cloudinary, Vercel, Render, Cloudflare, Stripe, Resend

---

## Step 1: MongoDB Atlas (Database)

1. Create account at [cloud.mongodb.com](https://cloud.mongodb.com)
2. Create free M0 cluster (AWS, region closest to users)
3. Create database user (username/password)
4. Add IP whitelist: `0.0.0.0/0` (allow all — required for Render)
5. Get connection string: `mongodb+srv://user:pass@cluster.mongodb.net/nyatsdb`

---

## Step 2: Upstash Redis (Cache)

1. Create account at [upstash.com](https://upstash.com)
2. Create Redis database (free tier)
3. Copy `UPSTASH_REDIS_REST_URL` as your Redis URL
4. Use format: `rediss://default:password@host:port`

---

## Step 3: Cloudinary (File Storage)

1. Create account at [cloudinary.com](https://cloudinary.com)
2. Go to Dashboard → copy `CLOUDINARY_URL`
3. Format: `cloudinary://api_key:api_secret@cloud_name`

---

## Step 4: Resend (Email)

1. Create account at [resend.com](https://resend.com)
2. Create API key
3. Verify your domain or use sandbox
4. SMTP settings:
   - Host: `smtp.resend.com`
   - Port: `587`
   - Username: `resend`
   - Password: your API key

---

## Step 5: Stripe (Payments)

1. Create account at [stripe.com](https://stripe.com)
2. Get test keys from Dashboard → Developers → API keys
3. Set up webhook endpoint: `https://your-backend.onrender.com/api/v1/payments/webhook`
4. Copy webhook signing secret

---

## Step 6: Deploy Backend to Render

1. Go to [render.com](https://render.com) and connect GitHub
2. Create **New Web Service**
3. Connect your repository
4. Settings:
   - Root Directory: `backend`
   - Dockerfile Path: `./Dockerfile`
   - Health Check Path: `/api/v1/health`
5. Add environment variables:
   - `SPRING_PROFILES_ACTIVE=prod`
   - `SPRING_DATA_MONGODB_URI=<your Atlas URI>`
   - `SPRING_DATA_REDIS_URL=<your Upstash URL>`
   - `JWT_SECRET=<generate 64-char random string>`
   - `CLOUDINARY_URL=<your Cloudinary URL>`
   - `STRIPE_SECRET_KEY=<your Stripe key>`
   - `STRIPE_WEBHOOK_SECRET=<your webhook secret>`
   - `MAIL_HOST=smtp.resend.com`
   - `MAIL_USERNAME=resend`
   - `MAIL_PASSWORD=<your Resend API key>`
   - `APP_CORS_ALLOWED_ORIGINS=https://nyalbanytamilsangam.org`

---

## Step 7: Deploy Frontend to Vercel

1. Go to [vercel.com](https://vercel.com) and import GitHub repo
2. Configure:
   - Root Directory: `frontend`
   - Framework: Angular
   - Build Command: `npm run build -- --configuration production`
   - Output Directory: `dist/nyats-frontend/browser`
3. Add environment variable:
   - Set API URL to your Render backend URL
4. Update `vercel.json` with your actual Render URL

---

## Step 8: Configure Cloudflare DNS

1. Add your domain to Cloudflare
2. Create CNAME records:
   - `www` → `cname.vercel-dns.com` (Vercel)
   - `api` → `your-app.onrender.com` (Render)
3. Enable proxy (orange cloud) for CDN + SSL
4. Set SSL/TLS to "Full (strict)"

---

## Step 9: GitHub Actions CI/CD

Add these secrets to your GitHub repository (Settings → Secrets):
- `RENDER_SERVICE_ID`
- `RENDER_API_KEY`
- `VERCEL_TOKEN`
- `VERCEL_ORG_ID`
- `VERCEL_PROJECT_ID`

---

## Keeping Render Awake (Free Tier)

Render free tier spins down after 15 minutes of inactivity. Options:
1. Use Uptime Robot (free) to ping `/api/v1/ping` every 10 minutes
2. The `/api/v1/ping` endpoint returns `"pong"` instantly

---

## Verification Checklist

- [ ] Backend health: `curl https://your-backend.onrender.com/api/v1/health`
- [ ] Frontend loads at your domain
- [ ] User registration works
- [ ] Events list loads from API
- [ ] Stripe payments work (test mode)
- [ ] Email notifications send
