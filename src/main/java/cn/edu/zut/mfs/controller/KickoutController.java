package cn.edu.zut.mfs.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.edu.zut.mfs.pojo.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sa-Token 账号注销
 */
@Slf4j
@RestController
@RequestMapping("/kickout/")
@Tag(name = "账号注销")
public class KickoutController {
    @Operation(summary = "将指定账号强制注销")
    @SaCheckPermission("user:logout")
    @PostMapping("logout")
    public BaseResponse<String> logout(@RequestParam String userId) {

        // 强制注销等价于对方主动调用了注销方法，再次访问会提示：Token无效。
        StpUtil.logout(userId);

        // 返回
        return BaseResponse.success("账号 " + userId + "强制注销成功");
    }

    @Operation(summary = "将指定账号踢下线")
    @SaCheckPermission("user:kickout")
    @PostMapping("kickout")
    public BaseResponse<String> kickout(@RequestParam String userId) {

        // 踢人下线不会清除Token信息，而是将其打上特定标记，再次访问会提示：Token已被踢下线。
        StpUtil.kickout(userId);

        // 返回
        return BaseResponse.success("账号 " + userId + "下线成功");
    }


    /*@Operation(summary = "根据 Token 值踢人")
    @PostMapping("kickoutByTokenValue")
    public BaseResponse<String> kickoutByTokenValue(@RequestParam String tokenValue) {

        StpUtil.kickoutByTokenValue(tokenValue);

        // 返回
        return BaseResponse.success("Token " + tokenValue + "下线成功");
    }*/
}
