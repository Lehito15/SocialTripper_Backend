package com.socialtripper.restapi.repositories;

import com.socialtripper.restapi.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select * from find_post_by_uuid(:post_uuid);", nativeQuery = true)
    Optional<Post> findPostByUuid(@Param("post_uuid") UUID post_uuid);

    @Query(value = "select * from find_user_posts(:user_uuid);", nativeQuery = true)
    List<Post> findPostsByUserUUID(@Param("user_uuid") UUID user_uuid);

    @Query(value = "select * from find_personal_posts(:user_uuid)", nativeQuery = true)
    List<Post> findPersonalPostsByUUID(@Param("user_uuid") UUID user_uuid);

    @Query(value = "select * from find_group_posts(:group_uuid);", nativeQuery = true)
    List<Post> findPostsByGroupUUID(@Param("group_uuid") UUID group_uuid);

    @Query(value = "select * from find_event_posts(:event_uuid);", nativeQuery = true)
    List<Post> findPostsByEventUUID(@Param("event_uuid") UUID event_uuid);

    @Query(value = "select expire_user_posts(:user_uuid);", nativeQuery = true)
    int expirePostsByUserUUID(@Param("user_uuid") UUID user_uuid);

    @Query(value = "select lock_user_posts(:user_uuid);", nativeQuery = true)
    int lockPostsByUserUUID(@Param("user_uuid") UUID user_uuid);

    @Query(value = "select expire_post(:post_uuid);", nativeQuery = true)
    boolean expirePostByUUID(@Param("post_uuid") UUID post_uuid);

    @Query(value = "select e.id from events e where e.uuid = :uuid;", nativeQuery = true)
    Optional<Long> findIdByUUID(@Param("uuid") UUID uuid);

}
