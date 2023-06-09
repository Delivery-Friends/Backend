package DeliveryFriends.Backend.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/**")
                .build();
    }
    @Bean
    public OpenAPI springApiConfig() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Delivery-Friends API")
                        .description("Delivery-Friends API 명세서입니다.")
                        .version("v0.0.1"));
    }
}
