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

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long> {
    @Query(
            "select e from Event e " +
                    "join EventParticipant ep on ep.event = e " +
                    "join ep.participant a on ep.participant = a " +
                    "where a.uuid = :userUuid and ep.leftAt is null order by e.eventStartTime desc"
    )
    List<Event> findUserEvents(@Param("userUuid") UUID uuid);

    @Query(
            "select e from Event e " +
                    "join EventParticipant ep on ep.event = e " +
                    "join ep.participant a " +
                    "where a.uuid = :userUuid and e.eventStartTime > current_timestamp and ep.leftAt is null order by e.eventStartTime desc"
    )
    List<Event> findUserUpcomingEvents(@Param("userUuid") UUID uuid);

    @Query(
            "select e from Event e" +
                    " join EventParticipant ep on ep.event = e " +
                    " join ep.participant a on ep.participant = a " +
                    " where a.uuid = :userUuid and e.eventEndTime < current_timestamp and ep.leftAt is null " +
                    " order by e.eventEndTime desc limit :eventsNumber"

    )
    List<Event> findUserAccomplishedEvents(@Param("userUuid") UUID uuid, @Param("eventsNumber") int eventsNumber);

    @Procedure(name = "userLeftEvent")
    void userLeftEvent(@Param("user_uuid") UUID userUUID, @Param("event_uuid") UUID eventUUID);
}
