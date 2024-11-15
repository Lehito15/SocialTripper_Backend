package com.socialtripper.restapi.services;

import com.socialtripper.restapi.entities.Country;

public interface CountryService {
    Country getCountryReference(String name);
}
