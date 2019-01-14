package com.dascom.website.config.template;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientURI;

/**
 * db_document
 * 
 * @author leisenquan 2018年12月20日
 */
@Configuration
public class DascomWebsiteMongoConfig {

	@Primary
	@Bean
	@ConfigurationProperties(prefix = "spring.data.mongodb")
	public MongoProperties websiteMongoProperties() {
		return new MongoProperties();
	}

	@Primary
	@Bean("websiteMongoTemplate")
	public MongoTemplate websiteMongoTemplate() throws Exception {
		MongoDbFactory factory = websiteFactory(websiteMongoProperties());
		MappingMongoConverter converter = new MappingMongoConverter(factory, new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		return new MongoTemplate(factory, converter);
	}

	@Primary
	@Bean
	public MongoDbFactory websiteFactory(MongoProperties mongoProperties) throws Exception {
		MongoProperties mongoProperties1 = websiteMongoProperties();
		return new SimpleMongoDbFactory(new MongoClientURI(mongoProperties1.getUri()));
	}

}
