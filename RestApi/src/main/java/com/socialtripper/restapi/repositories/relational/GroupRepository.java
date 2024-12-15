package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repozytorium do zarządzania encjami grup {@link Group} w bazie Postgres.
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    /**
     * Metoda zwracająca grupę o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @return opcjonalna grupa, jeżeli istnieje w bazie danych
     */
    Optional<Group> findByUuid(UUID uuid);

    /**
     * Metoda zwracająca klucz główny grupy o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @return opcjonalny klucz główny grupy, jeżeli istnieje w bazie danych
     */
    @Query(value = "select g.id from groups g where g.uuid = :uuid;", nativeQuery = true)
    Optional<Long> findIdByUUID(@Param("uuid") UUID uuid);
}
