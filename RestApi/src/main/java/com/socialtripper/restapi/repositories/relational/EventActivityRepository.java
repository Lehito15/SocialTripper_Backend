package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.EventActivity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repozytorium do zarządzania encjami aktywności w wydarzeniu {@link EventActivity} w bazie Postgres.
 */
public interface EventActivityRepository extends JpaRepository<EventActivity, Long> {
}
