package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserNodeRepository extends Neo4jRepository<UserNode, String> {
    Optional<UserNode> findByUuid(UUID uuid);

    @Query(value = "match (u1: USER {uuid: $followerUuid}), (u2: USER {uuid: $followedUuid})" +
            "create (u1)-[: REQUESTS_FOLLOW]->(u2)")
    void createFollowRequest(@Param("followerUuid") String followerUuid, @Param("followedUuid") String followedUuid);

    @Query(value = "match (u1: USER {uuid: $followerUuid})-[r: REQUESTS_FOLLOW]->(u2: USER {uuid: $followedUuid})" +
            " delete r")
    void removeFollowRequest(@Param("followerUuid") String followerUuid, @Param("followedUuid") String followedUuid);

    @Query(value = "match (u1: USER {uuid: $followerUuid}), (u2: USER {uuid: $followedUuid})" +
            " create (u1)-[: FOLLOWS]->(u2)")
    void addUserFollow(@Param("followerUuid") String followerUuid, @Param("followedUuid") String followedUuid);

    @Query(value = "match (u1: USER {uuid: $followerUuid})-[r: FOLLOWS]->(u2: USER {uuid: $followedUuid})" +
            " delete r")
    void removeUserFollow(@Param("followerUuid") String followerUuid, @Param("followedUuid") String followedUuid);

    @Query(value = "match (u1: USER {uuid: $followerUuid})-[r: FOLLOWS]->(u2: USER {uuid: $followedUuid})" +
            " return count(r) > 0 as isFollowing")
    boolean isFollowingUser(@Param("followerUuid") String followerUuid, @Param("followedUuid") String followedUuid);

    @Query(value = "match (u1: USER {uuid: $followedUuid})<-[:REQUESTS_FOLLOW]-(u2: USER)" +
            " return u2 {.*}")
    List<UserNode> getUserFollowRequests(@Param("followedUuid") String followedUuid);

    @Query(value = "match (u1: USER {uuid: $followedUuid})<-[r:REQUESTS_FOLLOW]-(u2: USER {uuid: $followerUuid})" +
            " return count(r) > 0 as isRequestSent")
    boolean isFollowRequestSent(@Param("followedUuid") String followedUuid, @Param("followerUuid") String followerUuid);
}
