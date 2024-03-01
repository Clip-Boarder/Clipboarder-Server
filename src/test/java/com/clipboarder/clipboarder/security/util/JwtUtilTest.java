package com.clipboarder.clipboarder.security.util;

import org.junit.jupiter.api.Test;

class JwtUtilTest {
    public JwtUtil jwtUtil = new JwtUtil();

    @Test
    public void justTest() throws Exception {
        String token = jwtUtil.generateToken("uadddy@naver.com");
        System.out.println(jwtUtil.validateAndExtract("Bearer " + token));
    }
}