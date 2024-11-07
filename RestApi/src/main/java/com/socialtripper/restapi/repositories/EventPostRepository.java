package com.socialtripper.restapi.repositories;

import com.socialtripper.restapi.entities.EventPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventPostRepository extends JpaRepository<EventPost, Long> {
}
