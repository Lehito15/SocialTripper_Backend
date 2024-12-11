package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.GroupLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium do zarządzania encjami języków grupy {@link GroupLanguage} w bazie Postgres.
 */
@Repository
public interface GroupLanguageRepository extends JpaRepository<GroupLanguage, Long> {
}
