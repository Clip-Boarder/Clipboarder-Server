package com.clipboarder.clipboarder.service;

import com.clipboarder.clipboarder.entity.ClipboarderUser;
import com.clipboarder.clipboarder.entity.dto.request.SignInRequestDTO;
import com.clipboarder.clipboarder.entity.dto.request.SignUpRequestDTO;
import com.clipboarder.clipboarder.entity.dto.response.SignInResponseDTO;
import com.clipboarder.clipboarder.entity.dto.response.SignUpResponseDTO;
import com.clipboarder.clipboarder.repository.ClipboarderUserRepository;
import com.clipboarder.clipboarder.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ClipboarderUserRepository clipboarderUserRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<? super SignUpResponseDTO> signUp(SignUpRequestDTO signUpRequestDTO){
        String email = signUpRequestDTO.getEmail();

        // 중복 확인
        boolean isExist = clipboarderUserRepository.existsByEmail(email);
        if(isExist)
            return SignUpResponseDTO.duplicated();


        // DB에 저장
        ClipboarderUser user = dtoToEntity(signUpRequestDTO);
        clipboarderUserRepository.save(user);

        return SignUpResponseDTO.success();
    }

    public ResponseEntity<? super SignInResponseDTO> signIn(SignInRequestDTO signInRequestDTO){
        String email = signInRequestDTO.getEmail();

        // DB 검색
        boolean isExist = clipboarderUserRepository.existsByEmail(email);
        if(!isExist)
            return SignInResponseDTO.fail();

        String token = null;
        try {
            token = jwtUtil.generateAccessToken(email);
        } catch (Exception e){
            e.printStackTrace();
        }

        return SignInResponseDTO.success(token);
    }

    private ClipboarderUser dtoToEntity(SignUpRequestDTO signUpRequestDTO){
        ClipboarderUser user = ClipboarderUser.builder()
                .email(signUpRequestDTO.getEmail())
                .name(signUpRequestDTO.getName())
                .provider(signUpRequestDTO.getProvider())
                .picture(signUpRequestDTO.getPicture())
                .role("ROLE_USER")
                .build();

        return user;
    }
}
