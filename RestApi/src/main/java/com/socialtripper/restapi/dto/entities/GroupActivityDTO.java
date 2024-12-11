package com.socialtripper.restapi.dto.entities;

/**
 * Data transfer object dla encji aktywności grupowej.
 *
 * @param group grupa - {@link GroupDTO}
 * @param activity aktywność - {@link ActivityDTO}
 */
public record GroupActivityDTO (GroupDTO group, ActivityDTO activity) {
}
