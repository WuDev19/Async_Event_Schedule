package com.example.testasync.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secret_key;

    public String generateToken (UserDetails userDetails, String fullName){
        var EXPIRATION = 60 * 60 * 1000;
        return Jwts.builder()
                .subject(userDetails.getUsername()) //tiêu đề thường để username unique để truy vấn lấy instance từ db
                .claim("fullName", fullName) //thông tin thêm
                .issuedAt(new Date()) //thời gian bắt đầu tạo
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION)) //hạn của token
                .signWith(Keys.hmacShaKeyFor(secret_key.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS256) //bắt buộc phải ký, nếu dùng thuật toán ký thì dùng Jwts.
                .compact();
    }

    private Claims extractClaims(String token){
        return Jwts.parser() //khởi tạo jwt parser để phân tích token
                .verifyWith(Keys.hmacShaKeyFor(secret_key.getBytes(StandardCharsets.UTF_8))) //tạo khóa bí mật để xác minh chũ ký
                .build() //tạo jwt parser hoàn chỉnh
                .parseSignedClaims(token) //chia token làm 3 phần sau đó xác thực
                .getPayload();
    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    public String extractFullName(String token){
        return extractClaims(token).get("fullName").toString();
    }

    public boolean isTokenNotExpire(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails){
        String username = extractUsername(token);
        try{
            return username.equals(userDetails.getUsername()) && !isTokenNotExpire(token);
        }
        catch (Exception e){
            return false;
        }
    }

}
