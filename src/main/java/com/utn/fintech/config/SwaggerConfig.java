package com.utn.fintech.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Fintech - UTN Diplomatura")
                        .version("1.0")
                        .description("API REST para gestion de cuentas bancarias. " +
                                "Incluye conversion de saldo USD a ARS usando la cotizacion MEP de DolarAPI."));
    }
}

