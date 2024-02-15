package com.clipboarder.clipboarder.controller;

import com.clipboarder.clipboarder.entity.dto.SignupResponse;
import com.clipboarder.clipboarder.entity.dto.TextCopyResponse;
import com.clipboarder.clipboarder.entity.dto.TextDTO;
import com.clipboarder.clipboarder.service.TextService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/text")
@RequiredArgsConstructor
@Log4j2
public class TextController {

    private final TextService textService;

    @PostMapping
    public ResponseEntity<TextCopyResponse> copy(HttpServletRequest request, @RequestBody TextDTO textDTO){
        String token = request.getHeader("Authorization");
        Long id = textService.copy(token, textDTO);

        return ResponseEntity.ok().body(new TextCopyResponse(true, id));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<TextDTO>> getList(HttpServletRequest request) throws Exception{
        String token = request.getHeader("Authorization");
        List<TextDTO> textDTOList = textService.getList(token);

        return ResponseEntity.ok(textDTOList);
    }

    @DeleteMapping
    public ResponseEntity<SignupResponse> delete(HttpServletRequest request, @RequestBody TextDTO textDTO){
        String token = request.getHeader("Authorization");

        textService.delete(token, textDTO);

        return ResponseEntity.ok(new SignupResponse(true, "GOOD~"));
    }
}
