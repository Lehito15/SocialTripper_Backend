package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.EventNode;
import com.socialtripper.restapi.nodes.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repozytorium do zarządzania węzłami wydarzeń {@link EventNode} w bazie Neo4j.
 */
@Repository
public interface EventNodeRepository extends Neo4jRepository<EventNode, String> {
    /**
     * Metoda zwracająca wydarzenie o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return opcjonalny węzeł wydarzenia, jeżeli istnieje w bazie danych
     */
    Optional<EventNode> findEventNodeByUuid(UUID uuid);

    /**
     * Metoda tworząca relację zapytania dołączenia do wydarzenia przez użytkownika.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param eventUuid globalny, unikalny identyfikator wydarzenia w systemie
     */
    @Query(value = "match (u: USER {uuid: $userUuid}), (e: EVENT {uuid: $eventUuid})" +
            " merge (u)-[:APPLIES_FOR_EVENT]->(e)")
    void createEventRequest(@Param("userUuid") String userUuid, @Param("eventUuid") String eventUuid);

    /**
     * Metoda usuwająca relację zapytania dołączenia do wydarzenia przez użytkownika.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param eventUuid globalny, unikalny identyfikator wydarzenia w systemie
     */
    @Query(value = "match(u:USER {uuid: $userUuid})-[a:APPLIES_FOR_EVENT]->(e:EVENT {uuid: $eventUuid}) " +
            " delete a")
    void removeEventRequest(@Param("userUuid") String userUuid, @Param("eventUuid") String eventUuid);

    /**
     * Metoda tworząca relację przynależności użytkownika do wydarzenia.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param eventUuid globalny, unikalny identyfikator wydarzenia w systemie
     */
    @Query(value = "match (u: USER {uuid: $userUuid}), (e: EVENT {uuid: $eventUuid})" +
            " merge (u)-[r:IS_EVENT_MEMBER]->(e)" +
            " set r.pathPoints = []")
    void addUserToEvent(@Param("userUuid") String userUuid, @Param("eventUuid") String eventUuid);

    /**
     * Metoda usuwająca relację przynależności użytkownika do wydarzenia.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param eventUuid globalny, unikalny identyfikator wydarzenia w systemie
     */
    @Query(value = "match(u:USER {uuid: $userUuid})-[a:IS_EVENT_MEMBER]->(e:EVENT {uuid: $eventUuid}) " +
            " delete a")
    void removeUserFromEvent(@Param("userUuid") String userUuid, @Param("eventUuid") String eventUuid);

    /**
     * Metoda zwracająca węzły użytkowników, którzy wysłali zapytanie o dołączenie do wydarzenia.
     *
     * @param eventUuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return list węzłów użytkowników, którzy wysłali zapytanie o dołączenie do wydarzenia
     */
    @Query(value = "match (u:USER)-[:APPLIES_FOR_EVENT]->(e:EVENT {uuid: $eventUuid}) return u {.*}")
    List<UserNode> findEventRequests(@Param("eventUuid") String eventUuid);

    /**
     * Metoda dodająca nowe współrzędne geograficzne odwiedzone przez użytkownika w trakcie trwania wydarzenia.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param eventUuid globalny, unikalny identyfikator wydarzenia w systemie
     * @param pathPoints lista współrzędnych geograficznych
     */
    @Query(value = "match (u:USER {uuid: $userUuid})-[m:IS_EVENT_MEMBER]->(e:EVENT {uuid: $eventUuid}) " +
            "set m.pathPoints = case " +
            "when m.pathPoints is null then $pathPoints " +
            "else m.pathPoints + $pathPoints " +
            "end")
    void updatePathPoints(@Param("userUuid") String userUuid, @Param("eventUuid") String eventUuid, @Param("pathPoints") List<Double> pathPoints);

    /**
     * Metoda zwracająca listę współrzędnych geograficznych odwiedzonych przez użytkownika w trakcie trwania wydarzenia.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param eventUuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return list współrzędnych geograficznych, które użytkownik odwiedził w trakcie trwania wydarzenia
     */
    @Query(value = "match (u:USER {uuid: $userUuid})-[m:IS_EVENT_MEMBER]->(e:EVENT {uuid: $eventUuid}) " +
            " return m.pathPoints")
    List<Double> getPathPoints(@Param("userUuid") String userUuid, @Param("eventUuid") String eventUuid);

    /**
     * Metoda zwracająca węzły wydarzeń w grupie.
     *
     * @param groupUuid globalny, unikalny identyfikator grupy w systemie
     * @return lista węzłów wydarzeń założonych w grupie
     */
    @Query(value = "match (e: EVENT)-[:IS_GROUP_EVENT]->(g: GROUP {uuid: $groupUuid})" +
            " return e {.*}")
    List<EventNode> getGroupEvents(@Param("groupUuid") String groupUuid);

    /**
     * Metoda zwracająca wartość logiczną informującą czy użytkownik jest członkiem wydarzenia.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param eventUuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return wartość logiczna informująca czy użytkownik jest członkiem wydarzenia
     */
    @Query(value = "match (u: USER {uuid: $userUuid})-[r:IS_EVENT_MEMBER]->(e: EVENT {uuid: $eventUuid})" +
            " return count(r) > 0 as isMember")
    boolean isEventMember(@Param("userUuid") String userUuid, @Param("eventUuid") String eventUuid);

    /**
     * Metoda zwracająca listę węzłów użytkowników będących członkami wydarzenia.
     *
     * @param eventUuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return lista węzłów użytkowników będących członkami wydarzenia
     */
    @Query(value = "match (u: USER)-[:IS_EVENT_MEMBER]->(e: EVENT {uuid: $eventUuid})" +
            " return u {.*}")
    List<UserNode> findEventMembers(@Param("eventUuid") String eventUuid);

    /**
     * Metoda zwracająca wartość logiczną informującą czy użytkownik wysłał zaptanie o dołączenie do wydarzenia.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param eventUuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return wartość logiczna informująca czy użytkownik wysłał zapytanie o dołączenie do wydarzenia
     */
    @Query(value = "match (u: USER {uuid: $userUuid})-[r: APPLIES_FOR_EVENT]->(e: EVENT {uuid: $eventUuid})" +
            " return count(r) > 0 as isRequestSent")
    boolean isEventRequestSent(@Param("userUuid") String userUuid, @Param("eventUuid") String eventUuid);
}
