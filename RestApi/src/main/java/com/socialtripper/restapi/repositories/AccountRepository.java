package com.socialtripper.restapi.repositories;

import com.socialtripper.restapi.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUuid(UUID uuid);

    @Query(value = "select a.id from accounts a WHERE a.uuid = :uuid", nativeQuery = true)
    Optional<Long> findIdByUUID(@Param("uuid") UUID uuid);
}
