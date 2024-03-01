package com.clipboarder.clipboarder.controller;

import com.clipboarder.clipboarder.entity.Image;
import com.clipboarder.clipboarder.entity.dto.response.ImageUploadResponseDTO;
import com.clipboarder.clipboarder.exception.NotFoundClipboarderUserException;
import com.clipboarder.clipboarder.exception.NotImageException;
import com.clipboarder.clipboarder.security.util.JwtUtil;
import com.clipboarder.clipboarder.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    private final ImageService imageService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<ImageUploadResponseDTO> uploadImage(HttpServletRequest request, @RequestPart MultipartFile uploadImage) throws NotImageException, NotFoundClipboarderUserException {
        String email = jwtUtil.validateAndExtract(request.getHeader("Authorization"));
        Long id = imageService.save(email, uploadImage);

        return ResponseEntity.ok(new ImageUploadResponseDTO(true, id));
    }

    @GetMapping
    public ResponseEntity<List<Image>> getImages(HttpServletRequest request){
        String email = jwtUtil.validateAndExtract(request.getHeader("Authorization"));

        List<Image> images = imageService.getImages(email);
        return ResponseEntity.ok().body(images);
    }

}
