package com.clipboarder.clipboarder.entity.dto;

import com.clipboarder.clipboarder.entity.BaseEntity;
import com.clipboarder.clipboarder.entity.ClipboarderUserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClipboarderUserDTO {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String name;
    private String picture;
    private String provider;
    private String role;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
