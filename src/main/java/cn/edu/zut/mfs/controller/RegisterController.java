package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.dto.UserLoginDto;
import cn.edu.zut.mfs.dto.UserRegisterDto;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.RegisterService;
import cn.edu.zut.mfs.utils.EncryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 用户注册
 */
@Slf4j
@RequestMapping("/register")
@RestController
public class RegisterController {
    RegisterService registerService;

    @Autowired
    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/user")
    @MessageMapping("/register")
    public Mono<BaseResponse<String>> user(@RequestBody UserRegisterDto userRegisterDto) {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername(userRegisterDto.getUsername());
        userRegisterDto.setPassword(EncryptUtils.encrypt(userRegisterDto.getPassword(), userRegisterDto.getUsername()));
        if (Boolean.TRUE.equals(registerService.register(userRegisterDto))) {
            return Mono.just(BaseResponse.success("注册成功"));
        }
        return Mono.just(BaseResponse.fail("注册失败"));
    }
}
