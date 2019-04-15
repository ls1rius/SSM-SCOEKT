package com.cm.service.impl;

import com.cm.dao.IUserDao;
import com.cm.entity.User;
import com.cm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;

    @Override
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public int userLogin(User user){
        /*
        如果登录成功的话，count的返回值大于0
         */
        int flag = 0;
        flag = userDao.userLogin(user);
        return flag;
    }
    @Override
    public User getUserByName(String username) {
        return userDao.getUserByName(username);
    }

    @Override
    public void userRegister(User user){
        userDao.userRegister(user);
    }

}
