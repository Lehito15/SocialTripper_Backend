package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.LocationScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium do zarządzania encjami zasięgu grupy {@link LocationScope} w bazie Postgres.
 */
@Repository
public interface LocationScopeRepository extends JpaRepository<LocationScope, Long> {
}
