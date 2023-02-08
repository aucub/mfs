package cn.edu.zut.mfs.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.edu.zut.mfs.config.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "UserController")
@RestController
@RequestMapping("/user/")
public class UserController {

    @ApiOperation("用户登录")
    // 测试登录，浏览器访问： http://localhost:8081/user/doLogin?username=zhang&password=123456
    @RequestMapping("doLogin")
    public BaseResponse<String> doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if ("zhang".equals(username) && "123456".equals(password)) {
            // 1、先检查此账号是否已被封禁
            StpUtil.checkDisable(username);
            StpUtil.login(10001);
            log.info(username + "登录成功");
            // 获取 Token  相关参数
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return BaseResponse.success("登录成功,token:" + tokenInfo);
        }
        return BaseResponse.fail("9999", "登录失败");
    }

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @RequestMapping("isLogin")
    public BaseResponse<String> isLogin() {
        // StpUtil.isLogin() 查询当前客户端是否登录，返回 true 或 false
        boolean isLogin = StpUtil.isLogin();
        return BaseResponse.success("当前会话是否登录：" + isLogin);
    }

    // 注销登录，浏览器访问： http://localhost:8081/user/logout
    @RequestMapping("logout")
    public BaseResponse<String> logout() {
        // 退出登录会清除三个地方的数据：
        // 		1、Redis中保存的 Token 信息
        // 		2、当前请求上下文中保存的 Token 信息
        // 		3、Cookie 中保存的 Token 信息（如果未使用Cookie模式则不会清除）
        StpUtil.logout();
        // StpUtil.logout() 在未登录时也是可以调用成功的，
        // 也就是说，无论客户端有没有登录，执行完 StpUtil.logout() 后，都会处于未登录状态
        log.info("当前是否处于登录状态：" + StpUtil.isLogin());
        return BaseResponse.success("注销登录成功");
    }

    // 检验当前会话是否已经登录，浏览器访问： http://localhost:8081/user/checkLogin
    @RequestMapping("checkLogin")
    public BaseResponse<String> checkLogin() {
        // 检验当前会话是否已经登录, 如果未登录，则抛出异常：`NotLoginException`
        StpUtil.checkLogin();
        // 抛出异常后，代码将走入全局异常处理（GlobalException.java），如果没有抛出异常，则代表通过了登录校验，返回下面信息
        return BaseResponse.success("当前已登录");
    }

    // 获取当前会话账号id，浏览器访问： http://localhost:8081/user/getLoginIdAsString
    @RequestMapping("getLoginIdAsString")
    public BaseResponse<String> getLoginIdAsString() {
        // 获取当前会话账号id, 如果未登录，则抛出异常：`NotLoginException`
        String userId = StpUtil.getLoginIdAsString();
        return BaseResponse.success("当前客户端登录的账号id是：" + userId);
    }

    // 封禁指定账号  ---- http://localhost:8081/disable/disable?userId=10001
    @RequestMapping("disable")
    public BaseResponse<String> disable(long userId) {
        /*
         * 账号封禁：
         * 	参数1：要封禁的账号id
         * 	参数2：要封禁的时间，单位：秒，86400秒=1天
         */
        StpUtil.disable(userId, 86400);
        return BaseResponse.success("账号 " + userId + " 封禁成功");
    }

    // 解封指定账号  ---- http://localhost:8081/disable/untieDisable?userId=10001
    @RequestMapping("untieDisable")
    public BaseResponse<String> untieDisable(long userId) {
        StpUtil.untieDisable(userId);
        return BaseResponse.success("账号 " + userId + " 解封成功");
    }

}