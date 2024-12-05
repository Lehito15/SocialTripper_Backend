package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.PostNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostNodeRepository extends Neo4jRepository<PostNode, String> {
    Optional<PostNode> findPostNodeByUuid(UUID uuid);

    @Query(value = "match (u: USER {uuid: $userUuid}), (p: POST {uuid: $postUuid})" +
            " merge (u)<-[:REACTION_TO_POST_BY]-(p)")
    void addPostReaction(@Param("userUuid") String userUuid, @Param("postUuid") String postUuid);

    @Query(value = "match (u: USER {uuid: $userUuid})<-[r:REACTION_TO_POST_BY]-(p: POST {uuid: $postUuid})" +
            " delete r")
    void removePostReaction(@Param("userUuid") String userUuid, @Param("postUuid") String postUuid);

    @Query(value = "match (u: USER {uuid: $userUuid})<-[r:REACTION_TO_POST_BY]-(p: POST {uuid: $postUuid})" +
            " return count(r) > 0 as isMember")
    boolean didUserReact(@Param("userUuid") String userUuid, @Param("postUuid") String postUuid);

    @Query(value = "match (u1:USER {uuid: $followerUuid})-[:FOLLOWS]->(u2:USER)<-[:POSTED_BY]-(p:POST) " +
            "where not (p)-[:POSTED_IN_GROUP]->(:GROUP) and not (p)-[:POSTED_IN_EVENT]->(:EVENT) " +
            "return p {.*}")
    List<PostNode> findFollowedUsersPosts(@Param("followerUuid") String followerUuid);
}
