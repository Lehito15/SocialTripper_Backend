package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.entities.Activity;
import com.socialtripper.restapi.entities.enums.Activities;
import com.socialtripper.restapi.repositories.relational.ActivityRepository;
import com.socialtripper.restapi.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementacja serwisu zarządzającego operacjami wykonywanymi na encjach aktywności.
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    /**
     * Repozytorium zarządzające encjami aktywności.
     */
    private final ActivityRepository activityRepository;

    /**
     * Konstruktor wstrzykujący repozytorium encji aktywności.
     *
     * @param activityRepository repozytorium zarządzające encjami aktywności
     */
    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * {@inheritDoc}
     * <p>
     *     W przypadku gdy nazwa aktywności nie jest związana z żadną encją rzucany jest wyjątek.
     * </p>
     */
    @Override
    public Activity getActivityReference(String activityName) {
        return activityRepository.getReferenceById(
                Activities.fromActivityName(activityName).orElseThrow(
                        () -> new IllegalArgumentException("Invalid activity name: " + activityName)
                ).getId());
    }
}
