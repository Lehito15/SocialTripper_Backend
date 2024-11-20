package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupPostRepository extends JpaRepository<GroupPost, Long> {
}
