package cn.edu.zut.mfs.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.edu.zut.mfs.dto.DisableAccountDto;
import cn.edu.zut.mfs.pojo.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Sa-Token 账号封禁
 */
@Slf4j
@RestController
@RequestMapping("/disable/")
@Tag(name = "账号封禁")
public class DisableController {


    @Operation(summary = "封禁指定账号")
    @SaCheckRole("disable")
    @PostMapping("disable")
    public BaseResponse<String> disable(@RequestBody DisableAccountDto disableAccountDto) {
        /*
         * 账号封禁：
         * 	参数1：要封禁的账号id
         * 	参数2：要封禁的时间，单位：秒，86400秒=1天
         */
        StpUtil.disable(disableAccountDto.getUserId(), disableAccountDto.getTime());
        return BaseResponse.success("账号 " + disableAccountDto.getUserId() + " 封禁成功");
    }

    @Operation(summary = "解封指定账号")
    @SaCheckRole("disable")
    @PostMapping("untieDisable")
    public BaseResponse<String> untieDisable(@RequestParam String userId) {
        StpUtil.untieDisable(userId);
        return BaseResponse.success("账号 " + userId + " 解封成功");
    }

}
