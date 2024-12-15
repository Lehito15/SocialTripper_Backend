package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.GroupNode;
import com.socialtripper.restapi.nodes.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repozytrium do zarządzania węzłami grup {@link GroupNode} w bazie Neo4j.
 */
@Repository
public interface GroupNodeRepository extends Neo4jRepository<GroupNode, String> {
    /**
     * Metoda zwracająca węzeł grupy o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @return opcjanalny węzeł grupy, jeżeli istnieje w bazie danych
     */
    Optional<GroupNode> findGroupNodeByUuid(UUID uuid);

    /**
     * Metoda zwracająca wartość logiczną informujacą czy użytkownik jest członkiem grupy.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param groupUuid globalny, unikalny identyfikator grupy w systemie
     * @return wartość logiczna informująca czy użytkownik jest członkiem grupy
     */
    @Query(value = "match (u: USER)-[r: IS_GROUP_MEMBER]->(g: GROUP)" +
            " where u.uuid = $userUuid and g.uuid = $groupUuid" +
            " return count(r) > 0 as isGroupMember")
    boolean isUserInGroup(@Param("userUuid") String userUuid, @Param("groupUuid") String groupUuid);

    /**
     * Metoda tworząca relację przynależności użytkownika do grupy.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param groupUuid globalny, unikalny identyfikator grupy w systemie
     */
    @Query(value = "match (u: USER {uuid: $userUuid}), (g: GROUP {uuid: $groupUuid})" +
            " merge (u)-[:IS_GROUP_MEMBER]->(g)")
    void addUserToGroup(@Param("userUuid") String userUuid, @Param("groupUuid") String groupUuid);

    /**
     * Metoda usuwająca relację przynalezności użytkownika do grupy.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param groupUuid globalny, unikalny identyfikator grupy w systemie
     */
    @Query(value = "match (u:USER {uuid: $userUuid})-[r: IS_GROUP_MEMBER]->(g:GROUP {uuid: $groupUuid})" +
            " delete r ")
    void removeUserFromGroup(@Param("userUuid") String userUuid, @Param("groupUuid") String groupUuid);

    /**
     * Metoda tworząca relację zapytanie o dołączenie do grupy przez użytkownika.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param groupUuid globalny, unikalny identyfikator grupy w systemie
     */
    @Query(value = "match (u: USER {uuid: $userUuid}), (g: GROUP {uuid: $groupUuid})" +
            " merge (u)-[:APPLIES_FOR_GROUP]->(g)")
    void addUserApplyForGroup(@Param("userUuid") String userUuid, @Param("groupUuid") String groupUuid);

    /**
     * Metoda usuwająca relację zapytania o dołączenie do grupy przez użytkownika.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param groupUuid globalny, unikalny identyfikator grupy w systemie
     */
    @Query(value = "match (u:USER {uuid: $userUuid})-[r: APPLIES_FOR_GROUP]->(g:GROUP {uuid: $groupUuid})" +
            " delete r ")
    void removeUserApplyForGroup(@Param("userUuid") String userUuid, @Param("groupUuid") String groupUuid);

    /**
     * Metoda zwracająca listę węzłów użytkowników, którzy należą do grupy.
     *
     * @param groupUuid globalny, unikalny identyfikator grupy w systemie
     * @return lista węzłów użytkowników należących do grupy
     */
    @Query(value = "match (u: USER)-[:IS_GROUP_MEMBER]->(g: GROUP {uuid: $groupUuid})" +
            " return u {.*}")
    List<UserNode> findGroupMembers(@Param("groupUuid") String groupUuid);

    /**
     * Metoda zwracająca wartość logiczną informującą czy użytkownik wysłał zapytanie o dołączenie do grupy.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @param groupUuid globalny, unikalny identyfikator grupy w systemie
     * @return wartość logiczna informująca czy użytkownik wysłał zapytanie o dołączenie do grupy
     */
    @Query(value = "match (u: USER {uuid: $userUuid})-[r: APPLIES_FOR_GROUP]->(g: GROUP {uuid: $groupUuid})" +
            " return count(r) > 0 as isRequestSent")
    boolean isGroupRequestSent(@Param("userUuid") String userUuid, @Param("groupUuid") String groupUuid);

    /**
     * Metoda zwracająca listę węzłów użytkowników, którzy wysłali zapytanie o dołączenie do grupy.
     *
     * @param groupUuid globalny, unikalny identyfikator grupy w systemie
     * @return lista węzłów użytkowników, którzy wysłali zapytanie o dołączenie do grupy
     */
    @Query(value = "match (u:USER)-[:APPLIES_FOR_GROUP]->(g:GROUP {uuid: $groupUuid})" +
            " return u {.*}")
    List<UserNode> findGroupRequests(@Param("groupUuid") String groupUuid);
}
