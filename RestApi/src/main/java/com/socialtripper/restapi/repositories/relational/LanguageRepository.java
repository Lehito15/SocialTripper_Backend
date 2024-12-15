package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repozytorium do zarządzania encjami jęzka {@link Language} w bazie Postgres.
 */
@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    /**
     * Metoda zwracająca klucz główny dla języka o wskazanej nazwie.
     *
     * @param name nazwa języka
     * @return opcjonalny klucz główny języka, jeżeli istnieje w bazie danych
     */
    @Query(value = "select l.id from languages l where l.name = :name;", nativeQuery = true)
    Optional<Long> findIdByName(String name);
}
