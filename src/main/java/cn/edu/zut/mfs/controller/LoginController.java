package cn.edu.zut.mfs.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.EncryptService;
import cn.edu.zut.mfs.service.LoginAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sa-Token 用户登录
 */
@Slf4j
@RestController
@RequestMapping("/user/")
@Tag(name = "用户登录")
public class LoginController {
    LoginAuthService loginAuthService;

    EncryptService encryptService;

    @Autowired
    public void setLoginAuthService(LoginAuthService loginAuthService) {
        this.loginAuthService = loginAuthService;
    }

    @Autowired
    public void setEncryptService(EncryptService encryptService) {
        this.encryptService = encryptService;
    }

    @Operation(summary = "获取公钥")
    @GetMapping("getPublicKey")
    public BaseResponse<String> getPublicKey() {
        return BaseResponse.success(encryptService.getPublicKey());
    }

    @Operation(summary = "登录")
    @PostMapping("doLogin")
    public BaseResponse<String> doLogin(String username, String publicKey, String password) {
        password = encryptService.transformer(username, publicKey, password);
        if (loginAuthService.login(username, password)) {
            // 先检查此账号是否已被封禁
            StpUtil.checkDisable(username);
            StpUtil.login(username);
            log.info(username + "登录成功");
            // 获取 Token  相关参数，这里需要把 Token 信息从响应体中返回到前端
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return BaseResponse.success("登录成功,token:" + tokenInfo);
        }
        return BaseResponse.fail("登录失败");
    }

    @Operation(summary = "记住我登录")
    @PostMapping("doLogin1")
    public BaseResponse<String> doLogin1(String username, String publicKey, String password) {
        password = encryptService.transformer(username, publicKey, password);
        if (loginAuthService.login(username, password)) {
            StpUtil.checkDisable(username);
            StpUtil.login(username, true);
            log.info(username + "登录成功");
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return BaseResponse.success("登录成功,token:" + tokenInfo);
        }
        return BaseResponse.fail("登录失败");
    }

    @Operation(summary = "不记住我登录")
    @PostMapping("doLogin2")
    public BaseResponse<String> doLogin2(String username, String publicKey, String password) {
        password = encryptService.transformer(username, publicKey, password);
        if (loginAuthService.login(username, password)) {
            StpUtil.checkDisable(username);
            StpUtil.login(username, false);
            log.info(username + "登录成功");
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return BaseResponse.success("登录成功,token:" + tokenInfo);
        }
        return BaseResponse.fail("登录失败");
    }

    @Operation(summary = "七天免登录")
    @PostMapping("doLogin3")
    public BaseResponse<String> doLogin3(String username, String publicKey, String password) {
        password = encryptService.transformer(username, publicKey, password);
        if (loginAuthService.login(username, password)) {
            StpUtil.checkDisable(username);
            StpUtil.login(username, 60 * 60 * 24 * 7);
            log.info(username + "登录成功");
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return BaseResponse.success("登录成功,token:" + tokenInfo);
        }
        return BaseResponse.fail("登录失败");
    }


    @Operation(summary = "查询登录状态")
    @GetMapping("isLogin")
    public BaseResponse<String> isLogin() {
        // StpUtil.isLogin() 查询当前客户端是否登录，返回 true 或 false
        boolean isLogin = StpUtil.isLogin();
        return BaseResponse.success("当前会话是否登录：" + isLogin);
    }

    @Operation(summary = "注销登录")
    @GetMapping("logout")
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

    @Operation(summary = "检验当前会话是否已经登录")
    @GetMapping("checkLogin")
    public BaseResponse<String> checkLogin() {
        // 检验当前会话是否已经登录, 如果未登录，则抛出异常：`NotLoginException`
        StpUtil.checkLogin();
        // 抛出异常后，代码将走入全局异常处理（GlobalException.java），如果没有抛出异常，则代表通过了登录校验，返回下面信息
        return BaseResponse.success("当前已登录");
    }

    @Operation(summary = "获取当前会话id")
    @GetMapping("getLoginIdAsString")
    public BaseResponse<String> getLoginIdAsString() {
        // 获取当前会话账号id, 如果未登录，则抛出异常：`NotLoginException`
        String username = StpUtil.getLoginIdAsString();
        return BaseResponse.success("当前客户端登录的账号是：" + username);
    }

}