package com.kittopmall.service;

import com.kittopmall.dto.UserDto;
import com.kittopmall.vo.UserVo;

public interface UserService {

    //회원 정보 등록
    void registerUser(UserDto userDto);

    //회원 아이디로 회원 조회
    UserDto getUserByUserId(Long id);

    //회원 이메일로 회원 조회
    UserDto getUserByEmail(String email);

    //회원 닉네임으로 회원 조회
    UserDto getUserByNickname(String nickname);

    //회원 정보 수정
    void modifyUser(UserDto userDto);

    //회원 정보 삭제
    void removeUser(Long userId);

}
