package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.dto.entities.UserActivityDTO;
import com.socialtripper.restapi.dto.entities.UserDTO;
import com.socialtripper.restapi.dto.entities.UserLanguageDTO;
import com.socialtripper.restapi.entities.User;
import com.socialtripper.restapi.entities.UserActivity;
import com.socialtripper.restapi.entities.UserLanguage;
import com.socialtripper.restapi.exceptions.UserNotFoundException;
import com.socialtripper.restapi.mappers.UserActivityMapper;
import com.socialtripper.restapi.mappers.UserLanguageMapper;
import com.socialtripper.restapi.mappers.UserMapper;
import com.socialtripper.restapi.nodes.UserNode;
import com.socialtripper.restapi.repositories.graph.UserNodeRepository;
import com.socialtripper.restapi.repositories.relational.UserActivityRepository;
import com.socialtripper.restapi.repositories.relational.UserLanguageRepository;
import com.socialtripper.restapi.repositories.relational.UserRepository;
import com.socialtripper.restapi.services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@Service
public class UserServiceImpl implements com.socialtripper.restapi.services.UserService {
    private final UserRepository userRepository;
    private final UserNodeRepository userNodeRepository;
    private final UserLanguageRepository userLanguageRepository;
    private final UserActivityRepository userActivityRepository;
    private final UserMapper userMapper;
    private final UserActivityMapper userActivityMapper;
    private final UserLanguageMapper userLanguageMapper;
    private final CountryService countryService;
    private final AccountService accountService;
    private final LanguageService languageService;
    private final ActivityService activityService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           CountryService countryService, AccountService accountService,
                           LanguageService languageService, ActivityService activityService,
                           UserActivityRepository userActivityRepository, UserLanguageRepository userLanguageRepository,
                           UserActivityMapper userActivityMapper, UserLanguageMapper userLanguageMapper,
                           UserNodeRepository userNodeRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.countryService = countryService;
        this.accountService = accountService;
        this.languageService = languageService;
        this.activityService = activityService;
        this.userActivityRepository = userActivityRepository;
        this.userLanguageRepository = userLanguageRepository;
        this.userActivityMapper = userActivityMapper;
        this.userLanguageMapper = userLanguageMapper;
        this.userNodeRepository = userNodeRepository;
    }

    @Override
    public UserDTO findUserByUUID(UUID uuid) {
        return userMapper.toDTO(
                userRepository.findByUuid
                        (uuid).orElseThrow(
                                () -> new UserNotFoundException(uuid)
                ));
    }


    @Override
    public User getUserReference(UUID uuid) {
        return userRepository.getReferenceById(
                userRepository.findIdByUuid(uuid).orElseThrow(
                        () -> new UserNotFoundException(uuid)
                )
        );
    }

    @Override
    public User getNewUserWithReferences(UserDTO userDTO, MultipartFile profilePicture) {
        User user = userMapper.toEntity(userDTO);
        user.setUuid(UUID.randomUUID());
        user.setCountry(countryService.getCountryReference(userDTO.country().name()));
        user.setAccount(
                accountService.getAccountReference(
                        accountService.createAccount(userDTO.account(), profilePicture).uuid())
        );
        return user;
    }

    @Override
    public UserActivity addActivity(UUID uuid, UserActivityDTO userActivityDTO) {
        UserActivity userActivity = new UserActivity(
                userActivityDTO.experience(),
                getUserReference(uuid),
                activityService.getActivityReference(userActivityDTO.activity().name()));
        return userActivityRepository.save(userActivity);
    }

    @Override
    public UserLanguage addLanguage(UUID uuid, UserLanguageDTO userLanguageDTO) {
        UserLanguage userLanguage = new UserLanguage(
                userLanguageDTO.level(),
                getUserReference(uuid),
                languageService.getLanguageReference(userLanguageDTO.language().name())
        );
        return userLanguageRepository.save(userLanguage);
    }

    @Transactional
    @Override
    public UserActivityDTO setUserActivity(UUID uuid, UserActivityDTO userActivityDTO) {
        UserActivity userActivityToSet = userActivityRepository
                .getUserActivityExperience(uuid, activityService.getActivityReference(userActivityDTO.activity().name()))
                .map(userActivity -> {
                    userActivity.setExperience(userActivityDTO.experience());
                    return userActivityRepository.save(userActivity);
                })
                .orElseGet(() -> addActivity(uuid, userActivityDTO));

        return userActivityMapper.toDTO(userActivityToSet);
    }

    @Override
    @Transactional
    public UserLanguageDTO setUserLanguage(UUID uuid, UserLanguageDTO userLanguageDTO) {
        UserLanguage userLanguageToSet = userLanguageRepository
                .getUserLanguageExperience(uuid, languageService.getLanguageReference(userLanguageDTO.language().name()))
                .map(userLanguage -> {
                    userLanguage.setLevel(userLanguageDTO.level());
                    return userLanguageRepository.save(userLanguage);
                })
                .orElseGet(() -> addLanguage(uuid, userLanguageDTO));

        return userLanguageMapper.toDTO(userLanguageToSet);
    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO, MultipartFile profilePicture) {
        User userToSave = getNewUserWithReferences(userDTO, profilePicture);
        User savedUser = userRepository.save(userToSave);

        userDTO.activities().forEach(activity -> {
            savedUser.getUserActivities().add(
                    addActivity(savedUser.getUuid(), activity)
            );
        });

        userDTO.languages().forEach(language -> {
            savedUser.getUserLanguages().add(
                    addLanguage(savedUser.getUuid(), language)
            );
        });
        saveUserInGraphDB(savedUser);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UUID uuid, UserDTO userDTO) {
        User userToUpdate = userMapper.copyNonNullValues(
                getUserReference(uuid),
                userDTO
        );
        return userMapper.toDTO(userRepository.save(userToUpdate));
    }

    @Override
    public UserNode findUserNodeByUUID(UUID uuid) {
        return userNodeRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid));
    }

    public void saveUserInGraphDB(User user) {
        UserNode userToSave = userMapper.toNode(user);
        userToSave.setUuid(user.getAccount().getUuid());
        userNodeRepository.save(userToSave);
    }

    public void saveUserInGraphDB(UserNode userNode) {
        userNodeRepository.save(userNode);
    }
}
