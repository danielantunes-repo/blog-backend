package com.backend.montreal.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {

	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Blog Pessoal")
                        .version("v1")
                        .description("Documentação da API do Blog Pessoal")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}

