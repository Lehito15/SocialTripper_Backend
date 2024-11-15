package com.socialtripper.restapi.services;

import com.socialtripper.restapi.entities.Activity;

public interface ActivityService {
    Activity getActivityReference(String activityName);
}
