package com.kittopmall.vo;

import com.kittopmall.vo.constants.Role;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

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
}
