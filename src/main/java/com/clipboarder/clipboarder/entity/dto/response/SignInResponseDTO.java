package com.clipboarder.clipboarder.entity.dto.response;

import com.clipboarder.clipboarder.entity.dto.ResponseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class SignInResponseDTO extends ResponseDTO {
    private String token;

    public SignInResponseDTO(String token){
        super();
        this.token = token;
    }

    public static ResponseEntity<SignInResponseDTO> success(String token){
        SignInResponseDTO responseBody = new SignInResponseDTO(token);
        return ResponseEntity.ok().body(responseBody);
    }

    public static ResponseEntity<ResponseDTO> fail(){
        ResponseDTO responseBody = new ResponseDTO("FAIL", "FAIL~~");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}
