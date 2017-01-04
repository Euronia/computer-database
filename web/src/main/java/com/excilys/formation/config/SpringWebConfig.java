package com.excilys.formation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by excilys on 03/01/17.
 */

@Configuration
@ComponentScan(basePackages = "com.excilys.formation.controller, com.excilys.formation.config")
public class SpringWebConfig {
}
