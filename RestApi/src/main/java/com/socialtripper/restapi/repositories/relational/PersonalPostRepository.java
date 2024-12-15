package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.PersonalPost;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repozytorium do zarządzania encjami postów personalnych {@link PersonalPost} w bazie Postgres.
 */
public interface PersonalPostRepository extends JpaRepository<PersonalPost, Long> {
}
