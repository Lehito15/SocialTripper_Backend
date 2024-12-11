package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.Event;
import com.socialtripper.restapi.entities.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

/**
 * Repozytorium do zarządzania encjami uczestników wydarzeń {@link EventParticipant} w bazie Postgres.
 */
@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long> {
    /**
     * Metoda zwracająca listę wydarzeń, których użytkownik jest członkiem.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return lista wydarzeń, których użytkownik jest członkiem
     */
    @Query(
            "select e from Event e " +
                    "join EventParticipant ep on ep.event = e " +
                    "join ep.participant a on ep.participant = a " +
                    "where a.uuid = :userUuid and ep.leftAt is null order by e.eventStartTime desc"
    )
    List<Event> findUserEvents(@Param("userUuid") UUID uuid);

    /**
     * Metoda zwracająca listę nadchodzących wydarzeń użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return lista nadchodzących wydarzeń
     */
    @Query(
            "select e from Event e " +
                    "join EventParticipant ep on ep.event = e " +
                    "join ep.participant a " +
                    "where a.uuid = :userUuid and e.eventStartTime > current_timestamp and ep.leftAt is null order by e.eventStartTime desc"
    )
    List<Event> findUserUpcomingEvents(@Param("userUuid") UUID uuid);

    /**
     * Metoda zwracająca listę ukończonych ostatnio wydarzeń. Liczba pobieranych wydarzeń jest parametryzowana.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @param eventsNumber liczba wydarzeń do pobrania
     * @return lista ostatnio ukończonych przez użytkownika wydarzeń
     */
    @Query(
            "select e from Event e" +
                    " join EventParticipant ep on ep.event = e " +
                    " join ep.participant a on ep.participant = a " +
                    " where a.uuid = :userUuid and e.eventEndTime < current_timestamp and ep.leftAt is null " +
                    " order by e.eventEndTime desc limit :eventsNumber"

    )
    List<Event> findUserAccomplishedEvents(@Param("userUuid") UUID uuid, @Param("eventsNumber") int eventsNumber);

    /**
     * Metoda wywołująca procedurę składowaną w bazie Postgres, odnotowującej opuszczenie wydarzenia przez użytkownika.
     * Jest to osiągane poprzez ustawienie daty opuszczenia wydarzenia na obecną datę.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
     */
    @Procedure(name = "userLeftEvent")
    void userLeftEvent(@Param("user_uuid") UUID userUUID, @Param("event_uuid") UUID eventUUID);
}
