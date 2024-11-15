package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

public record EventStatusChangedMessageDTO (UUID groupUUID, String status){
}
