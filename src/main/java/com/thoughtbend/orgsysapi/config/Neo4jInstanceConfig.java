package com.thoughtbend.orgsysapi.config;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.config.ConfigurationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;

@org.springframework.context.annotation.Configuration
@EnableNeo4jRepositories(basePackages = "com.thoughtbend.orgsysapi.data.repository")
public class Neo4jInstanceConfig {

	@Bean
    public org.neo4j.ogm.session.SessionFactory sessionFactory() {
        // with domain entity base package(s)
		final org.neo4j.ogm.session.SessionFactory sessionFactory =
				new org.neo4j.ogm.session.SessionFactory(configuration(), "com.thoughtbend.orgsysapi.data.entity");
		//sessionFactory.register(new UUIDIdStrategy());
        return sessionFactory;
    }
	
	@Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        ConfigurationSource properties = new ClasspathConfigurationSource("neo4j.properties");
        Configuration configuration = new Configuration.Builder(properties).build();
        return configuration;
    }

    @Bean
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }
}
