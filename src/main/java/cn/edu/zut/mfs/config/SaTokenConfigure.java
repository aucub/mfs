package cn.edu.zut.mfs.config;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.strategy.SaStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * [Sa-Token 权限认证] 配置类
 */
@Configuration
@Slf4j
public class SaTokenConfigure {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf().disable();
        http.httpBasic().disable();
        http.cors().disable();
        http.formLogin().disable();
        http.logout().disable();
        http.anonymous().disable();
        http.headers().disable();
        return http.build();
    }

    /**
     * sa-token全局过滤器
     */
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 指定 [拦截路由]
                .addInclude("/**")
                // 指定 [放行路由]
                .addExclude("/favicon.ico", "/v3/**", "/rsocket/**")
                // 指定[认证函数]: 每次请求执行
                .setAuth(r -> {
                    log.info("---------- sa全局认证 ----------");
                    SaRouter.match("/**", "", () -> StpUtil.checkLogin());
                })
                // 指定[异常处理函数]：每次[认证函数]发生异常时执行此函数
                .setError(e -> {
                    log.error("---------- sa全局异常 ----------");
                    e.printStackTrace();
                    return e.getMessage();
                })
                ;
    }

    /**
     * 重写 Sa-Token 框架内部算法策略
     */
    @Autowired
    public void rewriteSaStrategy() {
        // 重写Sa-Token的注解处理器，增加注解合并功能
        SaStrategy.me.getAnnotation = AnnotatedElementUtils::getMergedAnnotation;
    }

}
