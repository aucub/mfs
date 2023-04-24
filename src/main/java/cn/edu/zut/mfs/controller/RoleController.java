package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色管理
 */
@Slf4j
@RequestMapping("/role")
@RestController
public class RoleController {
    RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(value = "/list")
    public BaseResponse<List<Role>> list() {
        return BaseResponse.success(roleService.list());
    }

}
