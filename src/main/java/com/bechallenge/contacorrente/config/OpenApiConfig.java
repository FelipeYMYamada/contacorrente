package com.bechallenge.contacorrente.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Conta corrente")
                        .version("v1")
                        .description("Desafio back-end conta corrente")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("https://springdoc.org/")));
    }
}
