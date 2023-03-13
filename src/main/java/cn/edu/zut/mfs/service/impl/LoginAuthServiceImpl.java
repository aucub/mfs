package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.dao.LoginDao;
import cn.edu.zut.mfs.exception.BaseException;
import cn.edu.zut.mfs.service.LoginAuthService;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class LoginAuthServiceImpl implements LoginAuthService {

    LoginDao loginDao;

    @Autowired
    public void setLoginDao(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    @Override
    public Boolean login(String username, String password) {
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
            throw new BaseException("");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        if (!loginDao.selectByMap(params).isEmpty()) {
            return true;
        } else throw new BaseException("");
    }
}
