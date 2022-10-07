package com.computer.server.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.computer.server.entity.domain.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY ="NMA8JPctFuna59f5";
    public String create(UserEntity userEntity){
        Date expireDate = Date.from(Instant.now().plus(1,ChronoUnit.DAYS));
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(Integer.toString(userEntity.getId())) .setIssuer("todo app") .setIssuedAt(new Date()) .setExpiration(expireDate) .compact();
    }
    public int validateAndGetUserId(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY) .parseClaimsJws(token) .getBody();
        return Integer.parseInt(claims.getSubject());
    }
}