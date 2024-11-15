package com.socialtripper.restapi.repositories;

import com.socialtripper.restapi.entities.GroupLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupLanguageRepository extends JpaRepository<GroupLanguage, Long> {
}
