package com.socialtripper.restapi.services;

import com.socialtripper.restapi.entities.Country;

/**
 * Serwis zarządzający operacjami wykonywanymi na encjach krajów.
 */
public interface CountryService {
    /**
     * Metoda zwracająca referencję kraju na podstawie jego nazwy.
     *
     * @param name nazwa kraju
     * @return referencja na encję kraju w bazie danych
     */
    Country getCountryReference(String name);
}
