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

/**
 * Implementacja serwisu zarządzającego operacjami na postach.
 */
@Service
public class PostServiceImpl implements PostService {
    /**
     * Repozytorium zarządzające encjami postów.
     */
    private final PostRepository postRepository;
    /**
     * Repozytorium zarządzające węzłami postów.
     */
    private final PostNodeRepository postNodeRepository;
    /**
     * Repozytorium zarządzające encjami postów personalnych.
     */
    private final PersonalPostRepository personalPostRepository;
    /**
     * Repozytorium zarządzające encjami postów grupowych.
     */
    private final GroupPostRepository groupPostRepository;
    /**
     * Repozytorium zarządzające encjami postów w wydarzeniach.
     */
    private final EventPostRepository eventPostRepository;
    /**
     * Komponent mapujący posty.
     */
    private final PostMapper postMapper;
    /**
     * Komponent mapujący posty grupowe.
     */
    private final GroupPostMapper groupPostMapper;
    /**
     * Komponent mapujący posty w wydarzeniach.
     */
    private final EventPostMapper eventPostMapper;
    /**
     * Serwis zarządzający operacjami wykonywanymi na kontach użytkowników.
     */
    private final AccountService accountService;
    /**
     * Serwis zarządzający operacjami wykonywanymi na użytkownikach.
     */
    private final UserService userService;
    /**
     * Serwis zarządzający operacjami wykonywanymi na multimediach.
     */
    private final MultimediaService multimediaService;
    /**
     * Serwis zarządzający operacjami wykonywanymi na grupach.
     */
    private final GroupService groupService;
    /**
     * Serwis zarządzający operacjami wykonywanymi na wydarzeniach.
     */
    private final EventService eventService;

    /**
     * Konstruktor wstrzykujący komponenty.
     *
     * @param postRepository repozytorium zarządzające encjami postów
     * @param personalPostRepository repozytorium zarządzające encjami postów personalnych
     * @param groupPostRepository repozytorium zarządzające encjami postów grupowych
     * @param eventPostRepository repozytorium zarządzające encjami postów ww wydarzeniach
     * @param postMapper komponent mapujący posty
     * @param accountService serwis zarządzający kontami użytkowników
     * @param groupService serwis zarządzający grupami
     * @param eventService serwis zarządzający wydarzeniami
     * @param groupPostMapper komponent mapujący posty grupowe
     * @param eventPostMapper komponent mapujący posty w wydarzeniach
     * @param postNodeRepository repozytorium zarządzające węzłami postów
     * @param multimediaService serwis zarządzający multimediami
     * @param userService serwis zarządzający użytkownikami
     */
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

    /**
     * {@inheritDoc}
     * <p>
     *     W przypadku gdy encja nie istnieje rzucany jest wyjątek {@link PostNotFoundException}.
     * </p>
     */
    @Override
    public Post getPostReference(UUID uuid) {
        return postRepository.getReferenceById(postRepository.findIdByUuid(uuid).orElseThrow(
                () -> new PostNotFoundException(uuid)
        ));
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     * <p>
     *     W przypadku gdy encja nie istnieje rzucany jest wyjątek {@link PostNotFoundException}.
     * </p>
     */
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

    /**
     * {@inheritDoc}
     * <p>
     *     W przypadku gdy encja nie istnieje rzucany jest wyjątek {@link PostNotFoundException}.
     * </p>
     */
    @Override
    public PostNode findPostNodeByUUID(UUID uuid) {
        return postNodeRepository.findPostNodeByUuid(uuid).orElseThrow(
                () -> new PostNotFoundException(uuid)
        );
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Post getNewPostWithReferences(PostDTO postDTO, UUID userUUID) {
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

    /**
     * Metoda zapisująca multimedia związane z postem.
     *
     * @param filename nazwa pliku
     * @param multimedia plik multimedium
     * @return zbiór url do plików multimedialnych w Azure Blob Storage
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public PostExpiredMessageDTO expirePostByUUID(UUID uuid) {
        boolean expirationResult = postRepository.expirePostByUUID(uuid);
        if (expirationResult) return new PostExpiredMessageDTO("Success on expiring post with uuid: " + uuid);
        return new PostExpiredMessageDTO("Failure in expiring post with uuid: " + uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPostsExpiredMessageDTO expirePostsByUserUUID(UUID uuid) {
        int postCount = postRepository.expirePostsByUserUUID(uuid);
        if (postCount != 0) return new UserPostsExpiredMessageDTO(
                "Success on expiring posts of user with uuid: " + uuid,
                postCount);
        return new UserPostsExpiredMessageDTO("No posts to expire.", postCount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPostsLockedMessageDTO lockPostByUUID(UUID uuid) {
        int postCount = postRepository.lockPostsByUserUUID(uuid);
        if (postCount != 0) return new UserPostsLockedMessageDTO(
                "Success on locking posts of user with uuid: " + uuid,
                postCount);
        return new UserPostsLockedMessageDTO("No posts to lock.", postCount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PostDTO updatePost(UUID uuid, PostDTO postDTO) {
        Post postToUpdate = postMapper.copyNonNullValues(
                getPostReference(uuid),
                postDTO
        );
        return postMapper.toDTO(postRepository.save(postToUpdate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserReactionToPostMessageDTO addUserReactionToPost(UUID userUUID, UUID postUUID) {
        if (!didUserReactToPost(userUUID, postUUID)) {
            postNodeRepository.addPostReaction(
                    userUUID.toString(),
                    postUUID.toString()
            );

            Post post = postRepository.findByUuid(postUUID).orElseThrow(() ->
                    new PostNotFoundException(postUUID));
            post.setReactionsNumber(post.getReactionsNumber() + 1);
            postRepository.save(post);
        }
        return new UserReactionToPostMessageDTO(
                userUUID,
                postUUID,
                "user reacted to post"
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserReactionToPostMessageDTO removeUserReactionToPost(UUID userUUID, UUID postUUID) {
        if(didUserReactToPost(userUUID, postUUID)) {
            postNodeRepository.removePostReaction(
                    userUUID.toString(),
                    postUUID.toString()
            );

            Post post = postRepository.findByUuid(postUUID).orElseThrow(() ->
                    new PostNotFoundException(postUUID));
            post.setReactionsNumber(post.getReactionsNumber() - 1);
            postRepository.save(post);
        }
        return new UserReactionToPostMessageDTO(
                userUUID,
                postUUID,
                "user removed reaction from post"
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PostDTO> findFollowedUsersPosts(UUID uuid) {
        return postNodeRepository.findFollowedUsersPosts(uuid.toString())
                .stream()
                .map(post ->
                        findPostByUUID(post.getUuid()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean didUserReactToPost(UUID userUUID, UUID postUUID) {
        return postNodeRepository.didUserReact(
                userUUID.toString(),
                postUUID.toString()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PostDTO> findTrendingPosts(int numberOfPosts, int daysBound) {
        return postRepository.findTrendingPosts(numberOfPosts, LocalDateTime.now().minusDays(daysBound))
                .stream()
                .map(post ->
                        postMapper.toDTO(
                                post,
                                findPostNodeByUUID(post.getUuid()))
                )
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCommentToPost(UUID postUUID) {
        Post post = postRepository.findByUuid(postUUID).orElseThrow(() ->
                new PostNotFoundException(postUUID));
        post.setCommentsNumber(post.getCommentsNumber() + 1);
        postRepository.save(post);
    }

    /**
     * Metoda zapisująca encję postu z bazy relacyjnej jako węzeł w bazie grafowej.
     *
     * @param post encja postu
     * @param multimediaUrls zbiór url do multimediów postu
     * @param groupUUID globalny, unikalny identyfikator grupy w systemie - null jeżeli post nie jest grupowy
     * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie - null jeżeli post nie jest postem w wydarzeniu
     * @return węzeł postu
     */
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
