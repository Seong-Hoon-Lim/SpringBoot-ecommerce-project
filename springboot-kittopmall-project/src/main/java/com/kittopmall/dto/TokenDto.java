package com.kittopmall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * 클래스 설명
 * @author SeongHoonLim
 * @version v1.0
 * 목적: client 에 token 을 보내기 위한 DTO 객체 생성
 */
@Builder
@Data
@AllArgsConstructor
@ToString
public class TokenDto {

    private String grantType;       //JWT 인증 타입 - Bearer 사용
    private String accessToken;
}
