package vn.mos.core.sercurities;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import vn.mos.core.sercurities.properties.RoutesProvider;
import vn.mos.core.utils.JwtUtil;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Log4j2
@EnableWebSecurity
@EnableMethodSecurity
public class JwtSecurityConfig {

  private final JwtUtil jwtUtil;
  private final RoutesProvider routesProvider;
  private final JwtAuthEntryPoint jwtAuthEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

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
    List<String> publicRoutes = routesProvider.getPublicRoutes();
    List<String> allowCorsUrl = routesProvider.getCorsAllowUrl();

    log.info("\uD83D\uDCCC Public Routes: {}", publicRoutes);
    log.info("\uD83D\uDCCC CORS Allow URLs: {}", allowCorsUrl);

    http
        .cors(cors -> cors.configurationSource(request -> {
          CorsConfiguration config = new CorsConfiguration();
          config.setAllowedOrigins(allowCorsUrl); //
          config.setAllowedHeaders(List.of("*"));
          config.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS"));
          config.setAllowCredentials(true);
          return config;
        }))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(publicRoutes.toArray(new String[0])).permitAll()
            .anyRequest().authenticated()
        )
        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(jwtAuthEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
        )
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(securityFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
