package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.UserLoginLog;
import cn.edu.zut.mfs.dto.JwtDto;
import cn.edu.zut.mfs.dto.UpdatePasswordDto;
import cn.edu.zut.mfs.dto.UserLoginDto;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.LoginAuthService;
import cn.edu.zut.mfs.service.UserService;
import cn.edu.zut.mfs.utils.EncryptUtils;
import cn.edu.zut.mfs.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * 登录
 */
@Slf4j
@RestController
@RequestMapping("/login/")
public class LoginController {
    LoginAuthService loginAuthService;

    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setLoginAuthService(LoginAuthService loginAuthService) {
        this.loginAuthService = loginAuthService;
    }

    @PostMapping("doLogin")
    @MessageMapping("/doLogin")
    public Mono<BaseResponse<Object>> doLogin(@RequestBody UserLoginDto userLoginDto) {
        userLoginDto.setPassword(EncryptUtils.encrypt(userLoginDto.getPassword(), userLoginDto.getUsername()));
        if (Boolean.TRUE.equals(loginAuthService.login(userLoginDto))) {
            String userId = userService.getUserByUsername(userLoginDto.getUsername()).getId();
            loginAuthService.addLoginLog(new UserLoginLog(null, userId, Date.from(Instant.now()), null));
            return Mono.just(BaseResponse.success(JwtUtils.generate(new JwtDto(UUID.randomUUID().toString(), userId, userId, Instant.now().plusSeconds(60 * 60 * 24 * 7), "mfs", userService.getRoleListAsString(userId)))));
        }
        return Mono.just(BaseResponse.fail("登录失败"));
    }

    @GetMapping("getLoginIdAsString")
    @MessageMapping("/getLoginIdAsString")
    public Mono<BaseResponse<String>> getLoginIdAsString(@AuthenticationPrincipal Jwt jwt) {
        return Mono.just(BaseResponse.success("当前登录的账号是：" + jwt.getSubject()));
    }

    @PostMapping("updatePassword")
    @MessageMapping("/updatePassword")
    public Mono<BaseResponse<String>> updatePassword(@AuthenticationPrincipal Jwt jwt, @RequestBody UpdatePasswordDto updatePasswordDto) {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername(jwt.getSubject());
        userLoginDto.setPassword(EncryptUtils.encrypt(updatePasswordDto.getOldPassword(), userLoginDto.getUsername()));
        if (Boolean.TRUE.equals(loginAuthService.login(userLoginDto))) {
            userLoginDto.setPassword(EncryptUtils.encrypt(updatePasswordDto.getNewPassword(), userLoginDto.getUsername()));
            if (Boolean.TRUE.equals(loginAuthService.updatePassword(userLoginDto))) {
                return Mono.just(BaseResponse.success("修改密码成功"));
            }
            return Mono.just(BaseResponse.fail("修改密码失败"));
        }
        return Mono.just(BaseResponse.fail("密码不正确"));
    }

}