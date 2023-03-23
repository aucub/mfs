package cn.edu.zut.mfs.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.edu.zut.mfs.pojo.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Sa-Token 权限认证
 */
@Slf4j
@RestController
@RequestMapping("/jur/")
@Tag(name = "权限认证")
public class JurAuthController {

    @Operation(summary = "查询权限")
    @GetMapping("getPermission")
    public BaseResponse<LinkedHashMap<String, Object>> getPermission() {
        // 查询权限信息 ，如果当前会话未登录，会返回一个空集合
        List<String> permissionList = StpUtil.getPermissionList();
        // 查询角色信息 ，如果当前会话未登录，会返回一个空集合
        List<String> roleList = StpUtil.getRoleList();
        // 返回给前端
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("roleList", roleList);
        linkedHashMap.put("permissionList", permissionList);
        return BaseResponse.success(linkedHashMap);
    }
}


