package com.socialtripper.restapi.nodes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import java.util.List;


/**
 * Właściwości relacji w grafowej bazie danych.
 * Klasa mapuje właściwości zdefiniowane na relacji IS_EVENT_MEMBER.
 * TargetNode - {@link EventNode} - pole "event".
 */
@RelationshipProperties
@Getter
@Setter
public class EventMembership {
    /**
     * Unikalny identyfikator relacji w bazie grafowej.
     * Generowany automatycznie ciąg znaków.
     */
    @Id
    @GeneratedValue
    private String id;

    /**
     * Lista współrzędnych geograficznych, które odwiedził użtykownik w trakcie wydarzenia.
     */
    List<Double> pathPoints;

    /**
     * Węzeł, do którego wchodzi krawędź - węzeł typu EVENT.
     */
    @TargetNode
    EventNode event;

    /**
     * Konstruktor wiążący wpółrzędne geograficzne z wydarzeniem.
     *
     * @param pathPoints współrzędne geograficzne odwiedzone przez użytkownika
     * @param event wydarzenie, którego dotyczą współrzędne
     */
    public EventMembership(List<Double> pathPoints, EventNode event) {
        this.pathPoints = pathPoints;
        this.event = event;
    }
}
