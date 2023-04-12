package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.dto.UserLoginDto;
import cn.edu.zut.mfs.dto.UserRegisterDto;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.RegisterService;
import cn.edu.zut.mfs.utils.EncryptUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户注册
 */
@Slf4j
@RequestMapping("/register")
@Tag(name = "用户注册")
@RestController
public class RegisterController {
    RegisterService registerService;

    @Autowired
    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Operation(summary = "用户注册")
    @PostMapping("/user")
    public BaseResponse<String> user(@RequestBody UserRegisterDto userRegisterDto) {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername(userRegisterDto.getUsername());
        userLoginDto.setPassword(userRegisterDto.getPassword());
        if (EncryptUtils.encryptUser(userLoginDto)) {
            userRegisterDto.setPassword(userLoginDto.getPassword());
            if (registerService.register(userRegisterDto)) {
                return BaseResponse.success("注册成功");
            }
        }
        return BaseResponse.fail("注册失败");
    }
}
