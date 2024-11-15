package com.socialtripper.restapi.repositories;

import com.socialtripper.restapi.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    @Query(value = "select l.id from languages l where l.name = :name;", nativeQuery = true)
    Optional<Long> findIdByName(String name);
}
