package com.clipboarder.clipboarder.security.service;

import com.clipboarder.clipboarder.entity.ClipboarderUser;
import com.clipboarder.clipboarder.entity.ClipboarderUserRole;
import com.clipboarder.clipboarder.repository.ClipboarderUserRepository;
import com.clipboarder.clipboarder.security.AuthorityUtils;
import com.clipboarder.clipboarder.security.CustomOAuth2User;
import com.clipboarder.clipboarder.security.OAuthAttributes;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class OAuthUserDetailsServiceImpl extends DefaultOAuth2UserService {

    private final ClipboarderUserRepository clipboarderUserRepository;
    private final AuthorityUtils authorityUtils;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("----------OAuthUserDetailsService----------");

        // 유저 정보 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> originAttributes = oAuth2User.getAttributes();

        OAuthAttributes attributes = OAuthAttributes.of(originAttributes);
        ClipboarderUser user = saveOrUpdate(attributes);
        String email = user.getEmail();
//        List<GrantedAuthority> authorities = authorityUtils.createAuthorities(email);
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(email);
        return customOAuth2User;
    }

    /**
     * 이미 존재하는 회원이라면 이름과 프로필이미지를 업데이트해줍니다.
     * 처음 가입하는 회원이라면 User 테이블을 생성합니다.
     **/
    private ClipboarderUser saveOrUpdate(OAuthAttributes authAttributes) {
        ClipboarderUser user = clipboarderUserRepository.findByEmail(authAttributes.getEmail()).orElse(authAttributes.toEntity());

        return clipboarderUserRepository.save(user);
    }


}
