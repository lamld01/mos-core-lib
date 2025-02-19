package vn.mos.core.sercurities.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    /**
     * Kiểm tra mật khẩu người dùng nhập vào có khớp với mật khẩu trong database không.
     *
     * @param userPassword Mật khẩu trong database của người dùng
     * @param rawPassword Mật khẩu người dùng nhập vào
     * @return true nếu mật khẩu đúng, false nếu sai
     */
    public boolean checkPassword(String userPassword, String rawPassword) {
        if (userPassword == null) {
            return false;
        }

        return passwordEncoder.matches(rawPassword, userPassword);
    }
}
