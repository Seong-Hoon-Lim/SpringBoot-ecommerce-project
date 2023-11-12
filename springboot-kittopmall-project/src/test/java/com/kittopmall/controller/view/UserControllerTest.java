package com.kittopmall.controller.view;

import com.kittopmall.config.SecurityConfig;
import com.kittopmall.controller.view.UserController;
import com.kittopmall.dto.UserDto;
import com.kittopmall.mapper.UserMapper;
import com.kittopmall.service.UserService;
import com.kittopmall.vo.UserVo;
import com.kittopmall.vo.constants.Role;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("회원관리 View Controller 테스트")
@Log4j2
@Import(SecurityConfig.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    private final MockMvc mvc;

    @MockBean private UserService userService;

    public UserControllerTest(
            @Autowired MockMvc mvc
    ) {
        this.mvc = mvc;
    }

    @DisplayName("[View][GET] 회원가입 페이지 - 정상 호출")
    @Test
    void givenNothing_whenRequestingDisplayingSignupView_thenReturnSignupView() throws Exception {
        //Given

        //When & Then
        mvc.perform(get("/kittop/signup"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("user/signup"))
                .andExpect(model().attributeExists("user"));
    }

    @DisplayName("[View][GET] 회원 로그인 페이지 - 정상 호출")
    @WithMockUser
    @Test
    void givenNothing_whenRequestingDisplayingSigninView_thenReturnsSigninView() throws Exception {
        //Given

        //When & Then
        mvc.perform(get("/kittop/signin"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("user/signin"));
    }

    @DisplayName("[View][GET] 회원 정보 페이지 - 인증이 없다면 로그인 페이지로 이동")
    @Test
    void givenNothing_whenRequestingDisplayingUserInfoView_thenRedirectsToSigninView() throws Exception {
        //Given
        long userId = 1L;

        //When & Then
        mvc.perform(get("/kittop/user/" + userId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/signin"));
        then(userService).shouldHaveNoInteractions();
    }

    @DisplayName("[View][GET] 회원 정보 페이지 / 회원 수정 페이지 - 정상 호출, 인증된 사용자")
    @WithMockUser
    @Test
    void givenNothing_whenRequestingDisplayingUserInfoView_thenReturnsUserInfoView() throws Exception {
        //Given
        String email = "test@gmail.com";
        UserDto dto = createUserDto();
        given(userService.getUserByEmail(email)).willReturn(dto);

        //When & Then
        mvc.perform(get("/kittop/user/" + email))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("user/info"))
                .andExpect(model().attribute("user", dto));
        then(userService).should().getUserByEmail(email);
    }

    private UserVo updatedUser() {
        return UserVo.builder()
                .id(1L)
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
                .id(1L)
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
                .id(1L)
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
                .id(1L)
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