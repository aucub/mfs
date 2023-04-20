package cn.edu.zut.mfs.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.domain.User;
import cn.edu.zut.mfs.dto.*;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.RegisterService;
import cn.edu.zut.mfs.service.UserService;
import cn.edu.zut.mfs.utils.EncryptUtils;
import cn.edu.zut.mfs.utils.JwtUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * 用户管理
 */
@Slf4j
@RequestMapping("/user")
@Tag(name = "用户管理")
@RestController
public class UserController {
    UserService userService;
    RegisterService registerService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Operation(summary = "用户查询")
    @SaCheckRole("userMan")
    @PostMapping("/list")
    public BaseResponse<Page<User>> list(@RequestBody FindUserPageDto findUserPageDto) {
        return BaseResponse.success(userService.list(findUserPageDto));
    }

    @Operation(summary = "添加")
    @SaCheckRole("userMan")
    @PostMapping("/save")
    public BaseResponse<String> save(@RequestBody UserRegisterDto userRegisterDto) {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername(userRegisterDto.getUsername());
        userLoginDto.setPassword(EncryptUtils.encrypt(userRegisterDto.getPassword(), userRegisterDto.getUsername()));
        userRegisterDto.setPassword(userLoginDto.getPassword());
        if (registerService.register(userRegisterDto)) {
            return BaseResponse.success("添加成功");
        }
        return BaseResponse.fail("添加失败");
    }

    @Operation(summary = "修改")
    @SaCheckRole("userMan")
    @PostMapping(value = "/update")
    public BaseResponse<String> update(@RequestBody User user) {
        if (userService.update(user)) {
            return BaseResponse.success("修改成功");
        }
        return BaseResponse.fail("修改失败");
    }

    @Operation(summary = "删除")
    @SaCheckRole("userMan")
    @PostMapping(value = "/delete")
    public BaseResponse<String> delete(@NotBlank(message = "id不能为空") @RequestParam String id) {
        if (userService.delete(id)) {
            return BaseResponse.success("删除成功");
        } else return BaseResponse.fail("删除失败");
    }


    @Operation(summary = "根据用户名获取用户信息")
    @SaCheckRole("userMan")
    @PostMapping(value = "/getUserInfoByUsername")
    public BaseResponse<User> getUserInfoByUsername(@NotBlank(message = "用户名不能为空") @RequestParam String username) {
        return BaseResponse.success(userService.getUserByUsername(username));
    }

    @Operation(summary = "根据用户Id获取用户信息")
    @SaCheckRole("userMan")
    @PostMapping(value = "/getUserInfoByUserId")
    public BaseResponse<User> getUserInfoByUserId(@NotBlank(message = "用户Id不能为空") @RequestParam String userId) {
        return BaseResponse.success(userService.getUserById(userId));
    }

    @Operation(summary = "获取登录用户信息")
    @GetMapping("/getLoginUserInfo")
    public BaseResponse<User> getLoginUserInfo() {
        return BaseResponse.success(userService.getUserById(StpUtil.getLoginIdAsString()));
    }

    @Operation(summary = "获取用户的角色列表")
    @SaCheckPermission("userMan")
    @PostMapping(value = "/getRoleListByUserId")
    public BaseResponse<List<Role>> getRoleListByUserId(@NotBlank(message = "用户id不能为空") @RequestParam String userId) {
        return BaseResponse.success(userService.getRoleList(userId));
    }

    @Operation(summary = "保存授权角色")
    @SaCheckPermission("userMan")
    @PostMapping(value = "/saveAuthRole")
    public BaseResponse<String> saveAuthRole(@RequestBody RoleRelationDto roleRelationDto) {
        if (userService.updateRole(roleRelationDto.getUserId(), roleRelationDto.getRoleIds())) {
            return BaseResponse.success("保存成功");
        }
        return BaseResponse.fail("保存失败");
    }

    @Operation(summary = "得到Jwt")
    @SaCheckRole("getJwt")
    @PostMapping(value = "/getJwt")
    public BaseResponse<String> getJwt(JwtDto jwtDto) {
        return BaseResponse.success(JwtUtils.generate(new JwtDto(UUID.randomUUID().toString(), StpUtil.getLoginIdAsString(), StpUtil.getLoginIdAsString(), jwtDto.getExpiresAt(), "mfs", userService.getRoleListAsString(StpUtil.getLoginIdAsString()))));
    }

    @Operation(summary = "生成Jwt")
    @SaCheckRole("generateJwt")
    @PostMapping(value = "/generateJwt")
    public BaseResponse<String> generateJwt(JwtDto jwtDto) {
        return BaseResponse.success(JwtUtils.generate(new JwtDto(UUID.randomUUID().toString(), StpUtil.getLoginIdAsString(), jwtDto.getSubject(), jwtDto.getExpiresAt(), "mfs", userService.getRoleListAsString(StpUtil.getLoginIdAsString()))));
    }

}
