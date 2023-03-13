package cn.edu.zut.mfs.service;


import cn.edu.zut.mfs.domain.User;

public interface LoginAuthService {
    Boolean login(User user);
}
