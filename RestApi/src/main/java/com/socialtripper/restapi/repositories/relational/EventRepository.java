package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByUuid(UUID uuid);

    @Query(value = "select e.id from events e where e.uuid = :uuid;", nativeQuery = true)
    Optional<Long> findIdByUUID(UUID uuid);

}
