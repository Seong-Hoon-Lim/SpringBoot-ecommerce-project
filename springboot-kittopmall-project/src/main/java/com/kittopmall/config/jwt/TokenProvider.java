package com.kittopmall.config.jwt;

import com.kittopmall.dto.TokenDto;
import com.kittopmall.vo.UserVo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 클래스 설명
 * @author SeongHoonLim
 * @version v1.0
 * 목적: JWT 토큰의 생성, 검증, 사용자 정보 추출 등의 역할을 수행
 *
 * afterPropertiesSet: InitializingBean 인터페이스의 메소드로, 빈이 생성된 후 초기화 작업을 수행
 *
 * createToken: 인증 정보를 바탕으로 JWT 토큰을 생성하는 메소드입니다.
 * 인증 정보를 토큰의 클레임(claim)으로 저장하고, HS512 알고리즘을 사용하여 서명
 *
 * getAuthentication: 주어진 JWT 토큰으로부터 사용자 정보와 권한 정보를 추출하여 Authentication 객체 생성
 * 추출한 정보를 기반으로 사용자를 인증
 *
 * validateToken: 주어진 JWT 토큰의 유효성을 검사하는 메소드
 * 서명의 유효성 및 만료 여부 등을 확인 유효한 토큰인지 검증 결과를 반환
 */
@Log4j2
@Component
public class TokenProvider implements InitializingBean {

    //JWT 의 Payload 부분에서 사용자의 권한 정보를 저장하는 key 값
    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds
    ) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds = 1000;
    }

    //secret key 를 Base64 디코딩하여 Key 객체로 초기화
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //인증 정보를 바탕으로 JWT 토큰을 생성하는 메소드
    public TokenDto generateToken(Authentication authentication) {
        log.debug("TokenProvider: createAccessToken()...");
        String authorities = authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.joining(","));

        /*
        현재 시간을 기반으로 JWT 토큰의 만료시간(validity) 설정
        JWT 토큰을 생성할 때 exp (expiration time) 클레임으로 설정되며,
        토큰을 검증할 때 해당 토큰이 여전히 유효한지를 판단하는 데 사용
         */
        long nowTime = (new Date().getTime());
        Date validity = new Date(nowTime + this.tokenValidityInMilliseconds);

        //Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();

        log.debug("created accessToken: {}", accessToken);
        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    //JWT 토큰에서 사용자 정보와 권한 정보를 추출(복호화)하여 Authentication 객체 생성
    public Authentication getAuthentication(String accessToken) {
        //token 복호화
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        //Claims 에서 권한 정보 추출
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(authority -> new SimpleGrantedAuthority(authority))
                        .collect(Collectors.toList());

        //추출한 정보를 기반으로 사용자를 인증하여 Authentication 객체 생성
        UserVo principal = new UserVo(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);
    }

    //발급된 JWT 토큰을 검증하는 메소드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty", e);
        }
        return false;
    }

    //JWT 토큰에 포함된 정보를 파싱하는 메소드
    private Claims parseClaims(String accessToken) {
        try {
            // 토큰의 서명을 확인하고 클레임을 추출
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
            //만약 토큰이 만료되었다면(ExpiredJwtException), 만료된 클레임을 반환
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
