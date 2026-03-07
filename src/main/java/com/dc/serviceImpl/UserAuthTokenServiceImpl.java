package com.dc.serviceImpl;

import com.dc.entity.UserAuthEntity;
import com.dc.entity.UserAuthTokenEntity;
import com.dc.enums.TokenTypeEnum;
import com.dc.exception.TokenException;
import com.dc.repository.UserAuthRepository;
import com.dc.repository.UserAuthTokenRepository;
import com.dc.service.UserAuthTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserAuthTokenServiceImpl implements UserAuthTokenService {

    private final UserAuthTokenRepository userAuthTokenRepository;
    private final UserAuthRepository userAuthRepository;

    public UserAuthTokenServiceImpl(UserAuthTokenRepository userAuthTokenRepository, UserAuthRepository userAuthRepository){
        this.userAuthTokenRepository = userAuthTokenRepository;
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public void createToken(UserAuthEntity userAuthEntity, String token, TokenTypeEnum tokenType, Long time) {
        UserAuthTokenEntity userAuthTokenEntity = new UserAuthTokenEntity();
        userAuthTokenEntity.setToken(token.isBlank() ? UUID.randomUUID().toString() : token);
        userAuthTokenEntity.setUser(userAuthEntity);
        userAuthTokenEntity.setTokenCreatedDate(LocalDateTime.now());
        userAuthTokenEntity.setTokenRevoked(false);
        userAuthTokenEntity.setTokenType(tokenType);
        userAuthTokenEntity.setTokenExpirationDate(tokenType == TokenTypeEnum.SET_OR_RESET_PASSWORD_TOKEN ?
                LocalDateTime.now().plusMinutes(time) :
                LocalDateTime.now().plusSeconds(time/1000));
        userAuthTokenRepository.save(userAuthTokenEntity);
    }

    @Override
    public Boolean validateToken(String token) {
        UserAuthTokenEntity userAuthTokenEntity = userAuthTokenRepository.findByToken(token).orElseThrow(
                ()-> new TokenException("token",String.format("Invalid Token %s", token))
        );

        if(userAuthTokenEntity.getTokenRevoked())
            throw new TokenException("token",String.format("Token %s is already used", token));

        if(userAuthTokenEntity.getTokenExpirationDate().isBefore(LocalDateTime.now()))
            throw new TokenException("token",String.format("The Token %s is already Expired", token));

        return true;
    }


    public UserAuthEntity getUserFromToken(String token){
        return userAuthTokenRepository.findByToken(token).orElseThrow(
                ()-> new TokenException("token",String.format("Invalid Token %s", token))
        ).getUser();
    }

    @Override
    public void updateTokenUsed(String token) {
        UserAuthTokenEntity userAuthTokenEntity = userAuthTokenRepository.findByToken(token).orElseThrow(
                ()-> new TokenException("token",String.format("Invalid Token %s", token))
        );
        userAuthTokenEntity.setTokenRevoked(true);
        userAuthTokenRepository.save(userAuthTokenEntity);
    }

    @Override
    public void invalidateOldRefreshTokens(String email) {
        Long userID = userAuthRepository.findByEmail(email).map(UserAuthEntity::getId).orElse(null);
        if(userID != null){
            userAuthTokenRepository.revokeActiveTokens(userID);

        }
    }

//    @Scheduled(fixedRate = 1000*60*60)
//    public void clearExpiredTokens(){
//        List<UserAuthTokenEntity> userAuthTokenExpired = userAuthTokenRepository.findAllByTokenRevokedTrueOrTokenExpirationDateBefore(LocalDateTime.now());
//
//        if(!userAuthTokenExpired.isEmpty()){
//            userAuthTokenRepository.deleteAll(userAuthTokenExpired);
//        }
//    }
}
