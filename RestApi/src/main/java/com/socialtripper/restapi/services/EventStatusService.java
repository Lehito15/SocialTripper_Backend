package com.socialtripper.restapi.services;

import com.socialtripper.restapi.entities.EventStatus;

/**
 * Serwis zarządzający operacjami wykonywanymi na encjach statusu wydarzenia.
 */
public interface EventStatusService {
    /**
     * Metoda zwracająca referencję na encję statusu wydarzenia w bazie danych.
     *
     * @param id identyfikator statusu wydarzenia, klucz główny statusu
     * @return referencja na encję statusu wydarzenia w bazie danych
     */
    EventStatus getEventStatusReference(Long id);
}
