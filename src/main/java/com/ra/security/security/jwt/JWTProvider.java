package com.ra.security.security.jwt;

import com.ra.security.security.principal.UserDetailCustom;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JWTProvider {
    @Value("${jwt.expired-time}")
    private long expired;
    @Value("${jwt.secret-key}")
    private String secretKey;

    // Tạo ra chuỗi token để gửi về client -> sau khi muốn xác thực cái gì thì gửi lên token kèm theo để biết được người đó là ai
    public String generateToken(UserDetailCustom userDetailCustom) {
        // thành phần của jwt có 3 phần
        // 1. header{ alg: kiểu mã hoá, type: kiểu jwt}
        // 2. payload{ nội dung: trường dữ liệu unique để biết thằng đấy là thằng nào}
        // 3. signature { chứa chữ ký là 1 secret key }
        return Jwts.builder()
                .setSubject(userDetailCustom.getUsername()) // set username vào tiêu đề để khi gửi lên, hàm loadUserByUsername của UserDetailCustom để filter phân tích và xác thực
                .setIssuedAt(new Date()) // thời gian bắt đầu
                .setExpiration(new Date(new Date().getTime() + expired)) // thời gian hết hạn
                .signWith(SignatureAlgorithm.HS512,secretKey) // chữ ký và secret key mã hoá theo kiểu HS512
                .compact();
    }
    public boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e){ // Lỗi JWT hết hạn
            log.error("JWT: message error expired:", e.getMessage());
        }catch (UnsupportedJwtException e){ // Lỗi không hỗ trợ JWT mình mã hoá
            log.error("JWT: message error unsupported:", e.getMessage());
        }catch (MalformedJwtException e){ // Lỗi JWT không hợp lệ, ví dụ copy chuỗi bậy bạ dán vào
            log.error("JWT: message error formated:", e.getMessage());
        }catch (SignatureException e){ // Lỗi chữ ký, ví dụ không đúng secret key hoặc thuật toán mã hoá
            log.error("JWT: message error signature not math:", e.getMessage());
        }catch (IllegalArgumentException e){ // Lỗi đối số truyền vào không hợp lệ
            log.error("JWT: message claims empty or argument invalid:", e.getMessage());
        }
        return false;
    }
    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject(); // giải mã token để lấy username từ tiêu đề
    }
}
