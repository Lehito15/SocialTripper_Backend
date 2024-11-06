package com.socialtripper.restapi.repositories;

import com.socialtripper.restapi.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByUuid(UUID uuid);

    @Query(value = "select g.id from groups g where g.uuid = :uuid;", nativeQuery = true)
    Optional<Long> findIdByUUID(@Param("uuid") UUID uuid);
}
