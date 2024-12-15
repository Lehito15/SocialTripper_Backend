package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repozytorim do zarządzania encjami postów grupowych {@link GroupPost} w bazie Postgres.
 */
public interface GroupPostRepository extends JpaRepository<GroupPost, Long> {
}
