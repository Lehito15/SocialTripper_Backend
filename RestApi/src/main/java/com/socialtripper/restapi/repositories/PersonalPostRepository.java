package com.socialtripper.restapi.repositories;

import com.socialtripper.restapi.entities.PersonalPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalPostRepository extends JpaRepository<PersonalPost, Long> {
}
