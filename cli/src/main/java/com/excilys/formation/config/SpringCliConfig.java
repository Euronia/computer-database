package com.excilys.formation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by excilys on 05/01/17.
 */

@ComponentScan(basePackages = "com.excilys.formation")
@PropertySource("classpath:defaultConnection.properties")
public class SpringCliConfig {
}
