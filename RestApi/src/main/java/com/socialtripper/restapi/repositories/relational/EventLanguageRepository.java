package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.EventLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repozytorium do zarządzania encjami języka w wydarzeniu {@link EventLanguage} w bazie Postgres.
 */
public interface EventLanguageRepository extends JpaRepository<EventLanguage, Long> {
}
