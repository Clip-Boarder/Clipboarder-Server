package com.clipboarder.clipboarder.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDTO {
    private String code;
    private String message;

    public ResponseDTO(){
        this.code = "GOOD";
        this.message = "GOOD~~";
    }
}
