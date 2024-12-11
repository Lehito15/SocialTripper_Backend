package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repozytorium do zarządzania encjami wydarzeń {@link Event} w bazie Postgres.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    /**
     * Metoda zwracająca wydarzenie o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return opcjonale wydarzenie, jeżeli istnieje w bazie
     */
    Optional<Event> findByUuid(UUID uuid);

    /**
     * Metoda zwracjąca klucz głowny wydarzenia o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return opcjonalny klucz główny, jeżeli istnieje w bazie
     */
    @Query(value = "select e.id from events e where e.uuid = :uuid;", nativeQuery = true)
    Optional<Long> findIdByUUID(UUID uuid);

    /**
     * Metoda zwracająca listę wydarzeń, w których nazwie zawiera się podany łańcuch.
     *
     * @param eventName łańcuch nazwy wydarzenia
     * @return lista wydarzeń, których nazwa zawiera podany łańcuch
     */
    @Query(value = "select e from Event e " +
            "where e.name like %:event_name%")
    List<Event> getEventsByNameSubstring(@Param("event_name") String eventName);
}
