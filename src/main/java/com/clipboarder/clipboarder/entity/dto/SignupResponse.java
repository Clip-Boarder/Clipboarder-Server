package com.clipboarder.clipboarder.entity.dto;

import lombok.Builder;
import lombok.Data;
import net.minidev.json.JSONObject;

@Data
@Builder
public class SignupResponse {
    private boolean result;
    private JSONObject data;

    public SignupResponse(boolean result, String message){
        this.data = new JSONObject();
        this.result = result;
        data.put("message", message);
    }
}
