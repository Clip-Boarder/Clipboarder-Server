package com.clipboarder.clipboarder.security.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class JWTUtilTest {
    public JWTUtil jwtUtil = new JWTUtil();

    @Test
    public void justTest() throws Exception {
        String token = jwtUtil.generateToken("uadddy@naver.com");
        System.out.println(jwtUtil.validateAndExtract("Bearer " + token));
    }
}