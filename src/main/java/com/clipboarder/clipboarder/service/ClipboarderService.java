package com.clipboarder.clipboarder.service;

import com.clipboarder.clipboarder.entity.ClipboarderUser;
import com.clipboarder.clipboarder.entity.ClipboarderUserRole;
import com.clipboarder.clipboarder.entity.dto.ClipboarderUserDTO;
import com.clipboarder.clipboarder.repository.ClipboarderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClipboarderService {
    private final ClipboarderRepository clipboarderRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(ClipboarderUserDTO clipboarderUserDTO){
        ClipboarderUser clipboarderUser = dtoToEntity(clipboarderUserDTO);
        clipboarderUser.addRole(ClipboarderUserRole.USER);

        clipboarderRepository.save(clipboarderUser);
    }

    public ClipboarderUserDTO getUserByEmail(String email){
        Optional<ClipboarderUser> result = clipboarderRepository.findByEmail(email);
        ClipboarderUser clipboarderUser = result.get();

        ClipboarderUserDTO clipboarderUserDTO = entityToDTO(clipboarderUser);
        return clipboarderUserDTO;
    }

    private ClipboarderUser dtoToEntity(ClipboarderUserDTO clipboarderUserDTO){
        ClipboarderUser clipboarderUser = ClipboarderUser.builder()
                .email(clipboarderUserDTO.getEmail())
                .password(passwordEncoder.encode(clipboarderUserDTO.getPassword()))
                .name(clipboarderUserDTO.getName())
                .picture(clipboarderUserDTO.getPicture())
                .provider(clipboarderUserDTO.getProvider())
                .build();

        return clipboarderUser;
    }

    private ClipboarderUserDTO entityToDTO(ClipboarderUser clipboarderUser){
        ClipboarderUserDTO clipboarderUserDTO = ClipboarderUserDTO.builder()
                .email(clipboarderUser.getEmail())
                .password(clipboarderUser.getPassword())
                .name(clipboarderUser.getName())
                .picture(clipboarderUser.getPicture())
                .provider(clipboarderUser.getProvider())
                .roleSet(clipboarderUser.getRoleSet())
                .build();

        return clipboarderUserDTO;
    }
}
