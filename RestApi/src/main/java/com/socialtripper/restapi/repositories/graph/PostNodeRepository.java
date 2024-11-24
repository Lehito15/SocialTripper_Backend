package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.PostNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostNodeRepository extends Neo4jRepository<PostNode, String> {
    Optional<PostNode> findPostNodeByUuid(UUID uuid);

    @Query(value = "match (p:POST) " +
            "where p.uuid = $postUuid " +
            "return p {.*}")
    PostNode findPostByUuid(@Param("postUuid") String postUuid);

    @Query(value = "match (u: USER {uuid: $userUuid}), (p: POST {uuid: $postUuid})" +
            " create (u)-[:REACTS_TO_POST]->(p)")
    void addPostReaction(@Param("userUuid") String userUuid, @Param("postUuid") String postUuid);

    @Query(value = "match (u: USER {uuid: $userUuid})-[r:REACTS_TO_POST]->(p: POST {uuid: $postUuid})" +
            " delete r")
    void removePostReaction(@Param("userUuid") String userUuid, @Param("postUuid") String postUuid);
}
