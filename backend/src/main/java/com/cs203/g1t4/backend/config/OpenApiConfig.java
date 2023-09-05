package com.cs203.g1t4.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
  info = @Info(
    description = "OpenApi documentation for Crowwwd Sync",
    title = "OpenApi specification - Crowwwd Sync",
    version = "1.0"
  ),
  servers = {
    @Server(
      description = "Local ENV",
      url = "http://localhost:8080"
    )
    // TODO: update when doing deployment
    // @Server(
    //   description = "PROD ENV",
    //   url = "http://localhost:8080"
    // )
  }
)
@SecurityScheme(
  name = "bearerAuth",
  description = "JWT auth description",
  scheme = "bearer",
  type = SecuritySchemeType.HTTP,
  bearerFormat = "JWT",
  in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

  
}