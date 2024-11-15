package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.dto.entities.AccountDTO;
import com.socialtripper.restapi.dto.entities.FollowDTO;
import com.socialtripper.restapi.dto.messages.UserEndsFollowingMessageDTO;
import com.socialtripper.restapi.dto.messages.UserStartsFollowingMessageDTO;
import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.entities.Account;
import com.socialtripper.restapi.entities.Follow;
import com.socialtripper.restapi.exceptions.AccountNotFoundException;
import com.socialtripper.restapi.exceptions.EmailAlreadyInUseException;
import com.socialtripper.restapi.exceptions.PhoneNumberAlreadyInUseException;
import com.socialtripper.restapi.exceptions.UsernameAlreadyTakenException;
import com.socialtripper.restapi.mappers.AccountMapper;
import com.socialtripper.restapi.mappers.AccountThumbnailMapper;
import com.socialtripper.restapi.repositories.AccountRepository;
import com.socialtripper.restapi.repositories.FollowRepository;
import com.socialtripper.restapi.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final FollowRepository followRepository;
    private final AccountMapper accountMapper;
    private final AccountThumbnailMapper accountThumbnailMapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper,
                              AccountThumbnailMapper accountThumbnailMapper, FollowRepository followRepository) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.accountThumbnailMapper = accountThumbnailMapper;
        this.followRepository = followRepository;
    }

    @Override
    public AccountThumbnailDTO findAccountByUUID(UUID uuid) {
        return accountThumbnailMapper.toDTO(
                accountRepository.findByUuid(uuid)
                                    .orElseThrow(() -> new AccountNotFoundException(uuid)));
    }

    @Override
    public Long getAccountIdByUUID(UUID uuid) {
        return accountRepository.findIdByUUID(uuid).orElseThrow(() -> new AccountNotFoundException(uuid));
    }

    @Override
    public Account getAccountReference(UUID uuid) {
        return accountRepository.getReferenceById(getAccountIdByUUID(uuid));
    }

    @Override
    public Account getNewAccountWithReferences(AccountDTO accountDTO) {
        Account account = accountMapper.toEntity(accountDTO);
        account.setUuid(UUID.randomUUID());
        account.setHomePageUrl("http://users/" + account.getUuid());
        account.setCreatedAt(LocalDate.now());
        account.setFollowersNumber(0);
        account.setFollowingNumber(0);
        account.setNumberOfTrips(0);
        account.setExpired(false);
        account.setLocked(false);
        account.setRole("USER");
        account.setSalt(UUID.randomUUID().toString().substring(0, 16));
        return account;
    }

    @Override
    public AccountThumbnailDTO createAccount(AccountDTO accountDTO) {
        if (accountRepository.doesUsernameExist(accountDTO.nickname())) throw new UsernameAlreadyTakenException();
        if (accountRepository.isEmailInUse(accountDTO.email())) throw new EmailAlreadyInUseException();
        if (accountRepository.isPhoneInUse(accountDTO.phone())) throw new PhoneNumberAlreadyInUseException();
        return accountThumbnailMapper.toDTO(
                accountRepository.save(getNewAccountWithReferences(accountDTO)));
    }

    @Override
    public AccountThumbnailDTO updateAccount(UUID uuid, AccountDTO accountDTO) {
        Account accountToUpdate = accountMapper.copyNonNullValues(getAccountReference(uuid), accountDTO);
        return accountThumbnailMapper.toDTO(accountRepository.save(accountToUpdate));
    }

    @Override
    public UserStartsFollowingMessageDTO followUser(FollowDTO followDTO) {
        followRepository.save(
                new Follow(getAccountReference(followDTO.follower().uuid()),
                            getAccountReference(followDTO.followed().uuid()),
                            LocalDate.now())
        );
        return new UserStartsFollowingMessageDTO(followDTO.follower().uuid(),
                                                    followDTO.followed().uuid(),
                                            "user starts following");
    }

    @Override
    public UserEndsFollowingMessageDTO unfollowUser(FollowDTO followDTO) {
        followRepository.userEndsFollowing(followDTO.follower().uuid(), followDTO.followed().uuid());
        return new UserEndsFollowingMessageDTO(followDTO.follower().uuid(),
                                                followDTO.followed().uuid(),
                                                "user ends following");
    }

    @Override
    public List<AccountThumbnailDTO> getFollowedAccounts(UUID uuid) {
        return followRepository.findAccountsFollowedBy(uuid)
                                .stream().map(accountThumbnailMapper::toDTO)
                                .toList();
    }
}
