package com.clipboarder.clipboarder.entity.dto.request;

import lombok.Data;

@Data
public class SignUpRequestDTO{
    private String email;
    private String name;
    private String picture;
    private String provider;
    private String role;
}
