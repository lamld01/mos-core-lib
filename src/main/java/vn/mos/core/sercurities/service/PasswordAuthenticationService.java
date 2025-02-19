package vn.mos.core.sercurities.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordAuthenticationService {

    private final PasswordEncoder passwordEncoder;

    /**
     * Kiểm tra mật khẩu người dùng nhập vào có khớp với mật khẩu trong database không.
     *
     * @param rawPassword    Mật khẩu người dùng nhập vào
     * @param encodedPassword Mật khẩu đã được mã hóa trong database
     * @return true nếu mật khẩu đúng, false nếu sai
     */
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        if (encodedPassword == null) {
            return false;
        }

        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Mã hóa mật khẩu trước khi lưu vào database.
     *
     * @param rawPassword Mật khẩu gốc
     * @return Mật khẩu đã được mã hóa
     */
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
