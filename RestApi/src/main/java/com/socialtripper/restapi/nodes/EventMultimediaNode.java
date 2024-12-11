package com.socialtripper.restapi.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Węzeł reprezentujący multimedium wytworzone w trakcie trwania wydarzenia.
 * Klasa stanowi mapowanie na węzeł typu EVENT_MULTIMEDIA.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja typu OUTGOING z węzłem {@link EventNode} - pole "event"</li>
 *     <li>Relacja typu OUTGOING z węzłem {@link UserNode} - pole "producer"</li>
 * </ul>
 */
@Node("EVENT_MULTIMEDIA")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventMultimediaNode {
    /**
     * Unikalny identyfikator węzła w bazie.
     * Ciąg znaków.
     */
    @Id
    private String id;

    /**
     * Globalny, unikalny identyfikator multimedium w systemie.
     */
    private UUID uuid;
    /**
     * Link do multimedium w Azure Blob Storage.
     */
    private String multimediaUrl;
    /**
     * Szerokość geograficzna miejsca, gdzie wytworzono multimedium.
     */
    private Double latitude;
    /**
     * Długość geograficzna miejsca, gdzie wytworzono multimedium.
     */
    private Double longitude;
    /**
     * Znacznik czasowy dla wytworzonego multimedium.
     */
    private LocalDateTime timestamp;

    /**
     * Wydarzenie, którego dotyczy multimedium.
     * Relacja OUTGOING z węzłem typu EVENT.
     */
    @Relationship(value = "IS_EVENT_MULTIMEDIA", direction = Relationship.Direction.OUTGOING)
    private EventNode event;

    /**
     * Użytkownik, który wytworzył multimedium.
     * Relacja OUTGOING z węzłem typu USER.
     */
    @Relationship(value = "IS_PRODUCED_BY", direction = Relationship.Direction.OUTGOING)
    private UserNode producer;

    /**
     * Konstruktor wiążący metadane multimedium z jego autorem oraz wydarzeniem.
     *
     * @param uuid globalny unikalny identyfikator multimedium w systemie
     * @param multimediaUrl link do multimedium w Azure Blob Storage
     * @param latitude szerokość geograficzna miejsca wytworzenia multimedium
     * @param longitude długość geograficzna miejsca wytworzenia multimedium
     * @param timestamp znacznik czasowy
     * @param producer autor multimedium
     * @param event wydarzenie, którego dotyczy multimedium
     */
    public EventMultimediaNode(UUID uuid, String multimediaUrl, Double latitude,
                               Double longitude, LocalDateTime timestamp,
                               UserNode producer, EventNode event) {
        this.id = UUID.randomUUID().toString();
        this.uuid = uuid;
        this.multimediaUrl = multimediaUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.producer = producer;
        this.event = event;
    }
}
