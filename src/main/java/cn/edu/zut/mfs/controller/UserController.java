package cn.edu.zut.mfs.controller;

import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.domain.User;
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
    public BaseResponse<Page<User>> list(@RequestBody FindUserPageDto findUserPageDto) {
        return BaseResponse.success(userService.list(findUserPageDto));
    }

    @PostMapping("/save")
    public BaseResponse<String> save(@RequestBody UserRegisterDto userRegisterDto) {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername(userRegisterDto.getUsername());
        userLoginDto.setPassword(EncryptUtils.encrypt(userRegisterDto.getPassword(), userRegisterDto.getUsername()));
        userRegisterDto.setPassword(userLoginDto.getPassword());
        if (registerService.register(userRegisterDto)) {
            return BaseResponse.success("添加成功");
        }
        return BaseResponse.fail("添加失败");
    }

    @PostMapping(value = "/update")
    public BaseResponse<String> update(@RequestBody User user) {
        if (userService.update(user)) {
            return BaseResponse.success("修改成功");
        }
        return BaseResponse.fail("修改失败");
    }

    @PostMapping(value = "/delete")
    public BaseResponse<String> delete(@RequestParam String id) {
        if (userService.delete(id)) {
            return BaseResponse.success("删除成功");
        } else return BaseResponse.fail("删除失败");
    }


    @PostMapping(value = "/getUserInfoByUsername")
    public BaseResponse<User> getUserInfoByUsername(@RequestParam String username) {
        return BaseResponse.success(userService.getUserByUsername(username));
    }


    @PostMapping(value = "/getUserInfoByUserId")
    public BaseResponse<User> getUserInfoByUserId(@RequestParam String userId) {
        return BaseResponse.success(userService.getUserById(userId));
    }


    @GetMapping("/getLoginUserInfo")
    @PreAuthorize("hasRole('userMan')")
    public Mono<BaseResponse<User>> getLoginUserInfo(@AuthenticationPrincipal Jwt jwt) {
        System.out.println(jwt.getSubject());
        //return BaseResponse.success(userService.getUserById());
        return Mono.just(BaseResponse.success(new User()));
    }


    @PostMapping(value = "/getRoleListByUserId")
    public BaseResponse<List<Role>> getRoleListByUserId(@RequestBody String userId) {
        return BaseResponse.success(userService.getRoleList(userId));
    }


    @PostMapping(value = "/saveAuthRole")
    public BaseResponse<String> saveAuthRole(@RequestBody RoleRelationDto roleRelationDto) {
        if (userService.updateRole(roleRelationDto.getUserId(), roleRelationDto.getRoleIds())) {
            return BaseResponse.success("保存成功");
        }
        return BaseResponse.fail("保存失败");
    }

    @PostMapping(value = "/generateJwt")
    public BaseResponse<String> generateJwt(@AuthenticationPrincipal Jwt jwt, JwtDto jwtDto) {
        return BaseResponse.success(JwtUtils.generate(new JwtDto(UUID.randomUUID().toString(), jwt.getSubject(), jwtDto.getSubject(), jwtDto.getExpiresAt(), "mfs", userService.getRoleListAsString(jwt.getSubject()))));
    }

}
