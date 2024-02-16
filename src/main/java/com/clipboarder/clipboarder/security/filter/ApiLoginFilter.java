package com.clipboarder.clipboarder.security.filter;

import com.clipboarder.clipboarder.security.dto.ClipboarderAuthUserDTO;
import com.clipboarder.clipboarder.security.dto.LoginDTO;
import com.clipboarder.clipboarder.security.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JWTUtil jwtUtil;

    public ApiLoginFilter(String defaultFilterProcessUrl, JWTUtil jwtUtil){
        super(defaultFilterProcessUrl);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("----------ApiLoginFilter------------");

        LoginDTO loginDto = objectMapper.readValue(request.getInputStream(), LoginDTO.class);

        log.info(loginDto);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        return this.getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain,
                                            Authentication authResult) throws IOException, ServletException{
        log.info("-----------ApiLoginFilter Success Authentication : " + authResult);
        log.info(authResult.getPrincipal());

        // Get Eamil
        String email = ((ClipboarderAuthUserDTO) authResult.getPrincipal()).getUsername();

        String token = null;
        try {
            token = jwtUtil.generateToken(email);
            JSONObject data = new JSONObject();
            data.put("access_token", token);

            JSONObject json = new JSONObject();
            json.put("result", true);
            json.put("data", data);

            response.setContentType("application/json;charset=utf-8");

            PrintWriter out = response.getWriter();
            out.print(json);
            return;
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
