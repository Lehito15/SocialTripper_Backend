package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.AccountDTO;
import com.socialtripper.restapi.dto.entities.FollowDTO;
import com.socialtripper.restapi.dto.messages.UserEndsFollowingMessageDTO;
import com.socialtripper.restapi.dto.messages.UserStartsFollowingMessageDTO;
import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.dto.thumbnails.MultimediaDTO;
import com.socialtripper.restapi.entities.Account;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

/**
 * Serwis zarządzający operacjami związanymi z kontami użytkowników.
 */
public interface AccountService {
    /**
     * Metoda zwracająca refrencję encji konta użytkownika z bazy danych na podstawie UUID.
     * Takie podejście umożliwia ograniczenie informacji pobieranych z bazy w przypadku operacji
     * wykonywanych na istniejących już encjach.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return referencja encji konta
     */
    Account getAccountReference(UUID uuid);

    /**
     * Metoda zwracająca obiekt nowego konta, wraz z referencjami na encje związane z kluczami obcymi.
     *
     * @param accountDTO data transfer object zawierający dane nowo utworzonego konta
     * @return encja konta użytkownika
     */
    Account getNewAccountWithReferences(AccountDTO accountDTO);

    /**
     * Metoda zwracająca klucz główny konta na podstawie UUID konta.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return klucz główny konta
     */
    Long getAccountIdByUUID(UUID uuid);

    /**
     * Metoda zwracająca częściowe dane konta na podstawie jego identyfikatora UUID.
     * Pozwala to na ukrycie wrażliwych danych konta, a pobranie jedynie tych publicznych.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return data transfer object częściowych danych konta
     */
    AccountThumbnailDTO findAccountThumbnailByUUID(UUID uuid);

    /**
     * Metoda zwracająca dane konta na podstawie jego identfikatora UUID.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return data transfer object konta użytkownika
     */
    AccountDTO findAccountByUUID(UUID uuid);

    /**
     * Metoda aktualizująca dane konta. Aktualizowane są dane niezwiązane z encjami agregowanymi w encji konta.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @param accountDTO data transfer object zawierający nowe dane konta, wartości null są pomijane do edycji wartości
     * @return data transfer object częściowej infromacji o koncie użytkownika
     */
    AccountThumbnailDTO updateAccount(UUID uuid, AccountDTO accountDTO);

    /**
     * Metoda tworząca nowe konto użytkownika.
     *
     * @param accountDTO data transfer object z danymi nowego konta
     * @param profilePicture plik zdjęcia profilowego
     * @return data transfer object częściowej informacji o koncie użytkownika
     */
    AccountThumbnailDTO createAccount(AccountDTO accountDTO, MultipartFile profilePicture);

    /**
     * Metoda tworząca relację obserwacji konta użytkownika.
     *
     * @param followDTO data transfer object obserwacji
     * @return wiadomość o rozpoczęciu obserwacji
     */
    UserStartsFollowingMessageDTO followUser(FollowDTO followDTO);

    /**
     * Metoda usuwająca relację obserwacji konta użytkownika.
     *
     * @param followDTO data transfer object zakończenia obserwacji
     * @return wiadomość o zakończeniu obserwacji
     */
    UserEndsFollowingMessageDTO unfollowUser(FollowDTO followDTO);

    /**
     * Metoda zwracająca konta obserwowane przez użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return lista obserwowanych kont
     */
    List<AccountThumbnailDTO> getFollowedAccounts(UUID uuid);

    /**
     * Metoda zwracająca konta obserwujące użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return lista obserwujących kont
     */
    List<AccountThumbnailDTO> getFollowingAccounts(UUID uuid);

    /**
     * Metoda aktualizująca zdjęcie profilowe dla konta użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @param profilePicture plik nowego zdjęcia profilowego
     * @return data transfer object multimedium
     */
    MultimediaDTO updateProfilePicture(UUID uuid, MultipartFile profilePicture);

    /**
     * Metoda zwracająca konto użytkownika na podstawie adresu email, który jest unikalny w bazie danych.
     *
     * @param email adres email
     * @return data transfer object konta użytkownika
     */
    AccountDTO findAccountByEmail(String email);

    /**
     * Metoda zwracająca konta, które zawierają w sobie podany łańcuch. Metoda służy do wyszukiwania kont w pasku
     * wyszukiwania na podstawie danych podanych przez użtykownika.
     *
     * @param nickname łąńcuch nazwy użytkownika
     * @return data transfer object częściowej informacji na temat konta użytkownika
     */
    List<AccountThumbnailDTO> findAccountsByNicknameSubstring(String nickname);
}
