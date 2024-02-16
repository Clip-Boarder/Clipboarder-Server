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

    @PostMapping
    public ResponseEntity<Long> saveContent(@RequestBody ContentDTO contentDTO){
        Long id = contentService.saveContent(contentDTO);

        return ResponseEntity.ok().body(id);
    }
}
