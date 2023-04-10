package cn.edu.zut.mfs.config;

import cn.edu.zut.mfs.service.LoginAuthService;
import cn.edu.zut.mfs.service.UserService;
import cn.edu.zut.mfs.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Configuration
@Service
public class UserDetailsService implements ReactiveUserDetailsService{

    private LoginAuthService loginAuthService;
    private UserService userService;

    @Autowired
    public void setLoginAuthService(LoginAuthService loginAuthService) {
        this.loginAuthService = loginAuthService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public Mono<UserDetails> findByUsername(String username) {
        return Mono.just(new User(username, EncryptUtils.decrypt(loginAuthService.getPassword(username), username), userService.getPermissions(username)));
    }
}
