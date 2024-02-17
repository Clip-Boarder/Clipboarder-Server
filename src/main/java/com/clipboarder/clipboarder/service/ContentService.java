package com.clipboarder.clipboarder.service;

import com.clipboarder.clipboarder.entity.Content;
import com.clipboarder.clipboarder.entity.dto.ContentDTO;
import com.clipboarder.clipboarder.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;
    private final ModelMapper modelMapper;

    public Long saveContent(ContentDTO contentDTO){
        Content content = modelMapper.map(contentDTO, Content.class);
        Content resultContent = contentRepository.save(content);

        return resultContent.getId();
    }

    public List<ContentDTO> getContent(String email) {
        List<Content> contents = contentRepository.findAllByUser_Email(email);
        List<ContentDTO> contentDTOs = contents.stream().map(content -> modelMapper.map(content, ContentDTO.class)).toList();

        return contentDTOs;
    }
}
