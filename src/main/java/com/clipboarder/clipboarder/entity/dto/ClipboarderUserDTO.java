package com.clipboarder.clipboarder.entity.dto;

import com.clipboarder.clipboarder.entity.BaseEntity;
import com.clipboarder.clipboarder.entity.ClipboarderUserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private String email;
    private String password;
    private String name;
    private String picture;
    private String provider;
    private Set<ClipboarderUserRole> roleSet;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
