package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.domain.RoleRelation;
import cn.edu.zut.mfs.domain.User;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.UserService;
import cn.edu.zut.mfs.vo.FindPageVo;
import cn.edu.zut.mfs.vo.UserLoginVo;
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

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "用户查询")
    @GetMapping("/pageList")
    public BaseResponse<Object> pageList(FindPageVo findPageVo) {
        return BaseResponse.success(userService.list(findPageVo));
    }

    @Operation(summary = "添加")
    @PostMapping("/save")
    public BaseResponse<String> save(@RequestBody User user) {
        return null;
    }

    @Operation(summary = "修改")
    @PostMapping(value = "/update")
    public BaseResponse<String> update(@RequestBody User user) {
        return null;
    }

    @Operation(summary = "删除")
    @PostMapping(value = "/delete")
    public BaseResponse<String> delete(@NotBlank(message = "id不能为空") @RequestParam String id) {
       if(userService.delete(id)) {
           return BaseResponse.success("删除成功");
       }
       else return BaseResponse.fail("删除失败");
    }

    @Operation(summary = "重置密码")
    @PostMapping(value = "/resetPassword")
    public BaseResponse<String> resetPassword( @RequestBody UserLoginVo userLoginVo) {
        return null;
    }

    @Operation(summary = "根据用户名获取用户信息")
    @GetMapping(value = "/getUserInfoByUsername")
    public BaseResponse<User> getUserInfoByUsername(@NotBlank(message = "用户名不能为空") @RequestParam String username) {
        return null;
    }

    @Operation(summary = "获取已授权的角色列表")
    @GetMapping(value = "/getAuthRoleListByUserId")
    public BaseResponse<List<Role>> getAuthRoleListByUserId(@NotBlank(message = "用户id不能为空") @RequestParam String userId) {
        return BaseResponse.success(userService.getRoleList(userId));
    }

    @Operation(summary = "保存授权角色")
    @PostMapping(value = "/saveAuthRole")
    public BaseResponse<String> saveAuthRole( @RequestBody RoleRelation roleRelation) {
        return null;
    }


    @Operation(summary = "修改基本信息")
    @PostMapping(value = "/updateInfo")
    public BaseResponse<String> updateInfo(@RequestBody User user) {
        return null;
    }

    @Operation(summary = "修改密码")
    @PostMapping("/updatePass")
    public BaseResponse<String> updatePass( @RequestBody UserLoginVo userLoginVo) {
        return null;
    }


}
