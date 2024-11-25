package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.GroupNode;
import com.socialtripper.restapi.nodes.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupNodeRepository extends Neo4jRepository<GroupNode, String> {
    Optional<GroupNode> findGroupNodeByUuid(UUID uuid);

    @Query(value = "match (u: USER)-[r: IS_GROUP_MEMBER]->(g: GROUP)" +
            " where u.uuid = $userUuid and g.uuid = $groupUuid" +
            " return count(r) > 0 as isGroupMember")
    boolean isUserInGroup(@Param("userUuid") String userUuid, @Param("groupUuid") String groupUuid);

    @Query(value = "match (u: USER {uuid: $userUuid}), (g: GROUP {uuid: $groupUuid})" +
            " create (u)-[:IS_GROUP_MEMBER]->(g)")
    void addUserToGroup(@Param("userUuid") String userUuid, @Param("groupUuid") String groupUuid);

    @Query(value = "match (u:USER {uuid: $userUuid})-[r: IS_GROUP_MEMBER]->(g:GROUP {uuid: $groupUuid})" +
            " delete r ")
    void removeUserFromGroup(@Param("userUuid") String userUuid, @Param("groupUuid") String groupUuid);

    @Query(value = "match (u: USER {uuid: $userUuid}), (g: GROUP {uuid: $groupUuid})" +
            " create (u)-[:APPLIES_FOR_GROUP]->(g)")
    void addUserApplyForGroup(@Param("userUuid") String userUuid, @Param("groupUuid") String groupUuid);

    @Query(value = "match (u:USER {uuid: $userUuid})-[r: APPLIES_FOR_GROUP]->(g:GROUP {uuid: $groupUuid})" +
            " delete r ")
    void removeUserApplyForGroup(@Param("userUuid") String userUuid, @Param("groupUuid") String groupUuid);

    @Query(value = "match (u: USER)-[:IS_GROUP_MEMBER]->(g: GROUP {uuid: $groupUuid})" +
            " return u {.*}")
    List<UserNode> findGroupMembers(@Param("groupUuid") String groupUuid);

    @Query(value = "match (u: USER {uuid: $userUuid})-[r: APPLIES_FOR_GROUP]->(g: GROUP {uuid: $groupUuid})" +
            " return count(r) > 0 as isRequestSent")
    boolean isGroupRequestSent(@Param("userUuid") String userUuid, @Param("groupUuid") String groupUuid);
}
