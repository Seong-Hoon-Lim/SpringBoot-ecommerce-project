package com.kittopmall.vo;

import com.kittopmall.vo.constants.Role;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

@Getter
@Builder
@ToString
@Log4j2
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String birth;
    private String gender;
    private String addr;
    private String phone;

    private String provider;            //OAuth 계정 유무
    private String providerId;          //OAuth 계정 고유번호

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updateDate;
    private String updatedBy;

    private Role role;
    private Collection<GrantedAuthority> authorities;

    /*
     Authentication 객체를 생성할 때 사용자 정보와 권한 정보를 담기 위해 사용되는 생성자
     email 필드에는 username (JWT 의 subject claim) 값이 할당됨
     통상적으로 JWT 토큰을 사용할 때는 비밀번호 정보를 토큰에 포함시키지 않으므로 비어있게됨
     authorities 필드에는 권한 목록이 할당됨

     role 필드는 스트림을 통해 authorities 컬렉션에서 권한 문자열을 Role enum 으로 변환하여 설정함.
     만약 매치되는 Role 이 없으면 ROLE_USER 로 설정

     JWT의 AUTHORITIES_KEY claim에 권한이 콤마로 구분된 문자열로 저장되어 있다면,
     TokenProvider 클래스의 getAuthentication 메소드는 이를 적절히 분리하고
     SimpleGrantedAuthority 객체로 변환하여 권한 목록을 생성
     */
    public UserVo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.email = username;
        this.password = password;
        this.authorities = new ArrayList<>(authorities);

        // 권한 목록에서 'ROLE_ADMIN'이 있는지 확인하고, 그에 따라 Role 값을 설정.
        this.role = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .map(String::toUpperCase)
                .map(authStr -> {
                    try {
                        return Role.valueOf(authStr);
                    } catch (IllegalArgumentException e) {
                        // 권한 문자열이 Role enum에 정의되어 있지 않은 경우 처리
                        log.error("Invalid role: {}", authStr);
                        return null;
                    }
                })
                .filter(Objects::nonNull) // null 값 제거
                .findFirst()
                .orElse(Role.ROLE_USER);
    }
}
