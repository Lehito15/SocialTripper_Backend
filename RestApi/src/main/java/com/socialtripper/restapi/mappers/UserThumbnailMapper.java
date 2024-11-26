package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.thumbnails.UserThumbnailDTO;
import com.socialtripper.restapi.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserThumbnailMapper {
    private CountryMapper countryMapper;
    private UserLanguageMapper userLanguageMapper;
    private UserActivityMapper userActivityMapper;

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

    public UserThumbnailDTO toDTO(User user) {
        if (user == null) return null;
        return new UserThumbnailDTO(
                user.getUuid(),
                user.getName(),
                user.getSurname(),
                user.getGender(),
                user.getDateOfBirth(),
                user.getWeight(),
                user.getHeight(),
                user.getPhysicality(),
                countryMapper.toDTO(user.getCountry()),
                user.getUserLanguages().stream().map(userLanguageMapper::toDTO).collect(Collectors.toSet()),
                user.getUserActivities().stream().map(userActivityMapper::toDTO).collect(Collectors.toSet())
        );
    }
}
