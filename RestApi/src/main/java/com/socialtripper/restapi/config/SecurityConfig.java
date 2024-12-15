package com.socialtripper.restapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Konfiguracja zabezpieczeń endpointów przy pomocy tokenów jwt z AWS Cognito.
 */
@Configuration
public class SecurityConfig {
    /**
     * region aws
     */
   @Value("${cognito.region}")
   private String region;

    /**
     * identyfikator user poola AWS Cognito
     */
   @Value("${cognito.userPoolId}")
   private String userPoolId;

    /**
     * Metoda definiująca reguły dostępu do endpointów - filter chain.
     */
   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http
               .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                       .requestMatchers("/**").permitAll()
                       .anyRequest().authenticated()
               );
               .oauth2ResourceServer(oauth2 -> oauth2
                       .jwt(jwt -> jwt.decoder(jwtDecoder()))
               );
       return http.build();
   }

    /**
     * Metoda walidująca poprawność tokenów.
     */
   @Bean
   public JwtDecoder jwtDecoder() {
       String jwkSetUri = String.format(
               "https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json",
               region,
               userPoolId);
       return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
   }
}
