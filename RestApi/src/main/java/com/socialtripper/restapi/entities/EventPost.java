package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Encja reprezentująca posty dotyczące wydarzeń.
 * Klasa stanowi mapowanie tabeli events_posts.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja jeden do jednego z encją {@link Post} - pole "post"</li>
 *     <li>Relacja wiele do jednego z encją {@link Event} - pole "event"</li>
 * </ul>
 */
@Entity
@Table(name = "events_posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventPost {
    /**
     * Unikalny identyfikator postu w systemie.
     * Klucz identyczny z kluczem postu w tabeli posts.
     */
    @Id
    private Long id;

    /**
     * Post dodany do wydarzenia.
     * Relacja jeden do jednego z tabelą posts.
     */
    @MapsId
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * Wydarzenie, do którego dodano post.
     * Relacja wiele do jednego z tabelą events.
     */
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    /**
     * Konstruktor wiążący post z wydarzeniem.
     *
     * @param post post związany z wydarzeniem
     * @param event wydarzenie, do którego dodawany jest post
     */
    public EventPost(Post post, Event event) {
        this.post = post;
        this.event = event;
    }

    /**
     * Konstruktor ustawiający post do dodania w wydarzeniu.
     *
     * @param post post związany z wydarzeniem
     */
    public EventPost(Post post) {
        this.post = post;
    }
}