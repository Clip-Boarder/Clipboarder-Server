package com.clipboarder.clipboarder.security.filter;

import com.clipboarder.clipboarder.entity.dto.ClipboarderUserDTO;
import com.clipboarder.clipboarder.security.util.JWTUtil;
import com.clipboarder.clipboarder.service.ClipboarderService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Autowired
    private ClipboarderService clipboarderService;

    public ApiCheckFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("--------------ApiCheckFilter--------------");

        boolean checkHeader = checkAuthHeader(request);

        if (!checkHeader)
            return;

        filterChain.doFilter(request,response);
        return;
    }

    private boolean checkAuthHeader(HttpServletRequest request) {
        boolean checkResult = false;

        String authHeader = request.getHeader("Authorization");

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
            log.info("Authorization exist: " + authHeader);

            try {
                String email = jwtUtil.validateAndExtract(authHeader);
                ClipboarderUserDTO clipboarderUserDTO = clipboarderService.getUserByEmail(email);

                // Login한 email로 UsernamePasswordAuthenticationToken 발급
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(clipboarderUserDTO.getPassword(), null, clipboarderUserDTO.getRoleSet().stream().map(clipboarderUserRole -> new SimpleGrantedAuthority(clipboarderUserRole.name())).toList());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 권한 부여
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                log.info("validate : " + email);
                checkResult = email.length() > 0;
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return checkResult;
    }
}
