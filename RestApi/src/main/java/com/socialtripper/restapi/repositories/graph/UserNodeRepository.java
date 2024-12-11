package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repozytorium do zarządzania węzłami użytkowników {@link UserNode} w bazie Neo4j.
 */
@Repository
public interface UserNodeRepository extends Neo4jRepository<UserNode, String> {
    /**
     * Metoda zwracająca węzeł użytkownika o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @return opcjonalny węzeł użytkownika, jeżeli istnieje w bazie danych
     */
    Optional<UserNode> findByUuid(UUID uuid);

    /**
     * Metoda tworząca relację zapytania o obserwację użytkownika.
     *
     * @param followerUuid globalny, unikalny identyfikator użytkownika obserwującego w systemie
     * @param followedUuid globalny, unikalny identyfikator użytkownika obserwowanego w systemie
     */
    @Query(value = "match (u1: USER {uuid: $followerUuid}), (u2: USER {uuid: $followedUuid})" +
            "create (u1)-[: REQUESTS_FOLLOW]->(u2)")
    void createFollowRequest(@Param("followerUuid") String followerUuid, @Param("followedUuid") String followedUuid);

    /**
     * Metoda usuwająca relację zapytania o obserwajcę użytkownika.
     *
     * @param followerUuid globalny, unikalny identyfikator użytkownika obserwującego w systemie
     * @param followedUuid globalny, unikalny identyfikator użytkownika obserwowanego w systemie
     */
    @Query(value = "match (u1: USER {uuid: $followerUuid})-[r: REQUESTS_FOLLOW]->(u2: USER {uuid: $followedUuid})" +
            " delete r")
    void removeFollowRequest(@Param("followerUuid") String followerUuid, @Param("followedUuid") String followedUuid);

    /**
     * Metoda tworząca relację obserwacji użytkownika.
     *
     * @param followerUuid globalny, unikalny identyfikator użytkownika obserwującego w systemie
     * @param followedUuid globalny, unikalny identyfikator użytkownika obserwowanego w systemie
     */
    @Query(value = "match (u1: USER {uuid: $followerUuid}), (u2: USER {uuid: $followedUuid})" +
            " create (u1)-[: FOLLOWS]->(u2)")
    void addUserFollow(@Param("followerUuid") String followerUuid, @Param("followedUuid") String followedUuid);

    /**
     * Metoda usuwająca relację obserwacji użytkownika.
     *
     * @param followerUuid globalny, unikalny identyfikator użytkownika obserwującego w systemie
     * @param followedUuid globalny, unikalny identyfikator użytkownika obserwowanego w systemie
     */
    @Query(value = "match (u1: USER {uuid: $followerUuid})-[r: FOLLOWS]->(u2: USER {uuid: $followedUuid})" +
            " delete r")
    void removeUserFollow(@Param("followerUuid") String followerUuid, @Param("followedUuid") String followedUuid);

    /**
     * Metoda zwracająca wartość logiczną informującą czy użytkownik obserwuje innego użytkownika.
     *
     * @param followerUuid globalny, unikalny identyfikator użytkownika obserwującego w systemie
     * @param followedUuid globalny, unikalny identyfikator użytkownika obserwowanego w systemie
     * @return wartość logiczna informująca czy użytkownik obserwuje innego użytkownika
     */
    @Query(value = "match (u1: USER {uuid: $followerUuid})-[r: FOLLOWS]->(u2: USER {uuid: $followedUuid})" +
            " return count(r) > 0 as isFollowing")
    boolean isFollowingUser(@Param("followerUuid") String followerUuid, @Param("followedUuid") String followedUuid);

    /**
     * Metoda zwracająca listę węzłów użytkowników, którzy wysłali zapytanie do obserwację.
     *
     * @param followedUuid globalny, unikalny identyfikator użytkownika obserwowanego w systemie
     * @return lista węzłó użytkowników, którzy wysłali prośbę o obsewację
     */
    @Query(value = "match (u1: USER {uuid: $followedUuid})<-[:REQUESTS_FOLLOW]-(u2: USER)" +
            " return u2 {.*}")
    List<UserNode> getUserFollowRequests(@Param("followedUuid") String followedUuid);

    /**
     * Metoda zwracająca wartość logiczną informującą czy użytkownik wysłał zapytanie o obserwajcę.
     *
     * @param followedUuid globalny, unikalny identyfikator użytkownika obserwującego w systemie
     * @param followerUuid globalny, unikalny identyfikator użytkownika obserwowanego w systemie
     * @return wartość logiczna informująca czy użytkownik wysłał zapytanie o obserwajcę
     */
    @Query(value = "match (u1:USER {uuid: $followedUuid})<-[r:REQUESTS_FOLLOW]-(u2:USER {uuid: $followerUuid})" +
            " return count(r) > 0 as isRequestSent")
    boolean isFollowRequestSent(@Param("followedUuid") String followedUuid, @Param("followerUuid") String followerUuid);
}
