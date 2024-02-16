package com.clipboarder.clipboarder.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContentDTO {
    private Long id;
    private String content;
    private String type;
    private String email;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
