package com.example.user.config;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi salaryApi() {
        return GroupedOpenApi.builder()
                .group("salary")
                .pathsToMatch("/api/v1/employees/**")
                .build();
    }

}
