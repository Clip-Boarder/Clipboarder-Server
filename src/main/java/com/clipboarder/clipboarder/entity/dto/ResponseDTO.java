package com.clipboarder.clipboarder.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ResponseDTO {
    private boolean result;
    private String data;

    public ResponseDTO(boolean result, String data){
        this.result = result;
        this.data = data;
    }
}
