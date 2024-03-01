package com.clipboarder.clipboarder.controller;

import com.clipboarder.clipboarder.entity.dto.request.SignInRequestDTO;
import com.clipboarder.clipboarder.entity.dto.request.SignUpRequestDTO;
import com.clipboarder.clipboarder.entity.dto.response.SignInResponseDTO;
import com.clipboarder.clipboarder.entity.dto.response.SignUpResponseDTO;
import com.clipboarder.clipboarder.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDTO> signUp(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO){
        ResponseEntity<? super SignUpResponseDTO> responseBody = authService.signUp(signUpRequestDTO);
        return responseBody;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDTO> signIn(@RequestBody @Valid SignInRequestDTO signInRequestDTO){
        ResponseEntity<? super SignInResponseDTO> response = authService.signIn(signInRequestDTO);
        return response;
    }
}
