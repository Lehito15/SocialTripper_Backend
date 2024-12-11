package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Encja reprezentująca post grupowy.
 * Klasa stanowi mapowanie tabeli groups_posts.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja jeden do jednego z encją {@link Post} - pole "post"</li>
 *     <li>Relacja wiele do jednego z encją {@link Group} - pole "group"</li>
 * </ul>
 */
@Entity
@Table(name = "groups_posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupPost {
    /**
     * unikalny identyfikator postu grupowego w tabeli
     * identyczny z identyfikatorem postu w tabeli posts
     */
    @Id
    private Long id;

    /**
     * post grupowy
     * relacja jeden do jednego z tabelą posts
     */
    @MapsId
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * grupa, w której opublikowany został post
     * relacja wiele do jednego z tabelą groups
     */
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    /**
     * Konstruktor wiążący post.
     *
     * @param post post dodany do grupy
     */
    public GroupPost(Post post) {
        this.post = post;
    }

    /**
     * Konstruktor wiążący post z grupą.
     *
     *
     * @param post post dodany do grupy
     * @param group grupa, w której opublikowano post
     */
    public GroupPost(Post post, Group group) {
        this.post = post;
        this.group = group;
    }
}
