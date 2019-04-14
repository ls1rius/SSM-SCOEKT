package com.cm.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@CrossOrigin(origins = "*",maxAge = 3600)
@Controller
@RequestMapping(value = "/user")
@ResponseBody
public class UserController {

}
