package com.clipboarder.clipboarder.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorityUtils {
    public List<GrantedAuthority> createAuthorities(String email){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return authorities;
    }

    public List<String> createRoles(String email){
        List<String> roles = new ArrayList<>();
        roles.add(new String("ROLE_USER"));

        return roles;
    }
}
