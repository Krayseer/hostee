package ru.anykeyers.videoservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.anykeyers.videoservice.domain.dto.ErrorDTO;

import java.nio.charset.StandardCharsets;

/**
 * Фильтр авторизации по JWT токену
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Начало заголовка авторизации
     */
    private static final String START_TOKEN_TAG = "Bearer ";

    /**
     * Индекс, с которого начинается непосредственно сам токен
     */
    private static final int INDEX_START_TOKEN_VALUE = 7;

    private final UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper;

    private final JwtService jwtService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith(START_TOKEN_TAG)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = authorizationHeader.substring(INDEX_START_TOKEN_VALUE);
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(jwtService.extractUsername(token));
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception ex) {
            handleAuthenticationError(response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Обработать ошибку авторизации по JWT токену
     *
     * @param response тело ответа
     */
    @SneakyThrows
    private void handleAuthenticationError(HttpServletResponse response) {
        log.error("JWT auth error");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorDTO<>("JWT authentication error")));
    }

}
