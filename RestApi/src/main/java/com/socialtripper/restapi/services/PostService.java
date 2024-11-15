package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.EventPostDTO;
import com.socialtripper.restapi.dto.entities.GroupPostDTO;
import com.socialtripper.restapi.dto.entities.PostDTO;
import com.socialtripper.restapi.dto.messages.PostExpiredMessageDTO;
import com.socialtripper.restapi.dto.messages.UserPostsExpiredMessageDTO;
import com.socialtripper.restapi.dto.messages.UserPostsLockedMessageDTO;
import com.socialtripper.restapi.entities.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post getPostReference(UUID uuid);
    List<PostDTO> findAllPosts();
    PostDTO findPostByUUID(UUID uuid);
    List<PostDTO> findPostsByUserUUID(UUID uuid);
    PostDTO saveUserPost(PostDTO postDTO);
    GroupPostDTO saveGroupPost(GroupPostDTO groupPostDTO);
    EventPostDTO saveEventPost(EventPostDTO eventPostDTO);
    PostExpiredMessageDTO expirePostByUUID(UUID uuid);
    List<PostDTO> findPersonalPostsByUserUUID(UUID uuid);
    List<PostDTO> findPostsByGroupUUID(UUID uuid);
    List<PostDTO> findPostsByEventUUID(UUID uuid);
    UserPostsExpiredMessageDTO expirePostsByUserUUID(UUID uuid);
    UserPostsLockedMessageDTO lockPostByUUID(UUID uuid);
    PostDTO updatePost(UUID uuid, PostDTO postDTO);
}
