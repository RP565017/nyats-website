package org.nyalbanytamilsangam.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.Map;

@Configuration
public class CacheConfig {

    public static final String EVENTS_CACHE = "events";
    public static final String ANNOUNCEMENTS_CACHE = "announcements";
    public static final String GALLERY_CACHE = "gallery";
    public static final String MEMBERSHIP_PLANS_CACHE = "membershipPlans";

    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> serializationPair =
                RedisSerializationContext.SerializationPair.fromSerializer(serializer);

        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeValuesWith(serializationPair);

        Map<String, RedisCacheConfiguration> cacheConfigs = Map.of(
                EVENTS_CACHE, defaultConfig.entryTtl(Duration.ofHours(1)),
                ANNOUNCEMENTS_CACHE, defaultConfig.entryTtl(Duration.ofHours(2)),
                GALLERY_CACHE, defaultConfig.entryTtl(Duration.ofHours(6)),
                MEMBERSHIP_PLANS_CACHE, defaultConfig.entryTtl(Duration.ofHours(24))
        );

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigs)
                .build();
    }
}
