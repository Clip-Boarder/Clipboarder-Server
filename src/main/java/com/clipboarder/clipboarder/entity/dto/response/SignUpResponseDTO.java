package com.clipboarder.clipboarder.entity.dto.response;

import com.clipboarder.clipboarder.entity.dto.ResponseDTO;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class SignUpResponseDTO extends ResponseDTO {
    private SignUpResponseDTO(){
        super();
    }

    public static ResponseEntity<SignUpResponseDTO> success(){
        SignUpResponseDTO responseBody = new SignUpResponseDTO();
        return ResponseEntity.ok().body(responseBody);
    }

    public static ResponseEntity<ResponseDTO> duplicated(){
        ResponseDTO responseBody = new ResponseDTO("duplicated", "duplicated~~");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }
}
