package vn.mos.core.config.configImpl;

import org.springframework.data.domain.AuditorAware;
import org.slf4j.MDC;
import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // ✅ Lấy userId từ MDC (nếu có)
        return Optional.ofNullable(MDC.get("userId"));
    }
}