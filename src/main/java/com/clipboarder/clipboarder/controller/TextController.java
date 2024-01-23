package com.clipboarder.clipboarder.controller;

import com.clipboarder.clipboarder.entity.Text;
import com.clipboarder.clipboarder.entity.dto.TextDTO;
import com.clipboarder.clipboarder.security.util.JWTUtil;
import com.clipboarder.clipboarder.service.TextService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/text")
@RequiredArgsConstructor
@Log4j2
public class TextController {

    private final TextService textService;

    @PostMapping
    public void copy(@RequestBody TextDTO textDTO){
        textService.copy(textDTO);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<TextDTO>> getList(HttpServletRequest request) throws Exception{
        String token = request.getHeader("Authorization");
        List<TextDTO> textDTOList = textService.getList(token);

        return ResponseEntity.ok(textDTOList);
    }
}
