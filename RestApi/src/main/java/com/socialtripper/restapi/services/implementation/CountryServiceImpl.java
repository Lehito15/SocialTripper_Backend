package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.entities.Country;
import com.socialtripper.restapi.repositories.relational.CountryRepository;
import com.socialtripper.restapi.services.CountryService;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country getCountryReference(String name) {
        return countryRepository.getReferenceById(
                countryRepository.findIdByName(name)
                        .orElseThrow(
                                () -> new IllegalArgumentException("Country with name " + name + " not found")
                        )
        );
    }
}
