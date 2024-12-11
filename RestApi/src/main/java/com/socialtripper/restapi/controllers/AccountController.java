package com.socialtripper.restapi.controllers;

import com.socialtripper.restapi.dto.entities.AccountDTO;
import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.dto.thumbnails.MultimediaDTO;
import com.socialtripper.restapi.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.UUID;

/**
 * Kontroler zarządzający żądaniami HTTP związanymi z kontami użytkowników.
 */
@Controller
public class AccountController {
    /**
     * Serwis zarządzający operacjami związanymi z kontami użytkowników.
     */
    private final AccountService accountService;

    /**
     * Konstruktor wstrzykujący serwis zarządzający operacjami związanymi z kontami użytkowników.
     *
     * @param accountService serwis zarządzający operacajami związanymi z kontami użytkowników
     */
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Pobieranie danych konta.
     *
     * @param uuid globalny, unikalny identyfikator konta w systemie
     * @return odpowiedź http data transfer object częściowej informacji o koncie lub błąd 404 jeżeli konto nie istnieje
     */
    @GetMapping("/accounts/{uuid}")
    public ResponseEntity<AccountThumbnailDTO> getAccountByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(accountService.findAccountThumbnailByUUID(uuid));
    }

    /**
     * Tworzenie nowego konta.
     *
     * @param accountDTO data transfer object konta
     * @param profilePicture plik zdjęcia profilowego
     * @return odpowiedź http z data transfer object częściowej informacji o koncie lub błąd 409 jeżeli wprowadzone dane nie spełniają kryteriów unikalności (nazwa użytkownika, numer telefonu, adres email)
     */
    @PostMapping("/accounts")
    public ResponseEntity<AccountThumbnailDTO> createAccount(@RequestPart AccountDTO accountDTO,
                                                             @RequestPart(required = false) MultipartFile profilePicture) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED)
                                .body(accountService.createAccount(accountDTO, profilePicture));
    }

    /**
     * Aktualizacja danych konta.
     *
     * @param uuid globalny, unikalny identyfikator konta w systemie
     * @param accountDTO data transfer object konta
     * @return odpowiedź http z data transfer object częściowej informacji o koncie
     */
    @PatchMapping("/accounts/{uuid}")
    public ResponseEntity<AccountThumbnailDTO> updateAccount(@PathVariable UUID uuid,
                                                             @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.updateAccount(uuid, accountDTO));
    }

    /**
     * Pobieranie danych konta.
     *
     * @param email adres email
     * @return odpowiedź http z data transfer object konta lub błąd 404 gdy konto nie istnieje
     */
    @GetMapping("/accounts/email")
    public ResponseEntity<AccountDTO> getAccountByEmail(@RequestParam String email) {
        return ResponseEntity.ok(accountService.findAccountByEmail(email));
    }

    /**
     * Pobieranie danych kont, które zawierają w nazwie użytkownika wprowadzony łańcuch.
     *
     * @param nickname łańcuch znaków nazwy konta.
     * @return odpowiedź http z listą data transfer object częściowych informacji o koncie
     */
    @GetMapping("/accounts/by-nickname")
    public ResponseEntity<List<AccountThumbnailDTO>> getAccountsByNicknameSubstring(@RequestParam String nickname) {
        return ResponseEntity.ok(accountService.findAccountsByNicknameSubstring(nickname));
    }

    /**
     * Aktualizacja zdjęcia profilowego.
     *
     * @param account data transfer object konta
     * @param profilePicture plik nowego zdjęcia profilowego
     * @return odpowiedź http z data transfer object multimedium
     */
    @PatchMapping("/accounts/{uuid}/profile-picture")
    public ResponseEntity<MultimediaDTO> updateProfilePicture(@RequestPart AccountThumbnailDTO account,
                                                              @RequestPart MultipartFile profilePicture) {
        return ResponseEntity.ok(accountService.updateProfilePicture(account.uuid(), profilePicture));
    }

}
