package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.AccountDTO;
import com.socialtripper.restapi.entities.Account;

import java.util.UUID;

public interface AccountService {
    AccountDTO findUserByUUID(UUID uuid);
    Account getAccountReference(UUID uuid);
    Long getUserIdByUUID(UUID uuid);
}
