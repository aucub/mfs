package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.dao.RegisterDao;
import cn.edu.zut.mfs.dto.UserRegisterDto;
import cn.edu.zut.mfs.exception.BaseException;
import cn.edu.zut.mfs.service.RegisterService;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class RegisterServiceImpl implements RegisterService {
    RegisterDao registerDao;

    @Autowired
    public void setRegisterDao(RegisterDao registerDao) {
        this.registerDao = registerDao;
    }

    @Override
    public Boolean register(UserRegisterDto userRegisterDto) {
        if (StrUtil.isEmpty(userRegisterDto.getUsername()) || StrUtil.isEmpty(userRegisterDto.getUsername())) {
            throw new BaseException("账号或密码格式错误");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("username", userRegisterDto.getUsername());
        if (registerDao.selectByMap(params).isEmpty()) {
            // return registerDao.insert(userRegisterDto) == 1;
            return registerDao.insert(userRegisterDto) == 1;
        } else throw new BaseException("账户已存在");
    }
}
