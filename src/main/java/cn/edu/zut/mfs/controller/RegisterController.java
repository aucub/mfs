package cn.edu.zut.mfs.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.edu.zut.mfs.dto.UserRegisterDto;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.EncryptService;
import cn.edu.zut.mfs.service.RegisterService;
import cn.edu.zut.mfs.service.impl.EncryptServiceImpl;
import cn.edu.zut.mfs.dto.UserLoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户注册
 */
@Slf4j
@RequestMapping("/register")
@Tag(name = "用户注册")
@RestController
public class RegisterController {
    RegisterService registerService;
    EncryptService encryptService;

    @Autowired
    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Autowired
    public void setEncryptService(EncryptServiceImpl encryptService) {
        this.encryptService = encryptService;
    }

    @Operation(summary = "获取公钥")
    @GetMapping("getPublicKey")
    public BaseResponse<String> getPublicKey() {
        return BaseResponse.success(encryptService.getPublicKey());
    }

    @Operation(summary = "用户注册")
    @SaCheckPermission("register:user")
    @PostMapping("/user")
    public BaseResponse<String> user(@RequestBody UserRegisterDto userRegisterDto) {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername(userRegisterDto.getUsername());
        userLoginDto.setPassword(userRegisterDto.getPassword());
        userLoginDto.setPublicKey(userRegisterDto.getPublicKey());
        if (encryptService.transformer(userLoginDto)) {
            userRegisterDto.setPassword(userLoginDto.getPassword());
            if (registerService.register(userRegisterDto)) {
                return BaseResponse.success("注册成功");
            }
        }
        return BaseResponse.fail("注册失败");
    }
}
