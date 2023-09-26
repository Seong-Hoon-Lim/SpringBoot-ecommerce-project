package com.kittopmall.mapper;

import com.kittopmall.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    //회원 DB 저장
    void save(UserVo userVo);

    //회원 DB count 조회
    long count();

    //회원 아이디로 회원 DB 조회
    UserVo findUserByUserId(Long id);

    //회원 이메일로 회원 DB 조회
    UserVo findUserByEmail(String email);

    //회원 닉네임으로 회원 DB 조회
    UserVo findUserByNickname(String nickname);

    //회원 DB 수정
    void update(UserVo userVO);

    //회원 DB 삭제
    void delete(Long userId);

    //회원 DB 리스트 조회
//    List<UserVo> findUserList(PageRequestDTO pageRequestDTO);

    //회원 DB 전체 회원 count 조회
//    int findUserCount(PageRequestDTO pageRequestDTO);


}
