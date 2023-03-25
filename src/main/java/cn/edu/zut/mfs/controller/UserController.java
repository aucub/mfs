package cn.edu.zut.mfs.controller;

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
    @GetMapping("/pageList")
    public BaseResponse<Object> pageList(FindPageDto findPageDto) {
        return BaseResponse.success(userService.list(findPageDto));
    }

    @Operation(summary = "用户查询-角色")
    @GetMapping("/pageListByRoleId")
    public BaseResponse<Object> pageListByRoleId(FindPageDto findPageDto) {
        return BaseResponse.success(userService.listByRoleId(findPageDto));
    }

    @Operation(summary = "添加")
    @PostMapping("/save")
    public BaseResponse<String> save(@RequestBody UserRegisterDto userRegisterDto) {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername(userRegisterDto.getUsername());
        userLoginDto.setPassword(userRegisterDto.getPassword());
        encryptService.encrypt(userLoginDto);
        userLoginDto.setPassword(userLoginDto.getPassword());
        if(registerService.register(userRegisterDto)) {
            return BaseResponse.success("添加成功");
        }
        return BaseResponse.fail("添加失败");
    }

    @Operation(summary = "修改")
    @PostMapping(value = "/update")
    public BaseResponse<String> update(@RequestBody User user) {
        if(userService.update(user)) {
            return BaseResponse.success("修改成功");
        }
        return BaseResponse.fail("修改失败");
    }

    @Operation(summary = "删除")
    @PostMapping(value = "/delete")
    public BaseResponse<String> delete(@NotBlank(message = "id不能为空") @RequestParam String id) {
       if(userService.delete(id)) {
           return BaseResponse.success("删除成功");
       }
       else return BaseResponse.fail("删除失败");
    }


    @Operation(summary = "根据用户名获取用户信息")
    @GetMapping(value = "/getUserInfoByUsername")
    public BaseResponse<User> getUserInfoByUsername(@NotBlank(message = "用户名不能为空") @RequestParam String username) {
        return BaseResponse.success(userService.getUserByUsername(username));
    }

    @Operation(summary = "获取已授权的角色列表")
    @GetMapping(value = "/getAuthRoleListByUserId")
    public BaseResponse<List<Role>> getAuthRoleListByUserId(@NotBlank(message = "用户id不能为空") @RequestParam String userId) {
        return BaseResponse.success(userService.getRoleList(userId));
    }

    @Operation(summary = "保存授权角色")
    @PostMapping(value = "/saveAuthRole")
    public BaseResponse<String> saveAuthRole( @RequestBody RoleRelationDto roleRelationDto) {
        if(userService.updateRole(roleRelationDto.getUserId(), roleRelationDto.getRoleIds())) {
            return BaseResponse.success("保存成功");
        }
        return BaseResponse.fail("保存失败");
    }


    @Operation(summary = "修改基本信息")
    @PostMapping(value = "/updateInfo")
    public BaseResponse<String> updateInfo(@RequestBody User user) {
        if(userService.update(user)) {
            return BaseResponse.success("修改成功");
        }
        return BaseResponse.fail("修改失败");
    }

}
