package com.kittopmall.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kittopmall.config.SecurityConfig;
import com.kittopmall.dto.UserDto;
import com.kittopmall.mapper.UserMapper;
import com.kittopmall.service.UserService;
import com.kittopmall.vo.UserVo;
import com.kittopmall.vo.constants.Role;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("회원 관리 API Controller 테스트")
@Log4j2
@Import(SecurityConfig.class)
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    private final MockMvc mvc;

    @MockBean private UserService userService;
    @MockBean private UserMapper userMapper;
    @MockBean private ModelMapper mapper;

    public UserRestControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[API][POST] 회원 등록 - 정상 호출")
    @Test
    void givenUserForm_whenRequesting_thenSavesUserForm() throws Exception {
        //Given
        UserDto dto = createUserDto();
        String obj = new ObjectMapper().writeValueAsString(dto);
        willDoNothing().given(userService).registerUser(any(UserDto.class));

        //When & Then
        mvc.perform(
                        post("/kittop/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(obj)
                                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("정상적으로 회원 등록 되었습니다.")));

        then(userService).should().registerUser(any(UserDto.class));
    }

    @DisplayName("[API][PUT] 회원 수정 - 정상 호출, 인증된 사용자")
    @WithMockUser
    @Test
    void givenUpdatedUserForm_whenRequesting_thenUpdatesUserForm() throws Exception {
        //Given
        String email = "test@gmail.com";
        UserDto createdDto = createUserDto();
        UserDto updatedDto = updatedUserDto();

        // userService.modifyUser()가 호출될 때 createdDto를 updatedDto로 변경
        willAnswer(invocation -> {
            UserDto argument = invocation.getArgument(0);
            BeanUtils.copyProperties(argument, createdDto); // updatedDto의 값을 createdDto로 복사
            return null;
        }).given(userService).modifyUser(any(UserDto.class));

        String obj = new ObjectMapper().writeValueAsString(updatedDto);

        //When & Then
        mvc.perform(
                        put("/kittop/user/" + email)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(obj)
                                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("정상적으로 회원 수정 되었습니다.")));

        then(userService).should().modifyUser(any(UserDto.class));

        // createdDto가 updatedDto로 변경되었는지 검증
        assertThat(createdDto).isEqualToComparingFieldByField(updatedDto);
    }


    @DisplayName("[API][DELETE] 회원 삭제 - 정상 호출, 인증된 사용자")
    @WithMockUser
    @Test
    void givenUser_whenRequesting_thenDeletesUser() throws Exception {
        //Given
        long userId = 1L;
        UserDto dto = createUserDto();
        userService.registerUser(createUserDto());
        String obj = new ObjectMapper().writeValueAsString(dto);
        willDoNothing().given(userService).removeUser(userId);

        //When & Then
        mvc.perform(
                        delete("/kittop/user/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(obj)
                                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("정상적으로 회원 삭제 되었습니다.")));
        then(userService).should().removeUser(userId);
    }

    private UserVo updatedUser() {
        return UserVo.builder()
                .email("test@gmail.com")
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
                .email("test@gmail.com")
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