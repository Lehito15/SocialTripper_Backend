package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.*;
import com.socialtripper.restapi.dto.messages.*;
import com.socialtripper.restapi.dto.requests.UserRequestGroupDTO;
import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.dto.thumbnails.GroupThumbnailDTO;
import com.socialtripper.restapi.dto.thumbnails.MultimediaDTO;
import com.socialtripper.restapi.entities.Group;
import com.socialtripper.restapi.entities.GroupActivity;
import com.socialtripper.restapi.entities.GroupLanguage;
import com.socialtripper.restapi.nodes.GroupNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Serwis zarządzający operacjami wykonywanymi na encjach grup.
 */
public interface GroupService {
    /**
     * Metoda zwracająca wszystkie grupy w bazie danych.
     *
     * @return lista data transfer object grup.
     */
    List<GroupDTO> findAllGroups();

    /**
     * Metoda zwracająca grupę o wskazanym identyfikatorze UUID.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @return data transfer object grupy
     */
    GroupDTO findGroupByUUID(UUID uuid);

    /**
     * Metoda zwracająca klucz główny grupy na podstawie UUID.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @return klucz główny grupy
     */
    Long getGroupIdByUUID(UUID uuid);

    /**
     * Metoda zwracająca referencję encji grupy z bazy danych na podstawie UUID.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @return referencja na encję grupy w bazie danych
     */
    Group getGroupReference(UUID uuid);

    /**
     * Metoda zwracająca encję nowo utworzonej grupy.
     *
     * @param groupDTO data transfer object grupy
     * @return data transfer object nowo utworzonej grupy
     */
    Group getNewGroupWithReferences(GroupDTO groupDTO);

    /**
     * Metoda akutalizująca aktywności grupowe.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @param activityDTO data transfer object aktywności grupowej
     * @return encja aktywności grupowej
     */
    GroupActivity setActivity(UUID uuid, ActivityDTO activityDTO);

    /**
     * Metoda aktualizująca język w grupie.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @param languageDTO data transfer object języka w grupie
     * @return data transfer object języka w grupie
     */
    GroupLanguage setLanguage(UUID uuid, LanguageDTO languageDTO);

    /**
     * Metoda tworząca nową grupę.
     *
     * @param groupDTO data transfer object grupy
     * @param icon plik ikony grupy
     * @return data transfer object grupy
     */
    GroupDTO createGroup(GroupDTO groupDTO, MultipartFile icon);

    /**
     * Metoda zwracająca listę grup, których użytkownik jest aktualnym członkiem.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return lista data transfer object grup
     */
    List<GroupDTO> findUserCurrentGroups(UUID uuid);

    /**
     * Metoda tworząca relację przynależności użytkownika do grupy.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param groupUUID globalny, unikalny identyfikator grupy w systemie
     * @return wiadomość o dodaniu użytkownika do grupy
     */
    UserJoinsGroupMessageDTO addUserToGroup(UUID userUUID, UUID groupUUID);

    /**
     * Metoda usuwająca relację przynależności użytkownika do grupy.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param groupUUID globalny, unikalny identyfikator grupy w systemie
     * @return wiadomość o opuszczenia grupy przez użytkownika
     */
    UserLeavesGroupMessageDTO removeUserFromGroup(UUID userUUID, UUID groupUUID);

    /**
     * Metoda aktualizująca dane grupy. Metoda aktualizuje wartości nie związane z agregowanymi encjami.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @param groupDTO data transfer object grupy, wartości null są pomijane przy aktualizacji
     * @return data transfer object grupy
     */
    GroupDTO updateGroup(UUID uuid, GroupDTO groupDTO);

    /**
     * Metoda zwracająca węzeł grupy na podstawie UUID.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @return węzeł grupy
     */
    GroupNode findGroupNodeByUUID(UUID uuid);

    /**
     * Metoda tworząca relację zapytania o dołączenie do grupy.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param groupUUID globalny, unikalny identyfikator grupy w systemie
     * @return wiadomość o zapytaniu o dołączenie do grupy
     */
    UserRequestGroupDTO addUserRequestGroup(UUID userUUID, UUID groupUUID);

    /**
     * Metoda usuwająca relację zapytania o dołączenei do grupy.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param groupUUID globalny, unikalny identyfikator grupy w systemie
     * @return wiadomość o zapytaniu o dołączenie do grupy
     */
    UserRequestGroupDTO removeUserRequestGroup(UUID userUUID, UUID groupUUID);

    /**
     * Metoda zwracająca konta użytkowników, którzy wysłali zapytanie o dołączenie do grupy.
     *
     * @param groupUUID globalny, unikalny identyfikator grupy w systemie
     * @return lista data transfer object częściowych informacji o koncie użytkownika
     */
    List<AccountThumbnailDTO> findGroupRequests(UUID groupUUID);

    /**
     * Metoda zwracająca konta użytkowników, którzy są członkami grupy.
     *
     * @param groupUUID globalny, unikalny identyfikator grupy w systemie
     * @return lista data transfer object częściowych informacji o koncie użytkownika
     */
    List<AccountThumbnailDTO> getGroupMembers(UUID groupUUID);

    /**
     * Metoda zwracająca wartość logiczną informującą czy użytkownik wysłał zapytanie o dołączenie do grupy.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param groupUUID globalny, unikalny identyfikator grupy w systemie
     * @return wartość logiczna informująca czy użytkownik wysłał zapytanie o dołączenie do grupy
     */
    Boolean isGroupRequestSent(UUID userUUID, UUID groupUUID);

    /**
     * Metoda zwracająca wartość logiczną informującą czy użytkownik jest członkiem grupy.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param groupUUID globalny, unikalny identyfikator grupy w systemie
     * @return wartość logiczna informująca czy użytkownik jest członkiem grupy
     */
    Boolean isUserInGroup(UUID userUUID, UUID groupUUID);

    /**
     * Metoda aktualizująca ikonę grupy.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @param icon plik nowej ikony grupy
     * @return data transfer object multimedium
     */
    MultimediaDTO updateGroupPicture(UUID uuid, MultipartFile icon);
}
