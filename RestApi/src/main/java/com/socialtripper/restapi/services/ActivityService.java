package com.socialtripper.restapi.services;

import com.socialtripper.restapi.entities.Activity;

/**
 * Serwis zarządzający operajami wykonywanymi na encjach aktywności.
 */
public interface ActivityService {
    /**
     * Metoda zwracająca referencję encji aktywności istniejącej w bazie danych.
     *
     * @param activityName nazwa aktywności
     * @return referencja encji aktywności
     */
    Activity getActivityReference(String activityName);
}
