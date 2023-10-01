package com.kittopmall.dto;


import com.kittopmall.vo.constants.Role;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

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

