package cn.edu.zut.mfs.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.edu.zut.mfs.pojo.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kickout/")
@Tag(name = "强制注销")
public class KickoutController {
    @Operation(summary = "将指定账号强制注销")
    @RequestMapping("logout")
    public BaseResponse<String> logout(long userId) {

        // 强制注销等价于对方主动调用了注销方法，再次访问会提示：Token无效。
        StpUtil.logout(userId);

        // 返回
        return BaseResponse.success("");
    }

    @Operation(summary = "将指定账号踢下线")
    @RequestMapping("kickout")
    public BaseResponse<String> kickout(long userId) {

        // 踢人下线不会清除Token信息，而是将其打上特定标记，再次访问会提示：Token已被踢下线。
        StpUtil.kickout(userId);

        // 返回
        return BaseResponse.success("");
    }


    @Operation(summary = "根据 Token 值踢人")
    @RequestMapping("kickoutByTokenValue")
    public BaseResponse<String> kickoutByTokenValue(String tokenValue) {

        StpUtil.kickoutByTokenValue(tokenValue);

        // 返回
        return BaseResponse.success("");
    }
}
