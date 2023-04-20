package cn.edu.zut.mfs.interfaceImpl;


import cn.dev33.satoken.stp.StpInterface;
import cn.edu.zut.mfs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限认证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 查询权限list
        List<String> list = new ArrayList<String>();
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 查询角色list
        List<String> list = new ArrayList<String>();
        list.addAll(userService.getRoleListAsString((String) loginId));
        return list;
    }

}

