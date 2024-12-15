package com.socialtripper.restapi.dto.thumbnails;

import java.util.UUID;


/**
 * Data transfer object dla częściowej informacji o grupie.
 *
 * @param uuid globalny, unikalny identyfikator grupy w systemie
 * @param name nazwa grupy
 * @param numberOfMembers aktualna liczba członków
 * @param description opis grupy
 * @param homePageUrl dostęp do zasodu grupy
 * @param iconUrl link do ikony grupy
 */
public record GroupThumbnailDTO(UUID uuid, String name, Integer numberOfMembers,
                                String description, String homePageUrl, String iconUrl) {
}
