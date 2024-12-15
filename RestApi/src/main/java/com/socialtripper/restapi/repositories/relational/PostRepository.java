package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repozytorium do zarządzania encjami postów {@link Post} w bazie Postgres.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    /**
     * Metoda zwracająca post o wskazanym UUID.
     *
     * @param post_uuid globalny, unikalny identyfikator postu w systemie
     * @return opcjonalny post, jeżeli istnieje w bazie danych
     */
    Optional<Post> findByUuid(UUID post_uuid);

    /**
     * Metoda zwracająca klucz główny postu o wskazanym UUID.
     *
     * @param post_uuid globalny, unikalny identyfikator postu w systemie
     * @return opcjonalny klucz główny postu, jeżeli istnieje w bazie danych
     */
    @Query(value = "select p.id from posts p where p.uuid = :post_uuid;", nativeQuery = true)
    Optional<Long> findIdByUuid(@Param("post_uuid") UUID post_uuid);

    /**
     * Metoda zwracająca trendujące posty. Kryterium stanowi liczba reakcji w czasie wskazanym poprzez parametr.
     * Paramtryzowana jest również liczba postów do pobrania.
     *
     * @param numberOfPosts liczba postów do pobrania
     * @param boundDate data graniczna publikacji dla uzwględnianych postów
     * @return lista trendujących postów
     */
    @Query(value = "select p from Post p " +
            "where p.isPublic = true and p.dateOfPost >= :boundDate" +
            " order by p.reactionsNumber desc" +
            " limit :numberOfPosts")
    List<Post> findTrendingPosts(@Param("numberOfPosts") int numberOfPosts, @Param("boundDate") LocalDateTime boundDate);

    /**
     * Metoda zwracająca posty użytkownika. Wywołanie funkcji zdefiniowanej w bazie Postgres.
     *
     * @param user_uuid globalny, unikalny identyfikator konta publikującego użytkownika w systemie
     * @return lista opublikowanych postów
     */
    @Query(value = "select * from find_user_posts(:user_uuid);", nativeQuery = true)
    List<Post> findPostsByUserUUID(@Param("user_uuid") UUID user_uuid);

    /**
     * Metoda zwracająca posty personalne użytkownika. Wywołanie funkcji zdefiniowanej w bazie Postgres.
     *
     * @param user_uuid globalny, unikalny identyfikator użytkownika publikującego w systemie
     * @return lista postów personalnych
     */
    @Query(value = "select * from find_personal_posts(:user_uuid)", nativeQuery = true)
    List<Post> findPersonalPostsByUUID(@Param("user_uuid") UUID user_uuid);

    /**
     * Metoda zwracająca listę postów opublikowanych w grupie. Wywołanie funkcji zdefiniowanej w bazie Postgres.
     *
     * @param group_uuid globalny, unikalny identyfikator grupy w systemie
     * @return lista postów w grupie
     */
    @Query(value = "select * from find_group_posts(:group_uuid);", nativeQuery = true)
    List<Post> findPostsByGroupUUID(@Param("group_uuid") UUID group_uuid);

    /**
     * Metoda zwracająca listę postów opublikowanych w wydarzeniu. Wywołanie funkcji zdefinowanej w bazie Postgres.
     *
     * @param event_uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return lista postów w wydarzeniu
     */
    @Query(value = "select * from find_event_posts(:event_uuid);", nativeQuery = true)
    List<Post> findPostsByEventUUID(@Param("event_uuid") UUID event_uuid);

    /**
     * Metoda dezaktywująca posty użytkownika poprzez ustawienie flagi is_expired na true.
     * Wywołanie funkcji zdefiniowanej w bazie Postgres.
     *
     * @param user_uuid globalny, unikalny identyfikator użytkownika publikującego w systemie
     * @return liczba dezaktywowanych postów
     */
    @Query(value = "select expire_user_posts(:user_uuid);", nativeQuery = true)
    int expirePostsByUserUUID(@Param("user_uuid") UUID user_uuid);

    /**
     * Metoda blokująca posty użytkownika poprzez ustawienie flagi is_locked na true.
     * Wywołanie funkcji zdefinowanej w bazie Postgres.
     *
     * @param user_uuid globalny, unikalny identyfikator użytkowniak publikującego w systemie
     * @return liczba zablokowanych postów
     */
    @Query(value = "select lock_user_posts(:user_uuid);", nativeQuery = true)
    int lockPostsByUserUUID(@Param("user_uuid") UUID user_uuid);

    /**
     * Metoda dezaktywująca pojedynczy post użytkownika poprzez ustawienie flagi is_expired na true.
     * Wywołanie funkcji zdefiniowanej w bazie Postgres.
     *
     * @param post_uuid globalny, unikalny identyfikator postu w systemie
     * @return true jeżeli operacja się powiodła, false w przeciwnym wypadku
     */
    @Query(value = "select expire_post(:post_uuid);", nativeQuery = true)
    boolean expirePostByUUID(@Param("post_uuid") UUID post_uuid);

}
