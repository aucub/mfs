package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户注册
 */
@Slf4j
@RequestMapping("/register/")
@Tag(name = "用户注册")
@RestController
public class RegisterController {
    RegisterService registerService;

    @Autowired
    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Operation(summary = "用户注册")
    @PostMapping("user")
    public BaseResponse<String> user(String username, String password) {
        if (registerService.register(username, password)) {
            return BaseResponse.success("注册成功");
        }
        return BaseResponse.fail("注册失败");
    }
}
