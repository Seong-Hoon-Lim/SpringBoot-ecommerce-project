package com.kittopmall.config;

import com.kittopmall.config.jwt.TokenProvider;
import com.kittopmall.filter.JwtAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity //스프링 시큐리티를 활성화하고 웹 보안 설정을 구성
@Configuration
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    public SecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    //패스워드 암호화 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(tokenProvider);
        return http
                //jwt 토큰 사용으로 불필요
                .csrf().disable()
                //인증 처리 관점에서 스프링 시큐리티가 더 이상 세션쿠키 방식의 인증 메카니즘으로 인증처리를 하지 않음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .mvcMatchers(
                                HttpMethod.GET,
                                "/", "/kittop", "/kittop/signup", "/kittop/signin"
                        ).permitAll()
                        .mvcMatchers(HttpMethod.POST, "/kittop/signup").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/kittop/signin")
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
