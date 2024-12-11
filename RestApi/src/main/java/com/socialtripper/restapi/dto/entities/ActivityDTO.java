package com.socialtripper.restapi.dto.entities;

/**
 * Data transfer object dla encji typu aktywności.
 *
 * @param id identyfikator aktywności
 * @param name nazwa aktywności
 */
public record ActivityDTO (Long id, String name) {
}
