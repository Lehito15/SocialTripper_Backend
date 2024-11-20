package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.dto.entities.*;
import com.socialtripper.restapi.dto.messages.*;
import com.socialtripper.restapi.dto.thumbnails.GroupThumbnailDTO;
import com.socialtripper.restapi.entities.Group;
import com.socialtripper.restapi.entities.GroupActivity;
import com.socialtripper.restapi.entities.GroupLanguage;
import com.socialtripper.restapi.entities.GroupParticipant;
import com.socialtripper.restapi.exceptions.GroupNotFoundException;
import com.socialtripper.restapi.exceptions.AccountNotFoundException;
import com.socialtripper.restapi.mappers.GroupMapper;
import com.socialtripper.restapi.mappers.GroupThumbnailMapper;
import com.socialtripper.restapi.nodes.GroupNode;
import com.socialtripper.restapi.repositories.graph.GroupNodeRepository;
import com.socialtripper.restapi.repositories.relational.GroupActivityRepository;
import com.socialtripper.restapi.repositories.relational.GroupLanguageRepository;
import com.socialtripper.restapi.repositories.relational.GroupParticipantRepository;
import com.socialtripper.restapi.repositories.relational.GroupRepository;
import com.socialtripper.restapi.services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupNodeRepository groupNodeRepository;
    private final GroupActivityRepository groupActivityRepository;
    private final GroupLanguageRepository groupLanguageRepository;
    private final GroupParticipantRepository groupParticipantRepository;
    private final GroupMapper groupMapper;
    private final GroupThumbnailMapper groupThumbnailMapper;
    private final AccountService accountService;
    private final ActivityService activityService;
    private final LanguageService languageService;
    private final MultimediaService multimediaService;
    private final UserService userService;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper,
                            AccountService accountService, ActivityService activityService,
                            LanguageService languageService, GroupLanguageRepository groupLanguageRepository,
                            GroupActivityRepository groupActivityRepository, GroupParticipantRepository groupParticipantRepository,
                            GroupThumbnailMapper groupThumbnailMapper, GroupNodeRepository groupNodeRepository,
                            MultimediaService multimediaService, UserService userService) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.accountService = accountService;
        this.activityService = activityService;
        this.languageService = languageService;
        this.groupLanguageRepository = groupLanguageRepository;
        this.groupActivityRepository = groupActivityRepository;
        this.groupParticipantRepository = groupParticipantRepository;
        this.groupThumbnailMapper = groupThumbnailMapper;
        this.groupNodeRepository = groupNodeRepository;
        this.multimediaService = multimediaService;
        this.userService = userService;
    }

    @Override
    public List<GroupDTO> findAllGroups() {
        return groupRepository.findAll()
                                .stream()
                                .map(groupMapper::toDTO)
                                .toList();
    }

    @Override
    public GroupDTO findGroupByUUID(UUID uuid) {
        return groupMapper.toDTO(
                groupRepository.findByUuid(uuid)
                        .orElseThrow(() -> new GroupNotFoundException(uuid)));
    }

    @Override
    public Long getGroupIdByUUID(UUID uuid) {
        return groupRepository.findIdByUUID(uuid).orElseThrow(() -> new AccountNotFoundException(uuid));
    }

    @Override
    public Group getGroupReference(UUID uuid) {
        return groupRepository.getReferenceById(getGroupIdByUUID(uuid));
    }

    @Override
    public Group getNewGroupWithReferences(GroupDTO groupDTO) {
        Group group = groupMapper.toEntity(groupDTO);
        group.setUuid(UUID.randomUUID());
        group.setDateOfCreation(LocalDate.now());
        group.setOwner(accountService.getAccountReference(groupDTO.owner().uuid()));
        return group;
    }

    @Override
    public GroupActivity setActivity(UUID uuid, ActivityDTO activityDTO) {
        GroupActivity groupActivity = new GroupActivity(
                getGroupReference(uuid),
                activityService.getActivityReference(activityDTO.name())
        );
        return groupActivityRepository.save(groupActivity);
    }

    @Override
    public GroupLanguage setLanguage(UUID uuid, LanguageDTO languageDTO) {
        GroupLanguage groupLanguage = new GroupLanguage(
                getGroupReference(uuid),
                languageService.getLanguageReference(languageDTO.name())
        );
        return groupLanguageRepository.save(groupLanguage);
    }

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
        return groupMapper.toDTO(savedGroup);
    }

    @Override
    public List<GroupThumbnailDTO> findUserCurrentGroups(UUID uuid) {
       return groupParticipantRepository.findUserCurrentGroups(uuid).stream().map(groupThumbnailMapper::toDTO).toList();
    }

    @Override
    public UserJoinsGroupMessageDTO addUserToGroup(UUID groupUUID, UUID userUUID) {
        groupParticipantRepository.save(
                new GroupParticipant(getGroupReference(groupUUID),
                        accountService.getAccountReference(userUUID)));
        return new UserJoinsGroupMessageDTO(userUUID, groupUUID, "user has joined group");
    }

    @Override
    public UserLeavesGroupMessageDTO removeUserFromGroup(UUID groupUUID, UUID userUUID) {
        groupParticipantRepository.userLeftGroup(userUUID,groupUUID);
        return new UserLeavesGroupMessageDTO(userUUID, groupUUID, "user has left group");
    }

    @Override
    public GroupDTO updateGroup(UUID uuid, GroupDTO groupDTO) {
        Group group = groupMapper.copyNonNullValues(getGroupReference(uuid), groupDTO);
        return groupMapper.toDTO(groupRepository.save(group));
    }

    @Override
    public GroupNode findGroupNodeByUUID(UUID uuid) {
        return groupNodeRepository.findGroupNodeByUuid(uuid).
                orElseThrow(
                        () -> new GroupNotFoundException(uuid));
    }

    private void saveInGraphDB(Group group) {
        GroupNode groupToSave = groupMapper.toNode(group);
        groupToSave.setOwner(userService.findUserNodeByUUID(group.getOwner().getUuid()));
        groupNodeRepository.save(groupToSave);
    }

}
