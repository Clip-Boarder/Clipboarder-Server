package com.clipboarder.clipboarder.controller;

import com.clipboarder.clipboarder.exception.NotImageException;
import com.clipboarder.clipboarder.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
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

    private final String uploadPath = "/Users/hyun/Desktop/Clipboarder/Images";

    private final ImageService imageService;

    @PostMapping
    public void uploadImage(@RequestPart MultipartFile uploadImage) throws NotImageException {
        log.info(uploadImage);

        String originalName = uploadImage.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
        log.info("fileName : " + fileName);

        // 날짜 폴더 생성
        String folderPath = makeFolder();

        // UUID
        String uuid = UUID.randomUUID().toString();

        // 지정할 파일 이름 중간에 "_"를 이용해서 구분
        String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
        Path savePath = Paths.get(saveName);

        try {
            uploadImage.transferTo(savePath);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private String makeFolder(){
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);

        // Make Folder
        File uploadPathFolder = new File(uploadPath, folderPath);

        if(uploadPathFolder.exists() == false)
            uploadPathFolder.mkdirs();

        return folderPath;
    }

}
