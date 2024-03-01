package com.clipboarder.clipboarder.service;

import com.clipboarder.clipboarder.entity.ClipboarderUser;
import com.clipboarder.clipboarder.entity.dto.ClipboarderUserDTO;
import com.clipboarder.clipboarder.entity.dto.request.SignInRequestDTO;
import com.clipboarder.clipboarder.entity.dto.response.SignInResponseDTO;
import com.clipboarder.clipboarder.repository.ClipboarderUserRepository;
import com.clipboarder.clipboarder.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClipboarderUserService {
    private final ClipboarderUserRepository clipboarderUserRepository;


    public ClipboarderUser findByEmail(String email){
        Optional<ClipboarderUser> result = clipboarderUserRepository.findByEmail(email);
        ClipboarderUser clipboarderUser = result.get();

        return clipboarderUser;
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
