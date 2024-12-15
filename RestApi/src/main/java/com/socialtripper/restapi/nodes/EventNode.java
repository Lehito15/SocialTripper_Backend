package com.socialtripper.restapi.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Węzeł reprezentujący wydarzenie w systemie.
 * Klasa stanowi mapowanie węzeł typu EVENT.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja typu OUTGOING z węzłem {@link UserNode} - pole "owner"</li>
 *     <li>Relacja typu OUTGOING z węzłem {@link GroupNode} - pole "group"</li>
 *     <li>Relacja typu INCOMING z węzłem {@link UserNode} - pole "members"</li>
 * </ul>
 */
@Node("EVENT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventNode {
    /**
     * Unikalny identyfikator węzła w bazie.
     * Ciąg znaków.
     */
    @Id
    private String id;

    /**
     * Globalny, unikalny identyfikator wydarzenia w systemie.
     */
    private UUID uuid;
    /**
     * Nazwa wydarzenia.
     */
    private String name;

    /**
     * Właściciel wydarzenia.
     * Relacja OUTGOING z węzłem typu USER.
     */
    @Relationship(type = "IS_OWNED_BY_USER")
    private UserNode owner;

    /**
     * Grupa, do której należy wydarzenie.
     * Relacja OUTGOING z węzłem typu GROUP.
     */
    @Relationship(type = "IS_GROUP_EVENT")
    private GroupNode group;

    /**
     * Zbiór uczestników wydarzenia.
     * Relacja INCOMING z węzłem typu USER.
     */
    @Relationship(type = "IS_EVENT_MEMBER", direction = Relationship.Direction.INCOMING)
    private Set<UserNode> members;

    /**
     * Konstruktor tworzący nowo utworzone wydarzenie.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @param name nazwa wydarzenia
     */
    public EventNode(UUID uuid, String name) {
        this.id = UUID.randomUUID().toString();
        this.uuid = uuid;
        this.name = name;
        this.members = new HashSet<>();
    }
}
