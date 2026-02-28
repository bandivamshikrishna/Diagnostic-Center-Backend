package com.dc.repository;

import com.dc.entity.UserAuthTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserAuthTokenRepository extends JpaRepository<UserAuthTokenEntity, Long> {
    public Optional<UserAuthTokenEntity> findByToken(String token);

    @Modifying
    @Transactional
    @Query(value = "update tbl_user_token_details set token_revoked = true where token_revoked = false and user_id =:userID", nativeQuery = true)
    public void revokeActiveTokens(@Param("userID") Long userID);
}
