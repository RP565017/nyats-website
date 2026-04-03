package org.nyalbanytamilsangam.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.mongodb.embedded.version=4.0.2",
    "spring.data.mongodb.uri=mongodb://localhost:27017/nyats-test"
})
class NyatsApiApplicationTests {

    @Test
    void contextLoads() {
    }
}
