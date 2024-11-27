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
import com.socialtripper.restapi.repositories.graph.UserNodeRepository;
import com.socialtripper.restapi.repositories.relational.AccountRepository;
import com.socialtripper.restapi.repositories.relational.FollowRepository;
import com.socialtripper.restapi.services.AccountService;
import com.socialtripper.restapi.services.MultimediaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final FollowRepository followRepository;
    private final AccountMapper accountMapper;
    private final AccountThumbnailMapper accountThumbnailMapper;
    private final MultimediaService multimediaService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper,
                              AccountThumbnailMapper accountThumbnailMapper, FollowRepository followRepository,
                              MultimediaService multimediaService, UserNodeRepository userNodeRepository) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.accountThumbnailMapper = accountThumbnailMapper;
        this.followRepository = followRepository;
        this.multimediaService = multimediaService;
    }

    @Override
    public AccountThumbnailDTO findAccountThumbnailByUUID(UUID uuid) {
        return accountThumbnailMapper.toDTO(
                accountRepository.findByUuid(uuid)
                                    .orElseThrow(() -> new AccountNotFoundException(uuid)));
    }

    @Override
    public AccountDTO findAccountByUUID(UUID uuid) {
        return accountMapper.toDTO(accountRepository.findByUuid(uuid)
                .orElseThrow(() -> new AccountNotFoundException(uuid))
        );
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
        Account account = accountMapper.toNewEntity(accountDTO);
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
    @Transactional
    public AccountThumbnailDTO createAccount(AccountDTO accountDTO, MultipartFile profilePicture) {
        if (accountRepository.doesUsernameExist(accountDTO.nickname())) throw new UsernameAlreadyTakenException();
        if (accountRepository.isEmailInUse(accountDTO.email())) throw new EmailAlreadyInUseException();
        if (accountRepository.isPhoneInUse(accountDTO.phone())) throw new PhoneNumberAlreadyInUseException();

        Account newAccount = getNewAccountWithReferences(accountDTO);
        if (profilePicture != null) {
            try {
                newAccount.setProfilePictureUrl(
                        multimediaService.uploadMultimedia(
                                profilePicture,
                                "users/" + newAccount.getUuid() + "/" + UUID.randomUUID()));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return accountThumbnailMapper.toDTO(
                accountRepository.save(newAccount));
    }

    @Override
    public AccountThumbnailDTO updateAccount(UUID uuid, AccountDTO accountDTO) {
        Account accountToUpdate = accountMapper.copyNonNullValues(getAccountReference(uuid), accountDTO);
        return accountThumbnailMapper.toDTO(accountRepository.save(accountToUpdate));
    }

    @Override
    @Transactional
    public UserStartsFollowingMessageDTO followUser(FollowDTO followDTO) {
        followRepository.save(
                new Follow(getAccountReference(followDTO.follower().uuid()),
                            getAccountReference(followDTO.followed().uuid()),
                            LocalDate.now())
        );

        Account follower = accountRepository.findByUuid(
                followDTO.follower().uuid()).orElseThrow(() ->
                new AccountNotFoundException(followDTO.follower().uuid()));
        Account followed = accountRepository.findByUuid(
                followDTO.followed().uuid()).orElseThrow(() ->
                new AccountNotFoundException(followDTO.followed().uuid()));

        follower.setFollowingNumber(follower.getFollowingNumber() + 1);
        followed.setFollowersNumber(followed.getFollowersNumber() + 1);

        accountRepository.save(follower);
        accountRepository.save(followed);

        return new UserStartsFollowingMessageDTO(followDTO.follower().uuid(),
                                                    followDTO.followed().uuid(),
                                            "user starts following");
    }

    @Override
    @Transactional
    public UserEndsFollowingMessageDTO unfollowUser(FollowDTO followDTO) {
        followRepository.userEndsFollowing(
                followDTO.follower().uuid(),
                followDTO.followed().uuid());

        Account follower = accountRepository.findByUuid(
                followDTO.follower().uuid()).orElseThrow(() ->
                new AccountNotFoundException(followDTO.follower().uuid()));
        Account followed = accountRepository.findByUuid(
                followDTO.followed().uuid()).orElseThrow(() ->
                new AccountNotFoundException(followDTO.followed().uuid()));

        follower.setFollowingNumber(follower.getFollowingNumber() - 1);
        followed.setFollowersNumber(followed.getFollowersNumber() - 1);

        accountRepository.save(follower);
        accountRepository.save(followed);

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

    @Override
    public AccountDTO findAccountByEmail(String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() ->
                new AccountNotFoundException(email));
        return accountMapper.toDTO(account);
    }
}
