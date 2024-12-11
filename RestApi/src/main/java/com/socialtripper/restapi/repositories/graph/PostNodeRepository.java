package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.PostNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repozytorium do zarządzania węzłami postów {@link PostNode} w bazie Neo4j.
 */
@Repository
public interface PostNodeRepository extends Neo4jRepository<PostNode, String> {
    /**
     * Metoda zwracająca węzeł postu o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator postu w systemie
     * @return opcjonalny węzeł postu, jeżeli istnieje w bazie danych
     */
    Optional<PostNode> findPostNodeByUuid(UUID uuid);

    /**
     * Metoda tworząca relację reakcji na post przez użytkownika.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param postUuid globalny, unikalny identyfikator postu w systemie
     */
    @Query(value = "match (u: USER {uuid: $userUuid}), (p: POST {uuid: $postUuid})" +
            " merge (u)<-[:REACTION_TO_POST_BY]-(p)")
    void addPostReaction(@Param("userUuid") String userUuid, @Param("postUuid") String postUuid);

    /**
     * Metoda usuwająca relację reakcji na post.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param postUuid globalny, unikalny identyfikator postu w systemie
     */
    @Query(value = "match (u: USER {uuid: $userUuid})<-[r:REACTION_TO_POST_BY]-(p: POST {uuid: $postUuid})" +
            " delete r")
    void removePostReaction(@Param("userUuid") String userUuid, @Param("postUuid") String postUuid);

    /**
     * Metoda zwracająca wartość logiczną informującą czy użytkownik zareagował na post.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param postUuid globalny, unikalny identyfikator postu w systemie
     * @return wartość logiczna informująca czy użytkownik zareagował na post
     */
    @Query(value = "match (u: USER {uuid: $userUuid})<-[r:REACTION_TO_POST_BY]-(p: POST {uuid: $postUuid})" +
            " return count(r) > 0 as isMember")
    boolean didUserReact(@Param("userUuid") String userUuid, @Param("postUuid") String postUuid);

    /**
     * Metoda zwracająca listę postów autorstwa obserwowanych użytkowników.
     *
     * @param followerUuid globalny, unikalny obserwowanego użytkownika w systemie
     * @return lista węzłów postów autorstwa obserwowanych użytkowników
     */
    @Query(value = "match (u1:USER {uuid: $followerUuid})-[:FOLLOWS]->(u2:USER)<-[:POSTED_BY]-(p:POST) " +
            "where not (p)-[:POSTED_IN_GROUP]->(:GROUP) and not (p)-[:POSTED_IN_EVENT]->(:EVENT) " +
            "return p {.*}")
    List<PostNode> findFollowedUsersPosts(@Param("followerUuid") String followerUuid);
}
