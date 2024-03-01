package com.clipboarder.clipboarder.service;

import com.clipboarder.clipboarder.entity.ClipboarderUser;
import com.clipboarder.clipboarder.entity.Content;
import com.clipboarder.clipboarder.entity.Image;
import com.clipboarder.clipboarder.entity.dto.ContentDTO;
import com.clipboarder.clipboarder.entity.dto.ContentType;
import com.clipboarder.clipboarder.exception.NotFoundClipboarderUserException;
import com.clipboarder.clipboarder.repository.ClipboarderUserRepository;
import com.clipboarder.clipboarder.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
public class ContentService {
    private final ContentRepository contentRepository;
    private final ClipboarderUserRepository clipboarderUserRepository;
    private final ModelMapper modelMapper;

    private final String uploadPath = "/Users/hyun/Desktop/Clipboarder/Images";

    public Long saveContent(String email, ContentDTO contentDTO){
        contentDTO.setEmail(email);
//        Content content = modelMapper.map(contentDTO, Content.class);
        Content content = dtoToEntity(contentDTO);
        Content resultContent = contentRepository.save(content);

        return resultContent.getId();
    }

    public List<ContentDTO> getContent(String email) {
        List<Content> contents = contentRepository.findAllByUser_Email(email);
        List<ContentDTO> contentDTOs = contents.stream().map(content -> entityToDTO(content)).toList();

        return contentDTOs;
    }

    public Long saveImage(String email, MultipartFile uploadImage) throws NotFoundClipboarderUserException {
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
        Content content = Content.builder()
                .content(saveName)
                .type(ContentType.IMAGE)
                .user(clipboarderUser).build();
        Content returnContent = contentRepository.save(content);

        try { // 폴더에 저장
            uploadImage.transferTo(savePath);
        } catch (IOException e){
            e.printStackTrace();
        }

        return returnContent.getId();
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

    private Content dtoToEntity(ContentDTO contentDTO){
        Content content = Content.builder()
                .content(contentDTO.getContent())
                .type(contentDTO.getType())
                .user(ClipboarderUser.builder().email(contentDTO.getEmail()).build())
                .build();

        return content;
    }

    private ContentDTO entityToDTO(Content content){
        ContentDTO contentDTO = ContentDTO.builder()
                .id(content.getId())
                .content(content.getContent())
                .type(content.getType())
                .email(content.getUser().getEmail())
                .modDate(content.getModDate())
                .regDate(content.getRegDate())
                .build();

        return contentDTO;
    }
}
