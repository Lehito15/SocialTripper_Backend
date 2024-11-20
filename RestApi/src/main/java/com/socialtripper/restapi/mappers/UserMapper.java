package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.UserDTO;
import com.socialtripper.restapi.entities.User;
import com.socialtripper.restapi.nodes.UserNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private AccountMapper accountMapper;
    private CountryMapper countryMapper;
    private UserLanguageMapper userLanguageMapper;
    private UserActivityMapper userActivityMapper;

    @Autowired
    public void setAccountMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Autowired
    public void setCountryMapper(CountryMapper countryMapper) {
        this.countryMapper = countryMapper;
    }

    @Autowired
    public void setUserLanguageMapper(UserLanguageMapper userLanguageMapper) {
        this.userLanguageMapper = userLanguageMapper;
    }

    @Autowired
    public void setUserActivityMapper(UserActivityMapper userActivityMapper) {
        this.userActivityMapper = userActivityMapper;
    }

    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) return null;
        return new User(
                userDTO.uuid(),
                userDTO.name(),
                userDTO.surname(),
                userDTO.gender(),
                userDTO.dateOfBirth(),
                userDTO.weight(),
                userDTO.height(),
                userDTO.physicality()
        );
    }

    public UserDTO toDTO(User user) {
        if (user == null) return null;
        return new UserDTO(
                user.getUuid(),
                user.getName(),
                user.getSurname(),
                user.getGender(),
                user.getDateOfBirth(),
                user.getWeight(),
                user.getHeight(),
                user.getPhysicality(),
                accountMapper.toDTO(user.getAccount()),
                countryMapper.toDTO(user.getCountry()),
                user.getUserLanguages().stream().map(userLanguageMapper::toDTO).collect(Collectors.toSet()),
                user.getUserActivities().stream().map(userActivityMapper::toDTO).collect(Collectors.toSet())
        );
    }

    public UserNode toNode(User user) {
        if (user == null) return null;
        return new UserNode(
                user.getUuid(),
                user.getName(),
                user.getSurname(),
                user.getAccount().getNickname(),
                user.getAccount().isPublic(),
                user.getAccount().isLocked(),
                user.getAccount().getProfilePictureUrl(),
                user.getUserLanguages().stream().map(
                        userLanguage -> userLanguage.getLanguage().getName()).collect(Collectors.toSet()),
                user.getUserActivities().stream().map(
                        userActivity -> userActivity.getActivity().getName()).collect(Collectors.toSet())
        );
    }

    public User copyNonNullValues(User user, UserDTO userDTO) {
        if (userDTO == null) return null;
        if (userDTO.uuid() != null) user.setUuid(userDTO.uuid());
        if (userDTO.name() != null) user.setName(userDTO.name());
        if (userDTO.surname() != null) user.setSurname(userDTO.surname());
        if (userDTO.gender() != null) user.setGender(userDTO.gender());
        if (userDTO.dateOfBirth() != null) user.setDateOfBirth(userDTO.dateOfBirth());
        if (userDTO.weight() != null) user.setWeight(userDTO.weight());
        if (userDTO.height() != null) user.setHeight(userDTO.height());
        if (userDTO.physicality() != null) user.setPhysicality(userDTO.physicality());
        return user;
    }
}
