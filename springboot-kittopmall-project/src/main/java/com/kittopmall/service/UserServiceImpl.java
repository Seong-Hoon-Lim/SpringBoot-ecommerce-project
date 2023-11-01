package com.kittopmall.service;

import com.kittopmall.config.exception.PasswordNotChangedException;
import com.kittopmall.dto.UserDto;
import com.kittopmall.mapper.UserMapper;
import com.kittopmall.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final ModelMapper mapper;
    private final UserMapper userMapper;

    @Override
    public void registerUser(UserDto userDto) {
        UserVo user = mapper.map(userDto, UserVo.class);
        log.debug("UserService: registerUser() - {}", user);
        userMapper.save(user);
    }

    @Override
    public UserDto getUserByUserId(Long id) {
        log.debug("UserService: getUserByUserId()");
        UserVo user = userMapper.findUserByUserId(id);
        if (user == null) {
            throw new NullPointerException("해당되는 회원이 없습니다 - userId: " + id);
        }
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        log.debug("UserService: getUserByEmail()");
        UserVo user = userMapper.findUserByEmail(email);
        if (user == null) {
            throw new NullPointerException("해당되는 회원이 없습니다 - email: " + email);
        }
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByNickname(String nickname) {
        log.debug("UserService: getUserByNickname()");
        UserVo user = userMapper.findUserByNickname(nickname);
        if (user == null) {
            throw new NullPointerException("해당되는 회원이 없습니다 - nickname: " + nickname);
        }
        return mapper.map(user, UserDto.class);
    }

    @Override
    public void modifyUser(UserDto userDto) {
        UserVo user = mapper.map(userDto, UserVo.class);
        UserVo registeredUser = userMapper.findUserByEmail(user.getEmail());
        if (userDto.getPassword().equals(registeredUser.getPassword())) {
            throw new PasswordNotChangedException();
        }
        log.debug("UserService: modifyUser() - {}", user);
        userMapper.update(user);
    }

    @Override
    public void removeUser(Long id) {
        log.debug("UserService: removeUser()");
        userMapper.delete(id);
    }
}
