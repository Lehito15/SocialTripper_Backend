package com.socialtripper.restapi.repositories;

import com.socialtripper.restapi.entities.Activity;
import com.socialtripper.restapi.entities.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
    @Query("select ua from UserActivity ua where ua.user.uuid = :userUuid and ua.activity = :activity")
    Optional<UserActivity> getUserActivityExperience(@Param("userUuid") UUID userUuid, @Param("activity") Activity activity);
}
