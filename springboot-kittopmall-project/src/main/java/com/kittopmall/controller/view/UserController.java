package com.kittopmall.controller.view;

import com.kittopmall.dto.UserDto;
import com.kittopmall.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/kittop")
@Controller
public class UserController {

    private final UserService userService;

    //[View][GET] 회원가입 페이지
    @GetMapping("/signup")
    public String displaySignup(@ModelAttribute("user") UserDto user,
                                Model model) {
        model.addAttribute("user", user);
        return "user/signup";
    }

    //[View][GET] 회원 로그인 페이지
    @GetMapping("/signin")
    public String displaySignin() {
        return "user/signin";
    }

    //[View][GET] 회원 정보 페이지 / 회원 수정 페이지
    @GetMapping("/user/{email}")
    public String displayUserInfo(@PathVariable String email,
                                  Model model) {
        UserDto user = userService.getUserByEmail(email);
        log.debug("UserController displayUserInfo() - {}", user);
        model.addAttribute("user", user);
        return "user/info";
    }

}
