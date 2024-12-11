package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.entities.Country;
import com.socialtripper.restapi.repositories.relational.CountryRepository;
import com.socialtripper.restapi.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementacja serwisu odpowiedzialnego za operacje wykonywane na encjach krajów.
 */
@Service
public class CountryServiceImpl implements CountryService {
    /**
     * Repozytorium zarządzające encjami krajów.
     */
    private final CountryRepository countryRepository;

    /**
     * Konstruktor wstrzykujący repozytorium zarządzające encjami krajów.
     *
     * @param countryRepository repozytorium zarządzające encjami krajów
     */
    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * {@inheritDoc}
     * <p>
     *     W przypadku gdy kraj nie istnieje w bazie rzucany jest wyjątek.
     * </p>
     */
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
