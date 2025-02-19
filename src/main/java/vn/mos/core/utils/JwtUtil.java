package vn.mos.core.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import vn.mos.core.sercurities.data.TokenUserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key;
    private final long jwtExpiration;

    public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expiration) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)); // ✅ Decode Base64 key để tránh lỗi
        this.jwtExpiration = expiration;
    }

    /**
     * 🛡️ Sinh JWT từ `TokenUserInfo`
     */
    public String generateToken(TokenUserInfo userInfo) {
        String userInfoJson = JsonUtils.toJson(userInfo); // ✅ Chuyển thành JSON để lưu vào token
        return Jwts.builder()
            .subject(userInfo.getUsername()) // ✅ Dùng `.subject()` thay vì `.setSubject()`
            .claim("userInfo", userInfoJson) // ✅ Lưu user info dưới dạng JSON
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(key, Jwts.SIG.HS256) // ✅ Dùng `Jwts.SIG.HS256` để tránh deprecated
            .compact();
    }

    /**
     * ✅ Kiểm tra JWT hợp lệ
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(key) // ✅ Dùng `verifyWith` thay vì `setSigningKey`
                .build()
                .parseSignedClaims(token); // ✅ Dùng `parseSignedClaims` thay vì `parseClaimsJws`
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * 🔍 Giải mã Token thành `TokenUserInfo`
     */
    public TokenUserInfo extractUserInfo(String token) {
        Claims claims = Jwts.parser()
            .verifyWith(key) // ✅ Dùng `verifyWith` thay vì `setSigningKey`
            .build()
            .parseSignedClaims(token) // ✅ Dùng `parseSignedClaims`
            .getPayload();

        return JsonUtils.fromJson(claims.get("userInfo", String.class), TokenUserInfo.class);
    }
}
