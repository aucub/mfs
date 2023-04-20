package cn.edu.zut.mfs.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
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
@Tag(name = "注销")
public class KickoutController {
    @Operation(summary = "将指定账号强制注销")
    @SaCheckRole("logout")
    @PostMapping("logout")
    public BaseResponse<String> logout(@RequestParam String userId) {

        // 强制注销等价于对方主动调用了注销方法，再次访问会提示：Token无效。
        StpUtil.logout(userId);

        // 返回
        return BaseResponse.success("账号 " + userId + "强制注销成功");
    }
}
