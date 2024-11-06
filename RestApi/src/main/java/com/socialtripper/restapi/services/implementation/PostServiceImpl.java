package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.dto.entities.EventPostDTO;
import com.socialtripper.restapi.dto.entities.GroupPostDTO;
import com.socialtripper.restapi.dto.entities.PostDTO;
import com.socialtripper.restapi.dto.messages.PostExpiredMessageDTO;
import com.socialtripper.restapi.dto.messages.UserPostsExpiredMessageDTO;
import com.socialtripper.restapi.dto.messages.UserPostsLockedMessageDTO;
import com.socialtripper.restapi.entities.*;
import com.socialtripper.restapi.exceptions.PostNotFoundException;
import com.socialtripper.restapi.mappers.*;
import com.socialtripper.restapi.repositories.*;
import com.socialtripper.restapi.services.AccountService;
import com.socialtripper.restapi.services.EventService;
import com.socialtripper.restapi.services.GroupService;
import com.socialtripper.restapi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PersonalPostRepository personalPostRepository;
    private final GroupPostRepository groupPostRepository;
    private final EventPostRepository eventPostRepository;
    private final AccountMapper accountMapper;
    private final PostMapper postMapper;
    private final GroupPostMapper groupPostMapper;
    private final EventPostMapper eventPostMapper;
    private final GroupMapper groupMapper;
    private final EventMapper eventMapper;
    private final AccountService accountService;
    private final GroupService groupService;
    private final EventService eventService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           PersonalPostRepository personalPostRepository,
                           GroupPostRepository groupPostRepository,
                           EventPostRepository eventPostRepository,
                           PostMapper postMapper,
                           PostMultimediaMapper postMultimediaMapper,
                           AccountService accountService,
                           GroupService groupService,
                           EventService eventService,
                           AccountMapper accountMapper,
                           GroupPostMapper groupPostMapper,
                           EventPostMapper eventPostMapper,
                           GroupMapper groupMapper,
                           EventMapper eventMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.personalPostRepository = personalPostRepository;
        this.groupPostRepository = groupPostRepository;
        this.eventPostRepository = eventPostRepository;
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.groupPostMapper = groupPostMapper;
        this.eventPostMapper = eventPostMapper;
        this.eventService = eventService;
        this.groupService = groupService;
        this.eventMapper = eventMapper;
        this.groupMapper = groupMapper;
    }

    @Override
    public List<PostDTO> findAllPosts() {
        return postRepository.findAll()
                .stream().map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO findPostByUUID(UUID uuid) {
        return postMapper.toDTO(postRepository.findPostByUuid(uuid)
                                                .orElseThrow(
                                                         () -> new PostNotFoundException(uuid)
        ));
    }

    @Override
    public List<PostDTO> findPostsByUserUUID(UUID uuid) {
        return postRepository.findPostsByUserUUID(uuid).stream()
                .map(postMapper::toDTO)
                .toList();
    }

    @Override
    public List<PostDTO> findPersonalPostsByUserUUID(UUID uuid) {
        return postRepository.findPersonalPostsByUUID(uuid)
                .stream()
                .map(postMapper::toDTO)
                .toList();
    }

    @Override
    public List<PostDTO> findPostsByGroupUUID(UUID uuid) {
        return postRepository.findPostsByGroupUUID(uuid)
                .stream()
                .map(postMapper::toDTO)
                .toList();
    }

    @Override
    public List<PostDTO> findPostsByEventUUID(UUID uuid) {
        return postRepository.findPostsByEventUUID(uuid)
                .stream()
                .map(postMapper::toDTO)
                .toList();
    }

    private Post getPostWithPublisher(PostDTO postDTO, UUID userUUID) {
        Post postToSave = postMapper.toEntity(postDTO);
        Account publisherAccountReference = accountService.getAccountReference(userUUID);
        postToSave.setAccount(publisherAccountReference);
        return postToSave;
    }

    @Override
    public PostDTO saveUserPost(PostDTO postDTO, UUID userUUID) {
        Post postToSave = getPostWithPublisher(postDTO, userUUID);
        PersonalPost personalPost = new PersonalPost(postToSave);
        return postMapper.toDTO(personalPostRepository.save(personalPost).getPost());
    }

    @Override
    public GroupPostDTO saveGroupPost(PostDTO postDTO, UUID userUUID, UUID groupUUID) {
        Post postToSave = getPostWithPublisher(postDTO, userUUID);
        GroupPost groupPost = new GroupPost(postToSave, groupService.getGroupReference(groupUUID));
        return groupPostMapper.toDTO(groupPostRepository.save(groupPost));
    }

    @Override
    public EventPostDTO saveEventPost(PostDTO postDTO, UUID userUUID, UUID eventUUID) {
        Post postToSave = getPostWithPublisher(postDTO, userUUID);
        EventPost eventPost = new EventPost(postToSave, eventService.getEventReference(eventUUID));
        return eventPostMapper.toDTO(eventPostRepository.save(eventPost));
    }

    @Override
    public PostExpiredMessageDTO expirePostByUUID(UUID uuid) {
        boolean expirationResult = postRepository.expirePostByUUID(uuid);
        if (expirationResult) return new PostExpiredMessageDTO("Success on expiring post with uuid: " + uuid);
        return new PostExpiredMessageDTO("Failure in expiring post with uuid: " + uuid);
    }

    @Override
    public UserPostsExpiredMessageDTO expirePostsByUserUUID(UUID uuid) {
        int postCount = postRepository.expirePostsByUserUUID(uuid);
        if (postCount != 0) return new UserPostsExpiredMessageDTO(
                "Success on expiring posts of user with uuid: " + uuid,
                postCount);
        return new UserPostsExpiredMessageDTO("No posts to expire.", postCount);
    }

    @Override
    public UserPostsLockedMessageDTO lockPostByUUID(UUID uuid) {
        int postCount = postRepository.lockPostsByUserUUID(uuid);
        if (postCount != 0) return new UserPostsLockedMessageDTO(
                "Success on locking posts of user with uuid: " + uuid,
                postCount);
        return new UserPostsLockedMessageDTO("No posts to lock.", postCount);
    }


}
