package ru.anykeyers.videoservice.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * Роль пользователя
 */
public enum Role implements GrantedAuthority {

    /**
     * Обычный пользователь
     */
    USER,
    /**
     * Администратор
     */
    ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }

}
