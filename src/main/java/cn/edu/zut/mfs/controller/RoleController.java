package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.pojo.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class RoleController {
    @Operation(summary = "获取全部信息")
    @GetMapping(value = "/all")
    public BaseResponse<List<Role>> getAllRoleList() {
        return null;
    }

    @Operation(summary = "添加")
    @PostMapping(value = "/save")
    public BaseResponse<String> save(@RequestBody Role role) {
        return null;
    }

    @Operation(summary = "修改")
    @PostMapping(value = "/update")
    public BaseResponse<String> update(@RequestBody Role role) {
        return null;
    }

    @Operation(summary = "删除")
    @PostMapping(value = "/delete")
    public BaseResponse<String> delete(@NotBlank(message = "角色id不能为空") @RequestParam String id) {
        return null;
    }

}
