package com.socialtripper.restapi.repositories;

import com.socialtripper.restapi.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query(value = "select c.id from countries c where c.name = :name;", nativeQuery = true)
    Optional<Long> findIdByName(String name);
}
