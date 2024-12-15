package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.GroupEvent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repozytorium do zarządzania encjami wydarzeń grupowych {@link GroupEvent} w bazie Postgres.
 */
public interface GroupEventRepository extends JpaRepository<GroupEvent, Long> {
}
