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
 * Węzeł reprezentujący komentarz w bazie grafowej.
 * Klasa stanowi mapowanie na węzeł typu COMMENT.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja typu OUTGOING z węzłem {@link UserNode} - pole "reactingUsers"</li>
 *     <li>Relacja typu OUTGOING z węzłem {@link UserNode} - pole "commentAuthor"</li>
 *     <li>Relacja typu INCOMING z węzłem {@link PostNode} - pole "commentedPost"</li>
 * </ul>
 */
@Node("COMMENT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentNode {
    /**
     * Unikalny dentyfikator węzła w bazie grafowej.
     * Ciąg znaków.
     */
    @Id
    private String id;

    /**
     * Unikalny, globalny identyfikator postu w systemie.
     */
    private UUID uuid;
    /**
     * Treść komentarza.
     */
    private String content;
    /**
     * Data dodania postu.
     */
    private LocalDateTime commentDate;

    /**
     * Zbiór użytkowników, którzy zareagowali na komentarz.
     * Relacja OUTGOING z węzłem typu USER.
     */
    @Relationship(type = "REACTION_TO_COMMENT_BY")
    private Set<UserNode> reactingUsers;

    /**
     * Użytkownik będący autorem postu.
     * Relacja OUTGOING z węzłem typu USER.
     */
    @Relationship(type = "COMMENTED_BY")
    private UserNode commentAuthor;

    /**
     * Post, którego dotyczy komentarz.
     * Relacja INCOMING z węzłem typu POST.
     */
    @Relationship(type = "POST_COMMENTED", direction = Relationship.Direction.INCOMING)
    private PostNode commentedPost;

    /**
     * Metoda zwracjąca liczbę reakcji do komentarza.
     * Wartość wyliczana na podstawie liczności zbioru z pola reactingUsers.
     * @return liczba reakcji na komentarz
     */
    public int getReactionsNumber() {
        return reactingUsers.size();
    }

    /**
     * Konstruktor tworzący nowo utworzony komentarz.
     *
     * @param content treść komentarza
     * @param commentDate data dodania komentarza
     * @param commentAuthor autor komentarza
     * @param commentedPost komentowany post
     */
    public CommentNode(String content, LocalDateTime commentDate,
                       UserNode commentAuthor, PostNode commentedPost) {
        this.id = UUID.randomUUID().toString();
        this.uuid = UUID.randomUUID();
        this.content = content;
        this.commentDate = commentDate;
        this.commentAuthor = commentAuthor;
        this.reactingUsers = new HashSet<>();
        this.commentedPost = commentedPost;
    }
}
