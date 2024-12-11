package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.EventPost;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repozytorium do zarządzania encjami postów w wydarzeniu {@link EventPost} w bazie Postgres.
 */
public interface EventPostRepository extends JpaRepository<EventPost, Long> {
}
