package com.clipboarder.clipboarder.controller;

import com.clipboarder.clipboarder.entity.Image;
import com.clipboarder.clipboarder.entity.dto.ImageRequestDTO;
import com.clipboarder.clipboarder.entity.dto.ImageUploadResponseDTO;
import com.clipboarder.clipboarder.exception.NotFoundClipboarderUserException;
import com.clipboarder.clipboarder.exception.NotImageException;
import com.clipboarder.clipboarder.security.util.JWTUtil;
import com.clipboarder.clipboarder.service.ContentService;
import com.clipboarder.clipboarder.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@Log4j2
public class ImageController {

    private final ContentService contentService;
    private final JWTUtil jwtUtil;

    @PostMapping
    public ResponseEntity<ImageUploadResponseDTO> uploadImage(HttpServletRequest request, @RequestPart MultipartFile uploadImage) throws NotImageException, NotFoundClipboarderUserException {
        String email  = jwtUtil.validateAndExtract(request.getHeader("Authorization"));
        Long id = contentService.saveImage(email, uploadImage);

        return ResponseEntity.ok(new ImageUploadResponseDTO(true, id));
    }

    @GetMapping
    public ResponseEntity<Resource> getImage(HttpServletRequest request, @RequestBody ImageRequestDTO imageRequestDTO) throws MalformedURLException {
        jwtUtil.validateAndExtract(request.getHeader("Authorization"));

        String path = imageRequestDTO.getPath();
        String filename = path.split("_")[1].split("\\.")[0];
        Resource resource = new UrlResource("file:" + path);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

}
