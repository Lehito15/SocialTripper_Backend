package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repozytorium do zarządzania encjami statusu wydarzenia {@link EventStatus} w bazie Postgres.
 */
public interface EventStatusRepository extends JpaRepository<EventStatus, Long> {
}
