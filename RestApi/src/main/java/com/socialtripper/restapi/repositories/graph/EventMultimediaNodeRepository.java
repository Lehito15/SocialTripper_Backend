package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.EventMultimediaNode;
import com.socialtripper.restapi.nodes.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

/**
 * Repozytorium do zarządzania węzłami multimediów w wydarzeniu {@link EventMultimediaNode} w bazie Neo4j.
 */
@Repository
public interface EventMultimediaNodeRepository extends Neo4jRepository<EventMultimediaNode, String> {
    /**
     * Metoda zwracająca multimedia wygenerowane podczas wydarzenia.
     *
     * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
     * @return lista węzłów multimediów z wydarzenia
     */
    @Query(value = "match (em:EVENT_MULTIMEDIA)-[:IS_EVENT_MULTIMEDIA]->(e:EVENT)" +
            " where e.uuid = $eventUUID " +
            " return em {.*}")
    List<EventMultimediaNode> findEventMultimediaByUUID(@Param("eventUUID") UUID eventUUID);

    /**
     * Metoda zwracająca multimedia wygenerowane przez użytkownika podczas wydarzenia.
     *
     * @param userUUID globalny, unikalny identyfikator użytkownika w systemie
     * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
     * @return lista węzłów multimediów użytkownika z wydarzenia
     */
    @Query(value = "match (em:EVENT_MULTIMEDIA)-[:IS_EVENT_MULTIMEDIA]->(e:EVENT)," +
            " (em)-[:IS_PRODUCED_BY]->(u:USER)" +
            " where e.uuid = $eventUUID and u.uuid = $userUUID return em {.*}")
    List<EventMultimediaNode> findUserMultimediaInEvent(@Param("userUUID") UUID  userUUID, @Param("eventUUID") UUID eventUUID);

    /**
     * Metoda zwracająca autora multimedium.
     *
     * @param multimediaUuid globalny, unikalny identyfikator multimedium w systemie
     * @return węzeł użytkownika będącego autorem multimedium
     */
    @Query(value = "match (em:EVENT_MULTIMEDIA)-[:IS_PRODUCED_BY]->(u:USER) " +
            "where em.uuid = $multimediaUuid " +
            "return u {.*}")
    UserNode findMultimediaProducer(@Param("multimediaUuid") String  multimediaUuid);
}
