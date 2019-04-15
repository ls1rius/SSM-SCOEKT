package com.cm.controller;
import com.cm.entity.User;
import com.cm.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;


@CrossOrigin(origins = "*",maxAge = 3600)
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource(name="userService")
    private IUserService userService;

    @RequestMapping("getUser")
    @ResponseBody
    public User getUser(int userID) {
        return userService.getUser(userID);
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginUser(String username, String password, HttpSession session) {
        System.out.println(username);
        ModelAndView modelAndView=null;
        User user = new User(username,password);
        int result = userService.userLogin(user);
        if(result > 0){
            User userGet = userService.getUserByName(user.getUsername());
            session.setAttribute("user",userGet);
            modelAndView=new ModelAndView("chat");
        }else{
            modelAndView=new ModelAndView("redirect:register");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/register" ,method = RequestMethod.POST)
    public ModelAndView register(String userName,String password ){
        ModelAndView modelAndView=null;
        User user = new User();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setUsername(userName);
        user.setPassword(password);
        user.setRegisterDate(dateFormat.format(new Date()));
        userService.userRegister(user);
        modelAndView=new ModelAndView("redirect:register");
        return modelAndView;
    }

}
