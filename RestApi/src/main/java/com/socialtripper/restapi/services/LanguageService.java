package com.socialtripper.restapi.services;

import com.socialtripper.restapi.entities.Language;

public interface LanguageService {
    Language getLanguageReference(String name);
}
