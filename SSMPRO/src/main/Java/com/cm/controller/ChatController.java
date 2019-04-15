package com.cm.controller;

import com.cm.entity.ChatMsg;
import com.cm.service.IChatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/chat")
public class ChatController {

    @Resource(name="chatService")
    private IChatService chatService;


    @RequestMapping(value = "/findMsg", method = RequestMethod.POST)
    public ModelAndView findMsg(String conSearch, HttpSession session) {

        List<ChatMsg> listMsg = chatService.findMsgList(conSearch);
        session.setAttribute("msgList",listMsg);

        chatService.testMsg();
        ModelAndView modelAndView = new ModelAndView("chatHistory");
        return modelAndView;
    }
}