package org.nyalbanytamilsangam.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitConfig extends OncePerRequestFilter {

    private static final int MAX_REQUESTS_PER_MINUTE = 60;
    private final Map<String, RateLimitEntry> cache = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String ip = getClientIp(request);
        RateLimitEntry entry = cache.computeIfAbsent(ip, k -> new RateLimitEntry());

        long now = System.currentTimeMillis();
        if (now - entry.windowStart > 60_000) {
            entry.count.set(0);
            entry.windowStart = now;
        }

        if (entry.count.incrementAndGet() > MAX_REQUESTS_PER_MINUTE) {
            response.setStatus(429);
            response.getWriter().write("{\"error\":\"Too many requests\"}");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String xf = request.getHeader("X-Forwarded-For");
        if (xf != null && !xf.isBlank()) return xf.split(",")[0].trim();
        return request.getRemoteAddr();
    }

    private static class RateLimitEntry {
        AtomicInteger count = new AtomicInteger(0);
        volatile long windowStart = System.currentTimeMillis();
    }
}
