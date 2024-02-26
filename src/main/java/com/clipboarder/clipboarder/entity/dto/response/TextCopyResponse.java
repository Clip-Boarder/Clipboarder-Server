package com.clipboarder.clipboarder.entity.dto.response;

import lombok.Builder;
import lombok.Data;
import net.minidev.json.JSONObject;

@Data
public class TextCopyResponse {
    private boolean result;
    private JSONObject data;

    public TextCopyResponse(boolean result, Long contentId){
        this.data = new JSONObject();
        this.result = result;
        data.put("content_id", contentId);
    }
}
