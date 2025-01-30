package com.content.platform.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Configurations
{
    @Bean
    public ModelMapper modelMapper()
    {
        return new ModelMapper();
    }

//    @Bean
//    public OpenAPI swaggerConfiguration()
//    {
//        Server devServer = new Server();
//        devServer.setUrl("http://localhost:8080");
//        devServer.setDescription("Server URL for Development environment");
//
//        Contact contact = new Contact();
//        contact.setEmail("test@gmail.com");
//        contact.setName("demon");
//
//        Info info = new Info()
//                .title("Content Management Platform")
//                .version("0.1")
//                .contact(contact)
//                .description("API Documentation")
//                .termsOfService("None as of now");
//
//        return new OpenAPI().info(info).servers(List.of(devServer));
//    }

    @Bean
    public OpenAPI swaggerConfiguration() {
        // Creating a server configuration for the development environment
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080"); // URL of the server in the dev environment
        devServer.setDescription("Server URL for Development environment");

        // Creating contact information for the API documentation
        Contact contact = new Contact();
        contact.setEmail("demon@hell.com"); // Email of the contact person
        contact.setName("demon"); // Name of the contact person

        // Creating Info object that contains metadata about the API
        Info info = new Info()
                .title("Content Management Platform") // Title of the API
                .version("0.1") // Version of the API
                .contact(contact) // Adding the contact information
                .description("API Documentation") // Short description of the API
                .termsOfService("None as of now"); // Terms of Service (if applicable)

        // Creating security components for JWT bearer authentication
        SecurityScheme securityScheme = new SecurityScheme()
                .name("Content-Management-Platform-SecurityScheme") // The name of the security scheme
                .type(SecurityScheme.Type.HTTP) // The type is HTTP (Bearer token)
                .scheme("bearer") // The scheme used is "bearer" token
                .bearerFormat("JWT"); // The bearer format is JWT (JSON Web Token)

        // Defining a security requirement to use the "JavaInUseSecurityScheme"
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Content-Management-Platform-SecurityScheme");

        // Returning an OpenAPI instance with the info, server configuration, and security settings
        return new OpenAPI()
                .info(info) // Setting the API info
                .servers(List.of(devServer)) // Adding the development server
                .addSecurityItem(securityRequirement) // Adding the security requirement to the API
                .components(new Components()
                        .addSecuritySchemes("Content-Management-Platform-SecurityScheme", securityScheme)); // Adding the security scheme to the components
    }
}
