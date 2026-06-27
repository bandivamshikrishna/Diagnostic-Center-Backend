package com.dc.entity;

import com.dc.enums.TokenTypeEnum;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Audited
@Entity
@Table(name = "tbl_user_token_details")
public class UserAuthTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenTypeEnum tokenType;

    @Column(unique = true,nullable = false)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false)
    private UserAuthEntity user;

    @Column(nullable = false)
    private LocalDateTime tokenCreatedDate;

    @Column(nullable = false)
    private LocalDateTime tokenExpirationDate;

    private Boolean tokenRevoked;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserAuthEntity getUser() {
        return user;
    }

    public void setUser(UserAuthEntity user) {
        this.user = user;
    }

    public LocalDateTime getTokenCreatedDate() {
        return tokenCreatedDate;
    }

    public void setTokenCreatedDate(LocalDateTime tokenCreatedDate) {
        this.tokenCreatedDate = tokenCreatedDate;
    }

    public LocalDateTime getTokenExpirationDate() {
        return tokenExpirationDate;
    }

    public void setTokenExpirationDate(LocalDateTime tokenExpirationDate) {
        this.tokenExpirationDate = tokenExpirationDate;
    }


    public TokenTypeEnum getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenTypeEnum tokenType) {
        this.tokenType = tokenType;
    }

    public Boolean getTokenRevoked() {
        return tokenRevoked;
    }

    public void setTokenRevoked(Boolean tokenRevoked) {
        this.tokenRevoked = tokenRevoked;
    }
}
