package com.socialtripper.restapi.dto.entities;

import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;

public record FollowDTO (AccountThumbnailDTO follower, AccountThumbnailDTO followed) {
}
