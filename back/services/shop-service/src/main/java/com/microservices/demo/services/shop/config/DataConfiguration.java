package com.microservices.demo.services.shop.config;

import com.microservices.demo.services.config.R2dbcConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@Import({R2dbcConfiguration.class})
@EnableR2dbcRepositories(basePackages = {"com.microservices.demo.services.dao", "com.microservices.demo.services.product.dao"})
public class DataConfiguration {
}
