package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.thumbnails.UserThumbnailDTO;
import com.socialtripper.restapi.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją użytkownika {@link User},
 * a data transfer object częściowej informacji o użytkowniku {@link UserThumbnailDTO}.
 */
@Component
public class UserThumbnailMapper {
    /**
     * Komponent odpowiedzialny za mapowanie kraju.
     */
    private CountryMapper countryMapper;
    /**
     * Komponent odpowiedzialny za mapowanie języka użytkownika.
     */
    private UserLanguageMapper userLanguageMapper;
    /**
     * Komponent odpowiedzialny za mapowanie aktywności użytkownika.
     */
    private UserActivityMapper userActivityMapper;

    /**
     * Setter wstrzykujący komponent mapujący kraj.
     *
     * @param countryMapper komponent odpowiedzialny za mapowanie kraju
     */
    @Autowired
    public void setCountryMapper(CountryMapper countryMapper) {
        this.countryMapper = countryMapper;
    }

    /**
     * Setter wstrzykujący komponent mapujący język użytkownika.
     *
     * @param userLanguageMapper koponent odpowiedzialny za mapowanie języka użytkownika
     */
    @Autowired
    public void setUserLanguageMapper(UserLanguageMapper userLanguageMapper) {
        this.userLanguageMapper = userLanguageMapper;
    }

    /**
     * Setter wstrzykujący komponent mapujący aktywność użytkownika.
     *
     * @param userActivityMapper komponent odpowiedzialny za mapowanie aktywności użytkownika
     */
    @Autowired
    public void setUserActivityMapper(UserActivityMapper userActivityMapper) {
        this.userActivityMapper = userActivityMapper;
    }

    /**
     * Metoda mapująca encję użytkownika do data transfer object częściowej informacji o użytkowniku.
     *
     * @param user encja użytkownika
     * @return data transfer object częściowej informacji o użytkowniku
     */
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
