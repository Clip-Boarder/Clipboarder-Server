package com.clipboarder.clipboarder.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Text {
    @Id
    private int id;

    private String token; // foregin key reference(User.token)
    private String content;
    private LocalDateTime createed_date;
    private LocalDateTime modified_date;
}
