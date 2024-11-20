package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.EventActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventActivityRepository extends JpaRepository<EventActivity, Long> {
}
