package com.clipboarder.clipboarder.controller;

import com.clipboarder.clipboarder.entity.Image;
import com.clipboarder.clipboarder.entity.dto.ImageUploadResponseDTO;
import com.clipboarder.clipboarder.exception.NotFoundClipboarderUserException;
import com.clipboarder.clipboarder.exception.NotImageException;
import com.clipboarder.clipboarder.security.util.JwtUtil;
import com.clipboarder.clipboarder.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@Log4j2
public class ImageController {

    private final ImageService imageService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<ImageUploadResponseDTO> uploadImage(HttpServletRequest request, @RequestPart MultipartFile uploadImage) throws NotImageException, NotFoundClipboarderUserException {
        String token = request.getHeader("Authorization");
        Long id = imageService.save(token, uploadImage);

        return ResponseEntity.ok(new ImageUploadResponseDTO(true, id));
    }

    @GetMapping
    public ResponseEntity<List<Image>> getImages(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String email = jwtUtil.validateAndExtract(token);

        List<Image> images = imageService.getImages(email);
        return ResponseEntity.ok().body(images);
    }

}
