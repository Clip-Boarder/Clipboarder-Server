package com.clipboarder.clipboarder.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentDTO {
    private Long id;
    private String content;
    private ContentType type;
    private String email;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}