package com.clipboarder.clipboarder.service;

import com.clipboarder.clipboarder.entity.ClipboarderUser;
import com.clipboarder.clipboarder.entity.Text;
import com.clipboarder.clipboarder.entity.dto.TextDTO;
import com.clipboarder.clipboarder.repository.TextRepository;
import com.clipboarder.clipboarder.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TextService {

    private final TextRepository textRepository;
    private final JwtUtil jwtUtil;

    public Long copy(String token, TextDTO textDTO){
        String email = jwtUtil.validateAndExtract(token);
        textDTO.setEmail(email);
        Text text = dtoToEntity(textDTO);

        Text responseText = textRepository.save(text);
        Long id = responseText.getId();
        return id;
    }

    public List<TextDTO> getList(String token){
        String email = jwtUtil.validateAndExtract(token);

        List<Text> textList = textRepository.findAllByUserEmail(email);
        List<TextDTO> textDTOList = textList.stream().map(this::entityToDTO).toList();

        return textDTOList;
    }

    public void delete(String token, TextDTO textDTO) {
        String email = jwtUtil.validateAndExtract(token);

        Long id = textDTO.getId();
        textRepository.deleteById(id);
    }

    private TextDTO entityToDTO(Text text){
        TextDTO textDTO = TextDTO.builder()
                .id(text.getId())
                .content(text.getContent())
                .email(text.getUser().getEmail())
                .build();

        return textDTO;
    }

    private Text dtoToEntity(TextDTO textDTO){
        Text text = Text.builder()
                .id(textDTO.getId())
                .content(textDTO.getContent())
                .user(ClipboarderUser.builder().email(textDTO.getEmail()).build())
                .build();

        return text;
    }
}
