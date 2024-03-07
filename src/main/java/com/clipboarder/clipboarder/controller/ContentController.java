package com.clipboarder.clipboarder.controller;

import com.clipboarder.clipboarder.entity.dto.ContentDTO;
import com.clipboarder.clipboarder.entity.dto.ImageRequestDTO;
import com.clipboarder.clipboarder.security.util.JwtUtil;
import com.clipboarder.clipboarder.service.ContentService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;
    private final JwtUtil jwtUtil;

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

    @GetMapping
    public ResponseEntity<Resource> getImage(HttpServletRequest request, @RequestBody ImageRequestDTO imageRequestDTO) throws MalformedURLException {
        jwtUtil.validateAndExtract(request.getHeader("Authorization"));

        String path = imageRequestDTO.getPath();
        String filename = path.split("_")[1].split("\\.")[0];
        Resource resource = (Resource) new UrlResource("file:" + path);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
