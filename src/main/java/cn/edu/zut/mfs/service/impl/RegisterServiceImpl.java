package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.dao.RegisterDao;
import cn.edu.zut.mfs.domain.User;
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
    public Boolean register(String username, String password) {
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
            throw new BaseException("账号或密码格式错误");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        if (registerDao.selectByMap(params).isEmpty()) {
            return registerDao.insert(new User(username, password)) == 1;
        } else throw new BaseException("账户已存在");
    }
}
