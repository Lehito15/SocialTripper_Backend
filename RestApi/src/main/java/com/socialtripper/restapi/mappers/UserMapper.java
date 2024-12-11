package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.UserDTO;
import com.socialtripper.restapi.entities.User;
import com.socialtripper.restapi.nodes.UserNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją {@link User},
 * węzłem użytkownika {@link UserNode},
 * a data transfer object {@link UserDTO}.
 */
@Component
public class UserMapper {
    /**
     * Komponent odpowiedzialny za mapowanie konta użytkownika.
     */
    private AccountMapper accountMapper;
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
     * Setter wstrzykujący komponent mapujący konto użytkownika.
     *
     * @param accountMapper komponent odpowiedzialny za mapowanie konta.
     */
    @Autowired
    public void setAccountMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

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
     * Metoda mapująca data transfer object użytkownika do encji.
     *
     * @param userDTO data transfer użytkownika
     * @return encja użytkownika
     */
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
                userDTO.bmi(),
                userDTO.physicality()
        );
    }

    /**
     * Metoda mapująca encję użytkownika do data transfer object.
     *
     * @param user encja użytkownika
     * @return data transfer object użytkownika
     */
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
                user.getBmi(),
                user.getPhysicality(),
                accountMapper.toDTO(user.getAccount()),
                countryMapper.toDTO(user.getCountry()),
                user.getUserLanguages().stream().map(userLanguageMapper::toDTO).collect(Collectors.toSet()),
                user.getUserActivities().stream().map(userActivityMapper::toDTO).collect(Collectors.toSet())
        );
    }

    /**
     * Metoda mapująca encję użytkownika do węzła.
     *
     * @param user encja użytkownika
     * @return węzeł użytkownika
     */
    public UserNode toNode(User user) {
        if (user == null) return null;
        return new UserNode(
                user.getUuid(),
                user.getName(),
                user.getSurname()
        );
    }

    /**
     * Metoda kopiująca wartości pól data transfer object niezwiązanych z innymi encjami do encji użytkownika.
     *
     * @param user encja użytkownika, do której mają zostać przkopiowane wartości pól
     * @param userDTO data transfer object użytkownika, z którego mają zostać przekopiowne wartości pól
     * @return encja użytkownika
     */
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
