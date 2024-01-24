package com.clipboarder.clipboarder.security.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ClipboarderAuthUserDTO extends User {
    private String email;
    private String name;
    private String picture;
    private String provider;

    public ClipboarderAuthUserDTO(
            String username,
            String password,
            String name,
            String picture,
            String provider,
            Collection<? extends GrantedAuthority> authorities
    ){
        super(username, password, authorities);
        this.email = username;
        this.name = name;
        this.picture = picture;
        this.provider = provider;
    }
}
