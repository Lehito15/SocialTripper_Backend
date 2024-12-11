package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.GroupActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium do zarządzanie encjami aktywności grupowej {@link GroupActivity} w bazie Postgres.
 */
@Repository
public interface GroupActivityRepository extends JpaRepository<GroupActivity, Long> {
}
