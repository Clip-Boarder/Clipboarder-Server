package com.clipboarder.clipboarder.entity.dto.response;

import lombok.Builder;
import lombok.Data;
import net.minidev.json.JSONObject;

@Data
public class TextCopyResponseDTO {
    private boolean result;
    private JSONObject data;

    public TextCopyResponseDTO(boolean result, String message){
        this.data = new JSONObject();
        this.result = result;
        data.put("message", message);
    }
}
