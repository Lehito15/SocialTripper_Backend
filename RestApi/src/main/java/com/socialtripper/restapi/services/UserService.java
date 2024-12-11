package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.FollowDTO;
import com.socialtripper.restapi.dto.entities.UserActivityDTO;
import com.socialtripper.restapi.dto.entities.UserDTO;
import com.socialtripper.restapi.dto.entities.UserLanguageDTO;
import com.socialtripper.restapi.dto.messages.UserEndsFollowingMessageDTO;
import com.socialtripper.restapi.dto.messages.UserStartsFollowingMessageDTO;
import com.socialtripper.restapi.dto.requests.UserRequestFollowDTO;
import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.entities.User;
import com.socialtripper.restapi.entities.UserActivity;
import com.socialtripper.restapi.entities.UserLanguage;
import com.socialtripper.restapi.nodes.UserNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Serwis zarządzający operacjami wykonywanymi na encji użytkownika.
 */
public interface UserService {
    /**
     * Metoda zwracająca użytkownika o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @return data transfer object użytkownika
     */
    UserDTO findUserByUUID(UUID uuid);

    /**
     * Metoda tworząca nowego użytkownika w aplikacji.
     *
     * @param userDTO data transfer object użytkownika
     * @param profilePicture plik zdjęcia profilowego dla konta użytkownika
     * @return data transfer object nowego użytkownika aplikacji
     */
    UserDTO createUser(UserDTO userDTO, MultipartFile profilePicture);

    /**
     * Metoda aktualizująca użytkownika. Metoda aktualizuje wartości niezwiązane z encjami agregowanymi przez encję
     * użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @param userDTO data transfer object użytkownika
     * @return data transfer object użytkownika
     */
    UserDTO updateUser(UUID uuid, UserDTO userDTO);

    /**
     * Metoda zwracająca referencję na encję użytkownika w bazie danych.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @return referencja na encję użytkownika w bazie danych
     */
    User getUserReference(UUID uuid);

    /**
     * Metoda tworząca encję nowego użytkownika wraz z referencjami do encji agregowanych.
     *
     * @param userDTO data transfer object użytkownika
     * @param profilePicture plik zdjęcia profilowego dla konta użytkownika
     * @return encja nowego użytkownika
     */
    User getNewUserWithReferences(UserDTO userDTO, MultipartFile profilePicture);

    /**
     * Metoda dodająca aktywność użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @param userActivityDTO data transfer object aktywności użytkownika
     * @return encja aktywności użytkownika
     */
    UserActivity addActivity(UUID uuid, UserActivityDTO userActivityDTO);

    /**
     * Metoda dodająca język użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @param userLanguageDto data transfer object języka użytkownika
     * @return encja języka użytkownika
     */
    UserLanguage addLanguage(UUID uuid, UserLanguageDTO userLanguageDto);

    /**
     * Metoda aktualizująca aktywność użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @param userActivityDTO data transfer object aktywności użytkownika
     * @return encja aktywności użytkownika
     */
    UserActivityDTO setUserActivity(UUID uuid, UserActivityDTO userActivityDTO);

    /**
     * Metoda aktualizująca język użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @param userLanguageDTO data transfer object języka użytkownika
     * @return encja języka użytkownika
     */
    UserLanguageDTO setUserLanguage(UUID uuid, UserLanguageDTO userLanguageDTO);

    /**
     * Metoda zwracająca węzeł użytkownika o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @return węzeł użytkownika
     */
    UserNode findUserNodeByUUID(UUID uuid);

    /**
     * Metoda tworząca relację zapytania o obserwację użytkownika.
     *
     * @param followDTO data transfer object obserwacji
     * @return wiadomość o zapytaniu o obserwację
     */
    UserRequestFollowDTO addFollowRequest(FollowDTO followDTO);

    /**
     * Metoda usuwająca relację zapytania o obserwację użytkownika.
     *
     * @param followDTO data transfer object obserwacji
     * @return wiadomość o zapytaniu o obserwację
     */
    UserRequestFollowDTO removeFollowRequest(FollowDTO followDTO);

    /**
     * Metoda tworząca relację obserwacji użytkownika.
     *
     * @param followDTO data transfer object obserwacji
     * @return wiadomość o obserwacji użytkownika
     */
    UserStartsFollowingMessageDTO followUser(FollowDTO followDTO);

    /**
     * Metoda usuwająca relację obserwacji użytkownika.
     *
     * @param followDTO data transfer object obserwacji
     * @return wiadomość o zakończeniu obserwacji użytkownika
     */
    UserEndsFollowingMessageDTO unfollowUser(FollowDTO followDTO);

    /**
     * Metoda zwracająca konta obserwowanych użytkowników.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @return data transfer object częściowej informacji o użytkowniku
     */
    List<AccountThumbnailDTO> getFollowedAccounts(UUID uuid);

    /**
     * Metoda zwracająca konta obserwujących użytkowników.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @return data transfer object częściowej informacji o użytkowniku
     */
    List<AccountThumbnailDTO> getFollowingAccounts(UUID uuid);

    /**
     * Metoda zwracająca wartość logiczną informującą czy użytkownik obserwuje innego użytkownika.
     *
     * @param followerUUID globalny, unikalny identyfikator użytkownika obserwującego w systemie
     * @param followedUUID globalny, unikalny identyfikator użytkownika obserwowanego w systemie
     * @return wartość logiczna informująca czy użytkownik obserwuje innego użytkownika
     */
    Boolean isFollowingUser(UUID followerUUID, UUID followedUUID);

    /**
     * Metoda zwracająca konta użytkowników, którzy wysłali zapytanie o obserwację.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @return lista data transfer object częściowej informacji o użytkownikach
     */
    List<AccountThumbnailDTO> getUserFollowRequests(UUID uuid);

    /**
     * Metoda zwracająca wartość logiczną informującą czy użytkownik wysłał prośbę o obserwację innego użytkownika.
     *
     * @param followerUUID globalny, unikalny identyfikator użytkownika obserwującego w systemie
     * @param followedUUID globalny, unikalny identyfikator użytkownika obserwowanego w systemie
     * @return wartość logiczna informująca czy użytkownik wysłał prośbę o obserwację innego użytkownika
     */
    Boolean isFollowRequestSent(UUID followerUUID, UUID followedUUID);

    /**
     * Metoda zwracająca rekomendowanych użytkowników.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika systemie
     * @return lista data transfer object częściowej informacji o koncie użytkownika
     */
    List<AccountThumbnailDTO> getRecommendedUsers(UUID uuid);

    /**
     * Metoda zapisująca encję użytkownika z relacyjnej bazy danych w bazie grafowej.
     *
     * @param user encja użytkownika
     */
    void saveUserInGraphDB(User user);
}
