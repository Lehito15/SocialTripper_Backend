package com.socialtripper.restapi.dto.entities;

import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.entities.LocationScope;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * Data transfer object dla encji grupy.
 *
 * @param uuid globalny, unikalny identyfikator grupy w systemie
 * @param name nazwa grupy
 * @param numberOfMembers aktualna liczba użytkowników
 * @param isPublic inforamacja o dostępie do grupy (publiczny / prywatny)
 * @param description opis grupy
 * @param rules zasady obowiązujące uczestników grupy
 * @param dateOfCreation data utworzenia grupy
 * @param homePageUrl dostęp do zasobu grupy
 * @param locationLongitude długość geograficzna lokalizacji grupy
 * @param locationLatitude szerokośćc geograficzna lokalizacji grupy
 * @param locationScope zasięg grupy - {@link LocationScopeDTO}
 * @param owner właściciel grupy - {@link AccountThumbnailDTO}
 * @param iconUrl link do ikony grupy
 * @param activities aktywności przypisane do grupy - {@link ActivityDTO}
 * @param languages języki przypisane do grupy - {@link LanguageDTO}
 */
public record GroupDTO (UUID uuid, String name, Integer numberOfMembers,
                        Boolean isPublic, String description, String rules,
                        LocalDate dateOfCreation, String homePageUrl, BigDecimal locationLongitude,
                        BigDecimal locationLatitude, LocationScopeDTO locationScope, AccountThumbnailDTO owner,
                        String iconUrl, Set<ActivityDTO> activities, Set<LanguageDTO> languages
) {
}
