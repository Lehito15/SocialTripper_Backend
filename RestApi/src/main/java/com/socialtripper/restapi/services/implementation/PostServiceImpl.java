package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.dto.entities.EventPostDTO;
import com.socialtripper.restapi.dto.entities.GroupPostDTO;
import com.socialtripper.restapi.dto.entities.PostDTO;
import com.socialtripper.restapi.dto.messages.PostExpiredMessageDTO;
import com.socialtripper.restapi.dto.messages.UserPostsExpiredMessageDTO;
import com.socialtripper.restapi.dto.messages.UserPostsLockedMessageDTO;
import com.socialtripper.restapi.dto.messages.UserReactionToPostMessageDTO;
import com.socialtripper.restapi.entities.*;
import com.socialtripper.restapi.exceptions.PostNotFoundException;
import com.socialtripper.restapi.mappers.*;
import com.socialtripper.restapi.nodes.PostNode;
import com.socialtripper.restapi.repositories.graph.PostNodeRepository;
import com.socialtripper.restapi.repositories.relational.EventPostRepository;
import com.socialtripper.restapi.repositories.relational.GroupPostRepository;
import com.socialtripper.restapi.repositories.relational.PersonalPostRepository;
import com.socialtripper.restapi.repositories.relational.PostRepository;
import com.socialtripper.restapi.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostNodeRepository postNodeRepository;
    private final PersonalPostRepository personalPostRepository;
    private final GroupPostRepository groupPostRepository;
    private final EventPostRepository eventPostRepository;
    private final PostMapper postMapper;
    private final GroupPostMapper groupPostMapper;
    private final EventPostMapper eventPostMapper;
    private final AccountService accountService;
    private final UserService userService;
    private final MultimediaService multimediaService;
    private final GroupService groupService;
    private final EventService eventService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           PersonalPostRepository personalPostRepository,
                           GroupPostRepository groupPostRepository,
                           EventPostRepository eventPostRepository,
                           PostMapper postMapper,
                           AccountService accountService,
                           GroupService groupService,
                           EventService eventService,
                           GroupPostMapper groupPostMapper,
                           EventPostMapper eventPostMapper,
                           PostNodeRepository postNodeRepository,
                           MultimediaService multimediaService,
                           UserService userService) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.personalPostRepository = personalPostRepository;
        this.groupPostRepository = groupPostRepository;
        this.eventPostRepository = eventPostRepository;
        this.accountService = accountService;
        this.groupPostMapper = groupPostMapper;
        this.eventPostMapper = eventPostMapper;
        this.eventService = eventService;
        this.groupService = groupService;
        this.postNodeRepository = postNodeRepository;
        this.multimediaService = multimediaService;
        this.userService = userService;
    }

    @Override
    public Post getPostReference(UUID uuid) {
        return postRepository.getReferenceById(postRepository.findIdByUuid(uuid).orElseThrow(
                () -> new PostNotFoundException(uuid)
        ));
    }

    @Override
    public List<PostDTO> findAllPosts() {
        return postRepository.findAll()
                .stream().map(post -> postMapper.toDTO(
                        post,
                        postNodeRepository.findPostNodeByUuid((
                                post.getUuid())).orElseThrow(
                                        () -> new PostNotFoundException(post.getUuid())
                        ))
                ).toList();
    }

    @Override
    public PostDTO findPostByUUID(UUID uuid) {;
        return postMapper.toDTO(
                postRepository.findByUuid(uuid).orElseThrow(
                        () -> new PostNotFoundException(uuid)
                ),
                postNodeRepository.findPostNodeByUuid(uuid).orElseThrow(
                        () -> new PostNotFoundException(uuid))
                );
    }

    @Override
    public PostNode findPostNodeByUUID(UUID uuid) {
        return postNodeRepository.findPostNodeByUuid(uuid).orElseThrow(
                () -> new PostNotFoundException(uuid)
        );
    }

    @Override
    public List<PostDTO> findPostsByUserUUID(UUID uuid) {
        return postRepository.findPostsByUserUUID(uuid).stream()
                .map(post -> postMapper.toDTO(
                        post,
                        postNodeRepository.findPostNodeByUuid(post.getUuid()).orElseThrow(
                                () -> new PostNotFoundException(post.getUuid())
                        )))
                .toList();
    }

    @Override
    public List<PostDTO> findPersonalPostsByUserUUID(UUID uuid) {
        return postRepository.findPersonalPostsByUUID(uuid)
                .stream()
                .map(post -> postMapper.toDTO(
                        post,
                        postNodeRepository.findPostNodeByUuid(post.getUuid()).orElseThrow(
                                () -> new PostNotFoundException(post.getUuid())
                        )))
                .toList();
    }

    @Override
    public List<PostDTO> findPostsByGroupUUID(UUID uuid) {
        return postRepository.findPostsByGroupUUID(uuid)
                .stream()
                .map(post -> postMapper.toDTO(
                        post,
                        postNodeRepository.findPostNodeByUuid((post.getUuid())).orElseThrow(
                                () -> new PostNotFoundException(post.getUuid()))
                        ))
                .toList();
    }

    @Override
    public List<PostDTO> findPostsByEventUUID(UUID uuid) {
        return postRepository.findPostsByEventUUID(uuid)
                .stream()
                .map(post -> postMapper.toDTO(
                        post,
                        postNodeRepository.findPostNodeByUuid(post.getUuid()).orElseThrow(
                                () -> new PostNotFoundException(post.getUuid())
                        )))
                .toList();
    }

    private Post getNewPostWithReferences(PostDTO postDTO, UUID userUUID) {
        Post post = postMapper.toEntity(postDTO);
        post.setUuid(UUID.randomUUID());
        post.setDateOfPost(LocalDateTime.now());
        post.setExpired(false);
        post.setLocked(false);
        post.setCommentsNumber(0);
        post.setCommentsNumber(0);
        post.setAccount(accountService.getAccountReference(userUUID));
        return post;
    }

    private Set<String> uploadPostMultimedia(String filename, MultipartFile[] multimedia) {
        Set<String> multimediaUrls = new HashSet<>();
        if (multimedia != null) {
            for(MultipartFile multimediaFile : multimedia) {
                try {
                    multimediaUrls.add(multimediaService.uploadMultimedia(
                            multimediaFile,
                            filename + UUID.randomUUID()
                    ));
                } catch(IOException e){
                    System.err.println(e.getMessage());
                }
            }
        }
        return multimediaUrls;
    }

    @Override
    public PostDTO saveUserPost(PostDTO postDTO, MultipartFile[] multimedia) {
        Post postToSave = getNewPostWithReferences(postDTO, postDTO.account().uuid());
        PersonalPost personalPost = new PersonalPost(postToSave);
        PersonalPost savedPost = personalPostRepository.save(personalPost);

        Set<String> multimediaUrls = uploadPostMultimedia(
                "post/" + savedPost.getPost().getUuid() + "/",
                multimedia);
        PostNode savedPostNode = saveInGraphDB(savedPost.getPost(), multimediaUrls,
                null, null);
        return postMapper.toDTO(savedPost.getPost(), savedPostNode);
    }

    @Override
    public GroupPostDTO saveGroupPost(GroupPostDTO groupPostDTO, MultipartFile[] multimedia) {
        Post postToSave = getNewPostWithReferences(groupPostDTO.post(), groupPostDTO.post().account().uuid());
        GroupPost groupPost = new GroupPost(postToSave, groupService.getGroupReference(groupPostDTO.group().uuid()));
        GroupPost savedPost = groupPostRepository.save(groupPost);

        Set<String> multimediaUrls = uploadPostMultimedia(
                "groups/" + savedPost.getGroup().getUuid() +
                        "/posts" + savedPost.getPost().getUuid() + "/",
                multimedia
        );
        saveInGraphDB(savedPost.getPost(), multimediaUrls,
                savedPost.getGroup().getUuid(), null);
        return groupPostMapper.toDTO(savedPost);
    }

    @Override
    public EventPostDTO saveEventPost(EventPostDTO eventPostDTO, MultipartFile[] multimedia) {
        Post postToSave = getNewPostWithReferences(eventPostDTO.post(), eventPostDTO.post().account().uuid());
        EventPost eventPost = new EventPost(postToSave, eventService.getEventReference(eventPostDTO.event().uuid()));
        EventPost savedPost = eventPostRepository.save(eventPost);

        Set<String> multimediaUrls = uploadPostMultimedia(
                "events/" + savedPost.getEvent().getUuid() +
                        "/posts" + savedPost.getPost().getUuid()+ "/",
                multimedia);
        saveInGraphDB(savedPost.getPost(), multimediaUrls,
                null, savedPost.getEvent().getUuid());
        return eventPostMapper.toDTO(
                savedPost,
                eventService.findEventNodeByUUID(eventPostDTO.event().uuid()));
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

    @Override
    public PostDTO updatePost(UUID uuid, PostDTO postDTO) {
        Post postToUpdate = postMapper.copyNonNullValues(
                getPostReference(uuid),
                postDTO
        );
        return postMapper.toDTO(postRepository.save(postToUpdate));
    }

    @Override
    public UserReactionToPostMessageDTO addUserReactionToPost(UUID userUUID, UUID postUUID) {
        postNodeRepository.addPostReaction(
                userUUID.toString(),
                postUUID.toString()
        );

        return new UserReactionToPostMessageDTO(
                userUUID,
                postUUID,
                "user reacted to post"
        );
    }

    @Override
    public UserReactionToPostMessageDTO removeUserReactionToPost(UUID userUUID, UUID postUUID) {
        postNodeRepository.removePostReaction(
                userUUID.toString(),
                postUUID.toString()
        );
        return new UserReactionToPostMessageDTO(
                userUUID,
                postUUID,
                "user removed reaction from post"
        );
    }

    @Override
    public List<PostDTO> findFollowedUsersPosts(UUID uuid) {
        return postNodeRepository.findFollowedUsersPosts(uuid.toString())
                .stream()
                .map(post ->
                        findPostByUUID(post.getUuid()))
                .toList();
    }

    @Override
    public Boolean didUserReactToPost(UUID userUUID, UUID postUUID) {
        return postNodeRepository.didUserReact(
                userUUID.toString(),
                postUUID.toString()
        );
    }


    private PostNode saveInGraphDB(Post post, Set<String> multimediaUrls,
                                   UUID groupUUID, UUID eventUUID) {
        PostNode postToSave = postMapper.toNode(post);
        postToSave.setMultimediaUrls(multimediaUrls);
        postToSave.setAuthor(
                userService.findUserNodeByUUID(
                        post.getAccount().getUuid()));
        if (groupUUID != null) {
            postToSave.setGroup(
                    groupService.findGroupNodeByUUID(groupUUID));
        }
        if (eventUUID != null) {
            postToSave.setEvent(
                    eventService.findEventNodeByUUID(eventUUID)
            );
        }
        return postNodeRepository.save(postToSave);
    }

}
