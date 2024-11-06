package com.socialtripper.restapi.repositories;

import com.socialtripper.restapi.entities.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupPostRepository extends JpaRepository<GroupPost, Long> {
}
