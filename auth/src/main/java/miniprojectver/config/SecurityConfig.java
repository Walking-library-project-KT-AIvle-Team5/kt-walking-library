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
            .cors().disable()
            .csrf().disable()
            .authorizeRequests() // ✅ 여기 수정
                .antMatchers("/auth/signup").permitAll()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginProcessingUrl("/auth/login")
                .successHandler((request, response, authentication) -> {
                    response.setStatus(200);
                    response.getWriter().write("{\"message\": \"Login successful!\"}");
                    response.setContentType("application/json");
                })
                .failureHandler((request, response, exception) -> {
                    response.sendError(401, "Invalid username or password");
                })
                .permitAll();

        return http.build();
    }
}

