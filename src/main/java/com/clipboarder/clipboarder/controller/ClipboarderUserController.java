package com.clipboarder.clipboarder.controller;

import com.clipboarder.clipboarder.entity.dto.ClipboarderUserDTO;
import com.clipboarder.clipboarder.entity.dto.SignupResponse;
import com.clipboarder.clipboarder.service.ClipboarderUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class ClipboarderUserController {
    private final ClipboarderUserService clipboarderUserService;

    @PostMapping
    public ResponseEntity<SignupResponse> signUp(@RequestBody @Valid ClipboarderUserDTO clipboarderUserDTO){
        clipboarderUserService.signUp(clipboarderUserDTO);

        return ResponseEntity.ok().body(new SignupResponse(true, "GOOD~~"));
    }

}
