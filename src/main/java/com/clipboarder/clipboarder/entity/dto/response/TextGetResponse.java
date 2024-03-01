package com.clipboarder.clipboarder.entity.dto.response;

import com.clipboarder.clipboarder.entity.dto.TextDTO;
import lombok.Data;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.List;

@Data
public class TextGetResponse {
    private boolean result;
    private JSONObject data;

    public TextGetResponse(boolean result, List<TextDTO> textDTOs){
        this.data = new JSONObject();
        this.result = result;

        JSONArray texts = new JSONArray();
        textDTOs.stream().forEach(textDTO -> {
            texts.add(textDTO);
        });
        data.put("data", texts);
    }
}
