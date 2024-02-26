package com.clipboarder.clipboarder.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClipboarderUser extends BaseEntity{
    @Id
    private String email;

    private String password;
    private String name;
    private String picture;
    private String provider;
    private String role;
}
