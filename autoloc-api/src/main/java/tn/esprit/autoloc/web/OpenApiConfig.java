package tn.esprit.autoloc.web;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AutoLoc API")
                        .version("1.0.0")
                        .description("API REST pour la gestion des agences et des véhicules.")
                        .contact(new Contact()
                                .name("Équipe AutoLoc")));
    }
}