package com.clipboarder.clipboarder.security;

import com.clipboarder.clipboarder.entity.ClipboarderUser;
import com.clipboarder.clipboarder.security.util.JwtUtil;
import com.clipboarder.clipboarder.service.ClipboarderUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//    private final ClipboarderUserService clipboarderUserService;
//    private final AuthorityUtils authorityUtils;
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getEmail();
        try {
            redirect(request, response, email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void redirect(HttpServletRequest request, HttpServletResponse response, String email) throws Exception {
        log.info("Token 생성 시작");
        String accessToken = jwtUtil.generateAccessToken(email);  // Access Token 생성
        String refreshToken = jwtUtil.generateRefreshToken(email);     // Refresh Token 생성

        String uri = createURI(accessToken, refreshToken);   // Access Token과 Refresh Token을 포함한 URL을 생성
        getRedirectStrategy().sendRedirect(request, response, uri);   // sendRedirect() 메서드를 이용해 Frontend 애플리케이션 쪽으로 리다이렉트
    }

    // Redirect URI 생성. JWT를 쿼리 파라미터로 담아 전달한다.
    private String  createURI(String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("refreshToken", refreshToken);
        queryParams.add("access_token", accessToken);

        return "http://locathost:8080/oauth/reponse?accessToken=" + accessToken +"&refreshToken=" + refreshToken;
    }
}
