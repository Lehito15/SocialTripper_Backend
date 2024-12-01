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

@Controller
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts/{uuid}")
    public ResponseEntity<AccountThumbnailDTO> getAccountByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(accountService.findAccountThumbnailByUUID(uuid));
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountThumbnailDTO> createAccount(@RequestPart AccountDTO accountDTO,
                                                             @RequestPart(required = false) MultipartFile profilePicture) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED)
                                .body(accountService.createAccount(accountDTO, profilePicture));
    }

    @PatchMapping("/accounts/{uuid}")
    public ResponseEntity<AccountThumbnailDTO> updateAccount(@PathVariable UUID uuid,
                                                             @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.updateAccount(uuid, accountDTO));
    }

    @GetMapping("/accounts/email")
    public ResponseEntity<AccountDTO> getAccountByEmail(@RequestParam String email) {
        return ResponseEntity.ok(accountService.findAccountByEmail(email));
    }

    @GetMapping("/accounts/by-nickname")
    public ResponseEntity<List<AccountThumbnailDTO>> getAccountsByNicknameSubstring(@RequestParam String nickname) {
        return ResponseEntity.ok(accountService.findAccountsByNicknameSubstring(nickname));
    }

    @PatchMapping("/accounts/{uuid}/profile-picture")
    public ResponseEntity<MultimediaDTO> updateProfilePicture(@RequestPart AccountThumbnailDTO account,
                                                              @RequestPart MultipartFile profilePicture) {
        return ResponseEntity.ok(accountService.updateProfilePicture(account.uuid(), profilePicture));
    }

}
