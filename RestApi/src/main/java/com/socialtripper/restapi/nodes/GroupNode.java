package com.socialtripper.restapi.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.UUID;

/**
 * Węzeł reprezentujący grupę w systemie.
 * Klasa stanowi mapowanie na węzeł typu GROUP.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja typu OUTGOING z węzłem {@link UserNode} - pole "owner"</li>
 * </ul>
 */
@Node("GROUP")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupNode {
    /**
     * Unikalny identyfikator węzła w bazie.
     * Ciąg znaków.
     */
    @Id
    private String id;

    /**
     * Globalny, unikatowy identyfikator grupy w systemie.
     */
    private UUID uuid;
    /**
     * Nazwa grupy.
     */
    private String name;

    /**
     * Właściciel grupy.
     * Relacja OUTGOING z węzłem typu USER.
     */
    @Relationship(type = "IS_GROUP_OWNER")
    private UserNode owner;

    /**
     * Konstruktor tworzący nowo utworzoną grupę.
     *
     * @param uuid globalny, unikatowy identyfikator grupy w systemie
     * @param name nazwa grupy
     */
    public GroupNode(UUID uuid, String name) {
        this.id = UUID.randomUUID().toString();
        this.uuid = uuid;
        this.name = name;
    }
}
