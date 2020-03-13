package com.info.eassignment.users.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationInMs;

    public String generateToken(UserPrincipal userPrincipal) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setIssuedAt(new Date())
                .setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

        // return
        // Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setIssuedAt(new
        // Date())
        // .claim("roles",
        // getRolesFromAuthorities(userPrincipal.getAuthorities())).setExpiration(expiryDate)
        // .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String generateRefreshToken() {
        // generate a random UUID as refresh token
        return UUID.randomUUID().toString();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    private String[] getRolesFromAuthorities(final Collection<? extends GrantedAuthority> authorities) {

        List<String> roles = authorities.stream().map(r -> r.getAuthority()).collect(Collectors.toList());

        return roles.toArray(new String[0]);

    }

}
