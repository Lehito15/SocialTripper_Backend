package com.socialtripper.restapi.repositories;

import com.socialtripper.restapi.entities.EventActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventActivityRepository extends JpaRepository<EventActivity, Long> {
}
