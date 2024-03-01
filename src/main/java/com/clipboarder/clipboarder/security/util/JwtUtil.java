package com.clipboarder.clipboarder.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Log4j2
@Component
public class JwtUtil {
    private String secretKey = "heheh3122123dkfrwpTjddsadasadsadsadsadsadsehe";

    // 1month
    private long expire = 60 * 24 * 30;

    public String generateAccessToken(String email) throws Exception{
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
                .claim("sub", email)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"))
                .compact();
    }

    public String generateRefreshToken(String email) throws Exception{
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
                .claim("sub", email)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"))
                .compact();
    }

    public String validateAndExtract(String tokenStr){
        String contentValue = null;

        try {
            String token = tokenStr.split(" ")[1].trim();

            DefaultJws defaultJws = (DefaultJws) Jwts.parserBuilder().setSigningKey(secretKey.getBytes("UTF-8")).build().parseClaimsJws(token);

            log.info("Token Info: " + defaultJws);
            DefaultClaims claims = (DefaultClaims) defaultJws.getBody();
            log.info("-----------------");
            contentValue = claims.getSubject();
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            contentValue = null;
        }

        return contentValue;
    }
}
