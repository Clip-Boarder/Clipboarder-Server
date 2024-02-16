package com.clipboarder.clipboarder.entity.dto;

import lombok.Data;
import net.minidev.json.JSONObject;

@Data
public class ImageUploadResponseDTO {
    private boolean result;
    private JSONObject data;

    public ImageUploadResponseDTO(boolean result, Long id){
        this.result = result;
        this.data = new JSONObject();

        JSONObject contentId = new JSONObject();
        contentId.put("content_id", id);
        data.put("data", contentId);
    }
}
