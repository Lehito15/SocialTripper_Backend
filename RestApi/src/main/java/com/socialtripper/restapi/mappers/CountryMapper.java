package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.CountryDTO;
import com.socialtripper.restapi.entities.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    public Country toEntity(CountryDTO countryDTO) {
        if (countryDTO == null) return null;
        return new Country(
             countryDTO.name()
        );
    }

    public CountryDTO toDTO(Country country) {
        if (country == null) return null;
        return new CountryDTO(
                country.getName()
        );
    }
}
