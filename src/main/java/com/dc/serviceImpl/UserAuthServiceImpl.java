package com.dc.serviceImpl;

import com.dc.dto.JWTTokens;
import com.dc.dto.UserCreateRequestDTO;
import com.dc.dto.UserLoginRequestDTO;
import com.dc.dto.UserResponseDTO;
import com.dc.entity.UserAuthEntity;
import com.dc.entity.UserRoleEntity;
import com.dc.entity.VendorBranchEntity;
import com.dc.entity.VendorEntity;
import com.dc.enums.TokenTypeEnum;
import com.dc.exception.*;
import com.dc.mapper.UserAuthMapper;
import com.dc.repository.UserAuthRepository;
import com.dc.repository.UserRoleRepository;
import com.dc.repository.VendorBranchRepository;
import com.dc.repository.VendorRepository;
import com.dc.service.UserAuthService;
import com.dc.service.UserAuthTokenService;
import com.dc.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserAuthServiceImpl implements UserAuthService, UserDetailsService {

    private final UserAuthRepository userAuthRepository;
    private final VendorRepository vendorRepository;
    private final UserAuthTokenService userAuthTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private final VendorBranchRepository vendorBranchRepository;
    private final UserRoleRepository userRoleRepository;

    @Value("${password.setOrReset.token.expiration}")
    private Long passwordExpirationMinutes;

    @Value("${jwt.refresh.token.expiration}")
    private Long refreshTokenExpiration;


    public UserAuthServiceImpl(UserAuthRepository userAuthRepository,VendorRepository vendorRepository,
                               UserAuthTokenService userAuthTokenService,PasswordEncoder passwordEncoder,
                               AuthenticationManager authenticationManager, JWTUtils jwtUtils,
                               VendorBranchRepository vendorBranchRepository, UserRoleRepository userRoleRepository
                               ){
        this.userAuthRepository = userAuthRepository;
        this.vendorRepository = vendorRepository;
        this.userAuthTokenService = userAuthTokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.vendorBranchRepository = vendorBranchRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public String createUser(UserCreateRequestDTO userCreateRequestDTO, UserAuthEntity createdBy) {
        if(userAuthRepository.existsByEmail(userCreateRequestDTO.getEmail().toLowerCase().trim()))
            throw new UserException("email",String.format("User Already with Email ID : %s", userCreateRequestDTO.getEmail()));

        VendorEntity vendor = vendorRepository.findByVendorCode(userCreateRequestDTO.getVendor()).orElseThrow(
                () -> new VendorException("vendorID",String.format("Vendor Not Found with ID : %s", userCreateRequestDTO.getVendor()))
        );

        VendorBranchEntity vendorBranch = vendorBranchRepository.findByBranchCode(userCreateRequestDTO.getVendorBranch()).orElseThrow(
                ()-> new VendorException("branch", String.format("Branch Not Found with Code : %s", userCreateRequestDTO.getVendorBranch()))
        );

        UserRoleEntity userRole = userRoleRepository.findByRoleCode(userCreateRequestDTO.getRole()).orElseThrow(
                () -> new RoleNotFoundException("Invalid Role")
        );

        UserAuthEntity createdByUserID = userAuthRepository.findById(createdBy.getId()).orElseThrow(
                () -> new UserException("createdByUserID",String.format("User Not Found with ID : %d", createdBy.getId()))
        );

        UserAuthEntity userAuthEntity = UserAuthMapper.fromCreateDTOToEntity(userCreateRequestDTO);
        userAuthEntity.setEmail(userCreateRequestDTO.getEmail().toLowerCase());
        userAuthEntity.setVendorID(vendor);
        userAuthEntity.setVendorBranch(vendorBranch);
        userAuthEntity.setRole(userRole);
        userAuthEntity.setCreatedByUserID(createdByUserID);
        userAuthEntity.setActive(true);
        userAuthEntity.setLocked(true);
        userAuthEntity.setCreatedDate(LocalDate.now());
        userAuthEntity.setUserCode("U"+String.format("%010d", userAuthRepository.getNextUserCode()));
        String id = userAuthRepository.save(userAuthEntity).getUserCode();
        userAuthTokenService.createToken(userAuthEntity,"",
                TokenTypeEnum.SET_OR_RESET_PASSWORD_TOKEN, passwordExpirationMinutes);
        return String.format("User Created Successfully with ID : %s",id);
    }

    @Override
    public void setUserPassword(String token,String password) {
        if(userAuthTokenService.validateToken(token)) {
            UserAuthEntity userAuthEntity = userAuthTokenService.getUserFromToken(token);
            userAuthEntity.setPassword(passwordEncoder.encode(password));
            userAuthEntity.setLocked(false);
            userAuthRepository.save(userAuthEntity);
            userAuthTokenService.updateTokenUsed(token);
        }
    }


    @Override
    public JWTTokens loginUser(UserLoginRequestDTO userLoginRequestDTO){
        JWTTokens jwtTokens = new JWTTokens();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginRequestDTO.getEmail(), userLoginRequestDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(authentication.isAuthenticated()){
            jwtTokens.setAccessToken(jwtUtils.generateToken(userLoginRequestDTO.getEmail(),true));
            jwtTokens.setRefreshToken(jwtUtils.generateToken(userLoginRequestDTO.getEmail(),false));
            userAuthTokenService.invalidateOldRefreshTokens(userLoginRequestDTO.getEmail());
            userAuthTokenService.createToken((UserAuthEntity) authentication.getPrincipal(), jwtTokens.getRefreshToken(),
                        TokenTypeEnum.REFRESH_TOKEN, refreshTokenExpiration);
        }
        return  jwtTokens;
    }

    @Override
    public UserResponseDTO getUserDetails(@AuthenticationPrincipal UserAuthEntity userAuthEntity) {
        return UserAuthMapper.fromEntityToDTO(userAuthEntity);
    }

    @Override
    public List<Map<String, String>> getUserRoles() {
        return userRoleRepository.findByRoleCodeNot("AD").stream().map(role ->{
            Map<String, String> map = new HashMap<>();
            map.put("name", role.getRoleCode());
            map.put("value", role.getRoleName());
            return map;
        }).toList();
    }


    @Override
    public UserAuthEntity loadUserByUsername(String email) throws GenericException {
        return userAuthRepository.findByEmail(email).orElseThrow(
                ()-> new UserException("message",String.format("User Not Found with Email : %s", email))
        );
    }
}
