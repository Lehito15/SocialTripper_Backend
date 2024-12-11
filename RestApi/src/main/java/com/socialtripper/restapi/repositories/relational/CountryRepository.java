package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repozytorium do zarządzania encjami krajów {@link Country} w bazie Postgres.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    /**
     * Metoda zwracająca klucz główny rekordu kraju o podanej nazwie.
     *
     * @param name nazwa kraju
     * @return opcjonalny klucz główny kraju o podanej nazwie, jeżeli istnieje w bazie danych
     */
    @Query(value = "select c.id from countries c where c.name = :name;", nativeQuery = true)
    Optional<Long> findIdByName(String name);
}
