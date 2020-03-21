package com.info.eassignment.users.controller;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.info.eassignment.users.exception.BadRequestException;
import com.info.eassignment.users.payload.ApiResponse;
import com.info.eassignment.users.payload.JwtAuthenticationResponse;
import com.info.eassignment.users.payload.LoginRequest;
import com.info.eassignment.users.payload.RefreshTokenRequest;
import com.info.eassignment.users.payload.SignUpRequest;
import com.info.eassignment.users.persistence.model.JwtRefreshToken;
import com.info.eassignment.users.persistence.model.Role;
import com.info.eassignment.users.persistence.model.User;
import com.info.eassignment.users.persistence.repository.JwtRefreshTokenRepository;
import com.info.eassignment.users.persistence.repository.RoleRepository;
import com.info.eassignment.users.persistence.repository.UserRepository;
import com.info.eassignment.users.security.JwtTokenProvider;
import com.info.eassignment.users.security.UserPrincipal;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    JwtRefreshTokenRepository jwtRefreshTokenRepository;

    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationInMs;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String accessToken = tokenProvider.generateToken(userPrincipal);
        String refreshToken = tokenProvider.generateRefreshToken();

        saveRefreshToken(userPrincipal, refreshToken);

        return ResponseEntity.ok(new JwtAuthenticationResponse(accessToken, refreshToken, jwtExpirationInMs,getRolesFromAuthorities(userPrincipal.getAuthorities())));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshAccessToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return jwtRefreshTokenRepository.findById(refreshTokenRequest.getRefreshToken()).map(jwtRefreshToken -> {
            User user = jwtRefreshToken.getUser();
            UserPrincipal userPrincipal = UserPrincipal.create(user);
            String accessToken = tokenProvider.generateToken(userPrincipal); // Todo update new token expire date
            return ResponseEntity.ok(new JwtAuthenticationResponse(accessToken, jwtRefreshToken.getToken(), jwtExpirationInMs, getRolesFromAuthorities(userPrincipal.getAuthorities())));
        }).orElseThrow(() -> new BadRequestException("Invalid Refresh Token"));
    }

    private void saveRefreshToken(UserPrincipal userPrincipal, String refreshToken) {
        // Persist Refresh Token

        JwtRefreshToken jwtRefreshToken = new JwtRefreshToken(refreshToken);
        jwtRefreshToken.setUser(userRepository.getOne(userPrincipal.getId()));

        Instant expirationDateTime = Instant.now().plus(jwtExpirationInMs, ChronoUnit.MILLIS); // Todo Add this in application.properties

        jwtRefreshToken.setExpirationDateTime(expirationDateTime);

        jwtRefreshTokenRepository.save(jwtRefreshToken);
    }

    /* Signup for only user role */

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        Role userRole = roleRepository.findByName("ROLE_USER");
		LOGGER.info("user role ::: "+userRole.toString());

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}").buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User created successfully"));
    }

    // Non API
    private String[] getRolesFromAuthorities(final Collection<? extends GrantedAuthority> authorities) {
        List<String> roles = authorities.stream()
                                        .map(r -> r.getAuthority().replaceFirst("ROLE_",""))
                                        .collect(Collectors.toList());

        return roles.toArray(new String[0]);
    }
    
}
