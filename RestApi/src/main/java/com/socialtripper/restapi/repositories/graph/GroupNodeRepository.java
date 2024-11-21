package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.GroupNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupNodeRepository extends Neo4jRepository<GroupNode, String> {
    Optional<GroupNode> findGroupNodeByUuid(UUID uuid);
}
