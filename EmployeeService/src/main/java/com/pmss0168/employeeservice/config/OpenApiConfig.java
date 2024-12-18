package com.pmss0168.employeeservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Employee Service API - Event Sourcing Microservice",
                description = "Employee API documentation",
                version = "1.0.0"
        ),
        servers = {
                @Server(url = "http://localhost:9002",
                        description = "Local Environment"
                ),
                @Server(url = "http://employee-service.prod.com",
                        description = "Product Environment"
                )
        }
)
public class OpenApiConfig {
}
