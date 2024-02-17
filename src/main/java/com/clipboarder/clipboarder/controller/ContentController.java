package com.clipboarder.clipboarder.controller;

import com.clipboarder.clipboarder.entity.dto.ContentDTO;
import com.clipboarder.clipboarder.security.util.JWTUtil;
import com.clipboarder.clipboarder.service.ContentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;
    private final JWTUtil jwtUtil;

    @PostMapping
    public ResponseEntity<Long> saveContent(HttpServletRequest request, @RequestBody ContentDTO contentDTO){
        String email = jwtUtil.validateAndExtract(request.getHeader("Authorization"));
        Long id = contentService.saveContent(email, contentDTO);

        return ResponseEntity.ok().body(id);
    }

    @GetMapping
    public ResponseEntity<List<ContentDTO>> getContents(HttpServletRequest request){
        String email = jwtUtil.validateAndExtract(request.getHeader("Authorization"));
        List<ContentDTO> contentDTOs = contentService.getContent(email);

        return ResponseEntity.ok().body(contentDTOs);
    }
}
