package cn.edu.zut.mfs.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.edu.zut.mfs.pojo.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sa-Token 账号封禁
 */
@Slf4j
@RestController
@RequestMapping("/disable/")
@Tag(name = "账号封禁")
public class DisableController {


    @Operation(summary = "封禁指定账号")
    @SaCheckPermission("disable:disable")
    @PostMapping("disable")
    public BaseResponse<String> disable(String userId) {
        /*
         * 账号封禁：
         * 	参数1：要封禁的账号id
         * 	参数2：要封禁的时间，单位：秒，86400秒=1天
         */
        StpUtil.disable(userId, 86400);
        System.out.println(userId);
        return BaseResponse.success("账号 " + userId + " 封禁成功");
    }

    @Operation(summary = "解封指定账号")
    @SaCheckPermission("disable:untieDisable")
    @PostMapping("untieDisable")
    public BaseResponse<String> untieDisable(String userId) {
        StpUtil.untieDisable(userId);
        return BaseResponse.success("账号 " + userId + " 解封成功");
    }

}
