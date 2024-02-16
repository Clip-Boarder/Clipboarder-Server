package com.clipboarder.clipboarder.controller;

import com.clipboarder.clipboarder.entity.dto.ImageUploadResponseDTO;
import com.clipboarder.clipboarder.exception.NotFoundClipboarderUserException;
import com.clipboarder.clipboarder.exception.NotImageException;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@Log4j2
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ImageUploadResponseDTO> uploadImage(HttpServletRequest request, @RequestPart MultipartFile uploadImage) throws NotImageException, NotFoundClipboarderUserException {
        String token = request.getHeader("Authorization");
        Long id = imageService.save(token, uploadImage);

        return ResponseEntity.ok(new ImageUploadResponseDTO(true, id));
    }

}
