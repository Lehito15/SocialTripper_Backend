package com.socialtripper.restapi.dto.entities;

/**
 * Data transfer object dla współrzędnych geograficznych.
 *
 * @param latitude szerokość geograficzna
 * @param longitude długość geograficzna
 */
public record PointDTO (Double latitude, Double longitude) {
}
