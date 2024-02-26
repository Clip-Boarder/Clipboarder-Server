package com.clipboarder.clipboarder.security.service;

import com.clipboarder.clipboarder.entity.ClipboarderUser;
import com.clipboarder.clipboarder.entity.ClipboarderUserRole;
import com.clipboarder.clipboarder.repository.ClipboarderUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class OAuthUserDetailsServiceImpl extends DefaultOAuth2UserService {

    private final ClipboarderUserRepository clipboarderUserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("----------OAuthUserDetailsService----------");

        // 유저 정보 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getClientName();
        String email = oAuth2User.getAttribute("email");
        String picture = oAuth2User.getAttribute("picture");
        String name = oAuth2User.getAttribute("name");

        log.info("provider : " + provider);
        log.info("email : " + email);
        log.info("picture : " + picture);
        log.info("name : " + name);

        // 회원가입
        if(!isExistUser(email))
            signup(email, name, provider, picture);

        return oAuth2User;
    }

    private boolean isExistUser(String email){
        return clipboarderUserRepository.existsByEmail(email);
    }

    private void signup(String email, String name, String provider, String picture){
        ClipboarderUser signupUser = ClipboarderUser.builder()
                .email(email)
                .name(name)
                .provider(provider)
                .picture(picture)
                .role("ROLE_USER")
                .build();

        clipboarderUserRepository.save(signupUser);
    }
}
