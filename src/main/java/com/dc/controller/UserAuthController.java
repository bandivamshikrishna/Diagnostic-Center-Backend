package com.dc.controller;


import com.dc.dto.*;
import com.dc.entity.UserAuthEntity;
import com.dc.service.UserAuthService;
import com.dc.service.UserAuthTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserAuthController {

    @Value("${jwt.refresh.token.expiration}")
    Integer jwtRefreshExpiration;

    private final UserAuthService userAuthService;
    private final UserAuthTokenService userAuthTokenService;

    @PostMapping("/create")
    public ResponseEntity<Map<String,String>> createUser(@Valid @RequestBody UserCreateOrUpdateRequestDTO userCreateRequestDTO, @AuthenticationPrincipal UserAuthEntity userAuthEntity){
        Map<String,String> map = new HashMap<>();
        map.put("message", userAuthService.createUser(userCreateRequestDTO, userAuthEntity));
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Void> validToken(@RequestParam(name = "token") String token){
        userAuthTokenService.validateToken(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate-token")
    public ResponseEntity<Void> setUserPassword(@RequestParam(name = "token") String token, @Valid @RequestBody UserSetPasswordDTO userSetPasswordDTO){
            userAuthService.setUserPassword(token,userSetPasswordDTO.getPassword());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO, HttpServletResponse httpServletResponse){
        JWTTokens jwtTokens = userAuthService.loginUser(userLoginRequestDTO);
        httpServletResponse.setHeader("Authorization", "Bearer "+jwtTokens.getAccessToken());

        Cookie cookie = new Cookie("refreshToken", jwtTokens.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(jwtRefreshExpiration/1000);
        cookie.setPath("/api/user/refreshToken");
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getLoggedInUserDetails(@AuthenticationPrincipal UserAuthEntity userAuthEntity){
        return new ResponseEntity<>(userAuthService.getLoggedInUserDetails(userAuthEntity),HttpStatus.OK);
    }

    @GetMapping("/get-Roles")
    public ResponseEntity<List<Map<String,String>>> getUserRoles(){
        return new ResponseEntity<>(userAuthService.getUserRoles(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<UserListResponseDTO>> getAllUsers(
            @RequestParam(name = "filterType", required = false, defaultValue = "1") String filterType,
            @RequestParam(name = "ID", required = false, defaultValue = "") String userCode,
            @RequestParam(name = "fullName",required = false, defaultValue = "") String name,
            @RequestParam(name = "email", required = false, defaultValue = "") String email,
            @RequestParam(name = "role", required = false, defaultValue = "") String roleCode,
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
            @RequestParam(name = "endDate", required = false)
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "DESC") String sortDirection,
            @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @AuthenticationPrincipal UserAuthEntity userAuthEntity){

        Sort sort = Sort.by(sortBy);
        sort = sortDirection.equalsIgnoreCase("DESC") ? sort.descending() : sort.ascending();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return new ResponseEntity<>(userAuthService.getAllUsers(userAuthEntity,userCode, name,email,roleCode,startDate,endDate, filterType,pageRequest), HttpStatus.OK);
    }


    @PostMapping("/activateOrDeactivate/{id}")
    public ResponseEntity<Map<String,String>> activateOrDeactivateUser(@PathVariable("id") Long id){

        Map<String,String> map = new HashMap<>();
        map.put("message", userAuthService.activateOrDeActivateUser(id));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }


    @PostMapping("/unLock/{id}")
    public ResponseEntity<Map<String,String>> unLockUser(@PathVariable("id") Long id){
        Map<String, String> map = new HashMap<>();
        map.put("message", userAuthService.unLockUser(id));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getSpecificUserDetails(@PathVariable("id") Long id){
        return new ResponseEntity<>(userAuthService.getSpecificUserDetails(id), HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String,String>> updateUser(@Valid @RequestBody UserCreateOrUpdateRequestDTO userUpdateRequestDTO,
                                                         @PathVariable("id") Long id,
                                                         @AuthenticationPrincipal UserAuthEntity userAuthEntity){
        Map<String,String> map = new HashMap<>();
        map.put("message",userAuthService.updateUser(userUpdateRequestDTO,id,userAuthEntity));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
