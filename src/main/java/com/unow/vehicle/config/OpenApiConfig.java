package com.unow.vehicle.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** The Open api configuration. */
@Configuration
public class OpenApiConfig {

  /** Custom open api info. */
  @Bean
  public OpenAPI customOpenApi() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Vehicle API")
                .version("1.0.0")
                .description("Documentation of Vehicle API v1.0.0"));
  }
}
