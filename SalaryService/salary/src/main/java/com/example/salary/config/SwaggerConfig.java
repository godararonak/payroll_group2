package com.example.salary.config;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi salaryApi() {
        return GroupedOpenApi.builder()
                .group("salary")
                .pathsToMatch("/api/v1/**")
                .build();
    }



}
