package com.socialtripper.restapi.services;

import com.socialtripper.restapi.entities.Language;

/**
 * Serwis zarządzający operacjami wykonywanymi na encjach języków.
 */
public interface LanguageService {
    /**
     * Metoda zwracająca referencję na encję języka w bazie danych.
     *
     * @param name nazwa języka
     * @return referencja na encję języka
     */
    Language getLanguageReference(String name);
}
