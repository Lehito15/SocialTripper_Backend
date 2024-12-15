package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.Account;
import com.socialtripper.restapi.entities.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

/**
 * Repozytorium do zarządzania encjami obserwacji {@link Follow} w bazie Postgres.
 */
@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    /**
     * Metoda wywołująca procedurę składowaną w bazie Postgres, która kończy obserwację użytkownika.
     * Osiągane jest to poprzez ustawienie daty końca obserwacji na obecną datę.
     *
     * @param followerUuid globalny, unikalny identyfikator konta użytkownika obserwującego w systemie
     * @param followedUuid globalny, unikalny identyfikator konta użytkownika obserwowanego w systemie
     */
    @Procedure(name = "userEndsFollowing")
    void userEndsFollowing(@Param("follower_uuid") UUID followerUuid, @Param("followed_uuid") UUID followedUuid);

    /**
     * Metoda zwracająca listę kont obserwowanych przez użytkownika.
     *
     * @param followerUuid globalny, unikalny identyfikator konta użytkownika obserwującego w systemie
     * @return lista kont obserwowanych przez użytkownika
     */
    @Query(
            "select f.followed from Follow f " +
                    "where f.follower.uuid = :followerUuid and f.followingTo is null"
    )
    List<Account> findAccountsFollowedBy(@Param("followerUuid") UUID followerUuid);

    /**
     * Metoda zwracająca listę kont obserwujących użytkownika.
     *
     * @param followedUuid globalny, unikalny identyfikator konta użytkownika obserwowanego w systemie
     * @return lista kont obserwujących
     */
    @Query(
            "select f.follower from Follow f " +
                    "where f.followed.uuid = :followedUuid and f.followingTo is null"
    )
    List<Account> findAccountsFollowingBy(@Param("followedUuid") UUID followedUuid);
}
