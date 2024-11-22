package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.PostNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostNodeRepository extends Neo4jRepository<PostNode, String> {
    @Query(value = "match (p:POST) " +
            "where p.uuid = $postUuid " +
            "return p {.*}")
    PostNode findPostByUuid(@Param("postUuid") String postUuid);
}
