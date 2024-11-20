package com.socialtripper.restapi.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;
import java.util.UUID;

@Node("GROUP")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupNode {
    @Id
    @GeneratedValue
    private Long id;

    private UUID uuid;
    private String name;
    private boolean isPublic;
    private String homePageUrl;
    private String iconUrl;
    private Set<String> languages;
    private Set<String> activities;

    @Relationship(type = "IS_GROUP_OWNER")
    private UserNode owner;

    public GroupNode(UUID uuid, String name, boolean isPublic, String homePageUrl,
                     String iconUrl, Set<String> languages, Set<String> activities) {
        this.uuid = uuid;
        this.name = name;
        this.isPublic = isPublic;
        this.homePageUrl = homePageUrl;
        this.iconUrl = iconUrl;
        this.languages = languages;
        this.activities = activities;
    }
}
