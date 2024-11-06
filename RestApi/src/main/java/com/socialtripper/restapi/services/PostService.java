package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.EventPostDTO;
import com.socialtripper.restapi.dto.entities.GroupPostDTO;
import com.socialtripper.restapi.dto.entities.PostDTO;
import com.socialtripper.restapi.dto.messages.PostExpiredMessageDTO;
import com.socialtripper.restapi.dto.messages.UserPostsExpiredMessageDTO;
import com.socialtripper.restapi.dto.messages.UserPostsLockedMessageDTO;
import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostDTO> findAllPosts();
    PostDTO findPostByUUID(UUID uuid);
    List<PostDTO> findPostsByUserUUID(UUID uuid);
    PostDTO saveUserPost(PostDTO postDTO, UUID userUUID);
    GroupPostDTO saveGroupPost(PostDTO postDTO, UUID userUUID, UUID groupUUID);
    EventPostDTO saveEventPost(PostDTO postDTO, UUID userUUID, UUID eventUUID);
    PostExpiredMessageDTO expirePostByUUID(UUID uuid);

    List<PostDTO> findPersonalPostsByUserUUID(UUID uuid);

    List<PostDTO> findPostsByGroupUUID(UUID uuid);
    List<PostDTO> findPostsByEventUUID(UUID uuid);
    UserPostsExpiredMessageDTO expirePostsByUserUUID(UUID uuid);
    UserPostsLockedMessageDTO lockPostByUUID(UUID uuid);
}
