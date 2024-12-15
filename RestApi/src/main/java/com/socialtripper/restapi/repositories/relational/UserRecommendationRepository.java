package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.Event;
import com.socialtripper.restapi.entities.User;
import com.socialtripper.restapi.entities.UserRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repozytorium do zarządzania rekomendacjami użytkowników {@link UserRecommendation} w bazie Postgres.
 */
@Repository
public interface UserRecommendationRepository extends JpaRepository<UserRecommendation, Long> {
    /**
     * Metoda zwracająca wydarzenia rekomendowane dla użytkownika. Kryterium wyboru jest brak uczestnictwa
     * w wydarzeniu, którego członkiem jest rekomendowany użytkownik.
     *
     * @param userUuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return lista rekomendowanych wydarzeń
     */
    @Query(value = """
    select e from Event e
    join EventParticipant ep on ep.event.id = e.id
    join UserRecommendation ur on ur.recommendedUser.id = ep.participant.id
    left join EventParticipant epUser on epUser.event.id = e.id and epUser.participant.id = ur.user.id
    join Account a on a.id = ur.user.id
    where a.uuid = :userUuid and e.eventStartTime > current_timestamp
    and epUser.id is null""")
    List<Event> findUserRecommendedEvents(@Param("userUuid") UUID userUuid);

    /**
     * Metoda zwracająca rekomendowanych użytkowników do obserwacji.
     *
     * @param userUuid globalny, unikalny identyfikator użytkownika w systemie
     * @return lista rekomendowanych użytkowników
     */
    @Query(value = """
    select ru from User u
    join UserRecommendation ur on u.id = ur.user.id
    join User ru on ur.recommendedUser.id = ru.id
    where u.account.uuid = :userUuid""")
    List<User> findUserRecommendedAccounts(@Param("userUuid") UUID userUuid);
}
