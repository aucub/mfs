package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.User;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.EncryptService;
import cn.edu.zut.mfs.service.RegisterService;
import cn.edu.zut.mfs.service.impl.EncryptServiceImpl;
import cn.edu.zut.mfs.vo.UserLoginVo;
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
    @PostMapping("getPublicKey")
    @ResponseBody
    public BaseResponse<String> getPublicKey() {
        return BaseResponse.success(encryptService.getPublicKey());
    }

    @Operation(summary = "用户注册")
    @PostMapping("/user")
    public BaseResponse<String> user(@RequestBody UserLoginVo userLoginVo) {
        if (encryptService.transformer(userLoginVo)) {
            if (registerService.register(new User(userLoginVo.getUsername(), userLoginVo.getPassword()))) {
                return BaseResponse.success("注册成功");
            }
        }
        return BaseResponse.fail("注册失败");
    }
}
