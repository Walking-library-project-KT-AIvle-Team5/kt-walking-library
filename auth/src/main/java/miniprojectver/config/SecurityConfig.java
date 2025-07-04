package miniprojectver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.disable())
            .csrf(csrf -> csrf.disable());

        // 경로별 인증/인가 설정
        http
            .authorizeHttpRequests(authz -> authz
                // 회원가입 API는 누구나 접근 가능
                .antMatchers("/auth/signup").permitAll()
                // 그 외 모든 요청은 인증된 사용자만 접근 가능
                .anyRequest().authenticated()
            );

        // Spring Security의 formLogin 기능을 사용하도록 설정합니다.
        http
            .formLogin(form -> form
                // 로그인 요청을 처리할 URL을 지정합니다.
                .loginProcessingUrl("/auth/login")
                // 로그인 성공 시 API 서버에 맞는 응답을 보냅니다.
                .successHandler((request, response, authentication) -> {
                    response.setStatus(200);
                    response.getWriter().write("{\"message\": \"Login successful!\"}");
                    response.setContentType("application/json");
                })
                // 로그인 실패 시 API 서버에 맞는 응답을 보냅니다.
                .failureHandler((request, response, exception) -> {
                    response.sendError(401, "Invalid username or password");
                })
                .permitAll()
            );

        return http.build();
    }
}

