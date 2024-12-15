package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.EventPostDTO;
import com.socialtripper.restapi.dto.entities.GroupPostDTO;
import com.socialtripper.restapi.dto.entities.PostDTO;
import com.socialtripper.restapi.dto.messages.PostExpiredMessageDTO;
import com.socialtripper.restapi.dto.messages.UserPostsExpiredMessageDTO;
import com.socialtripper.restapi.dto.messages.UserPostsLockedMessageDTO;
import com.socialtripper.restapi.dto.messages.UserReactionToPostMessageDTO;
import com.socialtripper.restapi.entities.Post;
import com.socialtripper.restapi.nodes.PostNode;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

/**
 * Serwis zarządzający operacjami wykonywanymi na encjach postów.
 */
public interface PostService {
    /**
     * Metoda zwracająca referencję na encję postu o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator postu w systemie
     * @return referencja na encję postu
     */
    Post getPostReference(UUID uuid);

    /**
     * Metoda zwracająca wszystkie posty z bazy danych.
     *
     * @return lista data transfer object postów
     */
    List<PostDTO> findAllPosts();

    /**
     * Metoda zwracająca post o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator postu w systemie
     * @return data transfer object postu
     */
    PostDTO findPostByUUID(UUID uuid);

    /**
     * Metoda zwracająca encję nowo utworzonego postu.
     *
     * @param postDTO data transfer object postu
     * @param userUUID globalny, unikalny identyfikator konta użytkownika będącego autorem postu
     * @return encja nowo utworzonego postu
     */
    Post getNewPostWithReferences(PostDTO postDTO, UUID userUUID);

    /**
     * Metoda zwracająca węzeł postu o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator postu w systemie
     * @return węzeł postu
     */
    PostNode findPostNodeByUUID(UUID uuid);

    /**
     * Metoda zwracająca posty użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return lista data transfer object postów
     */
    List<PostDTO> findPostsByUserUUID(UUID uuid);

    /**
     * Metoda zapisująca post personalny użytkownika.
     *
     * @param postDTO data transfer object postu
     * @param multimedia pliki multimedialne dołączone do postu
     * @return data transfer object postu
     */
    PostDTO saveUserPost(PostDTO postDTO, MultipartFile[] multimedia);

    /**
     * Metoda zapisująca post grupowy.
     *
     * @param groupPostDTO data transfer object postu grupowego
     * @param multimedia pliki multimedialne dołączone do postu
     * @return data transfer object postu grupowego
     */
    GroupPostDTO saveGroupPost(GroupPostDTO groupPostDTO, MultipartFile[] multimedia);

    /**
     * Metoda zapisująca post w wydarzeniu.
     *
     * @param eventPostDTO data transfer object postu w wydarzeniu
     * @param multimedia pliki multimedialne dołączone do postu
     * @return data transfer object postu w wydarzeniu
     */
    EventPostDTO saveEventPost(EventPostDTO eventPostDTO, MultipartFile[] multimedia);

    /**
     * Metoda dezaktywująca post użytkownika na jego żądanie.
     *
     * @param uuid globalny, unikalny identyfikator postu w systemie
     * @return wiadomość o dezaktywacji postu
     */
    PostExpiredMessageDTO expirePostByUUID(UUID uuid);

    /**
     * Metoda zwracająca posty personalne użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return lista data transfer object postów
     */
    List<PostDTO> findPersonalPostsByUserUUID(UUID uuid);

    /**
     * Metoda zwracająca posty grupowe.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @return lista data transfer object postów
     */
    List<PostDTO> findPostsByGroupUUID(UUID uuid);

    /**
     * Metoda zwracająca posty w wydarzeniu.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return lista data transfer object postów
     */
    List<PostDTO> findPostsByEventUUID(UUID uuid);

    /**
     * Metoda dezaktywująca wszystkie posty użytkownika. Metoda wywoływana w przypadku dezaktywacji konta użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return wiadomość o dezaktywacji postów
     */
    UserPostsExpiredMessageDTO expirePostsByUserUUID(UUID uuid);

    /**
     * Metoda blokująca post. Metoda wykonywana na zgłoszonych postach, które zostały zweryfikowane jako nieodpowiednie.
     *
     * @param uuid globalny, unikalny identyfikator postu w systemie
     * @return wiadomość o zablokowaniu postu
     */
    UserPostsLockedMessageDTO lockPostByUUID(UUID uuid);

    /**
     * Metoda aktualizująca post.
     *
     * @param uuid globalny, unikalny identyfikator postu w systemie
     * @param postDTO data transfer object postu
     * @return data transfer object postu
     */
    PostDTO updatePost(UUID uuid, PostDTO postDTO);

    /**
     * Metoda tworząca relację reakcji na post.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param postUUID globalny, unikalny identyfikator postu w systemie
     * @return wiadomość o reakcji użytkownika na post
     */
    UserReactionToPostMessageDTO addUserReactionToPost(UUID userUUID, UUID postUUID);

    /**
     * Metoda usuwająca relację reakcji na post.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param postUUID globalny, unikalny identyfikator postu w systemie
     * @return wiadomość o usunięciu reakcji na post przez użytkownika
     */
    UserReactionToPostMessageDTO removeUserReactionToPost(UUID userUUID, UUID postUUID);

    /**
     * Metoda zwracająca posty użytkowników obserwowanych.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return lista data transfer object postów
     */
    List<PostDTO> findFollowedUsersPosts(UUID uuid);

    /**
     * Metoda zwracająca wartość logiczną informującą czy użytkownik zareagował na post.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param postUUID globalny, unikalny identyfikator postu w systemie
     * @return wartość logiczna informująca czy użytkownik zareagował na post
     */
    Boolean didUserReactToPost(UUID userUUID, UUID postUUID);

    /**
     * Metoda zwracająca aktualnie trendujące posty.
     *
     * @param numberOfPosts liczba postów do pobrania
     * @param daysBound liczba dni wstecz względem obecnej daty - dotyczy daty dodania postu
     * @return lista data transfer object postów
     */
    List<PostDTO> findTrendingPosts(int numberOfPosts, int daysBound);

    /**
     * Metoda aktualizująca liczbę komentarzy dotyczących postu.
     *
     * @param postUUID globalny, unikalny identyfikator postu w systemie
     */
    void addCommentToPost(UUID postUUID);
}
