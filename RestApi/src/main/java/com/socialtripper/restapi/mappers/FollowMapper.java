package com.socialtripper.restapi.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {
    private final AccountThumbnailMapper accountThumbnailMapper;

    @Autowired
    public FollowMapper(AccountThumbnailMapper accountThumbnailMapper) {
        this.accountThumbnailMapper = accountThumbnailMapper;
    }


}
