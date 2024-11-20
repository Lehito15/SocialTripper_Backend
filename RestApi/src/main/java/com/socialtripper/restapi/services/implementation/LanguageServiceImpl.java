package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.entities.Language;
import com.socialtripper.restapi.repositories.relational.LanguageRepository;
import com.socialtripper.restapi.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public Language getLanguageReference(String name) {
        return languageRepository.getReferenceById(
                languageRepository.findIdByName(name).orElseThrow(
                        () -> new IllegalArgumentException("Language with name " + name + " not found")
                ));
    }
}
