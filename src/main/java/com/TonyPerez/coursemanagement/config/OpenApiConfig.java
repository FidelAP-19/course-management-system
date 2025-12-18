package com.TonyPerez.coursemanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI courseManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Course Management System API")
                        .description("REST API for managing students, faculty, and courses in a university system")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Tony Perez")
                                .url("https://github.com/FidelAP-19")
                                .email("perezafidel1998@gmail.com")));
    }
}