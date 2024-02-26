package com.clipboarder.clipboarder.service;

import com.clipboarder.clipboarder.entity.ClipboarderUser;
import com.clipboarder.clipboarder.entity.Image;
import com.clipboarder.clipboarder.exception.NotFoundClipboarderUserException;
import com.clipboarder.clipboarder.repository.ClipboarderUserRepository;
import com.clipboarder.clipboarder.repository.ImageRepository;
import com.clipboarder.clipboarder.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final String uploadPath = "/Users/hyun/Desktop/Clipboarder/Images";
    private final ImageRepository imageRepository;
    private final ClipboarderUserRepository clipboarderUserRepository;
    private final JwtUtil jwtUtil;

    public Long save(String token, MultipartFile uploadImage) throws NotFoundClipboarderUserException {
        String email = jwtUtil.validateAndExtract(token);
        String fileName = uploadImage.getOriginalFilename();

        // 날짜 폴더 생성
        String folderPath = makeFolder();

        // UUID
        String uuid = UUID.randomUUID().toString();

        // 지정할 파일 이름 중간에 "_"를 이용해서 구분
        String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
        Path savePath = Paths.get(saveName);

        // DB에 저장
        ClipboarderUser clipboarderUser = clipboarderUserRepository.findByEmail(email).orElseThrow(() -> new NotFoundClipboarderUserException());
        Image image = Image.builder()
                .path(saveName)
                .user(clipboarderUser).build();
        Image returnImage = imageRepository.save(image);

        try { // 폴더에 저장
            uploadImage.transferTo(savePath);
        } catch (IOException e){
            e.printStackTrace();
        }

        return returnImage.getId();
    }

    public List<Image> getImages(String email) {
        List<Image> images = imageRepository.findAllByUser_Email(email);

        return images;
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
