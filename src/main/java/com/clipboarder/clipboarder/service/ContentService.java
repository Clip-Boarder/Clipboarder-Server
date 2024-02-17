package com.clipboarder.clipboarder.service;

import com.clipboarder.clipboarder.entity.ClipboarderUser;
import com.clipboarder.clipboarder.entity.Content;
import com.clipboarder.clipboarder.entity.dto.ContentDTO;
import com.clipboarder.clipboarder.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;
    private final ModelMapper modelMapper;

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
