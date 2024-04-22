package ru.anykeyers.videoservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.ApplicationConfig;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.function.Function;

/**
 * Сервис обработки JWT токенов
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final ApplicationConfig applicationConfig;

    /**
     * Получить имя пользователя из токена
     *
     * @param token токен
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Создать токен для пользователя
     *
     * @param username имя пользователя
     */
    public String generateToken(String username) {
        Date nowTime = new Date();
        Date expirationTime = new Date(System.currentTimeMillis() + applicationConfig.getJwtTokenLifecycle());
        return Jwts.builder()
                .setClaims(Collections.emptyMap())
                .setSubject(username)
                .setIssuedAt(nowTime)
                .setExpiration(expirationTime)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Получить секретный ключ
     */
    private Key getSignInKey() {
        String secretKey = applicationConfig.getJwtSecretKey();
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

}
