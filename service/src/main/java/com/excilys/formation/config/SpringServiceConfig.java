package com.excilys.formation.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import(SpringPersistenceConfig.class)
@EnableJpaRepositories(basePackages = {"com.excilys.formation.persistence"})
@ComponentScan(basePackages = "com.excilys.formation")
public class SpringServiceConfig {
}
