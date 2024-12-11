package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.Group;
import com.socialtripper.restapi.entities.GroupParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

/**
 * Repozytorium do zarządzani członkami grupy {@link GroupParticipant} w bazie Postgres.
 */
@Repository
public interface GroupParticipantRepository extends JpaRepository<GroupParticipant, Long> {

    /**
     * Metoda wywołująca procedurę składowaną w bazie Postgres, powodującą opuszczenie grupy przez użytkownika.
     * Jest to osiągane poprzez ustawienie daty opuszczenia grupy na obecną datę.
     *
     * @param user_uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @param group_uuid globalny, unikalny identyfikator grupy w systemie
     */
    @Procedure(name = "userLeftGroup")
    void userLeftGroup(@Param("user_uuid") UUID user_uuid, @Param("group_uuid") UUID group_uuid);

    /**
     * Metoda zwracająca listę grup, których użytkownik jest aktualnym członkiem.
     * Odfiltrowywane są grupy, dla których wartość pola left_at jest różna od null.
     *
     * @param userUuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return lista grup, których użytkownik jest aktualnym członkiem
     */
    @Query("select g from Group g " +
            "join GroupParticipant gp on gp.group = g " +
            "join gp.participant a " +
            "where a.uuid = :userUuid " +
            "and gp.leftAt is null")
    List<Group> findUserCurrentGroups(@Param("userUuid") UUID userUuid);
}
