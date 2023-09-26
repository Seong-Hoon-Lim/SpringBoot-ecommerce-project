package com.kittopmall.mapper;

import com.kittopmall.vo.UserVo;
import com.kittopmall.vo.constants.Role;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("mapper 테스트")
@Log4j2
@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @DisplayName("mapper 테스트 - 회원 DB 저장 테스트")
    @Test
    void givenUser_whenSaving_thenWorksFine() {
        //Given
        long previousCount = userMapper.count();
        UserVo user = UserVo.builder()
                .email("hooney@gmail.com")
                .password("1234")
                .nickname("hooney")
                .name("가길동")
                .birth("1990/01/01")
                .gender("남")
                .addr("34005/대전광역시 유성구 대덕대로1111번길 1-8/가나타운 1동 1호")
                .phone("01012341111")
                .provider(null)
                .providerId(null)
                .createdBy("가길동")
                .role(Role.ROLE_USER)
                .build();
        //When
        userMapper.save(user);
        //Then
        assertThat(userMapper.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("mapper 테스트 - 회원 고유번호로 회원 DB 조회 테스트")
    @Test
    void givenUserId_whenFindingUser_thenReturnUser() {
        //Given
        long userId = 1;

        //When
        UserVo findedUser = userMapper.findUserByUserId(userId);
        //Then
        assertThat(findedUser).isNotNull();
    }

    @DisplayName("mapper 테스트 - 회원 이메일로 회원 DB 조회 테스트")
    @Test
    void givenUserEmail_whenFindingUser_thenReturnUser() {
        //Given
        String email = "hooney@gmail.com";

        //When
        UserVo findedUser = userMapper.findUserByEmail(email);
        //Then
        assertThat(findedUser).isNotNull();
    }

    @DisplayName("mapper 테스트 - 회원 닉네임으로 회원 DB 조회 테스트")
    @Test
    void givenUserNickname_whenFindingUser_thenReturnUser() {
        //Given
        String nickname = "hooney";

        //When
        UserVo findedUser = userMapper.findUserByNickname(nickname);
        //Then
        assertThat(findedUser).isNotNull();
    }

    @DisplayName("mapper 테스트 - 회원 DB 수정 테스트")
    @Test
    void givenUser_whenUpdatingUser_thenWorksFine() {
        //Given
        Long userId = 1L;
        UserVo originalUser = userMapper.findUserByUserId(userId);
        UserVo updatedUser = UserVo.builder()
                .id(userId)
                .password("1212")
                .nickname("test#")
                .name("나길동")
                .birth("1990/01/01")
                .gender("남")
                .addr("34005/대전광역시 유성구 대덕대로1111번길 1-8/가나타운 1동 1호")
                .phone("01012341112")
                .updatedBy("나길동")
                .role(Role.ROLE_USER)
                .build();

        //When
        userMapper.update(updatedUser);
        //Then
        assertThat(originalUser).isNotEqualTo(updatedUser);
    }

    @DisplayName("mapper 테스트 - 회원 DB 수정 테스트")
    @Test
    void givenUserId_whenDeletingUser_thenWorksFine() {
        //Given
        Long userId = 1L;
        UserVo findedUser = userMapper.findUserByUserId(userId);

        //When
        userMapper.delete(userId);
        //Then
        assertThat(findedUser).isNull();
    }

}
