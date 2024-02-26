package com.clipboarder.clipboarder.service;

import com.clipboarder.clipboarder.entity.ClipboarderUser;
import com.clipboarder.clipboarder.entity.dto.ClipboarderUserDTO;
import com.clipboarder.clipboarder.repository.ClipboarderUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClipboarderUserService {
    private final ClipboarderUserRepository clipboarderUserRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean signUp(ClipboarderUserDTO clipboarderUserDTO){
        String email = clipboarderUserDTO.getEmail();

        // 중복 확인
        boolean isExist = clipboarderUserRepository.existsByEmail(email);
        if(isExist)
            return false;

        // DB에 저장
        ClipboarderUser clipboarderUser = dtoToEntity(clipboarderUserDTO);
        clipboarderUserRepository.save(clipboarderUser);

        return true;

    }

    public ClipboarderUserDTO getUserByEmail(String email){
        Optional<ClipboarderUser> result = clipboarderUserRepository.findByEmail(email);
        ClipboarderUser clipboarderUser = result.get();

        ClipboarderUserDTO clipboarderUserDTO = entityToDTO(clipboarderUser);
        return clipboarderUserDTO;
    }

    private ClipboarderUser dtoToEntity(ClipboarderUserDTO clipboarderUserDTO){
        ClipboarderUser clipboarderUser = ClipboarderUser.builder()
                .email(clipboarderUserDTO.getEmail())
                .name(clipboarderUserDTO.getName())
                .picture(clipboarderUserDTO.getPicture())
                .provider(clipboarderUserDTO.getProvider())
                .role("USER_USER")
                .build();

        return clipboarderUser;
    }

    private ClipboarderUserDTO entityToDTO(ClipboarderUser clipboarderUser){
        ClipboarderUserDTO clipboarderUserDTO = ClipboarderUserDTO.builder()
                .email(clipboarderUser.getEmail())
                .name(clipboarderUser.getName())
                .picture(clipboarderUser.getPicture())
                .provider(clipboarderUser.getProvider())
                .role(clipboarderUser.getRole())
                .build();

        return clipboarderUserDTO;
    }
}
