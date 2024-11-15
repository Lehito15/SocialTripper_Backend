package com.socialtripper.restapi.repositories;

import com.socialtripper.restapi.entities.Language;
import com.socialtripper.restapi.entities.UserLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserLanguageRepository extends JpaRepository<UserLanguage, Long> {
    @Query("select ul from UserLanguage ul where ul.user.uuid = :userUuid and ul.language = :language")
    Optional<UserLanguage> getUserLanguageExperience(@Param("userUuid") UUID userUuid, @Param("language") Language language);
}
