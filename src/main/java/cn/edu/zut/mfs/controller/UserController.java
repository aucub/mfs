package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.domain.RoleRelation;
import cn.edu.zut.mfs.domain.User;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.vo.UserLoginVo;
import cn.hutool.db.PageResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class UserController {
    @Operation(summary = "用户分页查询")
    @GetMapping("/pageList")
    public BaseResponse<PageResult<User>> pageList(User user) {
        return null;
    }

    @Operation(summary = "添加")
    @ApiOperationSupport(order = 5, author = "dcy")
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
        return null;
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
        return null;
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
