package com.ourmusic.platform.app.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "com.ourmusic.platform.repository")
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "test";
        //todo: rename to ourmusic once operational
    }

    @Override @Bean
    public MongoClient mongoClient() {
        return new MongoClient();
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.ourmusic";
    }

}
