package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.dto.entities.AccountDTO;
import com.socialtripper.restapi.entities.Account;
import com.socialtripper.restapi.exceptions.UserNotFoundException;
import com.socialtripper.restapi.mappers.AccountMapper;
import com.socialtripper.restapi.repositories.AccountRepository;
import com.socialtripper.restapi.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountDTO findUserByUUID(UUID uuid) {
        return accountMapper.toDTO(
                accountRepository.findByUuid(uuid)
                                    .orElseThrow(() -> new UserNotFoundException(uuid)));
    }

    @Override
    public Long getUserIdByUUID(UUID uuid) {
        return accountRepository.findIdByUUID(uuid).orElseThrow(() -> new UserNotFoundException(uuid));
    }

    @Override
    public Account getAccountReference(UUID uuid) {
        return accountRepository.getReferenceById(getUserIdByUUID(uuid));
    }
}
