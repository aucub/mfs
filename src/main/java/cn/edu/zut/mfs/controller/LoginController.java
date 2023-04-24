package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.UserLoginLog;
import cn.edu.zut.mfs.dto.JwtDto;
import cn.edu.zut.mfs.dto.UserLoginDto;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.LoginAuthService;
import cn.edu.zut.mfs.service.UserService;
import cn.edu.zut.mfs.utils.EncryptUtils;
import cn.edu.zut.mfs.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * Sa-Token 登录
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
    public BaseResponse<Object> doLogin(@RequestBody UserLoginDto userLoginDto) {
        userLoginDto.setPassword(EncryptUtils.encrypt(userLoginDto.getPassword(), userLoginDto.getUsername()));
        if (Boolean.TRUE.equals(loginAuthService.login(userLoginDto))) {
            String userId = userService.getUserByUsername(userLoginDto.getUsername()).getId();
            loginAuthService.addLoginLog(new UserLoginLog(null, userId, Date.from(Instant.now()), null));
            return BaseResponse.success(JwtUtils.generate(new JwtDto(UUID.randomUUID().toString(), userId, userId, Instant.now().plusSeconds(60 * 60 * 24 * 7), "mfs", userService.getRoleListAsString(userId))));
        }
        return BaseResponse.fail("登录失败");
    }

    @GetMapping("getLoginIdAsString")
    public BaseResponse<String> getLoginIdAsString(@AuthenticationPrincipal Jwt jwt) {
        return BaseResponse.success("当前客户端登录的账号是：" + jwt.getSubject());
    }

    @PostMapping("updatePassword")
    public BaseResponse<String> updatePassword(@RequestBody UserLoginDto userLoginDto) {
        userLoginDto.setPassword(EncryptUtils.encrypt(userLoginDto.getPassword(), userLoginDto.getUsername()));
        if (Boolean.TRUE.equals(loginAuthService.updatePassword(userLoginDto))) {
            return BaseResponse.success("修改密码成功");
        }
        return BaseResponse.fail("修改密码失败");
    }

}