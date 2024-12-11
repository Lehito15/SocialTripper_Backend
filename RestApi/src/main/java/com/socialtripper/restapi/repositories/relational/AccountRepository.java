package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repozytorium do zarządzania encjami kont użytkowników {@link Account} w bazie Postgres.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * Metoda zwracająca encję konta użytkownika o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return opcjonalna encja konta, jeżeli istnieje w bazie danych
     */
    Optional<Account> findByUuid(UUID uuid);

    /**
     * Metoda zwracająca encję konta użytkownika o wskazanym adresie email.
     *
     * @param email adres email
     * @return opcjonalna encja konta, jeżeli istnieje w bazie danych
     */
    Optional<Account> findByEmail(String email);

    /**
     * Metoda wykonująca natywne zapytanie zwracająca wartość klucza głownego dla konta o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return klucz główny konta
     */
    @Query(value = "select a.id from accounts a WHERE a.uuid = :uuid", nativeQuery = true)
    Optional<Long> findIdByUUID(@Param("uuid") UUID uuid);

    /**
     * Metoda wywołująca funkcję zdefinowaną w bazie danych Postgres, zwracającą wartość logiczną,
     * która informuje czy użytkownik o wskazanej nazwie istnieje.
     *
     * @param username nazwa użytkownika
     * @return wartość logiczna informująca czy użytkownik o wskazanej nazwie istnieje
     */
    @Query(value = "select * from does_username_exist(:username)",nativeQuery = true)
    Boolean doesUsernameExist(@Param("username") String username);

    /**
     * Metoda wywołująca funkcję zdefiniowaną w bazie Postgres, zwracającą wartość logiczną,
     * która informuje czy dany adres email jest w już w użyciu.
     *
     * @param email adres email
     * @return wartość logiczna informująca czy adres email jest w użytku
     */
    @Query(value = "select * from is_email_in_use(:email);", nativeQuery = true)
    Boolean isEmailInUse(@Param("email") String email);

    /**
     * Metoda wywołująca funkcję zdefinowaną w bazie Postgres, zwracającą wartość logiczną,
     * która informuje czy dany numer telefonu jest już w użyciu.
     *
     * @param phone numer telefonu
     * @return wartość logiczna informująca czy numer telefonu jest w użytku
     */
    @Query(value = "select * from is_phone_in_use(:phone);", nativeQuery = true)
    Boolean isPhoneInUse(@Param("phone") String phone);

    /**
     * Metoda zwracająca listę kont, które w nazwie użytkownika zawierają podany łańcuch.
     *
     * @param nickname łańcuch nazwy użytkownika
     * @return lista kont zawierających podany łańcuch
     */
    @Query(value = "select a from Account a where a.nickname like %:nickname%")
    List<Account> findByNicknameSubstring(@Param("nickname") String nickname);
}
