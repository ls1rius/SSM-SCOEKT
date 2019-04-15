package com.cm.dao;

import com.cm.entity.User;

public interface IUserDao {
    User getUser(int userID);

    User getUserByName(String username);
    //判断用户是否已经注册
    int userLogin(User user);

    void userRegister(User user);
}