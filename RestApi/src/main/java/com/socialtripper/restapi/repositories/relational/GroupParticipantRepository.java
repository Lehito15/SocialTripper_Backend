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

@Repository
public interface GroupParticipantRepository extends JpaRepository<GroupParticipant, Long> {

    @Procedure(name = "userLeftGroup")
    void userLeftGroup(@Param("user_uuid") UUID user_uuid, @Param("group_uuid") UUID group_uuid);

    @Query("select g from Group g " +
            "join GroupParticipant gp on gp.group = g " +
            "join gp.participant a " +
            "where a.uuid = :userUuid " +
            "and gp.leftAt is null")
    List<Group> findUserCurrentGroups(@Param("userUuid") UUID userUuid);
}
