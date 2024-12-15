package com.socialtripper.restapi.config;

import org.neo4j.driver.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Konfiguracja bazy grafowej Neo4j.
 */
@Configuration
public class Neo4jConfig {
    /**
     * Konfiguruje menedżera transakcji dla bazy danych Neo4j.
     *
     * @param driver instancja sterownika Neo4j, używana do zarządzania połączeniami z bazą danych
     * @return obiekt menedżera transakcji dla bazy danych Neo4j
     */
    @Bean
    public PlatformTransactionManager transactionManager(Driver driver) {
        return new Neo4jTransactionManager(driver);
    }

    /**
     * Tworzy i konfiguruje instancję Neo4jTemplate, która zapewnia wsparcie dla operacji
     * na bazie danych Neo4j w ramach aplikacji.
     *
     * @param neo4jClient     klient Neo4j, używany do wykonywania zapytań i operacji na bazie danych
     * @param mappingContext  kontekst mapowania Neo4j, używany do konwersji danych między bazą danych
     *                        a obiektami domenowymi
     * @return skonfigurowana instancja Neo4jTemplate
     */
    @Bean
    public Neo4jTemplate neo4jTemplate(Neo4jClient neo4jClient, Neo4jMappingContext mappingContext) {
        return new Neo4jTemplate(neo4jClient, mappingContext);
    }
}