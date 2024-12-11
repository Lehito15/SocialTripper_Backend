package com.socialtripper.restapi.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Encja reprezentująca post.
 * Klasa stanowiąca mapowanie tabeli posts.
 */
@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    /**
     * Unikalny identyfikator postu w tabeli.
     * Autoinkrementowany klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Globalny, unikalny identyfikator postu.
     */
    @Column(nullable = false, unique = true)
    private UUID uuid;

    /**
     * Treść postu.
     */
    @Column(length = 2220)
    private String content;

    /**
     * Informacja o dostępności postu.
     * Publiczny gdy wartość pola ustawiona na true, prywatny gdy wartość ustawiona na false.
     */
    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    /**
     * Data utworzenia postu.
     */
    @Column(name = "date_of_post", nullable = false)
    private LocalDateTime dateOfPost;

    /**
     * Informacja czy post został usunięty przez autora.
     * Dezaktywowany gdy wartość pola ustawiona na true, aktywny gdy wartość ustawiona na false.
     */
    @Column(name = "is_expired", nullable = false)
    private boolean isExpired;

    /**
     * Informacja czy post został zablokowany przez administrację.
     * Dezaktywowany gdy wartość pola ustawiona na true, aktywny gdy wartość ustawiona na false.
     */
    @Column(name = "is_locked", nullable = false)
    private boolean isLocked;

    /**
     * Aktualna liczba komentarzy.
     */
    @Column(name = "comments_number", nullable = false)
    private int commentsNumber;

    /**
     * Aktualna liczba reakcji.
     */
    @Column(name = "reactions_number", nullable = false)
    private int reactionsNumber;

    /**
     * Autor postu.
     * Relacja wiele do jednego z tabelą accounts.
     */
    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Account account;


    /**
     * Konstruktor ustawiający treść i publiczność postu.
     * Generowane jest UUID.
     *
     *
     * @param uuid globalny, unikalny identyfikator postu
     * @param content treść postu
     * @param isPublic informacja o publiczności postu
     */
    public Post(UUID uuid, String content, boolean isPublic) {
        this.uuid = uuid;
        this.content = content;
        this.isPublic = isPublic;
    }
}
