package com.socialtripper.restapi.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Węzeł reprezentujący post użytkownika w systemie.
 * Klasa stanowi mapowanie na węzeł typu POST.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja typu OUTGOING z węzłem {@link GroupNode} - pole "group"</li>
 *     <li>Relacja typu OUTGOING z węzłem {@link EventNode} - pole "event"</li>
 *     <li>Relacja typu OUTGOING z węzłem {@link UserNode} - pole "reactingUsers"</li>
 *     <li>Relacja typu OUTGOING z węzłem {@link CommentNode} - pole "comments"</li>
 *     <li>Relacja typu OUTGOING z węzłem {@link UserNode} - pole "author"</li>
 * </ul>
 */
@Node("POST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostNode {
    /**
     * Unikalny identyfikator węzła w bazie.
     * Ciag znaków.
     */
    @Id
    private String id;

    /**
     * Globalny, unikatowy identyfikator postu w systemie.
     */
    private UUID uuid;
    /**
     * Treść postu.
     */
    private String content;
    /**
     * Znacznik czasowy utworzenia postu.
     */
    private LocalDateTime postTime;
    /**
     * Zbiór zawierający linki do multimediów dotyczących postów w Azure Blob Storage.
     */
    private Set<String> multimediaUrls;

    /**
     * Grupa, do której został dodany post.
     * Relacja OUTGOING z węzłem typu GROUP.
     */
    @Relationship(type = "POSTED_IN_GROUP")
    private GroupNode group;

    /**
     * Wydarzenie, do którego został dodany post.
     * Relacja OUTGOING z węzłem typu EVENT.
     */
    @Relationship(type = "POSTED_IN_EVENT")
    private EventNode event;

    /**
     * Zbiór użytkowników, którzy zareagowali na post.
     * Relacja OUTGOING z węzłem typu USER.
     */
    @Relationship(type = "REACTION_TO_POST_BY")
    private Set<UserNode> reactingUsers;

    /**
     * Zbiór komentarzy do postu.
     * Relacja OUTGOING z węzłem typu COMMENT.
     */
    @Relationship(type = "POST_COMMENTED")
    private Set<CommentNode> comments;

    /**
     * Autor postu.
     * Relacja OUTGOING z węzłem typu USER.
     */
    @Relationship(type = "POSTED_BY")
    private UserNode author;

    /**
     * Konstruktor tworzący nowo utworzony post.
     *
     * @param uuid globalny, unikalny identyfikator postu w systemie
     * @param content treść postu
     * @param postTime znacznik czasowy dodania postu
     */
    public PostNode(UUID uuid, String content, LocalDateTime postTime) {
        this.id = UUID.randomUUID().toString();
        this.uuid = uuid;
        this.content = content;
        this.postTime = postTime;
        this.multimediaUrls = new HashSet<>();
        this.reactingUsers = new HashSet<>();
        this.comments = new HashSet<>();
    }

    /**
     * Metoda zwracająca liczbę komentarzy do postu.
     * Wartość liczności zbioru komentarzy z pola "comments"
     *
     * @return liczba komentarzy do postu
     */
    public int getCommentsNumber() {
        if (comments == null) return 0;
        return comments.size();
    }

    /**
     * Metoda zwracająca liczbę reakcji do postu.
     * Wartość liczności zbioru reakcji z pola "reactingUsers".
     * @return
     */
    public int getReactionsNumber() {
        if (reactingUsers == null) return 0;
        return reactingUsers.size();
    }
}
