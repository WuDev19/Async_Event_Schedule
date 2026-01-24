package com.example.testasync.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.produce.JWSSignerFactory;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secret_key;

    public String generateToken(UserDetails userDetails, String fullName) {
        var EXPIRATION = 60 * 60 * 1000;
        var roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        StringBuilder scope = new StringBuilder();
        for (String sc : roles) {
            assert sc != null;
            sc = sc.replace("ROLE_", "");
            scope.append(sc).append(" ");
        }
        //Cách generate jwt dùng nimbus
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userDetails.getUsername()) //tiêu đề thường để username unique để truy vấn lấy instance từ db
                .claim("fullName", fullName)//thông tin thêm
                .claim("scope", scope.toString().trim())
                .issueTime(new Date())//thời gian bắt đầu tạo
                .expirationTime(new Date(System.currentTimeMillis() + EXPIRATION))//hạn của token
                .build();
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(Keys.hmacShaKeyFor(secret_key.getBytes(StandardCharsets.UTF_8)))); //bắt buộc phải ký
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        return jwsObject.serialize();
    }

    private JWTClaimsSet extractClaims(String token) {
        //extract sử dụng nimbus
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier jwsVerifier = new MACVerifier(Keys.hmacShaKeyFor(secret_key.getBytes(StandardCharsets.UTF_8))); //lấy ra verifier để xác minh
            var verified = signedJWT.verify(jwsVerifier); //so sánh xem chữ ký có giống nhau ko
            return signedJWT.getJWTClaimsSet();
        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Date date = extractClaims(token).getExpirationTime();
            return date.before(new Date()) || !userDetails.getUsername().equals(extractUsername(token));
        } catch (Exception e) {
            return false;
        }
    }

}
