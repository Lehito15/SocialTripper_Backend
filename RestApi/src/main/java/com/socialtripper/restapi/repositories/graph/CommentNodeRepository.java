package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.CommentNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentNodeRepository extends Neo4jRepository<CommentNode, String> {
    Optional<CommentNode> findCommentNodeByUuid(UUID uuid);

    @Query(value = "match (p: POST)-[:POST_COMMENTED]->(c: COMMENT)" +
            " where p.uuid = $postUuid" +
            " return c {.*}")
    List<CommentNode> findPostComments(@Param("postUuid") String postUuid);

    @Query(value = "match (u: USER {uuid: $userUuid}), (c: COMMENT {uuid: $commentUuid}) " +
            "create (c)-[:REACTION_TO_COMMENT_BY]->(u)")
    void addCommentReaction(@Param("userUuid") String userUuid, @Param("commentUuid") String commentUuid);

    @Query(value = "match (u: USER {uuid: $userUuid})<-[r:REACTION_TO_COMMENT_BY]-(c: COMMENT {uuid: $commentUuid}) " +
            "delete r")
    void removeCommentReaction(@Param("userUuid") String userUuid, @Param("commentUuid") String commentUuid);

    @Query(value = "match (u: USER {uuid: $userUuid})<-[r: REACTION_TO_COMMENT_BY]-(c: COMMENT {uuid: $commentUuid})" +
            " return count(r) > 0 as didReact")
    boolean didUserReact(@Param("userUuid") String userUuid, @Param("commentUuid") String commentUuid);
}
