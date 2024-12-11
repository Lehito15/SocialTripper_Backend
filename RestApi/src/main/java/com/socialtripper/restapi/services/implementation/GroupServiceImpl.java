package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.dto.entities.*;
import com.socialtripper.restapi.dto.messages.*;
import com.socialtripper.restapi.dto.requests.UserRequestGroupDTO;
import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.dto.thumbnails.MultimediaDTO;
import com.socialtripper.restapi.entities.Group;
import com.socialtripper.restapi.entities.GroupActivity;
import com.socialtripper.restapi.entities.GroupLanguage;
import com.socialtripper.restapi.entities.GroupParticipant;
import com.socialtripper.restapi.entities.enums.LocationScopes;
import com.socialtripper.restapi.exceptions.GroupNotFoundException;
import com.socialtripper.restapi.exceptions.AccountNotFoundException;
import com.socialtripper.restapi.mappers.GroupMapper;
import com.socialtripper.restapi.nodes.GroupNode;
import com.socialtripper.restapi.repositories.graph.GroupNodeRepository;
import com.socialtripper.restapi.repositories.relational.*;
import com.socialtripper.restapi.services.*;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementacja serwisu zarządzającego operacjami wykonywanymi na grupach.
 */
@Service
public class GroupServiceImpl implements GroupService {
    /**
     * Repozytorium zarządzające encjami grup.
     */
    private final GroupRepository groupRepository;
    /**
     * Repozytorium zarządzające węzłami grup.
     */
    private final GroupNodeRepository groupNodeRepository;
    /**
     * Repozytorium zarządzające encjami aktywności grup.
     */
    private final GroupActivityRepository groupActivityRepository;
    /**
     * Repozytorium zarządzające encjami języków grup.
     */
    private final GroupLanguageRepository groupLanguageRepository;
    /**
     * Repozytorium zarządzające encjami członków grup.
     */
    private final GroupParticipantRepository groupParticipantRepository;
    /**
     * Repozytorium zarządzające encjami zasięgami grup.
     */
    private final LocationScopeRepository locationScopeRepository;
    /**
     * Komponent mapujący grupy.
     */
    private final GroupMapper groupMapper;
    /**
     * Serwis zarządzający operacjami wykonywanymi na kontach użytkowników.
     */
    private final AccountService accountService;
    /**
     * Serwis zarządzający operacjami wykonywanymi na aktywnościach.
     */
    private final ActivityService activityService;
    /**
     * Serwis zarządzający operacjami wykonywanymi na językach.
     */
    private final LanguageService languageService;
    /**
     * Serwis zarządzający operacjami wykonywanymi na multimediach.
     */
    private final MultimediaService multimediaService;
    /**
     * Serwis zarządzający operacjami wykonywanymi na użytkownikach.
     */
    private final UserService userService;

    /**
     * Konstruktor wstrzykujący komponenty.
     *
     * @param groupRepository repozytorium zarządzające encjami grup
     * @param groupMapper komponent mapujący grupy
     * @param accountService serwis zarządzający kontami użytkowników
     * @param activityService serwis zarządzający aktywnościami
     * @param languageService serwis zarządzający językami
     * @param groupLanguageRepository repozytorium zarządzające encjami języków grup
     * @param groupActivityRepository repozytorium zarządzające encjami aktywności grup
     * @param groupParticipantRepository repozytorium zarządzające encjami członkami grup
     * @param groupNodeRepository repozytorium zarządzające węzłami grup
     * @param multimediaService serwis zarządzający multimediami
     * @param userService serwis zarządzający użytkownikami
     * @param locationScopeRepository repozytorium zarządzające encjami zasięgów grup
     */
    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper,
                            AccountService accountService, ActivityService activityService,
                            LanguageService languageService, GroupLanguageRepository groupLanguageRepository,
                            GroupActivityRepository groupActivityRepository, GroupParticipantRepository groupParticipantRepository,
                            GroupNodeRepository groupNodeRepository,
                            MultimediaService multimediaService, UserService userService,
                            LocationScopeRepository locationScopeRepository) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.accountService = accountService;
        this.activityService = activityService;
        this.languageService = languageService;
        this.groupLanguageRepository = groupLanguageRepository;
        this.groupActivityRepository = groupActivityRepository;
        this.groupParticipantRepository = groupParticipantRepository;
        this.groupNodeRepository = groupNodeRepository;
        this.multimediaService = multimediaService;
        this.userService = userService;
        this.locationScopeRepository = locationScopeRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupDTO> findAllGroups() {
        return groupRepository.findAll()
                                .stream()
                                .map(groupMapper::toDTO)
                                .toList();
    }

    /**
     * {@inheritDoc}
     * <p>
     *     W przypadku gdy encja nie istnieje w bazie rzucany jest wyjątek {@link GroupNotFoundException}.
     * </p>
     */
    @Override
    public GroupDTO findGroupByUUID(UUID uuid) {
        return groupMapper.toDTO(
                groupRepository.findByUuid(uuid)
                        .orElseThrow(() -> new GroupNotFoundException(uuid)));
    }

    /**
     * {@inheritDoc}
     * <p>
     *     W przypadku gdy encja nie istnieje w bazie rzucany jest wyjątek {@link GroupNotFoundException}.
     * </p>
     */
    @Override
    public Long getGroupIdByUUID(UUID uuid) {
        return groupRepository.findIdByUUID(uuid).orElseThrow(() -> new AccountNotFoundException(uuid));
    }

    /**
     * {@inheritDoc}
     * <p>
     *     W przypadku gdy encja nie istnieje w bazie rzucany jest wyjątek {@link GroupNotFoundException}.
     * </p>
     */
    @Override
    public Group getGroupReference(UUID uuid) {
        return groupRepository.getReferenceById(getGroupIdByUUID(uuid));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Group getNewGroupWithReferences(GroupDTO groupDTO) {
        Group group = groupMapper.toNewEntity(groupDTO);
        group.setUuid(UUID.randomUUID());
        group.setDateOfCreation(LocalDate.now());
        group.setOwner(accountService.getAccountReference(groupDTO.owner().uuid()));
        group.setHomePageUrl("/groups/" + group.getUuid());
        group.setIsPublic(groupDTO.isPublic());
        group.setLocationScope(locationScopeRepository.getReferenceById(
                Objects.requireNonNull(
                        LocationScopes.fromScope(
                                groupDTO.locationScope().name()).orElse(null)).getId()
        ));
        return group;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupActivity setActivity(UUID uuid, ActivityDTO activityDTO) {
        GroupActivity groupActivity = new GroupActivity(
                getGroupReference(uuid),
                activityService.getActivityReference(activityDTO.name())
        );
        return groupActivityRepository.save(groupActivity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupLanguage setLanguage(UUID uuid, LanguageDTO languageDTO) {
        GroupLanguage groupLanguage = new GroupLanguage(
                getGroupReference(uuid),
                languageService.getLanguageReference(languageDTO.name())
        );
        return groupLanguageRepository.save(groupLanguage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public GroupDTO createGroup(GroupDTO groupDTO, MultipartFile icon) {
        Group groupToSave = getNewGroupWithReferences(groupDTO);

        if (icon != null) {
            try {
                groupToSave.setIconUrl(
                        multimediaService.uploadMultimedia(
                                icon,
                                "groups/" + groupToSave.getUuid() + "/" + UUID.randomUUID()));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        Group savedGroup = groupRepository.save(groupToSave);
        groupDTO.activities().forEach(
                activity -> {
                    savedGroup.getGroupActivities()
                                .add(setActivity(savedGroup.getUuid(), activity));
                }
        );

        groupDTO.languages().forEach(
                language -> {
                    savedGroup.getGroupLanguages()
                                .add(setLanguage(savedGroup.getUuid(), language));
                }
        );
        saveInGraphDB(savedGroup);
        addUserToGroup(
                groupDTO.owner().uuid(),
                savedGroup.getUuid());
        return groupMapper.toDTO(savedGroup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupDTO> findUserCurrentGroups(UUID uuid) {
       return groupParticipantRepository.findUserCurrentGroups(uuid).stream().map(groupMapper::toDTO).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UserJoinsGroupMessageDTO addUserToGroup(UUID userUUID, UUID groupUUID) {
        if (!isUserInGroup(userUUID, groupUUID)) {
            groupParticipantRepository.save(
                    new GroupParticipant(getGroupReference(groupUUID),
                            accountService.getAccountReference(userUUID),
                            LocalDate.now())
            );

            groupNodeRepository.addUserToGroup(
                    userUUID.toString(),
                    groupUUID.toString()
            );

            groupNodeRepository.removeUserApplyForGroup(
                    userUUID.toString(),
                    groupUUID.toString()
            );

            Group group = groupRepository.findByUuid(groupUUID).orElseThrow(() ->
                    new GroupNotFoundException(groupUUID));
            group.setNumberOfMembers(group.getNumberOfMembers() + 1);
            groupRepository.save(group);
        }

        return new UserJoinsGroupMessageDTO(
                userUUID,
                groupUUID,
                "user has joined group");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserLeavesGroupMessageDTO removeUserFromGroup(UUID userUUID, UUID groupUUID) {
        groupParticipantRepository.userLeftGroup(userUUID,groupUUID);

        groupNodeRepository.removeUserFromGroup(
                userUUID.toString(),
                groupUUID.toString()
        );

        Group group = groupRepository.findByUuid(groupUUID).orElseThrow(() ->
                new GroupNotFoundException(groupUUID));
        group.setNumberOfMembers(group.getNumberOfMembers() -1);

        groupRepository.save(group);

        return new UserLeavesGroupMessageDTO(
                userUUID,
                groupUUID,
                "user has left group");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupDTO updateGroup(UUID uuid, GroupDTO groupDTO) {
        Group group = groupMapper.copyNonNullValues(getGroupReference(uuid), groupDTO);
        return groupMapper.toDTO(groupRepository.save(group));
    }

    /**
     * {@inheritDoc}
     * <p>
     *     W przypadku gdy encja nie istnieje w bazie rzucany jest wyjątek {@link GroupNotFoundException}.
     * </p>
     */
    @Override
    public GroupNode findGroupNodeByUUID(UUID uuid) {
        return groupNodeRepository.findGroupNodeByUuid(uuid).
                orElseThrow(
                        () -> new GroupNotFoundException(uuid));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRequestGroupDTO addUserRequestGroup(UUID userUUID, UUID groupUUID) {
        groupNodeRepository.addUserApplyForGroup(
                userUUID.toString(),
                groupUUID.toString()
        );
        return new UserRequestGroupDTO(
                userUUID,
                groupUUID,
                "user applied for group"
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRequestGroupDTO removeUserRequestGroup(UUID userUUID, UUID groupUUID) {
        groupNodeRepository.removeUserApplyForGroup(
                userUUID.toString(),
                groupUUID.toString()
        );
        return new UserRequestGroupDTO(
                userUUID,
                groupUUID,
                "user refused to join the group"
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AccountThumbnailDTO> findGroupRequests(UUID groupUUID) {
        return groupNodeRepository.findGroupRequests(groupUUID.toString()).stream().map(user ->
                accountService.findAccountThumbnailByUUID(user.getUuid())).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AccountThumbnailDTO> getGroupMembers(UUID groupUUID) {
        return groupNodeRepository.findGroupMembers(groupUUID.toString())
                .stream()
                .map(member ->
                        accountService.findAccountThumbnailByUUID(member.getUuid()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isGroupRequestSent(UUID userUUID, UUID groupUUID) {
        return groupNodeRepository.isGroupRequestSent(
                userUUID.toString(),
                groupUUID.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isUserInGroup(UUID userUUID, UUID groupUUID) {
        return groupNodeRepository.isUserInGroup(
                userUUID.toString(),
                groupUUID.toString()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultimediaDTO updateGroupPicture(UUID uuid, MultipartFile icon) {
        Group group = groupRepository.findByUuid(uuid).orElseThrow(() ->
                new GroupNotFoundException(uuid));
        if (icon != null) {
            try {
                group.setIconUrl(
                        multimediaService.uploadMultimedia(
                                icon,
                                "groups/" + group.getUuid() + "/" + UUID.randomUUID()));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        groupRepository.save(group);
        return new MultimediaDTO(group.getIconUrl());
    }

    /**
     * Metoda zapisująca encję grupy z bazy relacyjnej jako węzeł w bazie grafowej.
     *
     * @param group encja grupy
     */
    private void saveInGraphDB(Group group) {
        GroupNode groupToSave = groupMapper.toNode(group);
        groupToSave.setOwner(userService.findUserNodeByUUID(group.getOwner().getUuid()));
        groupNodeRepository.save(groupToSave);
    }

}
