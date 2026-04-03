package org.nyalbanytamilsangam.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfig {

    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDatabaseFactory factory,
            MongoMappingContext context,
            MongoCustomConversions conversions) {
        MappingMongoConverter converter = new MappingMongoConverter(
                new org.springframework.data.mongodb.core.convert.DefaultDbRefResolver(factory), context);
        converter.setCustomConversions(conversions);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }
}
