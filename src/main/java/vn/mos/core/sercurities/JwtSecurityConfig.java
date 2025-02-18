package vn.mos.core.sercurities;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vn.mos.core.properties.PublicRoutesProvider;
import vn.mos.core.utils.JwtUtil;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class JwtSecurityConfig {

    private final JwtUtil jwtUtil;
    private final PublicRoutesProvider publicRoutesProvider;

    @Bean
    public SecurityFilter securityFilter() {
        return new SecurityFilter(jwtUtil);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            if ("admin".equals(username)) {
                return User.withUsername(username).password("{noop}password").roles("ADMIN").build();
            }
            throw new UsernameNotFoundException("User not found");
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<String> publicRoutes = publicRoutesProvider.getPublicRoutes();

        http.csrf(AbstractHttpConfigurer::disable) // ✅ Cập nhật cách gọi phương thức trong Spring Security 6
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(publicRoutes.toArray(new String[0])).permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(securityFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
