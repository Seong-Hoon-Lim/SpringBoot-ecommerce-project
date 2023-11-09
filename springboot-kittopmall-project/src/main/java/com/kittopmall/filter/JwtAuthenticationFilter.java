package com.kittopmall.filter;

import com.kittopmall.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 클래스 설명
 * @author SeongHoonLim
 * @version v1.0
 * 목적: 스프링 시큐리티의 GenericFilterBean을 상속받아서 JWT 기반의 인증을 처리하는 필터를 구현한 것으로,
 * 클래스의 주된 목적은 HTTP 요청의 헤더에서 JWT 토큰을 추출하고, 해당 토큰이 유효한지 검증한 후,
 * 유효한 토큰일 경우 해당 사용자의 인증 정보를 SecurityContext에 저장하여
 * 다른 필터나 서비스에서 현재 인증된 사용자의 정보를 사용
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //Request Header 에서 JWT 토큰 추출
        String token = resolveToken((HttpServletRequest) request);
        //validateToken 으로 토큰 유효성 검사
        if (token != null && tokenProvider.validateToken(token)) {
            //토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 저장
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    //Request Authorization' 헤더에서 "Bearer " 접두사를 가진 토큰을 찾아내어 반환
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
