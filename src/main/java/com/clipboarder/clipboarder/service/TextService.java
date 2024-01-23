package com.clipboarder.clipboarder.service;

import com.clipboarder.clipboarder.entity.ClipboarderUser;
import com.clipboarder.clipboarder.entity.Text;
import com.clipboarder.clipboarder.entity.dto.TextDTO;
import com.clipboarder.clipboarder.repository.TextRepository;
import com.clipboarder.clipboarder.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TextService {

    private final TextRepository textRepository;
    private final JWTUtil jwtUtil;

    public void copy(TextDTO textDTO){
        Text text = dtoToEntity(textDTO);

        textRepository.save(text);
    }

    public List<TextDTO> getList(String token) throws Exception {
        String email = jwtUtil.validateAndExtract(token);

        List<Text> textList = textRepository.findAllByWriterEmail(email);
        List<TextDTO> textDTOList = textList.stream().map(this::entityToDTO).toList();

        return textDTOList;
    }

    private TextDTO entityToDTO(Text text){
        TextDTO textDTO = TextDTO.builder()
                .id(text.getId())
                .content(text.getContent())
                .email(text.getWriter().getEmail())
                .build();

        return textDTO;
    }

    private Text dtoToEntity(TextDTO textDTO){
        Text text = Text.builder()
                .id(textDTO.getId())
                .content(textDTO.getContent())
                .writer(ClipboarderUser.builder().email(textDTO.getEmail()).build())
                .build();

        return text;
    }
}
