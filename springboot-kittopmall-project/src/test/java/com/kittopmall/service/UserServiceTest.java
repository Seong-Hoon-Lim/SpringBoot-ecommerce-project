package com.kittopmall.service;

import com.kittopmall.dto.UserDto;
import com.kittopmall.mapper.UserMapper;
import com.kittopmall.vo.UserVo;
import com.kittopmall.vo.constants.Role;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@DisplayName("회원관리 service 설계 테스트")
@Log4j2
@SpringBootTest
class UserServiceTest {

    @InjectMocks private UserServiceImpl sut;
    @Mock private ModelMapper mapper;
    @Mock private UserMapper userMapper;

    @DisplayName("회원관리 service 설계 - 회원 정보를 입력하면 회원 정보를 생성한다")
    @Test
    void givenUserInfo_whenSavingUser_thenSavesUser() {
        //Given
        UserDto dto = createUserDto();
        given(mapper.map(dto, UserVo.class)).willReturn(createUser());
        UserVo user = mapper.map(dto, UserVo.class);
        given(userMapper.findUserByUserId(user.getId())).willReturn(createUser());

        //When
        sut.registerUser(dto);

        //Then
        then(userMapper).should().save(any(UserVo.class));
    }

    @DisplayName("회원관리 service 설계 - 일치하는 회원 고유 번호가 없으면, 예외를 던진다")
    @Test
    void givenUserNonexistentUserId_whenSearchingUser_thenThrowsException() {
        //Given
        Long userId = 0L;
        given(userMapper.findUserByUserId(userId)).willReturn(null);
        //When
        Throwable throwable = catchThrowable(() -> sut.getUserByUserId(userId));
        //Then
        assertThat(throwable)
                .isInstanceOf(NullPointerException.class)
                .hasMessage("해당되는 회원이 없습니다 - userId: " + userId);
        then(userMapper).should().findUserByUserId(userId);
    }

    @DisplayName("회원관리 service 설계 - 일치하는 회원 고유 번호가 있으면, 회원을 반환한다")
    @Test
    void givenUserId_whenSearchingUser_thenReturnsUser() {
        //Given
        Long userId = 1L;
        UserDto dto = createUserDto();
        given(mapper.map(any(UserVo.class), eq(UserDto.class))).willReturn(createUserDto());
        given(userMapper.findUserByUserId(userId)).willReturn(createUser());
        //When
        dto = sut.getUserByUserId(userId);
        //Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("email", dto.getEmail())
                .hasFieldOrPropertyWithValue("nickname", dto.getNickname())
                .hasFieldOrPropertyWithValue("name", dto.getName())
                .hasFieldOrPropertyWithValue("birth", dto.getBirth())
                .hasFieldOrPropertyWithValue("gender", dto.getGender())
                .hasFieldOrPropertyWithValue("addr", dto.getAddr())
                .hasFieldOrPropertyWithValue("phone", dto.getPhone())
                .hasFieldOrPropertyWithValue("role", dto.getRole());
        then(userMapper).should().findUserByUserId(userId);
    }

    @DisplayName("회원관리 service 설계 - 일치하는 회원 이메일이 있으면, 회원을 반환한다")
    @Test
    void givenUserEmail_whenSearchingUser_thenReturnsUser() {
        //Given
        String userEmail = "test@gmail.com";
        UserDto dto = createUserDto();
        given(mapper.map(any(UserVo.class), eq(UserDto.class))).willReturn(createUserDto());
        given(userMapper.findUserByEmail(userEmail)).willReturn(createUser());
        //When
        dto = sut.getUserByEmail(userEmail);
        //Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("email", dto.getEmail())
                .hasFieldOrPropertyWithValue("nickname", dto.getNickname())
                .hasFieldOrPropertyWithValue("name", dto.getName())
                .hasFieldOrPropertyWithValue("birth", dto.getBirth())
                .hasFieldOrPropertyWithValue("gender", dto.getGender())
                .hasFieldOrPropertyWithValue("addr", dto.getAddr())
                .hasFieldOrPropertyWithValue("phone", dto.getPhone())
                .hasFieldOrPropertyWithValue("role", dto.getRole());
        then(userMapper).should().findUserByEmail(userEmail);
    }

    @DisplayName("회원관리 service 설계 - 일치하는 회원 닉네임이 있으면, 회원을 반환한다")
    @Test
    void givenUserNickname_whenSearchingUser_thenReturnsUser() {
        //Given
        String nickname = "test";
        UserDto dto = createUserDto();
        given(mapper.map(any(UserVo.class), eq(UserDto.class))).willReturn(createUserDto());
        given(userMapper.findUserByNickname(nickname)).willReturn(createUser());
        //When
        dto = sut.getUserByNickname(nickname);
        //Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("email", dto.getEmail())
                .hasFieldOrPropertyWithValue("nickname", dto.getNickname())
                .hasFieldOrPropertyWithValue("name", dto.getName())
                .hasFieldOrPropertyWithValue("birth", dto.getBirth())
                .hasFieldOrPropertyWithValue("gender", dto.getGender())
                .hasFieldOrPropertyWithValue("addr", dto.getAddr())
                .hasFieldOrPropertyWithValue("phone", dto.getPhone())
                .hasFieldOrPropertyWithValue("role", dto.getRole());
        then(userMapper).should().findUserByNickname(nickname);
    }

    @DisplayName("회원관리 service 설계 - 회원 수정 정보를 입력하면, 회원 정보를 수정한다")
    @Test
    void givenModifiedUserInfo_whenUpdatingUser_thenUpdatesUser() {
        //Given
        UserDto createdDto = createUserDto();
        given(mapper.map(createdDto, UserVo.class)).willReturn(createUser());
        UserVo createdUser = mapper.map(createdDto, UserVo.class);
        given(userMapper.findUserByUserId(createdUser.getId())).willReturn(createUser());

        UserDto updatedDto = updatedUserDto();
        given(mapper.map(updatedDto, UserVo.class)).willReturn(updatedUser());
        UserVo updatedUser = mapper.map(updatedDto, UserVo.class);
        given(userMapper.findUserByUserId(updatedUser.getId())).willReturn(createUser());

        //When
        sut.registerUser(createdDto);
        sut.modifyUser(updatedDto);

        //Then
        assertThat(createdUser).isNotEqualTo(updatedUser);
        then(userMapper).should().save(any(UserVo.class));
    }

    @DisplayName("회원관리 service 설계 - 회원 고유 정보를 입력하면, 회원을 삭제한다")
    @Test
    void givenUserId_whenDeletingUser_thenDeletesUser() {
        //Given
        Long userId = 1L;
        willDoNothing().given(userMapper).delete(userId);
        //When
        sut.removeUser(userId);
        //Then
        then(userMapper).should().delete(userId);
    }


    private UserVo updatedUser() {
        return UserVo.builder()
                .email("modify@gmail.com")
                .password("1111")
                .nickname("modify")
                .name("modify")
                .birth("1990/01/01")
                .gender("남")
                .addr("34005/대전광역시 유성구 대덕대로1111번길 1-8/가나타운 1동 1호")
                .phone("01012341111")
                .provider(null)
                .providerId(null)
                .createdBy("modify")
                .role(Role.ROLE_USER)
                .build();
    }

    private UserDto updatedUserDto() {
        return UserDto.builder()
                .email("modify@gmail.com")
                .password("1111")
                .nickname("modify")
                .name("modify")
                .birth("1990/01/01")
                .gender("남")
                .addr("34005/대전광역시 유성구 대덕대로1111번길 1-8/가나타운 1동 1호")
                .phone("01012341111")
                .provider(null)
                .providerId(null)
                .createdBy("modify")
                .role(Role.ROLE_USER)
                .build();
    }
    private UserDto createUserDto() {
        return UserDto.builder()
                .email("test@gmail.com")
                .password("1234")
                .nickname("test")
                .name("test")
                .birth("1990/01/01")
                .gender("남")
                .addr("34005/대전광역시 유성구 대덕대로1111번길 1-8/가나타운 1동 1호")
                .phone("01012341111")
                .provider(null)
                .providerId(null)
                .createdBy("test")
                .role(Role.ROLE_USER)
                .build();
    }

    private UserVo createUser() {
        return UserVo.builder()
                .email("test@gmail.com")
                .password("1234")
                .nickname("test")
                .name("test")
                .birth("1990/01/01")
                .gender("남")
                .addr("34005/대전광역시 유성구 대덕대로1111번길 1-8/가나타운 1동 1호")
                .phone("01012341111")
                .provider(null)
                .providerId(null)
                .createdBy("test")
                .role(Role.ROLE_USER)
                .build();
    }
}