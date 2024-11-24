package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserNodeRepository extends Neo4jRepository<UserNode, String> {
    Optional<UserNode> findByUuid(UUID uuid);


}
