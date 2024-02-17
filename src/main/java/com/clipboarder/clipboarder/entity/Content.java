package com.clipboarder.clipboarder.entity;

import com.clipboarder.clipboarder.entity.dto.ContentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Content extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;

    @Enumerated(EnumType.STRING)
    private ContentType type;

    @ManyToOne
    private ClipboarderUser user;
}
