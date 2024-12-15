package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.CommentNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repozytorium do zarządzania węzłami komentarzy {@link CommentNode} w bazie Neo4j.
 */
@Repository
public interface CommentNodeRepository extends Neo4jRepository<CommentNode, String> {
    /**
     * Metoda zwracająca węzeł komentarza o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator komentarza w systemie
     * @return opcjonalny węzeł komentarza, jeżeli istnieje w bazie danych
     */
    Optional<CommentNode> findCommentNodeByUuid(UUID uuid);

    /**
     * Metoda zwracająca węzły komentarzy dotyczących postu o wskazanym UUID.
     *
     * @param postUuid globalny, unikalny identyfikator postu w systemie
     * @return lista węzłów komentarzy dotyczących postu
     */
    @Query(value = "match (p: POST)-[:POST_COMMENTED]->(c: COMMENT)" +
            " where p.uuid = $postUuid" +
            " return c {.*}")
    List<CommentNode> findPostComments(@Param("postUuid") String postUuid);

    /**
     * Metoda dodająca reakcję użytkownika na komentarz.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param commentUuid globalny, unikalny identyfikator komentarza w systemie
     */
    @Query(value = "match (u: USER {uuid: $userUuid}), (c: COMMENT {uuid: $commentUuid}) " +
            "create (c)-[:REACTION_TO_COMMENT_BY]->(u)")
    void addCommentReaction(@Param("userUuid") String userUuid, @Param("commentUuid") String commentUuid);

    /**
     * Metoda usuwająca reakcję użytkownika na komentarz.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param commentUuid globalny, unikalny identyfikator komentarza w systemie
     */
    @Query(value = "match (u: USER {uuid: $userUuid})<-[r:REACTION_TO_COMMENT_BY]-(c: COMMENT {uuid: $commentUuid}) " +
            "delete r")
    void removeCommentReaction(@Param("userUuid") String userUuid, @Param("commentUuid") String commentUuid);

    /**
     * Metoda zwracająca wartość logiczną informującą czy użytkownika zareagował na komentarz.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param commentUuid globalny, unikalny identyfikator komentarza w systemie
     * @return wartość logiczna informująca czy użytkownik zareagował na komentarz
     */
    @Query(value = "match (u: USER {uuid: $userUuid})<-[r: REACTION_TO_COMMENT_BY]-(c: COMMENT {uuid: $commentUuid})" +
            " return count(r) > 0 as didReact")
    boolean didUserReact(@Param("userUuid") String userUuid, @Param("commentUuid") String commentUuid);
}
