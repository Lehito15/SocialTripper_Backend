package com.socialtripper.restapi.repositories;

import com.socialtripper.restapi.entities.GroupActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupActivityRepository extends JpaRepository<GroupActivity, Long> {
}
