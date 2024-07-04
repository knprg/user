package com.auth1.auth.learning.services;

import com.auth1.auth.learning.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


public class CustomGrantedAuthority implements GrantedAuthority {

    private String role;

    public CustomGrantedAuthority(Role role) {
        this.role = role.getName();
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
