package com.glauber.MyLowPrice.configuration.swaggerConfig;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Springapiteste-public")
                .pathsToMatch("/api/alerts/**", "/api/products/**")
                .build();
    }
}
