package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventStatusRepository extends JpaRepository<EventStatus, Long> {
}
