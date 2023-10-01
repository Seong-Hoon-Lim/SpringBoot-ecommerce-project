package com.kittopmall.service;

import com.kittopmall.vo.UserVo;

public interface UserService {

    //회원 정보 등록
    void registerUser(UserVo userVo);

    //회원 아이디로 회원 조회
    UserVo getUserByUserId(Long id);

    //회원 이메일로 회원 조회
    UserVo getUserByEmail(String email);

    //회원 닉네임으로 회원 조회
    UserVo getUserByNickname(String nickname);

    //회원 정보 수정
    void modifyUser(UserVo userVO);

    //회원 정보 삭제
    void removeUser(Long userId);

}
