package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.UserActivityDTO;
import com.socialtripper.restapi.dto.entities.UserDTO;
import com.socialtripper.restapi.dto.entities.UserLanguageDTO;
import com.socialtripper.restapi.entities.User;
import com.socialtripper.restapi.entities.UserActivity;
import com.socialtripper.restapi.entities.UserLanguage;
import com.socialtripper.restapi.nodes.UserNode;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

public interface UserService {
    UserDTO findUserByUUID(UUID uuid);
    UserDTO createUser(UserDTO userDTO, MultipartFile profilePicture);
    UserDTO updateUser(UUID uuid, UserDTO userDTO);
    User getUserReference(UUID uuid);
    User getNewUserWithReferences(UserDTO userDTO, MultipartFile profilePicture);
    UserActivity addActivity(UUID uuid, UserActivityDTO userActivityDTO);
    UserLanguage addLanguage(UUID uuid, UserLanguageDTO userLanguageDto);
    UserActivityDTO setUserActivity(UUID uuid, UserActivityDTO userActivityDTO);
    UserLanguageDTO setUserLanguage(UUID uuid, UserLanguageDTO userLanguageDTO);
    UserNode findUserNodeByUUID(UUID uuid);
    void saveUserInGraphDB(User user);
    void saveUserInGraphDB(UserNode userNode);
}
