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
 * Węzeł reprezentujący użytkownika systemu.
 * Klasa stanowi mapowanie na węzeł typu USER.
 * <b>Relacje: </b>
 * <ul>
 *    <li>Relacja typu OUTGOING z węzłem {@link EventNode} - pole "appliedEvents"</li>
 *    <li>Relacja typu OUTGOING z węzłem {@link EventNode} - pole "events"</li>
 *    <li>Relacja typu OUTGOING z węzłem {@link EventNode} - pole "ownedEvents"</li>
 *    <li>Relacja typu OUTGOING z węzłem {@link GroupNode} - pole "appliedGroups"</li>
 *    <li>Relacja typu OUTGOING z węzłem {@link GroupNode} - pole "groups"</li>
 *    <li>Relacja typu OUTGOING z węzłem {@link UserNode} - pole "requestedFollows"</li>
 *    <li>Relacja typu OUTGOING z węzłem {@link UserNode} - pole "follows"</li>
 *    <li>Relacja typu OUTGOING z węzłem {@link UserNode} - pole "followedBy"</li>
 * </ul>
 */
@Node("USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserNode {
    /**
     * Unikalny identyfikator węzłą w bazie.
     * Ciąg znaków.
     */
    @Id
    private String id;

    /**
     * Globalny, unikalny identyfikator użytkownika w systemie.
     */
    private UUID uuid;
    /**
     * Imię użytkownika.
     */
    private String name;
    /**
     * Nazwisko użytkownika.
     */
    private String surname;

    /**
     * Zbiór wydarzeń, w których użytkownik oczekuje na przyjęcie.
     * Relacja OUTGOING z węzłem typu EVENT.
     */
    @Relationship(type = "APPLIES_FOR_EVENT")
    private Set<EventNode> appliedEvents;

    /**
     * Zbiór wydarzeń, których użytkownik jest właścicielem.
     * Relacja OUTGOING z węzłem typu EVENT.
     */
    @Relationship(type = "OWNS_EVENT")
    private Set<EventNode> ownedEvents;

    /**
     * Zbiór wydarzeń, których użytkownik jest członkiem.
     * Relacja OUTGOING z węzłem typu EVENT.
     */
    @Relationship(type = "IS_EVENT_MEMBER")
    private Set<EventMembership> events;

    /**
     * Zbiór grup, których użytkownik jest członkiem.
     * Relacja OUTGOING z węzłem typu GROUP.
     */
    @Relationship(type = "IS_GROUP_MEMBER")
    private Set<GroupNode> groups;

    /**
     * Zbiór grup, w których użytkownik oczekuje na przyjęcie.
     * Relacja OUTGOING z węzłem typu GROUP.
     */
    @Relationship(type = "APPLIES_FOR_GROUP")
    private Set<GroupNode> appliedGroups;

    /**
     * Zbiór użytkowników, którym zostało wysłana prośba o obserwację.
     * Relacja OUTGOING z węzłem typu USER.
     */
    @Relationship(type = "REQUESTS_FOLLOW")
    private Set<UserNode> requestedFollows;

    /**
     * Zbiór użytkowników, którzy są obserwowani.
     * Relacja OUTGOING z węzłem typu USER.
     */
    @Relationship(type = "FOLLOWS")
    private Set<UserNode> follows;

    /**
     * Zbiór użytkowników, którzy obserwują konto.
     * Relacja INCOMING z węzłem typu USER.
     */
    @Relationship(type = "FOLLOWS", direction = Relationship.Direction.INCOMING)
    private Set<UserNode> followedBy;

    /**
     * Konstruktor tworzący węzeł nowego użytkownika w systemie.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @param name imię użytkownika
     * @param surname nazwisko użytkownika
     */
    public UserNode(UUID uuid, String name, String surname) {
        this.id = UUID.randomUUID().toString();
        this.uuid = uuid;
        this.name = name;
        this.surname = surname;
        this.appliedEvents = new HashSet<>();
        this.ownedEvents = new HashSet<>();
        this.events = new HashSet<>();
        this.groups = new HashSet<>();
        this.appliedGroups = new HashSet<>();
        this.requestedFollows = new HashSet<>();
        this.follows = new HashSet<>();
        this.followedBy = new HashSet<>();
    }

}
