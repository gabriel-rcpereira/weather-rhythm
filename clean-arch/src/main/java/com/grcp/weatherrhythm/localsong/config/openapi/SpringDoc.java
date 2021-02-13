package com.grcp.weatherrhythm.localsong.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDoc {

    @Autowired
    private BuildProperties buildProperties;

    @Bean
    public OpenAPI configureOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Weather Rhythm API")
                        .description("The APIs return Playlist Songs by you local data")
                        .version(buildProperties.getVersion()));
    }

}
