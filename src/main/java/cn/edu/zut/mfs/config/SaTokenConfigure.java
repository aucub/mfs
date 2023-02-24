package cn.edu.zut.mfs.config;

import cn.dev33.satoken.strategy.SaStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * [Sa-Token 权限认证] 配置类
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    /* *//**
     * 注册 Sa-Token 拦截器打开注解鉴权功能
     *//*
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器打开注解鉴权功能
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 指定一条 match 规则
            SaRouter
                    .match("/user/**")    // 拦截的 path 列表，可以写多个
                    .notMatch("/user/doLogin", "/user/isLogin","/test","rsocket")     // 排除掉的 path 列表，可以写多个
                    .check(r -> StpUtil.checkLogin());        // 要执行的校验动作，可以写完整的 lambda 表达式

            // 权限校验 -- 不同模块认证不同权限
            SaRouter.match("/user/**", r -> StpUtil.checkPermission("user"));

        })).addPathPatterns("/**");

    }

    *//**
     * 注册 [Sa-Token全局过滤器]
     *//*
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 指定 [拦截路由]
                .addInclude("")    *//* 拦截所有path *//*
                // 指定 [放行路由]
                .addExclude("/favicon.ico", "/user/doLogin", "/user/isLogin","/test","rsocket")
                // 指定[认证函数]: 每次请求执行
                .setAuth(obj -> {
                    SaRouter.match("/user/**", () -> StpUtil.checkLogin());
                })
                // 指定[异常处理函数]：每次[认证函数]发生异常时执行此函数
                .setError(e -> {
                    return SaResult.error(e.getMessage());
                })
                ;
    }*/

    /**
     * 重写 Sa-Token 框架内部算法策略
     */
    @Autowired
    public void rewriteSaStrategy() {
        // 重写Sa-Token的注解处理器，增加注解合并功能
        SaStrategy.me.getAnnotation = AnnotatedElementUtils::getMergedAnnotation;
    }

}
