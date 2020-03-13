package com.info.eassignment.users.payload;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long expiresInMsec;
    private Collection<? extends GrantedAuthority> authorities;
    private String[] roles;


    public JwtAuthenticationResponse(String accessToken, String refreshToken, Long expiresInMsec,
            Collection<? extends GrantedAuthority> authorities) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresInMsec = expiresInMsec;
        this.authorities = authorities;
    }

    public JwtAuthenticationResponse(String accessToken, String refreshToken, Long expiresInMsec,String[] roles) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresInMsec = expiresInMsec;
        this.roles = roles;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresInMsec() {
        return expiresInMsec;
    }

    public void setExpiresInMsec(Long expiresInMsec) {
        this.expiresInMsec = expiresInMsec;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}