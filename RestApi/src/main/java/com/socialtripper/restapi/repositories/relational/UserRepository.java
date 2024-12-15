package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

/**
 * Repozytorium do zarządzania encjami użytkowników {@link User} w bazie Postgres.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Metoda zwracająca użytkownika o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @return opcjonalny użytkownik, jeżeli istnieje w systemie
     */
    Optional<User> findByUuid(UUID uuid);

    /**
     * Metoda zwracająca klucz główny użytkownika o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @return opcjonalny klucz główny, jeżeli istnieje w systemie
     */
    @Query("select u.id from User u where u.uuid = :userUuid")
    Optional<Long> findIdByUuid(@Param("userUuid") UUID uuid);
}
