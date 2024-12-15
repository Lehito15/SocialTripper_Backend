package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.entities.Language;
import com.socialtripper.restapi.exceptions.GroupNotFoundException;
import com.socialtripper.restapi.repositories.relational.LanguageRepository;
import com.socialtripper.restapi.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementacja serwisu zarządzającego operacjami wykonywanymi na encjach języka.
 */
@Service
public class LanguageServiceImpl implements LanguageService {
    /**
     * Repozytorium zarządzające encjami języków.
     */
    private final LanguageRepository languageRepository;

    /**
     * Konstruktor wstrzykujący repozytorium zarządzające encjami języków
     * @param languageRepository repozytorium zarządzające endjami języków
     */
    @Autowired
    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    /**
     * {@inheritDoc}
     * <p>
     *     W przypadku gdy encja nie istnieje w bazie rzucany jest wyjątek.
     * </p>
     */
    @Override
    public Language getLanguageReference(String name) {
        return languageRepository.getReferenceById(
                languageRepository.findIdByName(name).orElseThrow(
                        () -> new IllegalArgumentException("Language with name " + name + " not found")
                ));
    }
}
