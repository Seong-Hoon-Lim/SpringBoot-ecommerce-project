package com.kittopmall.controller.api;

import com.kittopmall.dto.UserDto;
import com.kittopmall.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/kittop")
@RestController
public class UserRestController {

    private final UserService userService;

    //[API][POST] 회원 등록
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto user) {
        userService.registerUser(user);
        log.debug("UserRestController: signup() - user: {}", user);
        return ResponseEntity.ok("정상적으로 회원 등록 되었습니다.");
    }

    //[API][PUT] 회원 수정
    @PutMapping("/user/{email}")
    public ResponseEntity<String> updateUser(@PathVariable String email,
                                              @RequestBody UserDto user) {
        user.setEmail(email);
        userService.modifyUser(user);
        log.debug("UserRestController: updateUser() - user: {}", user);
        return ResponseEntity.ok("정상적으로 회원 수정 되었습니다.");

    }

    //[API][DELETE] 회원 삭제
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.removeUser(userId);
        log.debug("UserRestController: deleteUser()");
        return ResponseEntity.ok("정상적으로 회원 삭제 되었습니다.");
    }

}
