package org.nyalbanytamilsangam.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.data.mongodb.uri=mongodb://localhost:27017/nyatsdb-test",
        "spring.data.redis.url=redis://localhost:6379",
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration",
        "jwt.secret=test-secret-key-that-is-at-least-256-bits-long-for-testing-purposes-only",
        "stripe.secret-key=sk_test_placeholder",
        "cloudinary.url=cloudinary://key:secret@cloud"
})
class NyatsApplicationTests {

    @Test
    void contextLoads() {
    }
}
