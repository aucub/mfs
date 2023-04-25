package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.dao.LinkLogDao;
import cn.edu.zut.mfs.dao.LoginDao;
import cn.edu.zut.mfs.dao.UserLoginLogDao;
import cn.edu.zut.mfs.domain.LinkLog;
import cn.edu.zut.mfs.domain.UserLoginLog;
import cn.edu.zut.mfs.dto.UserLoginDto;
import cn.edu.zut.mfs.exception.BaseException;
import cn.edu.zut.mfs.service.LoginAuthService;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class LoginAuthServiceImpl implements LoginAuthService {

    private LoginDao loginDao;
    private UserLoginLogDao userLoginLogDao;

    private LinkLogDao linkLogDao;

    @Autowired
    public void setLinkLogDao(LinkLogDao linkLogDao) {
        this.linkLogDao = linkLogDao;
    }

    @Autowired
    public void setLoginDao(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    @Autowired
    public void setUserLoginLogDao(UserLoginLogDao userLoginLogDao) {
        this.userLoginLogDao = userLoginLogDao;
    }

    public Boolean save(UserLoginLog userLoginLog) {
        return userLoginLogDao.insert(userLoginLog) == 1;
    }

    @Override
    public Boolean login(UserLoginDto userLoginDto) {
        if (!loginDao.selectByMap(set(userLoginDto)).isEmpty()) {
            return true;
        } else throw new BaseException("账号或密码错误");
    }

    public Map<String, Object> set(UserLoginDto userLoginDto) {
        if (CharSequenceUtil.isEmpty(userLoginDto.getUsername()) || CharSequenceUtil.isEmpty(userLoginDto.getPassword())) {
            throw new BaseException("账号或密码格式错误");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("username", userLoginDto.getUsername());
        params.put("password", userLoginDto.getPassword());
        return params;
    }


    @Override
    public Boolean updatePassword(UserLoginDto userLoginDto) {
        if (loginDao.selectByMap(set(userLoginDto)).isEmpty()) {
            return loginDao.updateById(userLoginDto) == 1;
        } else throw new BaseException("密码格式错误");
    }


    @Override
    public Boolean addLoginLog(UserLoginLog userLoginLog) {
        return userLoginLogDao.insert(userLoginLog) == 1;
    }

    @Override
    public Boolean addLinkLog(LinkLog linkLog) {
        return linkLogDao.insert(linkLog) == 1;
    }
}
