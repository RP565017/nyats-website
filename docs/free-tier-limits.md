# Free Tier Limits & Monitoring

## Service Limits Summary

| Service | Free Tier Limit | When Exceeded |
|---------|-----------------|---------------|
| MongoDB Atlas M0 | 512 MB storage, 100 connections | Upgrade to M2 ($9/mo) |
| Upstash Redis | 10,000 commands/day, 256MB | Free daily limit resets |
| Cloudinary | 25 GB storage, 25 GB bandwidth/month | Delete old images |
| Resend | 3,000 emails/month | Queue emails or upgrade |
| Vercel | 100 GB bandwidth/month, 6,000 build minutes | Upgrade to Pro ($20/mo) |
| Render | 750 hours/month, spins down after 15min idle | Keep-alive ping |
| Cloudflare | Unlimited DNS, 100K worker requests/day | Free tier is generous |
| GitHub Actions | 2,000 minutes/month | Optimize workflows |
| Stripe | 2.9% + 30¢ per transaction | No monthly fee |

---

## MongoDB Atlas M0 Optimization

- Remove `_class` field (already configured in `MongoConfig.java`)
- Use sparse indexes where possible
- Compress images before storing URLs (Cloudinary handles this)
- Keep documents lean — no large embedded arrays
- Monitor storage in Atlas dashboard

### Estimated Storage Usage
```
Users (150):           ~30 KB
Events (50):           ~50 KB  
Memberships (100):     ~40 KB
Payments (200):        ~80 KB
Gallery (20 albums):   ~20 KB
Announcements (50):    ~30 KB
─────────────────────────────
Total:                ~250 KB  (well within 512 MB)
```

---

## Redis Cache Optimization

- Events: 1 hour TTL
- Announcements: 2 hour TTL
- Gallery: 6 hour TTL
- Membership Plans: 24 hour TTL
- Cache-aside pattern: read cache first, fallback to MongoDB
- Evict on mutations with `@CacheEvict`

---

## Render Free Tier Management

- **Spin-down**: App sleeps after 15 min of inactivity
- **Cold start**: ~30-60 seconds on first request
- **Keep-alive**: Configure Uptime Robot to ping `/api/v1/ping` every 10 minutes

```
Uptime Robot Setup:
- URL: https://your-app.onrender.com/api/v1/ping
- Interval: 10 minutes
- Type: HTTP(s)
```

---

## Email Budget (Resend 3,000/month)

| Email Type | Estimate/Month |
|------------|---------------|
| Welcome emails | 20 |
| Event registrations | 100 |
| Membership confirmations | 15 |
| Password resets | 10 |
| **Total** | **~145/month** |

Well within the 3,000/month free limit.

---

## Cloudinary Bandwidth

- Automatic image optimization with `q_auto,f_auto`
- Thumbnails generated via URL transformation (no extra storage)
- Lazy loading on frontend reduces bandwidth
- Estimated: ~5 GB/month for active community site

---

## Monitoring Setup

### Free Monitoring Options

1. **UptimeRobot** (free) — uptime monitoring for backend
2. **Vercel Analytics** (free) — frontend performance
3. **MongoDB Atlas** — built-in database monitoring
4. **GitHub Actions** — CI/CD status

### Recommended Alerts
- Uptime drop (UptimeRobot)
- MongoDB storage > 400 MB (Atlas)
- 5xx error rate spike (Render logs)
