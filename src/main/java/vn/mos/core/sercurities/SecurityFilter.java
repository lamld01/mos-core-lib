package vn.mos.core.sercurities;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.mos.core.filter.TraceIdFilter;
import vn.mos.core.sercurities.data.TokenUserInfo;
import vn.mos.core.utils.JwtUtil;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        String traceId = TraceIdFilter.getTraceId();
        MDC.put(TraceIdFilter.TRACE_ID, traceId);
        request.setAttribute(TraceIdFilter.TRACE_ID, traceId);

        // Lấy token từ header Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Lấy JWT từ header
        String token = authHeader.substring(7);
        TokenUserInfo userInfo = jwtUtil.extractUserInfo(token); // ✅ Lấy full user info từ token

        // Kiểm tra token hợp lệ và chưa có authentication trong SecurityContext
        if (userInfo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(token)) {

                // ✅ Set UserDetails từ thông tin trong TokenUserInfo
                UserDetails userDetails = User.withUsername(userInfo.getUsername())
                    .password("") // Không cần mật khẩu
                    .roles(userInfo.getUserRole().name()) // Gán role từ user info
                    .build();

                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // ✅ Set authentication vào SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}
