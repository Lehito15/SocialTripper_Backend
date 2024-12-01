package com.socialtripper.restapi.repositories.relational;

import com.socialtripper.restapi.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUuid(UUID uuid);
    Optional<Account> findByEmail(String email);

    @Query(value = "select a.id from accounts a WHERE a.uuid = :uuid", nativeQuery = true)
    Optional<Long> findIdByUUID(@Param("uuid") UUID uuid);

    @Query(value = "select * from does_username_exist(:username)",nativeQuery = true)
    Boolean doesUsernameExist(@Param("username") String username);

    @Query(value = "select * from is_email_in_use(:email);", nativeQuery = true)
    Boolean isEmailInUse(@Param("email") String email);

    @Query(value = "select * from is_phone_in_use(:phone);", nativeQuery = true)
    Boolean isPhoneInUse(@Param("phone") String phone);

    @Query(value = "select a from Account a where a.nickname like %:nickname%")
    List<Account> findByNicknameSubstring(@Param("nickname") String nickname);
}
