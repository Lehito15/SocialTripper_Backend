package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.CountryDTO;
import com.socialtripper.restapi.entities.Country;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją {@link Country}, a {@link CountryDTO}.
 */
@Component
public class CountryMapper {
    /**
     * Metoda mapująca data transfer object kraju do encji kraju.
     *
     * @param countryDTO data transfer object kraju
     * @return encja kraju
     */
    public Country toEntity(CountryDTO countryDTO) {
        if (countryDTO == null) return null;
        return new Country(
             countryDTO.name()
        );
    }

    /**
     * Metoda mapująca encję kraju na data transfer object kraju.
     *
     * @param country encja kraju
     * @return data transfer object kraju
     */
    public CountryDTO toDTO(Country country) {
        if (country == null) return null;
        return new CountryDTO(
                country.getName()
        );
    }
}
