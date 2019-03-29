package com.ivd.example.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * Перечисление ролей допустимых пользователю
 */
public enum Role implements GrantedAuthority {
    USER,
    ADMIN,
    ANALYST;

    @Override
    public String getAuthority() {
        return name();
    }}
