package com.socialtripper.restapi.services.implementation;

import com.socialtripper.restapi.entities.Activity;
import com.socialtripper.restapi.entities.enums.Activities;
import com.socialtripper.restapi.repositories.ActivityRepository;
import com.socialtripper.restapi.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Activity getActivityReference(String activityName) {
        return activityRepository.getReferenceById(
                Activities.fromActivityName(activityName).orElseThrow(
                        () -> new IllegalArgumentException("Invalid activity name: " + activityName)
                ).getId());
    }
}
