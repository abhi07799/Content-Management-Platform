package com.content.platform.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
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

    @Bean
    public OpenAPI swaggerConfiguration()
    {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Server URL for Development environment");

        Contact contact = new Contact();
        contact.setEmail("test@gmail.com");
        contact.setName("demon");

        Info info = new Info()
                .title("Content Management Platform")
                .version("0.1")
                .contact(contact)
                .description("API Documentation")
                .termsOfService("None as of now");

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
