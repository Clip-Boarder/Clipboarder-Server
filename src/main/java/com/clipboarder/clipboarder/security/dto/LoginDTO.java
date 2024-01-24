package com.clipboarder.clipboarder.security.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
}
