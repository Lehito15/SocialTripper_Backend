package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.PostNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostNodeRepository extends Neo4jRepository<PostNode, Long> {
}
