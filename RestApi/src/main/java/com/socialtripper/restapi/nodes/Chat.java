package com.socialtripper.restapi.nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import java.util.List;

@Node
public class Chat {
    @Id
    @GeneratedValue
    private Long id;

    private List<String> messages;
}
