package com.socialtripper.restapi.repositories;

import com.socialtripper.restapi.entities.Account;
import com.socialtripper.restapi.entities.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    @Procedure(name = "userEndsFollowing")
    void userEndsFollowing(@Param("follower_uuid") UUID followerUuid, @Param("followed_uuid") UUID followedUuid);

    @Query(
            "select f.followed from Follow f " +
                    "where f.follower.uuid = :followerUuid and f.followingTo is not null"
    )
    List<Account> findAccountsFollowedBy(@Param("followerUuid") UUID followerUuid);
}
