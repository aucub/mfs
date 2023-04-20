package cn.edu.zut.mfs.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 */
@Slf4j
@RequestMapping("/role")
@Tag(name = "角色管理")
@RestController
public class RoleController {
    RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "获取角色列表")
    @GetMapping(value = "/list")
    public BaseResponse<List<Role>> list() {
        return BaseResponse.success(roleService.list());
    }

    @Operation(summary = "添加")
    @SaCheckRole("role")
    @PostMapping(value = "/save")
    public BaseResponse<String> save(@RequestBody Role role) {
        if (Boolean.TRUE.equals(roleService.save(role))) {
            return BaseResponse.success("添加成功");
        }
        return BaseResponse.fail("添加失败");
    }

    @Operation(summary = "修改")
    @SaCheckRole("role")
    @PostMapping(value = "/update")
    public BaseResponse<String> update(@RequestBody Role role) {
        if (Boolean.TRUE.equals(roleService.update(role))) {
            return BaseResponse.success("修改成功");
        }
        return BaseResponse.fail("修改失败");
    }

    @Operation(summary = "删除")
    @SaCheckRole("role")
    @PostMapping(value = "/delete")
    public BaseResponse<String> delete(@NotBlank(message = "角色id不能为空") @RequestParam String id) {
        if (Boolean.TRUE.equals(roleService.delete(id))) {
            return BaseResponse.success("删除成功");
        }
        return BaseResponse.fail("删除失败");
    }

}
