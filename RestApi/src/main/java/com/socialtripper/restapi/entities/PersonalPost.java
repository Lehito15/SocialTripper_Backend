package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Encja reprezentująca post personalny.
 * Klasa stanowi mapowanie tabeli personal_posts.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja jeden do jednego z encją {@link Post} - pole "post"</li>
 * </ul>
 */
@Entity
@Table(name = "personal_posts")
@NoArgsConstructor
@Getter
@Setter
public class PersonalPost {
    /**
     * Unikalny identyfikator postu personalego w tabeli.
     * Identyczny z identyfikatorem postu w tabeli posts.
     */
    @Id
    private Long id;

    /**
     * Post personalny.
     * Relacja jeden do jednego z tabelą posts.
     */
    @MapsId
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * Konstruktor wiążący post.
     * @param post post personalny
     */
    public PersonalPost(Post post) {
        this.post = post;
    }
}
