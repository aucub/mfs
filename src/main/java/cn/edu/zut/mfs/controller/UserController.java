package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.LinkLog;
import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.domain.User;
import cn.edu.zut.mfs.domain.UserLoginLog;
import cn.edu.zut.mfs.dto.*;
import cn.edu.zut.mfs.pojo.BaseResponse;
import cn.edu.zut.mfs.service.RegisterService;
import cn.edu.zut.mfs.service.UserService;
import cn.edu.zut.mfs.utils.EncryptUtils;
import cn.edu.zut.mfs.utils.JwtUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

/**
 * 用户管理
 */
@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {
    UserService userService;
    RegisterService registerService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('userMan')")
    public Mono<BaseResponse<Page<User>>> list(@RequestBody FindUserPageDto findUserPageDto) {
        return Mono.just(BaseResponse.success(userService.list(findUserPageDto)));
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('userMan')")
    public Mono<BaseResponse<String>> save(@RequestBody UserRegisterDto userRegisterDto) {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername(userRegisterDto.getUsername());
        userLoginDto.setPassword(EncryptUtils.encrypt(userRegisterDto.getPassword(), userRegisterDto.getUsername()));
        userRegisterDto.setPassword(userLoginDto.getPassword());
        if (Boolean.TRUE.equals(registerService.register(userRegisterDto))) {
            return Mono.just(BaseResponse.success("添加成功"));
        }
        return Mono.just(BaseResponse.fail("添加失败"));
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasRole('userMan')")
    public Mono<BaseResponse<String>> update(@RequestBody User user) {
        if (Boolean.TRUE.equals(userService.update(user))) {
            return Mono.just(BaseResponse.success("修改成功"));
        }
        return Mono.just(BaseResponse.fail("修改失败"));
    }

    @PostMapping(value = "/updateAccount")
    public Mono<BaseResponse<String>> update(@AuthenticationPrincipal Jwt jwt, @RequestBody User user) {
        user.setUsername(jwt.getSubject());
        if (Boolean.TRUE.equals(userService.update(user))) {
            return Mono.just(BaseResponse.success("修改成功"));
        }
        return Mono.just(BaseResponse.fail("修改失败"));
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasRole('userMan')")
    public Mono<BaseResponse<String>> delete(@RequestParam String id) {
        if (Boolean.TRUE.equals(userService.delete(id))) {
            return Mono.just(BaseResponse.success("删除成功"));
        } else return Mono.just(BaseResponse.fail("删除失败"));
    }


    @GetMapping(value = "/getUserInfoByUsername")
    @PreAuthorize("hasRole('userMan')")
    public Mono<BaseResponse<User>> getUserInfoByUsername(@RequestParam String username) {
        return Mono.just(BaseResponse.success(userService.getUserByUsername(username)));
    }


    @GetMapping(value = "/getUserInfoByUserId")
    @PreAuthorize("hasRole('userMan')")
    public Mono<BaseResponse<User>> getUserInfoByUserId(@RequestParam String userId) {
        return Mono.just(BaseResponse.success(userService.getUserById(userId)));
    }


    @GetMapping("/getLoginUserInfo")
    @PreAuthorize("hasRole('userMan')")
    public Mono<BaseResponse<User>> getLoginUserInfo(@AuthenticationPrincipal Jwt jwt) {
        return Mono.just(BaseResponse.success(userService.getUserById(jwt.getSubject())));
    }


    @GetMapping(value = "/getRoleListByUserId")
    @PreAuthorize("hasRole('userMan')")
    public Mono<BaseResponse<List<Role>>> getRoleListByUserId(@RequestParam String userId) {
        return Mono.just(BaseResponse.success(userService.getRoleList(userId)));
    }


    @PostMapping(value = "/saveAuthRole")
    @PreAuthorize("hasRole('userMan')")
    public Mono<BaseResponse<String>> saveAuthRole(@RequestBody RoleRelationDto roleRelationDto) {
        if (Boolean.TRUE.equals(userService.updateRole(roleRelationDto.getUserId(), roleRelationDto.getRoleIds()))) {
            return Mono.just(BaseResponse.success("保存成功"));
        }
        return Mono.just(BaseResponse.fail("保存失败"));
    }

    @PostMapping(value = "/generateJwt")
    @PreAuthorize("hasRole('userMan')")
    public Mono<BaseResponse<String>> generateJwt(@AuthenticationPrincipal Jwt jwt, @RequestBody JwtDto jwtDto) {
        return Mono.just(BaseResponse.success(JwtUtils.generate(new JwtDto(UUID.randomUUID().toString(), jwt.getSubject(), jwtDto.getSubject(), jwtDto.getExpiresAt(), "mfs", userService.getRoleListAsString(jwt.getSubject())))));
    }


    @GetMapping(value = "/connectList")
    @PreAuthorize("hasRole('userMan')")
    public Mono<BaseResponse<List<User>>> connectList() {
        return Mono.just(BaseResponse.success(userService.connectList()));
    }

    @PostMapping(value = "/getUserLoginLogList")
    @PreAuthorize("hasRole('userMan')")
    public Mono<BaseResponse<Page<UserLoginLog>>> getUserLoginLogList(@RequestBody FindPageDto findPageDto) {
        return Mono.just(BaseResponse.success(userService.getUserLoginLogList(findPageDto)));
    }

    @PostMapping(value = "/getLinkLogList")
    @PreAuthorize("hasRole('userMan')")
    public Mono<BaseResponse<Page<LinkLog>>> getLinkLogList(@RequestBody FindPageDto findPageDto) {
        return Mono.just(BaseResponse.success(userService.getLinkLogList(findPageDto)));
    }
}
