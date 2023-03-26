package cn.edu.zut.mfs.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.domain.User;
import cn.edu.zut.mfs.dto.RoleRelationDto;
import cn.edu.zut.mfs.dto.UserRegisterDto;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.EncryptService;
import cn.edu.zut.mfs.service.RegisterService;
import cn.edu.zut.mfs.service.UserService;
import cn.edu.zut.mfs.dto.FindPageDto;
import cn.edu.zut.mfs.dto.UserLoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 用户管理
 */
@Slf4j
@RequestMapping("/user")
@Tag(name = "用户管理")
@RestController
public class UserController {
    UserService userService;
    EncryptService encryptService;
    RegisterService registerService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEncryptService(EncryptService encryptService) {
        this.encryptService = encryptService;
    }

    @Autowired
    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Operation(summary = "用户查询")
    @SaCheckPermission("user:pageList")
    @PostMapping("/pageList")
    public BaseResponse<Object> pageList(@RequestBody FindPageDto findPageDto) {
        return BaseResponse.success(userService.list(findPageDto));
    }

    @Operation(summary = "用户查询-角色")
    @SaCheckPermission("user:pageListByRoleId")
    @PostMapping("/pageListByRoleId")
    public BaseResponse<Object> pageListByRoleId(@RequestBody FindPageDto findPageDto) {
        return BaseResponse.success(userService.listByRoleId(findPageDto));
    }

    @Operation(summary = "添加")
    @SaCheckPermission("user:save")
    @PostMapping("/save")
    public BaseResponse<String> save(@RequestBody UserRegisterDto userRegisterDto) {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername(userRegisterDto.getUsername());
        userLoginDto.setPassword(userRegisterDto.getPassword());
        encryptService.encrypt(userLoginDto);
        userRegisterDto.setPassword(userLoginDto.getPassword());
        if(registerService.register(userRegisterDto)) {
            return BaseResponse.success("添加成功");
        }
        return BaseResponse.fail("添加失败");
    }

    @Operation(summary = "修改")
    @SaCheckPermission("user:update")
    @PostMapping(value = "/update")
    public BaseResponse<String> update(@RequestBody User user) {
        if(userService.update(user)) {
            return BaseResponse.success("修改成功");
        }
        return BaseResponse.fail("修改失败");
    }

    @Operation(summary = "删除")
    @SaCheckPermission("user:delete")
    @PostMapping(value = "/delete")
    public BaseResponse<String> delete(@NotBlank(message = "id不能为空") @RequestBody String id) {
       if(userService.delete(id)) {
           return BaseResponse.success("删除成功");
       }
       else return BaseResponse.fail("删除失败");
    }


    @Operation(summary = "根据用户名获取用户信息")
    @SaCheckPermission("user:getUserInfoByUsername")
    @PostMapping(value = "/getUserInfoByUsername")
    public BaseResponse<User> getUserInfoByUsername(@NotBlank(message = "用户名不能为空") @RequestParam String username) {
        return BaseResponse.success(userService.getUserByUsername(username));
    }

    @Operation(summary = "获取用户的角色列表")
    @SaCheckPermission("user:getRoleListByUserId")
    @PostMapping(value = "/getRoleListByUserId")
    public BaseResponse<List<Role>> getRoleListByUserId(@NotBlank(message = "用户id不能为空") @RequestParam String userId) {
        return BaseResponse.success(userService.getRoleList(userId));
    }

    @Operation(summary = "保存授权角色")
    @SaCheckPermission("user:saveAuthRole")
    @PostMapping(value = "/saveAuthRole")
    public BaseResponse<String> saveAuthRole( @RequestBody RoleRelationDto roleRelationDto) {
        if(userService.updateRole(roleRelationDto.getUserId(), roleRelationDto.getRoleIds())) {
            return BaseResponse.success("保存成功");
        }
        return BaseResponse.fail("保存失败");
    }

}
