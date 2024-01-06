package com.clipboarder.clipboarder.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    private String token;

    private String hardware_info;
}
