package com.socialtripper.restapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Konfiguracja polityki CORS.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * Konfiguracja zasad CORS dla aplikacji.
     * Umożliwia określenie, które żądania pochodzące z różnych domen mogą być obsługiwane przez aplikację.
     *
     * @param registry obiekt {@link CorsRegistry} używany do rejestrowania reguł CORS
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}
