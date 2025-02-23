package vn.mos.core.config.configImpl;

import org.springframework.data.domain.AuditorAware;
import org.slf4j.MDC;
import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        String userId = MDC.get("userId");
        if (userId != null) {
            // ✅ Lấy userId từ request (nếu có)
            return Optional.of(Long.valueOf(userId));
        }else {
            return Optional.empty();
        }
    }
}