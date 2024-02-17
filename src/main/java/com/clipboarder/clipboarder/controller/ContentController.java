package com.clipboarder.clipboarder.controller;

import com.clipboarder.clipboarder.entity.dto.ContentDTO;
import com.clipboarder.clipboarder.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;
    private final JWTUtil jwtUtil;

    @PostMapping
    public ResponseEntity<Long> saveContent(@RequestBody ContentDTO contentDTO){
        Long id = contentService.saveContent(contentDTO);

        return ResponseEntity.ok().body(id);
    }

    @GetMapping
    public ResponseEntity<List<ContentDTO>> getContents(HttpServletRequest request){
        String email = jwtUtil.validateAndExtract(request.getHeader("Authorization"));
        List<ContentDTO> contentDTOs = contentService.getContent(email);

        return ResponseEntity.ok().body(contentDTOs);
    }
}
