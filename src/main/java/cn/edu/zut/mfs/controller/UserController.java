package cn.edu.zut.mfs.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.edu.zut.mfs.config.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "UserController", description = "用户管理")
@RestController
@RequestMapping("/user/")
public class UserController {

    @ApiOperation("用户登录")
    // 测试登录，浏览器访问： http://localhost:8081/user/doLogin?username=zhang&password=123456
    @RequestMapping("doLogin")
    public BaseResponse<String> doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if ("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            log.info(username + "登录成功");
            return BaseResponse.success("登录成功");
        }
        return BaseResponse.fail("9999", "登录失败");
    }

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

}