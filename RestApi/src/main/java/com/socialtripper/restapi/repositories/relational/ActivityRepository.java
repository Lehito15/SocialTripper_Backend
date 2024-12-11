package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium do zarządzania encjami aktywności {@link Activity} w bazie Postgres.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
