package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.GroupEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupEventRepository extends JpaRepository<GroupEvent, Long> {
}
