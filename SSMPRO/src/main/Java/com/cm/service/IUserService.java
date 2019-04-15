package com.cm.service;

import com.cm.entity.User;

public interface IUserService {

    User getUser(int id);

    User getUserByName(String username);
    //查询用户是否已经注册
    int userLogin(User user);

    void userRegister(User user);

}